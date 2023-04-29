package com.lhd.tams.module.coursescheduling.service;

import com.lhd.tams.module.coursescheduling.model.dto.CourseSchedulingBatchSaveDTO;
import com.lhd.tams.module.coursescheduling.model.dto.CourseSchedulingQuery;
import com.lhd.tams.module.coursescheduling.model.dto.CourseSchedulingSaveDTO;
import com.lhd.tams.module.coursescheduling.model.dto.CourseSchedulingTimeUpdateDTO;
import com.lhd.tams.module.coursescheduling.model.vo.CourseSchedulingListVO;
import com.lhd.tams.module.coursescheduling.model.vo.CourseSchedulingReportVO;

import java.util.List;
import java.util.Map;

/**
 * @author lhd
 */
public interface CourseSchedulingService {

    /**
     * 列表
     * @param query
     * @return
     */
    List<CourseSchedulingListVO> listCourseScheduling(CourseSchedulingQuery query);

    /**
     * 详情
     * @param id
     * @return
     */
    CourseSchedulingListVO getCourseSchedulingById(Long id);

    /**
     * 数量
     * @param query
     * @return
     */
    Map<String, Integer> getCourseSchedulingCourseCount(CourseSchedulingQuery query);

    /**
     * 教师数量
     * @param startDate
     * @param endDate
     * @return
     */
    List<CourseSchedulingReportVO> getReportTeacherCount(String startDate, String endDate);

    /**
     * 课程数量
     * @param startDate
     * @param endDate
     * @return
     */
    List<CourseSchedulingReportVO> getReportCourseCount(String startDate, String endDate);

    /**
     * 新增
     * @param saveDTO
     * @return
     */
    boolean saveCourseScheduling(CourseSchedulingSaveDTO saveDTO);

    /**
     * 批量新增
     * @param saveDTO
     */
    void batchSaveCourseScheduling(CourseSchedulingBatchSaveDTO saveDTO);

    /**
     * 编辑时间
     * @param id
     * @param updateDTO
     * @return
     */
    boolean updateCourseSchedulingTimeById(Long id, CourseSchedulingTimeUpdateDTO updateDTO);

    /**
     * 编辑
     * @param id
     * @param saveDTO
     * @return
     */
    boolean updateCourseSchedulingById(Long id, CourseSchedulingSaveDTO saveDTO);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean removeCourseSchedulingById(Long id);

    /**
     * 批量删除
     * @param idList
     */
    void removeCourseSchedulingByIdList(List<Long> idList);
}
