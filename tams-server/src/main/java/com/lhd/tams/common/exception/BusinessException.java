package com.lhd.tams.common.exception;

import com.lhd.tams.common.consts.ErrorCodeEnum;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    /**
     * 错误编码
     */
    private Integer code;

    /**
     * 错误数据
     */
    private Object data;

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String msg, Throwable t) {
        super(msg, t);
    }

    public BusinessException(ErrorCodeEnum errorCodeEnum) {
        this(errorCodeEnum.getCode(), errorCodeEnum.getMsg(), null);
    }

    public BusinessException(ErrorCodeEnum errorApiStatusEnum, Throwable t) {
        this(errorApiStatusEnum.getCode(), errorApiStatusEnum.getMsg(), t);
    }

    public BusinessException(Integer code, String msg, Throwable t) {
        this(code, null, msg, t);
    }

    public BusinessException(Integer code, Object data, String msg, Throwable t) {
        super(msg, t);
        this.code = code;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
