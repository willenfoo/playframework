package org.apache.playframework.domain;


import org.apache.playframework.enums.ErrorCode;
import org.apache.playframework.exception.IErrorCode;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

/**
 * <p>
 * API REST 返回结果
 * </p>
 *
 * @author hubin
 * @since 2017-02-12
 */
public class ApiResult<T>  {

    public static ApiResult ok(Object object) {
        return restResult(object, HttpStatus.OK, ErrorCode.SUCCESS);
    }

    public static ApiResult failed(String msg) {
        return restResult(new ArrayList<>(), HttpStatus.OK, ErrorCode.FAILED.getCode(), msg);
    }

    public static ApiResult failed(IErrorCode errorCode) {
        return restResult(new ArrayList<>(), HttpStatus.OK, errorCode.getCode(), errorCode.getMsg());
    }

    public static ApiResult restResult(Object object, HttpStatus httpStatus, ErrorCode errorCode) {
        return restResult(object, httpStatus, errorCode.getCode(), errorCode.getMsg());
    }

    private static ApiResult restResult(Object object, HttpStatus httpStatus, Integer code, String msg) {
        return new ApiResult(code, msg, object);
    }

    /**
     * 业务错误码
     */
    private Integer code;
    /**
     * 结果集
     */
    private T data;
    /**
     * 描述
     */
    private String msg;

    private String message;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApiResult(Integer code, String msg, T data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.message = msg;
    }
}