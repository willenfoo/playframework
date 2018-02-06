package org.apache.playframework.enums;

/**
 * <p>
 * 错误码
 * </p>
 *
 * @author hubin
 * @Date 2017-02-16
 */
public enum ErrorCode {
    SUCCESS("0000", "成功"),
    FAILED("0001", "失败"),
    PARAMS_ERROR("0002", "参数有误"),
    TOKEN_ERROR("-1", "失败 RestToken 错误"),

    ;

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

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return " ErrorCode:{code=" + code + ", msg=" + msg + "} ";
    }
}
