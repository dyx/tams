package com.lhd.tams.module.classroom.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhd.tams.common.base.BaseController;
import com.lhd.tams.common.model.ApiResult;
import com.lhd.tams.module.classroom.model.dto.ClassroomPageQuery;
import com.lhd.tams.module.classroom.model.dto.ClassroomSaveDTO;
import com.lhd.tams.module.classroom.model.vo.ClassroomListVO;
import com.lhd.tams.module.classroom.service.ClassroomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "教室")
@RequestMapping("classroom")
@RestController
public class ClassroomController extends BaseController {

    @Autowired
    private ClassroomService classroomService;

    @ApiOperation(value = "分页列表", response = ClassroomListVO.class)
    @GetMapping
    public ResponseEntity<ApiResult<IPage<ClassroomListVO>>> pageCourse(ClassroomPageQuery pageQuery) {

        return success(classroomService.pageCourse(pageQuery));
    }

    @ApiOperation(value = "参照列表", response = ClassroomListVO.class)
    @GetMapping("list/ref")
    public ResponseEntity<ApiResult<List<ClassroomListVO>>> refList() {

        return success(classroomService.refList());
    }

    @ApiOperation(value = "详情", response = ClassroomListVO.class)
    @GetMapping("{id}")
    public ResponseEntity<ApiResult<ClassroomListVO>> getCourseById(@PathVariable("id") Long id) {

        return success(classroomService.getCourseById(id));
    }

    @ApiOperation(value = "新增")
    @PostMapping
    public ResponseEntity<ApiResult<?>> saveCourse(@Validated @RequestBody ClassroomSaveDTO saveDTO) {

        return successOrFail(classroomService.saveCourse(saveDTO));
    }

    @ApiOperation(value = "修改")
    @PutMapping("{id}")
    public ResponseEntity<ApiResult<?>> updateCourseById(@PathVariable("id") Long id, @Validated @RequestBody ClassroomSaveDTO saveDTO) {

        return successOrFail(classroomService.updateCourseById(id, saveDTO));
    }

    @ApiOperation(value = "停启用")
    @PutMapping("{id}/enable-state/{enableState}")
    public ResponseEntity<ApiResult<?>> updateCourseEnableStateById(@PathVariable("id") Long id, @PathVariable("enableState") Integer enableState) {

        return successOrFail(classroomService.updateCourseEnableStateById(id, enableState));
    }
}
