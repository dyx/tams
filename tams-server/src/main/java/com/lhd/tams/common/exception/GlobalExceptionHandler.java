package com.lhd.tams.common.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.lhd.tams.common.consts.ErrorCodeEnum;
import com.lhd.tams.common.model.ApiResult;
import com.lhd.tams.common.util.ResponseEntityUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.sql.DataTruncation;
import java.sql.SQLException;

/**
 * @author lhd
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final int SQL_ERROR_CODE_NO_DEFAULT_VALUE = 1364;

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
     * 参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResult<?>> handle(ConstraintViolationException e) {
        StringBuilder msgBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            msgBuilder.append(violation.getMessage());
            msgBuilder.append(";");
        }
        if (log.isDebugEnabled()) {
            log.debug(msgBuilder.toString());
        }
        return ResponseEntityUtils.badRequest(msgBuilder.toString());
    }

    /**
     * 请求方式错误
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResult<?>> handle(HttpRequestMethodNotSupportedException e) {
        return ResponseEntityUtils.badRequest(String.format("请求方式错误，支持的请求方式为%s",
                e.getSupportedHttpMethods() != null ? e.getSupportedHttpMethods().toString() : ""));
    }

    /**
     * 参数缺失异常
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResult<?>> handle(MissingServletRequestParameterException e) {
        return ResponseEntityUtils.badRequest(String.format("请求参数缺失，缺失参数【%s】", e.getParameterName()));
    }

    /**
     * 参数转换异常
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResult<?>> handle(HttpMessageNotReadableException e) {

        if (e.getCause() instanceof InvalidFormatException) {
            InvalidFormatException exception = (InvalidFormatException) e.getCause();
            return ResponseEntityUtils.badRequest(String.format("请求参数转换异常，值【%s】转换为类型【%s】失败", exception.getValue(), exception.getTargetType().getName()));
        }

        log.error("请求参数转换异常，原因：{}", e.getMessage());

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

        log.error("数据库异常，原因：{}", e.getMessage());

        if (e.getCause() instanceof DataTruncation) {
            return ResponseEntityUtils.badRequest("数据库异常，要保存的字段中有超长值");
        }
        if (e.getCause() instanceof SQLException) {
            SQLException exception = (SQLException) e.getCause();
            if (exception.getErrorCode() == SQL_ERROR_CODE_NO_DEFAULT_VALUE) {
                return ResponseEntityUtils.badRequest("数据库异常，要保存的必填字段中有null值");
            }
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

        log.error(e.getMessage(), e);

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
        log.error(e.getMessage(), e);
        return ResponseEntityUtils.internalServerError(ErrorCodeEnum.UNKNOWN_ERROR, e);
    }
}
