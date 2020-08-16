package com.lhd.tams.module.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhd.tams.module.course.model.dto.CoursePageQuery;
import com.lhd.tams.module.course.model.dto.CourseSaveDTO;
import com.lhd.tams.module.course.model.vo.CourseListVO;

import java.util.List;

public interface CourseService {

    IPage<CourseListVO> pageCourse(CoursePageQuery pageQuery);

    List<CourseListVO> refList();

    CourseListVO getCourseById(Long id);

    boolean saveCourse(CourseSaveDTO saveDTO);

    boolean updateCourseById(Long id, CourseSaveDTO saveDTO);

    boolean updateCourseEnableStateById(Long id, Integer enableState);
}
