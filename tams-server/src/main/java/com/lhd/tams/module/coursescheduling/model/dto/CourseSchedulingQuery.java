package com.lhd.tams.module.coursescheduling.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author lhd
 */
@Data
@Schema(description = "排课查询参数")
public class CourseSchedulingQuery {

    @Schema(description = "教室id列表")
    private List<Long> classroomIdList;

    @Schema(description = "课程id列表")
    private List<Long> courseIdList;

    @Schema(description = "老师id列表")
    private List<Long> teacherIdList;

    @Schema(description = "开始日期")
    private String startDate;

    @Schema(description = "结束日期")
    private String endDate;

    @Schema(description = "上课时间")
    private String attendTime;

    @Schema(description = "下课时间")
    private String finishTime;
}
