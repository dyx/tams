package com.lhd.tams.common.exception;

import com.lhd.tams.common.consts.ErrorCodeEnum;
import com.lhd.tams.common.model.ApiResult;
import com.lhd.tams.common.util.ResponseEntityUtils;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResult<?>> handle(MethodArgumentNotValidException e) {

        String msgStr = "";
        StringBuilder msgBuilder = new StringBuilder();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            msgBuilder.append("【");
            msgBuilder.append(error.getField());
            msgBuilder.append("】");
            msgBuilder.append(error.getDefaultMessage());
            msgBuilder.append(";");
        }

        if (msgBuilder.length() > 0) {
            msgStr = msgBuilder.substring(0, msgBuilder.length() - 1);
        }

        return ResponseEntityUtils.badRequest(msgStr);
    }

    /**
     * 参数缺失异常
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResult<?>> handle(HttpMessageNotReadableException e) {

        return ResponseEntityUtils.badRequest("请求参数转换异常");
    }

    /**
     * 媒体类型异常
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResult<?>> handle(HttpMediaTypeNotSupportedException e) {

        return ResponseEntityUtils.badRequest("不支持的媒体类型");
    }

    /**
     * 访问异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResult<?>> handle(AccessDeniedException e) {

        return ResponseEntityUtils.forbidden(e.getMessage());
    }

    /**
     * 数据库-数据完整性异常
     * @param e
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResult<?>> handle(DataIntegrityViolationException e) {

        if (((MysqlDataTruncation) e.getCause()).getErrorCode() == 1406) {
            return ResponseEntityUtils.internalServerError(ErrorCodeEnum.DB_DATA_TOO_LONG, e);
        }

        return ResponseEntityUtils.internalServerError(ErrorCodeEnum.DB_DATA_INTEGRITY_VIOLATION, e);
    }

    /**
     * 业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResult<?>> handle(BusinessException e) {

        if (e.getCode() == null || e.getCode() >= ErrorCodeEnum.BUSINESS_ERROR.getCode()) {
            return ResponseEntityUtils.badRequest(e.getCode(), e.getMessage(), e.getData());
        }

        return ResponseEntityUtils.internalServerError(e.getCode(), e.getMessage(), e);
    }

    /**
     * 未知异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResult<?>> handle(Exception e) {
        return ResponseEntityUtils.internalServerError(ErrorCodeEnum.UNKNOWN_ERROR, e);
    }
}
