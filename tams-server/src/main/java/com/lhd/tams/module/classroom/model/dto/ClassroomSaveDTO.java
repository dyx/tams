package com.lhd.tams.module.classroom.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "教室新增参数")
@Data
public class ClassroomSaveDTO {

    @ApiModelProperty(value = "名称")
    private String name;
}
