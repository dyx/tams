package com.lhd.tams.module.coursescheduling.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@ApiModel(value = "排课新增参数")
@Data
public class CourseSchedulingSaveDTO {

    @NotNull
    @ApiModelProperty(value = "教室id")
    private Long classroomId;

    @NotNull
    @ApiModelProperty(value = "课程id")
    private Long courseId;

    @NotNull
    @ApiModelProperty(value = "老师id")
    private Long teacherId;

    @NotNull
    @ApiModelProperty(value = "日期")
    private LocalDate date;

    @NotNull
    @ApiModelProperty(value = "上课时间")
    private LocalTime attendTime;

    @NotNull
    @ApiModelProperty(value = "下课时间")
    private LocalTime finishTime;
}
