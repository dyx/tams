package com.lhd.tams.module.coursescheduling.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@ApiModel(value = "排课列表返回结果")
@Data
public class CourseSchedulingListVO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "教室id")
    private Long classroomId;

    @ApiModelProperty(value = "教室名称")
    private String classroomName;

    @ApiModelProperty(value = "课程id")
    private Long courseId;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课程时长，单位分钟")
    private Integer duration;

    @ApiModelProperty(value = "课程背景颜色")
    private String backgroundColor;

    @ApiModelProperty(value = "老师id")
    private Long teacherId;

    @ApiModelProperty(value = "老师姓名")
    private String teacherName;

    @ApiModelProperty(value = "日期")
    private LocalDate date;

    @ApiModelProperty(value = "上课时间")
    private LocalTime attendTime;

    @ApiModelProperty(value = "下课时间")
    private LocalTime finishTime;
}
