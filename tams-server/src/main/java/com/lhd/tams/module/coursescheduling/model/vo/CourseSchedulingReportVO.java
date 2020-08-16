package com.lhd.tams.module.coursescheduling.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "排课报表返回结果")
@Data
public class CourseSchedulingReportVO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "数量")
    private Integer count;
}
