package com.lhd.tams.module.coursescheduling.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(value = "排课修改参数")
@Data
public class CourseSchedulingUpdateDTO {

    @NotNull
    @ApiModelProperty(value = "教室id")
    private Long classroomId;

    @NotNull
    @ApiModelProperty(value = "课程id")
    private Long courseId;

    @NotNull
    @ApiModelProperty(value = "老师id")
    private Long teacherId;
}
