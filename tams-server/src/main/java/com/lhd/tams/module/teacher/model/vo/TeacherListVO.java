package com.lhd.tams.module.teacher.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lhd
 */
@Schema(description = "教师列表返回结果")
@Data
public class TeacherListVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "停启用状态")
    private Integer enableState;
}
