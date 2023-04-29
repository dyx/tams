package com.lhd.tams.module.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhd.tams.module.course.model.dto.CoursePageQuery;
import com.lhd.tams.module.course.model.dto.CourseSaveDTO;
import com.lhd.tams.module.course.model.vo.CourseListVO;

import java.util.List;

/**
 * @author lhd
 */
public interface CourseService {

    /**
     * 分页列表
     * @param pageQuery
     * @return
     */
    IPage<CourseListVO> pageCourse(CoursePageQuery pageQuery);

    /**
     * 引用列表
     * @return
     */
    List<CourseListVO> refList();

    /**
     * 详情
     * @param id
     * @return
     */
    CourseListVO getCourseById(Long id);

    /**
     * 新增
     * @param saveDTO
     * @return
     */
    boolean saveCourse(CourseSaveDTO saveDTO);

    /**
     * 编辑
     * @param id
     * @param saveDTO
     * @return
     */
    boolean updateCourseById(Long id, CourseSaveDTO saveDTO);

    /**
     * 停启用
     * @param id
     * @param enableState
     * @return
     */
    boolean updateCourseEnableStateById(Long id, Integer enableState);
}
