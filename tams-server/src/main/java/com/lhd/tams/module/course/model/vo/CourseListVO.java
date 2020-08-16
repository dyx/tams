package com.lhd.tams.module.course.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "课程列表返回结果")
@Data
public class CourseListVO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "停启用状态")
    private Integer enableState;

    @ApiModelProperty(value = "课程时长，单位分钟")
    private Integer duration;

    @ApiModelProperty(value = "背景颜色")
    private String backgroundColor;
}
