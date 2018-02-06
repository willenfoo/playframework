package org.apache.playframework.domain;


import java.util.HashMap;
import java.util.Map;

import org.apache.playframework.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * <p>
 * API REST 返回结果
 * </p>
 *
 * @author hubin
 * @since 2017-02-12
 */
public class RestResult<T> extends ResponseEntity<T> {

    public RestResult(HttpStatus status) {
        super(status);
    }

    public RestResult(T body, HttpStatus status) {
        super(body, status);
    }

    public static RestResult ok(Object object) {
        return restResult(object, HttpStatus.OK, ErrorCode.SUCCESS);
    }

    public static RestResult failed(String msg) {
        return restResult(null, HttpStatus.OK, ErrorCode.FAILED.getCode(), msg);
    }

    public static RestResult failed(ErrorCode errorCode) {
        return restResult(null, HttpStatus.OK, errorCode);
    }

    public static RestResult restResult(Object object, HttpStatus httpStatus, ErrorCode errorCode) {
        return restResult(object, httpStatus, errorCode.getCode(), errorCode.getMsg());
    }

    private static RestResult restResult(Object object, HttpStatus httpStatus, String code, String msg) {
        Map<String, Object> data = new HashMap<>();
        data.put("code", code);// 业务错误码
        data.put("data", object);// 结果集
        data.put("msg", msg);// 描述
        return new RestResult(data, httpStatus);
    }
}
