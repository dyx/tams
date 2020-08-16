package com.lhd.tams.module.coursescheduling.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@ApiModel(value = "排课导出返回结果")
@Data
public class CourseSchedulingExportVO {

    @ApiModelProperty(value = "教室名称")
    private String classroomName;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课程背景颜色")
    private String backgroundColor;

    @ApiModelProperty(value = "老师姓名")
    private String teacherName;

    @ApiModelProperty(value = "日期")
    private LocalDate date;

    @ApiModelProperty(value = "时间")
    private String time;
}
