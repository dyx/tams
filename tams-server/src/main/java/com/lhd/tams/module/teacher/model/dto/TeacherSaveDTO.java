package com.lhd.tams.module.teacher.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "教师分页查询参数")
@Data
public class TeacherSaveDTO {

    @ApiModelProperty(value = "姓名")
    private String name;
}
