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

    public static final CustomErrorCode SYSTEM_ERROR = new CustomErrorCode(1000, "系统维护中,请稍后再试!");

    public static final CustomErrorCode USER_NOT_LOGIN = new CustomErrorCode(1001, "用户登录已过期!");

    public static final CustomErrorCode USER_NAME_OR_PASSWORD_ERROR = new CustomErrorCode(1002, "用户名或密码错误!");

    public static final CustomErrorCode CHECK_CODE_ERROR = new CustomErrorCode(1003, "验证码错误!");

    public static final CustomErrorCode RESUBMIT = new CustomErrorCode(1004, "重复提交!");

    public static final CustomErrorCode PARAMETER_ERROR = new CustomErrorCode(1005, "参数错误!");

    public static final CustomErrorCode DATA_ERROR = new CustomErrorCode(1006, "数据错误!");

    public static final CustomErrorCode MISS_HEADER_PARAM = new CustomErrorCode(1007, "缺少请求头参数!");

    public static final CustomErrorCode REQUEST_TIMEOUT = new CustomErrorCode(1008, "请求已超时!");

    public static final CustomErrorCode BANNED_USER = new CustomErrorCode(1009, "用户已被封禁!");

    public static final CustomErrorCode USER_OTHER_PLACE_LOGIN = new CustomErrorCode(1010, "您的账号在其它地方登录, 请及时修改密码!");

    public static final CustomErrorCode INVALID_SIGN = new CustomErrorCode(1011, "无效的签名!");

    private final long code;

    private final String msg;

    public CustomErrorCode(final String msg) {
        this.code = DATA_ERROR.code;
        this.msg = msg;
    }

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