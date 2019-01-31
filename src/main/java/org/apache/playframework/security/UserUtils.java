package org.apache.playframework.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 得到用户id工具类
 */
public class UserUtils {

    /**
     * 得到当前登录用户id
     * @return
     */
    public static String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserInfo) {
                return ((UserInfo) principal).getUserId();
            }
        }
        return null;
    }

    /**
     * 得到当前登录用户的真实姓名
     * @return
     */
    public static String getRealName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserInfo) {
                return ((UserInfo) principal).getRealName();
            }
        }
        return null;
    }

    /**
     * 得到当前登录用户的商户id
     * @return
     */
    public static String getMerchantId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserInfo) {
                return ((UserInfo) principal).getMerchantId();
            }
        }
        return null;
    }


    /**
     * 得到当前登录用户名
     * @return
     */
    public static String getUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        if (principal instanceof UserInfo) {
            return ((UserInfo)principal).getUsername();
        } else {
            return principal.toString();
        }
    }

}
