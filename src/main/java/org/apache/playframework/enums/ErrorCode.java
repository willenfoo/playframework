package org.apache.playframework.enums;

import com.baomidou.mybatisplus.extension.api.IErrorCode;

/**
 * <p>
 * 错误码
 * </p>
 *
 * @author hubin
 * @Date 2017-02-16
 */
public enum ErrorCode implements IErrorCode {

    SYSTEM_ERROR(1002, "系统维护中,请稍后再试"),
    USER_NOT_LOGIN(1003, "用户未登录"),
    RESUBMIT(1004, "重复提交"),
    PARAMETER__ERROR(1004, "参数错误");

    private final long code;
    private final String msg;

    ErrorCode(final long code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return " ErrorCode:{code=" + code + ", msg=" + msg + "} ";
    }
}

