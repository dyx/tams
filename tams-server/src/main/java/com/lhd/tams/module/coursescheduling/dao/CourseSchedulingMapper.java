package com.lhd.tams.module.coursescheduling.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.lhd.tams.module.coursescheduling.model.data.CourseSchedulingDO;
import com.lhd.tams.module.coursescheduling.model.vo.CourseSchedulingExportVO;
import com.lhd.tams.module.coursescheduling.model.vo.CourseSchedulingListVO;
import com.lhd.tams.module.coursescheduling.model.vo.CourseSchedulingReportVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CourseSchedulingMapper extends BaseMapper<CourseSchedulingDO> {

    List<CourseSchedulingListVO> selectCourseSchedulingList(@Param(Constants.WRAPPER) Wrapper<?> queryWrapper);

    CourseSchedulingListVO selectCourseSchedulingById(Long id);

    List<Map<String, String>> selectCourseSchedulingCourseCount(@Param(Constants.WRAPPER) Wrapper<?> queryWrapper);

    List<String> selectTimePeriodByDateRange(@Param("dateList") List<LocalDate> dateList, @Param("classroomId") Long classroomId);

    List<CourseSchedulingExportVO> selectByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("classroomId") Long classroomId);

    List<CourseSchedulingReportVO> selectReportTeacherCount(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<CourseSchedulingReportVO> selectReportCourseCount(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
