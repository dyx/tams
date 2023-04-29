package com.lhd.tams.module.coursescheduling.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author lhd
 */
@Schema(description = "排课导出返回结果")
@Data
public class CourseSchedulingExportVO {

    @Schema(description = "教室名称")
    private String classroomName;

    @Schema(description = "课程名称")
    private String courseName;

    @Schema(description = "课程背景颜色")
    private String backgroundColor;

    @Schema(description = "老师姓名")
    private String teacherName;

    @Schema(description = "日期")
    private LocalDate date;

    @Schema(description = "时间")
    private String time;
}
