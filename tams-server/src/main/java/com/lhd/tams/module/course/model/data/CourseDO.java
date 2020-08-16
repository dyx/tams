package com.lhd.tams.module.course.model.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_course")
public class CourseDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 停启用状态
     */
    private Integer enableState;

    /**
     * 课程时长，单位分钟
     */
    private Integer duration;

    /**
     * 背景颜色
     */
    private String backgroundColor;
}
