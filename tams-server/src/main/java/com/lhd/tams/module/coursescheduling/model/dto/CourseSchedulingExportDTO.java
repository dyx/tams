package com.lhd.tams.module.coursescheduling.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(value = "排课导出参数")
@Data
public class CourseSchedulingExportDTO {

    @NotNull
    @ApiModelProperty(value = "开课日期")
    private String startDate;

    @NotNull
    @ApiModelProperty(value = "结课日期")
    private String endDate;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "文件名")
    private String filename;

    @ApiModelProperty(value = "sheet命名方式")
    private Integer sheetNamingType;

    @ApiModelProperty(value = "指定教室id")
    private Long classroomId;

    @ApiModelProperty(value = "指定教室名称")
    private String classroomName;

    @ApiModelProperty(value = "是否显示周末")
    private Boolean isShowWeek;
}
