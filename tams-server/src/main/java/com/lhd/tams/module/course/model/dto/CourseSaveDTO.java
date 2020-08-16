package com.lhd.tams.module.course.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "课程新增参数")
@Data
public class CourseSaveDTO {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "课程时长，单位分钟")
    private Integer duration;

    @ApiModelProperty(value = "背景颜色")
    private String backgroundColor;
}
