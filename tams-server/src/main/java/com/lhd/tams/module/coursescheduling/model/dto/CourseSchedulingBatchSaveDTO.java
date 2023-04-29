package com.lhd.tams.module.coursescheduling.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * @author lhd
 */
@Schema(description = "排课批量新增参数")
@Data
public class CourseSchedulingBatchSaveDTO {

    @NotNull
    @Schema(description = "教室id")
    private Long classroomId;

    @NotNull
    @Schema(description = "课程id")
    private Long courseId;

    @NotNull
    @Schema(description = "老师id")
    private Long teacherId;

    @NotNull
    @Schema(description = "上课时间")
    private LocalTime attendTime;

    @NotNull
    @Schema(description = "下课时间")
    private LocalTime finishTime;

    @NotNull
    @Schema(description = "开课日期")
    private LocalDate startDate;

    @NotNull
    @Schema(description = "结课日期")
    private LocalDate endDate;

    @NotEmpty
    @Schema(description = "周几上课列表")
    private List<Integer> weekList;
}
