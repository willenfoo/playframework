package org.apache.playframework.exception;

/**
 * <p>
 * REST API 错误码接口
 * </p>
 *
 * @author hubin
 * @since 2018-06-05
 */
public interface IErrorCode {

    /**
     * 错误编码 0、失败 1、正常
     */
    String getCode();

    /**
     * 错误描述
     */
    String getMsg();
}
