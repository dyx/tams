package com.lhd.tams.module.teacher.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhd.tams.common.base.BaseController;
import com.lhd.tams.common.model.ApiResult;
import com.lhd.tams.module.teacher.model.dto.TeacherPageQuery;
import com.lhd.tams.module.teacher.model.dto.TeacherSaveDTO;
import com.lhd.tams.module.teacher.model.vo.TeacherListVO;
import com.lhd.tams.module.teacher.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "教师")
@RequestMapping("teacher")
@RestController
public class TeacherController extends BaseController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "分页列表", response = TeacherListVO.class)
    @GetMapping
    public ResponseEntity<ApiResult<IPage<TeacherListVO>>> pageTeacher(TeacherPageQuery pageQuery) {

        return success(teacherService.pageTeacher(pageQuery));
    }

    @ApiOperation(value = "参照列表", response = TeacherListVO.class)
    @GetMapping("list/ref")
    public ResponseEntity<ApiResult<List<TeacherListVO>>> refList() {

        return success(teacherService.refList());
    }

    @ApiOperation(value = "详情", response = TeacherListVO.class)
    @GetMapping("{id}")
    public ResponseEntity<ApiResult<TeacherListVO>> getTeacherById(@PathVariable("id") Long id) {

        return success(teacherService.getTeacherById(id));
    }

    @ApiOperation(value = "新增")
    @PostMapping
    public ResponseEntity<ApiResult<?>> saveTeacher(@Validated @RequestBody TeacherSaveDTO saveDTO) {

        return successOrFail(teacherService.saveTeacher(saveDTO));
    }

    @ApiOperation(value = "修改")
    @PutMapping("{id}")
    public ResponseEntity<ApiResult<?>> updateTeacherById(@PathVariable("id") Long id, @Validated @RequestBody TeacherSaveDTO saveDTO) {

        return successOrFail(teacherService.updateTeacherById(id, saveDTO));
    }

    @ApiOperation(value = "停启用")
    @PutMapping("{id}/enable-state/{enableState}")
    public ResponseEntity<ApiResult<?>> updateTeacherEnableStateById(@PathVariable("id") Long id, @PathVariable("enableState") Integer enableState) {

        return successOrFail(teacherService.updateTeacherEnableStateById(id, enableState));
    }
}
