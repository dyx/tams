package com.lhd.tams.module.teacher.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhd.tams.module.teacher.model.dto.TeacherPageQuery;
import com.lhd.tams.module.teacher.model.dto.TeacherSaveDTO;
import com.lhd.tams.module.teacher.model.vo.TeacherListVO;

import java.util.List;

/**
 * @author lhd
 */
public interface TeacherService {

    /**
     * 分页列表
     * @param pageQuery
     * @return
     */
    IPage<TeacherListVO> pageTeacher(TeacherPageQuery pageQuery);

    /**
     * 引用列表
     * @return
     */
    List<TeacherListVO> refList();

    /**
     * 详情
     * @param id
     * @return
     */
    TeacherListVO getTeacherById(Long id);

    /**
     * 新增
     * @param saveDTO
     * @return
     */
    boolean saveTeacher(TeacherSaveDTO saveDTO);

    /**
     * 编辑
     * @param id
     * @param saveDTO
     * @return
     */
    boolean updateTeacherById(Long id, TeacherSaveDTO saveDTO);

    /**
     * 停启用
     * @param id
     * @param enableState
     * @return
     */
    boolean updateTeacherEnableStateById(Long id, Integer enableState);
}
