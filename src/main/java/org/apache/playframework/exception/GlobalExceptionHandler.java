package org.apache.playframework.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.apache.playframework.domain.ApiResult;
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
import org.springframework.web.bind.annotation.*;


/**
 * @author willenfoo
 * @description:全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * 400 - Bad Request
	 * 缺少请求参数
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ApiResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
		logger.warn("缺少请求参数", e);
		return ApiResult.failed(e.getMessage());
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ApiResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		logger.warn("参数解析失败", e);
		return ApiResult.failed(e.getMessage());
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		logger.warn("参数验证失败", e);
		BindingResult result = e.getBindingResult();
		FieldError error = result.getFieldError();
		String field = error.getField();
		String msg = error.getDefaultMessage();
		String message = String.format("%s:%s", field, msg);
		return ApiResult.failed(message);
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public ApiResult handleBindException(BindException e) {
		logger.warn("参数绑定失败", e);
		BindingResult result = e.getBindingResult();
		FieldError error = result.getFieldError();
		String field = error.getField();
		String msg = error.getDefaultMessage();
		String message = String.format("%s:%s", field, msg);
		return ApiResult.failed(message);
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public ApiResult handleServiceException(ConstraintViolationException e) {
		logger.warn("参数验证失败", e);
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		ConstraintViolation<?> violation = violations.iterator().next();
		String message = violation.getMessage();
		return ApiResult.failed("parameter:" + message);
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	public ApiResult handleValidationException(ValidationException e) {
		logger.warn("参数验证失败", e);
		return ApiResult.failed("validation_exception");
	}

	/**
	 * 405 - Method Not Allowed
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ApiResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		logger.warn("不支持当前请求方法", e);
		return ApiResult.failed("request_method_not_supported");
	}

	/**
	 * 415 - Unsupported Media Type
	 */
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ApiResult handleHttpMediaTypeNotSupportedException(Exception e) {
		logger.warn("不支持当前媒体类型", e);
		return ApiResult.failed("content_type_not_supported");
	}

	/**
	 * 500 - Internal Server Error
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ApitException.class)
	public ApiResult handleRestException(ApitException e) {
		logger.warn("业务逻辑异常", e);
		return ApiResult.failed(e.getErrorCode());
	}

	/**
	 * 500 - Internal Server Error
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ApiResult handleException(Exception e) {
		logger.error("系统异常", e);
		return ApiResult.failed(ErrorCode.SYSTEM_ERROR);
	}

	/**
	 * 操作数据库出现异常:名称重复，外键关联
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ApiResult handleException(DataIntegrityViolationException e) {
		logger.error("操作数据库系统异常:", e);
		return ApiResult.failed(ErrorCode.SYSTEM_ERROR);
	}

}
	

