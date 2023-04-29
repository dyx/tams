package com.lhd.tams.module.color.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lhd
 */
@Schema(description = "颜色列表返回结果")
@Data
public class ColorListVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "值")
    private String value;
}
