package org.apache.playframework.exception;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.lang.reflect.Field;
import java.util.Set;


/**
 * @author willenfoo
 * @description:全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private R<Object> paramFailed(String message, Boolean appendFlag) {
        R<Object> r = new R<>();
        r.setCode(CustomErrorCode.PARAMETER_ERROR.getCode());
        if (appendFlag) {
            r.setMsg(CustomErrorCode.PARAMETER_ERROR.getMsg() + "," + message);
        } else {
            r.setMsg(message);
        }
        return r;
    }

    private R<Object> paramFailed(String message) {
        return paramFailed(message, true);
    }

    /**
     * 400 - Bad Request
     * 缺少请求参数
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ServletRequestBindingException.class)
    public R<Object> handleMissingServletRequestParameterException(ServletRequestBindingException e) {
        logger.warn("缺少请求参数, message:{}", e.getMessage());
        return paramFailed(e.getMessage());
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.warn("参数解析失败, message:{}", e.getCause().getMessage());
        return paramFailed(e.getCause().getMessage());
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();

        String field = error.getField();
        String msg = error.getDefaultMessage();
        String message = String.format("%s:%s", field, msg);
        logger.warn("参数验证失败, message:{}", String.format("%s:%s, 接到前端参数为:%s", field, msg, error.getRejectedValue()));

        Field fieldViolation = ReflectionUtils.findField(error.getClass(), "violation", ConstraintViolation.class);
        fieldViolation.setAccessible(true);
        ConstraintViolation<?> violation = (ConstraintViolation<?>) ReflectionUtils.getField(fieldViolation, error);
        String messageTemplate = violation.getMessageTemplate();
        if (StrUtil.indexOfIgnoreCase(messageTemplate, "{") == -1) {
            return paramFailed(messageTemplate, false);
        }
        return paramFailed(message);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
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
    @ResponseStatus(HttpStatus.OK)
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
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ValidationException.class)
    public R<Object> handleValidationException(ValidationException e) {
        logger.warn("参数验证失败, message:{}", e.getMessage());
        return paramFailed(e.getMessage());
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.OK)
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
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public R<Object> handleException(Exception e) {
        logger.error("系统异常", e);
        return R.failed(CustomErrorCode.SYSTEM_ERROR);
    }

    /**
     * 操作数据库出现异常:名称重复，外键关联
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(DataAccessException.class)
    public R<Object> handleException(DataAccessException e) {
        logger.error("操作数据库系统异常:", e);
        return R.failed(CustomErrorCode.SYSTEM_ERROR);
    }

}
