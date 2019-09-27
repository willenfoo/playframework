package org.apache.playframework.exception;


import com.baomidou.mybatisplus.extension.api.IErrorCode;

/**
 * <p>
 * 错误码
 * </p>
 *
 * @author hubin
 * @Date 2017-02-16
 */
public class CustomErrorCode implements IErrorCode {

    public static final CustomErrorCode SYSTEM_ERROR = new CustomErrorCode(1002, "系统维护中,请稍后再试");

    public static final CustomErrorCode USER_NOT_LOGIN = new CustomErrorCode(1003, "用户未登录");

    public static final CustomErrorCode RESUBMIT = new CustomErrorCode(1004, "重复提交");

    public static final CustomErrorCode PARAMETER__ERROR = new CustomErrorCode(1005, "参数错误");

    private final long code;
    private final String msg;

    public CustomErrorCode(final long code, final String msg) {
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
        return " CustomErrorCode:{code=" + code + ", msg=" + msg + "} ";
    }
}