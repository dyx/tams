package com.lhd.tams.common.consts;

/**
 * 停启用状态
 */
public enum EnableStateEnum {

    DISABLED(2, "停用"),
    ENABLED(1, "启用");

    private Integer code;
    private String msg;

    EnableStateEnum(Integer code, String msg) {
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
