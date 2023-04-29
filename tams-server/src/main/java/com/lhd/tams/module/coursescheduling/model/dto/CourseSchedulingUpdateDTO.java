package com.lhd.tams.module.coursescheduling.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author lhd
 */
@Schema(description = "排课修改参数")
@Data
public class CourseSchedulingUpdateDTO {

    @NotNull
    @Schema(description = "教室id")
    private Long classroomId;

    @NotNull
    @Schema(description = "课程id")
    private Long courseId;

    @NotNull
    @Schema(description = "老师id")
    private Long teacherId;
}
