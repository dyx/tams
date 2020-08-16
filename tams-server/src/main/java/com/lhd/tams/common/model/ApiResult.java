package com.lhd.tams.common.model;

/**
 * 接口返回值结构
 * @param <T>
 */
public class ApiResult<T> {

    /**
     * 错误编码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;

    public ApiResult(String msg) {
        this(msg, null);
    }

    public ApiResult(T data) {
        this("", data);
    }

    public ApiResult(String msg, T data) {
        this(null, msg, data);
    }

    public ApiResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
