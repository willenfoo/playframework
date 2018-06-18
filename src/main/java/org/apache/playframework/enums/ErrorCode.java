package org.apache.playframework.enums;


import org.apache.playframework.exception.IErrorCode;

/**
 * <p>
 * 错误码
 * </p>
 *
 * @author hubin
 * @Date 2017-02-16
 */
public enum ErrorCode implements IErrorCode {
    SUCCESS("0000", "成功"),
    FAILED("0001", "失败"),
    SYSTEM_ERROR("0002", "系统维护中,请稍后再试"),
    USER_NOT_LOGIN("0003", "用户未登录"),
    USER_SESSION_EXPIRE("0004", "用户session过期"),
    TOKEN_ERROR("-1", "失败 RestToken 错误"),;

    private final String code;
    private final String msg;

    ErrorCode(final String code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ErrorCode fromCode(String code) {
        ErrorCode[] ecs = ErrorCode.values();
        for (ErrorCode ec : ecs) {
            if (ec.getCode().equalsIgnoreCase(code)) {
                return ec;
            }
        }
        return SUCCESS;
    }

    @Override
    public String getCode() {
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
