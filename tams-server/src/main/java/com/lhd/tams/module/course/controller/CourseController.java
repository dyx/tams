package com.lhd.tams.module.course.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhd.tams.common.base.BaseController;
import com.lhd.tams.common.model.ApiResult;
import com.lhd.tams.module.course.model.dto.CoursePageQuery;
import com.lhd.tams.module.course.model.dto.CourseSaveDTO;
import com.lhd.tams.module.course.model.vo.CourseListVO;
import com.lhd.tams.module.course.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lhd
 */
@Tag(name = "课程")
@RequestMapping("course")
@RestController
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;

    @Operation(summary = "分页列表")
    @GetMapping
    public ResponseEntity<ApiResult<IPage<CourseListVO>>> pageCourse(CoursePageQuery pageQuery) {

        return success(courseService.pageCourse(pageQuery));
    }

    @Operation(summary = "参照列表")
    @GetMapping("list/ref")
    public ResponseEntity<ApiResult<List<CourseListVO>>> refList() {

        return success(courseService.refList());
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    public ResponseEntity<ApiResult<CourseListVO>> getCourseById(@PathVariable("id") Long id) {

        return success(courseService.getCourseById(id));
    }

    @Operation(summary = "新增")
    @PostMapping
    public ResponseEntity<ApiResult<?>> saveCourse(@Validated @RequestBody CourseSaveDTO saveDTO) {

        return successOrFail(courseService.saveCourse(saveDTO));
    }

    @Operation(summary = "修改")
    @PutMapping("{id}")
    public ResponseEntity<ApiResult<?>> updateCourseById(@PathVariable("id") Long id, @Validated @RequestBody CourseSaveDTO saveDTO) {

        return successOrFail(courseService.updateCourseById(id, saveDTO));
    }

    @Operation(summary = "停启用")
    @PutMapping("{id}/enable-state/{enableState}")
    public ResponseEntity<ApiResult<?>> updateCourseEnableStateById(@PathVariable("id") Long id, @PathVariable("enableState") Integer enableState) {

        return successOrFail(courseService.updateCourseEnableStateById(id, enableState));
    }
}
