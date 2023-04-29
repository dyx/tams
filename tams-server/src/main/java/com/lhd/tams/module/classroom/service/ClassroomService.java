package com.lhd.tams.module.classroom.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhd.tams.module.classroom.model.dto.ClassroomPageQuery;
import com.lhd.tams.module.classroom.model.dto.ClassroomSaveDTO;
import com.lhd.tams.module.classroom.model.vo.ClassroomListVO;

import java.util.List;

/**
 * @author lhd
 */
public interface ClassroomService {

    /**
     * 分页列表
     * @param pageQuery
     * @return
     */
    IPage<ClassroomListVO> pageCourse(ClassroomPageQuery pageQuery);

    /**
     * 引用列表
     * @return
     */
    List<ClassroomListVO> refList();

    /**
     * 详情
     * @param id
     * @return
     */
    ClassroomListVO getCourseById(Long id);

    /**
     * 新增
     * @param saveDTO
     * @return
     */
    boolean saveCourse(ClassroomSaveDTO saveDTO);

    /**
     * 编辑
     * @param id
     * @param saveDTO
     * @return
     */
    boolean updateCourseById(Long id, ClassroomSaveDTO saveDTO);

    /**
     * 停启用
     * @param id
     * @param enableState
     * @return
     */
    boolean updateCourseEnableStateById(Long id, Integer enableState);
}
