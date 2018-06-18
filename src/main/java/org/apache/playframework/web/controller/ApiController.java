package org.apache.playframework.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.playframework.domain.ApiResult;
import org.apache.playframework.exception.IErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * REST API 通用控制器
 * </p>
 *
 * @author hubin
 * @since 2018-06-08
 */
public class ApiController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;


    /**
     * <p>
     * 请求成功
     * </p>
     *
     * @param data 数据内容
     * @param <T>  对象泛型
     * @return
     */
    protected <T> ApiResult<T> success(T data) {
        return ApiResult.ok(data);
    }

    /**
     * <p>
     * 请求失败
     * </p>
     *
     * @param msg 提示内容
     * @return
     */
    protected ApiResult<Object> failed(String msg) {
        return ApiResult.failed(msg);
    }

    /**
     * <p>
     * 请求失败
     * </p>
     *
     * @param errorCode 请求错误码
     * @return
     */
    protected ApiResult<Object> failed(IErrorCode errorCode) {
        return ApiResult.failed(errorCode);
    }

}