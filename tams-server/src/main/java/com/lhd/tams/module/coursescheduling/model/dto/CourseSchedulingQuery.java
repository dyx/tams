package com.lhd.tams.module.coursescheduling.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "排课查询参数")
public class CourseSchedulingQuery {

    @ApiModelProperty(value = "教室id列表")
    private List<Long> classroomIdList;

    @ApiModelProperty(value = "课程id列表")
    private List<Long> courseIdList;

    @ApiModelProperty(value = "老师id列表")
    private List<Long> teacherIdList;

    @ApiModelProperty(value = "开始日期")
    private String startDate;

    @ApiModelProperty(value = "结束日期")
    private String endDate;

    @ApiModelProperty(value = "上课时间")
    private String attendTime;

    @ApiModelProperty(value = "下课时间")
    private String finishTime;
}
