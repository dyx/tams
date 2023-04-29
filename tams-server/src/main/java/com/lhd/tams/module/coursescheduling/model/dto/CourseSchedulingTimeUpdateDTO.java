package com.lhd.tams.module.coursescheduling.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author lhd
 */
@Schema(description = "排课时间修改参数")
@Data
public class CourseSchedulingTimeUpdateDTO {

    @NotNull
    @Schema(description = "日期")
    private LocalDate date;

    @NotNull
    @Schema(description = "上课时间")
    private LocalTime attendTime;

    @NotNull
    @Schema(description = "下课时间")
    private LocalTime finishTime;
}
