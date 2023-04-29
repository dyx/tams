package com.lhd.tams.module.coursescheduling.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author lhd
 */
@Schema(description = "排课列表返回结果")
@Data
public class CourseSchedulingListVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "教室id")
    private Long classroomId;

    @Schema(description = "教室名称")
    private String classroomName;

    @Schema(description = "课程id")
    private Long courseId;

    @Schema(description = "课程名称")
    private String courseName;

    @Schema(description = "课程时长，单位分钟")
    private Integer duration;

    @Schema(description = "课程背景颜色")
    private String backgroundColor;

    @Schema(description = "老师id")
    private Long teacherId;

    @Schema(description = "老师姓名")
    private String teacherName;

    @Schema(description = "日期")
    private LocalDate date;

    @Schema(description = "上课时间")
    private LocalTime attendTime;

    @Schema(description = "下课时间")
    private LocalTime finishTime;
}
