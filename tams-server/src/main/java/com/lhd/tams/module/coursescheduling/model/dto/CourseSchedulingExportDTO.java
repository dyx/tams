package com.lhd.tams.module.coursescheduling.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author lhd
 */
@Schema(description = "排课导出参数")
@Data
public class CourseSchedulingExportDTO {

    @NotNull
    @Schema(description = "开课日期")
    private String startDate;

    @NotNull
    @Schema(description = "结课日期")
    private String endDate;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "文件名")
    private String filename;

    @Schema(description = "sheet命名方式")
    private Integer sheetNamingType;

    @Schema(description = "指定教室id")
    private Long classroomId;

    @Schema(description = "指定教室名称")
    private String classroomName;

    @Schema(description = "是否显示周末")
    private Boolean isShowWeek;
}
