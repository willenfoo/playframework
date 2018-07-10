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
    SUCCESS(0, "成功"),
    FAILED(-1, "失败"),
    SYSTEM_ERROR(10000002, "系统维护中,请稍后再试");

    private final Integer code;
    private final String msg;

    ErrorCode(final Integer code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ErrorCode fromCode(Integer code) {
        ErrorCode[] ecs = ErrorCode.values();
        for (ErrorCode ec : ecs) {
            if (ec.getCode().equals(code)) {
                return ec;
            }
        }
        return SUCCESS;
    }

    @Override
    public Integer getCode() {
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
