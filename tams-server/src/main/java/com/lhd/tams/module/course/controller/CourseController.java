package com.lhd.tams.module.course.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhd.tams.common.base.BaseController;
import com.lhd.tams.common.model.ApiResult;
import com.lhd.tams.module.course.model.dto.CoursePageQuery;
import com.lhd.tams.module.course.model.dto.CourseSaveDTO;
import com.lhd.tams.module.course.model.vo.CourseListVO;
import com.lhd.tams.module.course.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "课程")
@RequestMapping("course")
@RestController
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "分页列表", response = CourseListVO.class)
    @GetMapping
    public ResponseEntity<ApiResult<IPage<CourseListVO>>> pageCourse(CoursePageQuery pageQuery) {

        return success(courseService.pageCourse(pageQuery));
    }

    @ApiOperation(value = "参照列表", response = CourseListVO.class)
    @GetMapping("list/ref")
    public ResponseEntity<ApiResult<List<CourseListVO>>> refList() {

        return success(courseService.refList());
    }

    @ApiOperation(value = "详情", response = CourseListVO.class)
    @GetMapping("{id}")
    public ResponseEntity<ApiResult<CourseListVO>> getCourseById(@PathVariable("id") Long id) {

        return success(courseService.getCourseById(id));
    }

    @ApiOperation(value = "新增")
    @PostMapping
    public ResponseEntity<ApiResult<?>> saveCourse(@Validated @RequestBody CourseSaveDTO saveDTO) {

        return successOrFail(courseService.saveCourse(saveDTO));
    }

    @ApiOperation(value = "修改")
    @PutMapping("{id}")
    public ResponseEntity<ApiResult<?>> updateCourseById(@PathVariable("id") Long id, @Validated @RequestBody CourseSaveDTO saveDTO) {

        return successOrFail(courseService.updateCourseById(id, saveDTO));
    }

    @ApiOperation(value = "停启用")
    @PutMapping("{id}/enable-state/{enableState}")
    public ResponseEntity<ApiResult<?>> updateCourseEnableStateById(@PathVariable("id") Long id, @PathVariable("enableState") Integer enableState) {

        return successOrFail(courseService.updateCourseEnableStateById(id, enableState));
    }
}
