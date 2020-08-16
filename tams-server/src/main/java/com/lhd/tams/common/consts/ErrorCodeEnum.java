package com.lhd.tams.common.consts;

/**
 * 详细错误码
 * 起始值1000，每100个为一类
 * 命名：类别_具体描述
 */
public enum ErrorCodeEnum {

    /**
     * 业务异常
     */
    BUSINESS_COURSE_SCHEDULING_DATE_CONFLICT(100001, "排课时间冲突"),
    BUSINESS_ERROR(100000, "业务异常"),
    /**
     * 关系型数据库
     */
    DB_DATA_TOO_LONG(1101, "字段超长"),
    DB_DATA_INTEGRITY_VIOLATION(1100, "数据完整性异常"),

    UNKNOWN_ERROR(1000, "未知异常");

    private Integer code;
    private String msg;

    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
