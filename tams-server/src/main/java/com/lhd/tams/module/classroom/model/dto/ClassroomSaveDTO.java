package com.lhd.tams.module.classroom.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author lhd
 */
@Schema(description = "教室新增参数")
@Data
public class ClassroomSaveDTO {

    @NotEmpty(message = "名称不能为空")
    @Schema(description = "名称")
    private String name;
}
