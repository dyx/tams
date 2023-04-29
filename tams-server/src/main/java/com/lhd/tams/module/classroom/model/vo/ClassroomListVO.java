package com.lhd.tams.module.classroom.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lhd
 */
@Schema(description = "教室列表返回结果")
@Data
public class ClassroomListVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "停启用状态")
    private Integer enableState;
}
