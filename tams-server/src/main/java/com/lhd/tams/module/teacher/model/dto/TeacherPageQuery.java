package com.lhd.tams.module.teacher.model.dto;

import com.lhd.tams.common.model.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lhd
 */
@Schema(description = "教师分页查询参数")
@Data
public class TeacherPageQuery extends BasePageQuery {

    @Schema(description = "停启用状态")
    private Integer enableState;
}
