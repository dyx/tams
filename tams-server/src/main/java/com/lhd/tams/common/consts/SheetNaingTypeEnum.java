package com.lhd.tams.common.consts;

/**
 * sheet命名方式
 */
public enum SheetNaingTypeEnum {

    WEEK_NUM(2, "第几周"),
    TIME_PERIOD(1, "时间段");

    private Integer code;
    private String msg;

    SheetNaingTypeEnum(Integer code, String msg) {
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
