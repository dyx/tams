package com.lhd.tams.module.coursescheduling.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@ApiModel(value = "排课时间修改参数")
@Data
public class CourseSchedulingTimeUpdateDTO {

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
