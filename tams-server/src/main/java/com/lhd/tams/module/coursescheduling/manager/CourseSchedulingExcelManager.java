package com.lhd.tams.module.coursescheduling.manager;

import com.lhd.tams.common.consts.SheetNaingTypeEnum;
import com.lhd.tams.common.exception.BusinessException;
import com.lhd.tams.common.util.CollectionUtils;
import com.lhd.tams.module.coursescheduling.dao.CourseSchedulingMapper;
import com.lhd.tams.module.coursescheduling.model.dto.CourseSchedulingExportDTO;
import com.lhd.tams.module.coursescheduling.model.vo.CourseSchedulingExportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CourseSchedulingExcelManager {

    private static final String FONT_NAME = "等线";
    private static final Map<DayOfWeek, String> DAY_OF_WEEK_STRING_MAP;
    static {
        DAY_OF_WEEK_STRING_MAP = new HashMap<>();
        DAY_OF_WEEK_STRING_MAP.put(DayOfWeek.MONDAY, "星期一");
        DAY_OF_WEEK_STRING_MAP.put(DayOfWeek.TUESDAY, "星期二");
        DAY_OF_WEEK_STRING_MAP.put(DayOfWeek.WEDNESDAY, "星期三");
        DAY_OF_WEEK_STRING_MAP.put(DayOfWeek.THURSDAY, "星期四");
        DAY_OF_WEEK_STRING_MAP.put(DayOfWeek.FRIDAY, "星期五");
        DAY_OF_WEEK_STRING_MAP.put(DayOfWeek.SATURDAY, "星期六");
        DAY_OF_WEEK_STRING_MAP.put(DayOfWeek.SUNDAY, "星期日");
    }

    @Autowired
    private CourseSchedulingMapper courseSchedulingMapper;

    public Workbook createExcel(CourseSchedulingExportDTO dto) {

        LocalDate startDate = LocalDate.parse(dto.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(dto.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 日期map <第几周, dateList>
        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        int week = 0;
        Map<Integer, List<LocalDate>> dateMap = new HashMap<>();
        for (int i = 0; i < days; i++) {
            LocalDate date = startDate.plusDays(i);
            boolean isWeek = date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
            if (!dto.getIsShowWeek() && isWeek) {
                continue;
            }
            // 前6天中有非周一日期
            if (week == 0 && date.getDayOfWeek().getValue() > 1 && i < 6) {
                week ++;
            }
            if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
                // 计算有多少周
                week ++;
            }
            List<LocalDate> dateList = dateMap.computeIfAbsent(week, k -> new ArrayList<>());
            dateList.add(date);
        }

        // 时间map <第几周, timeList>
        Map<Integer, List<String>> timeMap = new HashMap<>();
        for (Map.Entry<Integer, List<LocalDate>> entry : dateMap.entrySet()) {
            timeMap.put(entry.getKey(), courseSchedulingMapper.selectTimePeriodByDateRange(entry.getValue(), dto.getClassroomId()));
        }

        // 课程map <date+time, course>
        List<CourseSchedulingExportVO> voList = courseSchedulingMapper.selectByDateRange(startDate, endDate, dto.getClassroomId());
        Map<String, CourseSchedulingExportVO> dataMap = new HashMap<>();
        for (CourseSchedulingExportVO vo : voList) {
            dataMap.put(vo.getDate() + vo.getTime(), vo);
        }

        try {
            // xssf
            Workbook workbook = WorkbookFactory.create(true);
            for (int i = 1; i <= dateMap.size(); i++) {
                List<LocalDate> dateList = dateMap.get(i);
                createSheet(workbook, dateList, timeMap.get(i), dataMap,
                        getSheetName(dto.getSheetNamingType(), i, dateList),
                        dto.getTitle(),
                        dto.getClassroomName());
            }
            return workbook;
        }
        catch (Exception e) {
            log.error("生成Excel异常", e);
            throw new BusinessException("生成Excel异常");
        }
    }

    private static String getSheetName(Integer sheetNamingType, int num, List<LocalDate> dateList) {
        if (SheetNaingTypeEnum.WEEK_NUM.getCode().equals(sheetNamingType)) {
            return String.format("第%s周", num);
        } else {
            return dateList.size() == 1 ?
                    dateList.get(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) :
                    String.format("%s至%s",
                            dateList.get(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            dateList.get(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
    }

    private static void createSheet(Workbook workbook,
                                    List<LocalDate> dateList, List<String> timeList, Map<String, CourseSchedulingExportVO> dataMap,
                                    String sheetName, String title, String subtitleClassroomName) {

        if (workbook == null || CollectionUtils.isAnyEmpty(dateList, timeList) || CollectionUtils.isEmpty(dataMap)) {
            return;
        }

        Sheet sheet = workbook.createSheet(sheetName);
        // 时间列
        sheet.setColumnWidth(0, 15 * 256);

        int dateRowIndex = 0;
        int dataRowIndex = 0;
        int classroomRowIndex = 0;
        int dateColumnNum = dateList.size();
        if (StringUtils.isNotEmpty(title)) {
            createTitleRow(sheet, 0, dateColumnNum + 1, title);

            dateRowIndex ++;
            dataRowIndex ++;
            classroomRowIndex ++;
        }

        if (StringUtils.isNotEmpty(subtitleClassroomName)) {
            createClassroomRow(sheet, classroomRowIndex, dateColumnNum + 1, "教室：" + subtitleClassroomName);

            dateRowIndex ++;
            dataRowIndex ++;
        }

        // 星期行
        Row dateRow = sheet.createRow(dateRowIndex);
        dateRow.setHeight((short) 800);
        CellStyle headerCellStyle = workbook.createCellStyle();
        Font headerCellFont = workbook.createFont();
        for (int i = 0; i < dateColumnNum; i++) {
            LocalDate date = dateList.get(i);
            sheet.setColumnWidth(i + 1, 20 * 256);
            createHeaderCell(dateRow,
                    i + 1,
                    String.format("%s\n%s", date, DAY_OF_WEEK_STRING_MAP.get(date.getDayOfWeek())),
                    headerCellStyle, headerCellFont);
        }

        // 数据行
        CellStyle timeCellStyle = workbook.createCellStyle();
        Font timeCellFont = workbook.createFont();
        for (int i = 0; i < timeList.size(); i++) {

            dataRowIndex ++;
            Row courseRow = sheet.createRow(dataRowIndex);
            courseRow.setHeight((short) 1000);
            // 时间列
            String time = timeList.get(i);
            createTimeCell(courseRow, 0, time, timeCellStyle, timeCellFont);

            // 数据列
            for (int j = 0; j < dateColumnNum; j++) {

                CourseSchedulingExportVO vo = dataMap.get(dateList.get(j) + time);
                if (vo != null) {
                    createDataCell(courseRow, j + 1, vo, StringUtils.isEmpty(subtitleClassroomName));
                }
            }
        }
    }

    private static void createTitleRow(Sheet sheet, int index, int totalColumnNum, String value) {

        CellRangeAddress region = new CellRangeAddress(index, index, 0, totalColumnNum - 1);
        sheet.addMergedRegion(region);

        Row row = sheet.createRow(index);
        row.setHeight((short) 800);

        Workbook workbook = sheet.getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Font font = workbook.createFont();
        font.setFontName(FONT_NAME);
        font.setFontHeightInPoints((short) 14);
        cellStyle.setFont(font);

        CellUtil.createCell(row, 0, value, cellStyle);
    }


    private static void createClassroomRow(Sheet sheet, int index, int totalColumnNum, String value) {

        CellRangeAddress region = new CellRangeAddress(index, index, 0, totalColumnNum - 1);
        sheet.addMergedRegion(region);

        Row row = sheet.createRow(index);
        row.setHeight((short) 800);

        Workbook workbook = sheet.getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Font font = workbook.createFont();
        font.setFontName(FONT_NAME);
        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);

        CellUtil.createCell(row, 0, value, cellStyle);
    }

    private static void createHeaderCell(Row row, int index, String value, CellStyle cellStyle, Font font) {

        font.setFontName(FONT_NAME);
        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);

        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        CellUtil.createCell(row, index, value, cellStyle);
    }

    private static void createTimeCell(Row row, int index, String value, CellStyle cellStyle, Font font) {

        font.setFontName(FONT_NAME);
        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        CellUtil.createCell(row, index, value, cellStyle);
    }

    private static void createDataCell(Row row, int index, CourseSchedulingExportVO vo, boolean isShowClassroom) {
        if (vo != null) {
            // 不同单元格可能需要不同的样式，因此为每个单元格创建单独样式
            Workbook workbook = row.getSheet().getWorkbook();
            CellStyle cellStyle = workbook.createCellStyle();

            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setWrapText(true);

            if (StringUtils.isNotEmpty(vo.getBackgroundColor())) {
                XSSFColor xssfColor = new XSSFColor(java.awt.Color.decode(vo.getBackgroundColor()) , new DefaultIndexedColorMap());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                ((XSSFCellStyle) cellStyle).setFillForegroundColor(xssfColor);
            }

            setBorderStyle(cellStyle, BorderStyle.THIN, IndexedColors.GREY_25_PERCENT);

            Font courseFont = workbook.createFont();
            courseFont.setFontName(FONT_NAME);
            courseFont.setFontHeightInPoints((short) 16);
            courseFont.setColor(IndexedColors.WHITE.index);
            Font otherFont = workbook.createFont();
            otherFont.setFontName(FONT_NAME);
            otherFont.setFontHeightInPoints((short) 12);
            otherFont.setColor(IndexedColors.WHITE.index);

            String course = vo.getCourseName();
            String otherInfo = isShowClassroom ? String.format("%s %s", vo.getClassroomName(), vo.getTeacherName()) : vo.getTeacherName();
            String value = String.format("%s\n%s", course, otherInfo);
            RichTextString richTextString = new XSSFRichTextString(value);
            richTextString.applyFont(0, course.length(), courseFont);
            richTextString.applyFont(course.length(), value.length(), otherFont);

            Cell cell = row.createCell(index);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(richTextString);
        }


    }

    private static void setBorderStyle(CellStyle style, BorderStyle borderStyle, IndexedColors borderColor) {
        style.setBorderTop(borderStyle);
        style.setTopBorderColor(borderColor.index);
        style.setBorderBottom(borderStyle);
        style.setBottomBorderColor(borderColor.index);
        style.setBorderLeft(borderStyle);
        style.setLeftBorderColor(borderColor.index);
        style.setBorderRight(borderStyle);
        style.setRightBorderColor(borderColor.index);
    }
}
