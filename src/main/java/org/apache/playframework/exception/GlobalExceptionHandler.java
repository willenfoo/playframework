package org.apache.playframework.exception;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import org.apache.playframework.enums.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;


/**
 * @author willenfoo
 * @description:全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private R<Object> paramFailed(String message) {
        R<Object> r = new R<>();
        r.setCode(ErrorCode.PARAMETER__ERROR.getCode());
        r.setMsg(ErrorCode.PARAMETER__ERROR.getMsg() + "," + message);
        return r;
    }

    /**
     * 400 - Bad Request
     * 缺少请求参数
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.warn("缺少请求参数, message:{}", e.getMessage());
        return paramFailed(e.getMessage());
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.warn("参数解析失败, message:{}", e.getCause().getMessage());
        return paramFailed(e.getCause().getMessage());
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.warn("参数验证失败, message:{}", e.getMessage());
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String msg = error.getDefaultMessage();
        String message = String.format("%s:%s", field, msg);
        return paramFailed(message);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public R<Object> handleBindException(BindException e) {
        logger.warn("参数绑定失败, message:{}", e.getMessage());
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String msg = error.getDefaultMessage();
        String message = String.format("%s:%s", field, msg);
        return paramFailed(message);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public R<Object> handleServiceException(ConstraintViolationException e) {
        logger.warn("参数验证失败, message:{}", e.getMessage());
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return paramFailed("parameter:" + message);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public R<Object> handleValidationException(ValidationException e) {
        logger.warn("参数验证失败, message:{}", e.getMessage());
        return paramFailed(e.getMessage());
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.warn("不支持当前请求方法, message:{}", e.getMessage());
        return paramFailed("request_method_not_supported");
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public R<Object> handleHttpMediaTypeNotSupportedException(Exception e) {
        logger.warn("不支持当前媒体类型, message:{}", e.getMessage());
        return paramFailed("content_type_not_supported");
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ApiException.class)
    public R<Object> handleRestException(ApiException e) {
        logger.warn("业务逻辑异常, message:{}", e.getErrorCode());
        return R.failed(e.getErrorCode());
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public R<Object> handleException(Exception e) {
        logger.error("系统异常", e);
        return R.failed(ErrorCode.SYSTEM_ERROR);
    }

    /**
     * 操作数据库出现异常:名称重复，外键关联
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public R<Object> handleException(DataIntegrityViolationException e) {
        logger.error("操作数据库系统异常:", e);
        return R.failed(ErrorCode.SYSTEM_ERROR);
    }

}
