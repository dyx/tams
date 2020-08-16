package com.lhd.tams.common.util;

import com.lhd.tams.common.consts.ErrorCodeEnum;
import com.lhd.tams.common.model.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/**
 * HTTP响应结果构建器，HTTP状态码+接口返回值结构
 */
public class ResponseEntityUtils {

    private static final String SERVER_ERROR = "服务器异常，请联系管理员。";

    private static final Logger log = LoggerFactory.getLogger(ResponseEntityUtils.class);

    public static ResponseEntity<ApiResult<?>> ok(String msg) {
        return ResponseEntity.ok(new ApiResult<>(msg));
    }

    public static <T> ResponseEntity<ApiResult<T>> ok(T data) {
        return ResponseEntity.ok(new ApiResult<>(data));
    }

    public static ResponseEntity<ApiResult<?>> badRequest(String msg) {
        return new ResponseEntity<>(new ApiResult<>(msg), HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<ApiResult<?>> badRequest(Integer code, String msg, T data) {
        return new ResponseEntity<>(new ApiResult<>(code, msg, data), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<ApiResult<?>> unauthorized(String msg) {
        return new ResponseEntity<>(new ApiResult<>(msg), HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<ApiResult<?>> forbidden(String msg) {
        return new ResponseEntity<>(new ApiResult<>(msg), HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<ApiResult<?>> internalServerError(ErrorCodeEnum errorCodeEnum, Throwable throwable) {

        log.error(errorCodeEnum.getMsg(), throwable);

        return new ResponseEntity<>(new ApiResult<>(String.format("【%s】%s", errorCodeEnum.getCode(), SERVER_ERROR)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<ApiResult<?>> internalServerError(Integer code, String msg, Throwable throwable) {

        log.error(msg, throwable);

        return new ResponseEntity<>(new ApiResult<>(String.format("【%s】%s", code, SERVER_ERROR)), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
