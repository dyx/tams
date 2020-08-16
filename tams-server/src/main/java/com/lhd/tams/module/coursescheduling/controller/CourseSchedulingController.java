package com.lhd.tams.module.coursescheduling.controller;

import com.lhd.tams.common.base.BaseController;
import com.lhd.tams.common.model.ApiResult;
import com.lhd.tams.module.coursescheduling.manager.CourseSchedulingExcelManager;
import com.lhd.tams.module.coursescheduling.model.dto.*;
import com.lhd.tams.module.coursescheduling.model.vo.CourseSchedulingListVO;
import com.lhd.tams.module.coursescheduling.service.CourseSchedulingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Api(tags = "排课")
@RequestMapping("course-scheduling")
@RestController
public class CourseSchedulingController extends BaseController {

    @Autowired
    private CourseSchedulingService courseSchedulingService;

    @Autowired
    private CourseSchedulingExcelManager courseSchedulingExcelManager;

    @ApiOperation(value = "列表", response = CourseSchedulingListVO.class)
    @PostMapping("list")
    public ResponseEntity<ApiResult<List<CourseSchedulingListVO>>> listCourseScheduling(@RequestBody CourseSchedulingQuery query) {

        return success(courseSchedulingService.listCourseScheduling(query));
    }

    @ApiOperation(value = "详情", response = CourseSchedulingListVO.class)
    @GetMapping("{id}")
    public ResponseEntity<ApiResult<CourseSchedulingListVO>> getCourseSchedulingById(@PathVariable("id") Long id) {

        return success(courseSchedulingService.getCourseSchedulingById(id));
    }

    @ApiOperation(value = "课程数量", response = Map.class)
    @PostMapping("course/count")
    public ResponseEntity<ApiResult<Map<String, Integer>>> getCourseSchedulingCourseCount(@RequestBody CourseSchedulingQuery query) {

        return success(courseSchedulingService.getCourseSchedulingCourseCount(query));
    }

    @ApiOperation(value = "新增")
    @PostMapping
    public ResponseEntity<ApiResult<?>> saveCourseScheduling(@Validated @RequestBody CourseSchedulingSaveDTO saveDTO) {

        return successOrFail(courseSchedulingService.saveCourseScheduling(saveDTO));
    }

    @ApiOperation(value = "批量新增")
    @PostMapping("batch")
    public ResponseEntity<ApiResult<?>> saveCourseScheduling(@Validated @RequestBody CourseSchedulingBatchSaveDTO saveDTO) {

        courseSchedulingService.batchSaveCourseScheduling(saveDTO);

        return success();
    }

    @ApiOperation(value = "修改排课时间")
    @PutMapping("{id}/time")
    public ResponseEntity<ApiResult<?>> updateCourseSchedulingTimeById(@PathVariable("id") Long id, @Validated @RequestBody CourseSchedulingTimeUpdateDTO updateDTO) {

        return successOrFail(courseSchedulingService.updateCourseSchedulingTimeById(id, updateDTO));
    }

    @ApiOperation(value = "修改")
    @PutMapping("{id}")
    public ResponseEntity<ApiResult<?>> updateCourseSchedulingById(@PathVariable("id") Long id, @Validated @RequestBody CourseSchedulingSaveDTO saveDTO) {

        return successOrFail(courseSchedulingService.updateCourseSchedulingById(id, saveDTO));
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResult<?>> removeCourseSchedulingById(@PathVariable("id") Long id) {

        return successOrFail(courseSchedulingService.removeCourseSchedulingById(id));
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping("batch")
    public ResponseEntity<ApiResult<?>> removeCourseSchedulingByIdList(@RequestBody List<Long> idList) {

        courseSchedulingService.removeCourseSchedulingByIdList(idList);

        return success();
    }

    @ApiOperation(value = "导出excel")
    @GetMapping("export/excel")
    public void exportExcel(HttpServletResponse response, @Validated CourseSchedulingExportDTO dto) throws IOException {

        Workbook workbook = courseSchedulingExcelManager.createExcel(dto);

        String filename = (StringUtils.isEmpty(dto.getFilename()) ? "课表" : dto.getFilename()) + ".xlsx";
        response.reset();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), StandardCharsets.ISO_8859_1));

        workbook.write(response.getOutputStream());
        response.flushBuffer();

        workbook.close();
    }
}
