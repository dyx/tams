package com.lhd.tams.module.teacher.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhd.tams.module.teacher.model.dto.TeacherPageQuery;
import com.lhd.tams.module.teacher.model.dto.TeacherSaveDTO;
import com.lhd.tams.module.teacher.model.vo.TeacherListVO;

import java.util.List;

public interface TeacherService {

    IPage<TeacherListVO> pageTeacher(TeacherPageQuery pageQuery);

    List<TeacherListVO> refList();

    TeacherListVO getTeacherById(Long id);

    boolean saveTeacher(TeacherSaveDTO saveDTO);

    boolean updateTeacherById(Long id, TeacherSaveDTO saveDTO);

    boolean updateTeacherEnableStateById(Long id, Integer enableState);
}
