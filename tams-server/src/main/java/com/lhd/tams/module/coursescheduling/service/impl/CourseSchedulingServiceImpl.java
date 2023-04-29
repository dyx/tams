package com.lhd.tams.module.coursescheduling.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhd.tams.common.consts.ErrorCodeEnum;
import com.lhd.tams.common.exception.BusinessException;
import com.lhd.tams.module.coursescheduling.dao.CourseSchedulingMapper;
import com.lhd.tams.module.coursescheduling.model.convert.AbstractCourseSchedulingConverter;
import com.lhd.tams.module.coursescheduling.model.data.CourseSchedulingDO;
import com.lhd.tams.module.coursescheduling.model.dto.CourseSchedulingBatchSaveDTO;
import com.lhd.tams.module.coursescheduling.model.dto.CourseSchedulingQuery;
import com.lhd.tams.module.coursescheduling.model.dto.CourseSchedulingSaveDTO;
import com.lhd.tams.module.coursescheduling.model.dto.CourseSchedulingTimeUpdateDTO;
import com.lhd.tams.module.coursescheduling.model.vo.CourseSchedulingListVO;
import com.lhd.tams.module.coursescheduling.model.vo.CourseSchedulingReportVO;
import com.lhd.tams.module.coursescheduling.service.CourseSchedulingService;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author lhd
 */
@Slf4j
@Service
public class CourseSchedulingServiceImpl extends ServiceImpl<CourseSchedulingMapper, CourseSchedulingDO> implements CourseSchedulingService {

    @Override
    public List<CourseSchedulingListVO> listCourseScheduling(CourseSchedulingQuery query) {

        QueryWrapper<CourseSchedulingDO> queryWrapper = Wrappers.<CourseSchedulingDO>query()
                .in(query.getClassroomIdList() != null && query.getClassroomIdList().size() > 0, "cs.classroom_id", query.getClassroomIdList())
                .in(query.getCourseIdList() != null && query.getCourseIdList().size() > 0, "cs.course_id", query.getCourseIdList())
                .in(query.getTeacherIdList() != null && query.getTeacherIdList().size() > 0, "cs.teacher_id", query.getTeacherIdList())
                .ge(StrUtil.isNotEmpty(query.getStartDate()), "cs.date", query.getStartDate())
                .le(StrUtil.isNotEmpty(query.getEndDate()), "cs.date", query.getEndDate())
                .eq(StrUtil.isNotEmpty(query.getAttendTime()), "cs.attend_time", query.getAttendTime())
                .eq(StrUtil.isNotEmpty(query.getFinishTime()), "cs.finish_time", query.getFinishTime());

        return baseMapper.selectCourseSchedulingList(queryWrapper);
    }

    @Override
    public CourseSchedulingListVO getCourseSchedulingById(Long id) {

        return baseMapper.selectCourseSchedulingById(id);
    }

    @Override
    public Map<String, Integer> getCourseSchedulingCourseCount(CourseSchedulingQuery query) {

        Map<String, Integer> map = new HashMap<>(16);

        QueryWrapper<CourseSchedulingDO> queryWrapper = Wrappers.<CourseSchedulingDO>query()
                .in(query.getClassroomIdList() != null && query.getClassroomIdList().size() > 0, "classroom_id", query.getClassroomIdList())
                .in(query.getCourseIdList() != null && query.getCourseIdList().size() > 0, "course_id", query.getCourseIdList())
                .in(query.getTeacherIdList() != null && query.getTeacherIdList().size() > 0, "teacher_id", query.getTeacherIdList())
                .ge(StrUtil.isNotEmpty(query.getStartDate()), "date", query.getStartDate())
                .le(StrUtil.isNotEmpty(query.getEndDate()), "date", query.getEndDate())
                .groupBy("date")
                .orderByAsc("date");;
        List<Map<String, String>> list = baseMapper.selectCourseSchedulingCourseCount(queryWrapper);
        if (list != null && list.size() > 0) {
            list.forEach(item -> map.put(String.valueOf(item.get("date")), item.get("count") != null ? Integer.parseInt(String.valueOf(item.get("count"))) : 0));
        }

        return map;
    }

    @Override
    public List<CourseSchedulingReportVO> getReportTeacherCount(String startDate, String endDate) {
        return baseMapper.selectReportTeacherCount(startDate, endDate);
    }

    @Override
    public List<CourseSchedulingReportVO> getReportCourseCount(String startDate, String endDate) {
        return baseMapper.selectReportCourseCount(startDate, endDate);
    }

    @Override
    public boolean saveCourseScheduling(CourseSchedulingSaveDTO saveDTO) {

        check(null, saveDTO.getClassroomId(), saveDTO.getTeacherId(), saveDTO.getDate(), saveDTO.getAttendTime(), saveDTO.getFinishTime());

        CourseSchedulingDO dataObj = AbstractCourseSchedulingConverter.INSTANCE.saveDto2DO(saveDTO);

        return save(dataObj);
    }

    @Override
    public void batchSaveCourseScheduling(CourseSchedulingBatchSaveDTO saveDTO) {

        List<Integer> weekList = saveDTO.getWeekList();
        LocalDate startDate = saveDTO.getStartDate();
        LocalDate endDate = saveDTO.getEndDate();
        List<LocalDate> dateList = new ArrayList<>();
        while (startDate.compareTo(endDate) <= 0) {
            if (weekList.contains(startDate.getDayOfWeek().getValue())) {
                dateList.add(startDate);
            }
            startDate = startDate.plusDays(1);
        }

        List<CourseSchedulingListVO> voList = baseMapper.selectCourseSchedulingList(Wrappers.<CourseSchedulingDO>query()
                .eq("cs.classroom_id",saveDTO.getClassroomId())
                .in("cs.date", dateList)
                .orderByAsc("cs.date, cs.attend_time"));
        Set<String> errorSet = new HashSet<>();
        for (CourseSchedulingListVO vo : voList) {
            if (isTimeConflict(saveDTO.getAttendTime(), saveDTO.getFinishTime(), vo.getAttendTime(), vo.getFinishTime())) {
                errorSet.add(String.format("%s %s-%s %s %s %s", vo.getDate(), vo.getAttendTime(), vo.getFinishTime(), vo.getClassroomName(), vo.getCourseName(), vo.getTeacherName()));
            }
        }

        List<CourseSchedulingListVO> teacherVoList = baseMapper.selectCourseSchedulingList(Wrappers.<CourseSchedulingDO>query()
                .eq("cs.teacher_id", saveDTO.getTeacherId())
                .in("cs.date", dateList)
                .orderByAsc("cs.date, cs.attend_time"));
        for (CourseSchedulingListVO vo : teacherVoList) {
            if (isTimeConflict(saveDTO.getAttendTime(), saveDTO.getFinishTime(), vo.getAttendTime(), vo.getFinishTime())) {
                errorSet.add(String.format("%s %s-%s %s %s %s", vo.getDate(), vo.getAttendTime(), vo.getFinishTime(), vo.getClassroomName(), vo.getCourseName(), vo.getTeacherName()));
            }
        }

        if (errorSet.size() > 0) {
            throw new BusinessException(ErrorCodeEnum.BUSINESS_COURSE_SCHEDULING_DATE_CONFLICT.getCode(), errorSet, "检测到排课时间冲突", null);
        }

        List<CourseSchedulingDO> doList = new ArrayList<>();
        for (LocalDate date : dateList) {
            CourseSchedulingDO dataObj = AbstractCourseSchedulingConverter.INSTANCE.batchSaveDto2DO(saveDTO);
            dataObj.setDate(date);
            doList.add(dataObj);
        }

        saveBatch(doList);
    }

    @Override
    public boolean updateCourseSchedulingTimeById(Long id, CourseSchedulingTimeUpdateDTO updateDTO) {

        CourseSchedulingDO detailDO = getById(id);
        check(id, detailDO.getClassroomId(), detailDO.getTeacherId(), updateDTO.getDate(), updateDTO.getAttendTime(), updateDTO.getFinishTime());

        CourseSchedulingDO dataObj = AbstractCourseSchedulingConverter.INSTANCE.timeUpdateDto2DO(updateDTO);
        dataObj.setId(id);

        return updateById(dataObj);
    }

    @Override
    public boolean updateCourseSchedulingById(Long id, CourseSchedulingSaveDTO saveDTO) {

        check(id, saveDTO.getClassroomId(), saveDTO.getTeacherId(), saveDTO.getDate(), saveDTO.getAttendTime(), saveDTO.getFinishTime());

        CourseSchedulingDO dataObj = AbstractCourseSchedulingConverter.INSTANCE.saveDto2DO(saveDTO);
        dataObj.setId(id);

        return updateById(dataObj);
    }

    @Override
    public boolean removeCourseSchedulingById(Long id) {
        return removeById(id);
    }

    @Override
    public void removeCourseSchedulingByIdList(List<Long> idList) {
        remove(Wrappers.<CourseSchedulingDO>lambdaUpdate()
                .in(CourseSchedulingDO::getId, idList));
    }

    private void check(Long id, Long classroomId, Long teacherId, LocalDate date, LocalTime attendTime, LocalTime finishTime) {

        List<CourseSchedulingListVO> classroomVoList = baseMapper.selectCourseSchedulingList(Wrappers.<CourseSchedulingDO>query()
                .ne(id != null, "cs.id", id)
                .eq("cs.classroom_id", classroomId)
                .eq("cs.date", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .orderByAsc("cs.attend_time"));
        for (CourseSchedulingListVO vo : classroomVoList) {
            /**
             * 同一教室不能同时上多节课
             */
            if (isTimeConflict(attendTime, finishTime, vo.getAttendTime(), vo.getFinishTime())) {
                throw new BusinessException(String.format("教室时间冲突，冲突信息【%s %s-%s %s %s %s】",
                        vo.getDate(), vo.getAttendTime(), vo.getFinishTime(), vo.getClassroomName(), vo.getCourseName(), vo.getTeacherName()));
            }
        }

        List<CourseSchedulingListVO> teacherVoList = baseMapper.selectCourseSchedulingList(Wrappers.<CourseSchedulingDO>query()
                .ne(id != null, "cs.id", id)
                .eq("cs.teacher_id", teacherId)
                .eq("cs.date", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .orderByAsc("cs.attend_time"));
        for (CourseSchedulingListVO vo : teacherVoList) {
            /**
             * 同一老师不能同时上多节课
             */
            if (isTimeConflict(attendTime, finishTime, vo.getAttendTime(), vo.getFinishTime())) {
                throw new BusinessException(String.format("老师时间冲突，冲突信息【%s %s-%s %s %s %s】",
                        vo.getDate(), vo.getAttendTime(), vo.getFinishTime(), vo.getClassroomName(), vo.getCourseName(), vo.getTeacherName()));
            }
        }
    }

    private Integer calcMinute(LocalTime time) {
        return time.getHour() * 60 + time.getMinute();
    }

    private boolean isBetween(LocalTime time, LocalTime destTime1, LocalTime destTime2) {
        Integer minute = calcMinute(time);
        return calcMinute(destTime1) < minute && minute < calcMinute(destTime2);
    }

    /**
     * 新增时间范围跨度较小，在现有时间范围内
     *     新增时间是否在已有课程的时间段，在则冲突
     * 新增时间范围跨度较大大，包含现有时间段
     *     现有时间是否在新增时间范围内，在则冲突
     * 时间完全相等
     */
    private boolean isTimeConflict(LocalTime attendTime1, LocalTime finishTime1, LocalTime attendTime2, LocalTime finishTime2) {
        return isBetween(attendTime1, attendTime2, finishTime2)
                || isBetween(finishTime1, attendTime2, finishTime2)
                || isBetween(attendTime2, attendTime1, finishTime1)
                || isBetween(finishTime2, attendTime1, finishTime1)
                || (attendTime1.equals(attendTime2) && finishTime1.equals(finishTime2));
    }
}
