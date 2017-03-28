package com.lawu.eshop.authorization.util;

import com.lawu.eshop.authorization.interceptor.AuthorizationInterceptor;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leach
 * @date 2017/3/15
 */
public class UserUtil {

    /**
     * 获取当前登录用户ID
     *
     * @param request
     * @return
     */
    public static Long getCurrentUserId(HttpServletRequest request) {
        Object userId = request.getAttribute(AuthorizationInterceptor.REQUEST_CURRENT_USER_ID);
        if (userId == null) {
            return 0L;
        }

        return Long.valueOf(userId.toString());
    }

    /**
     * 获取当前登录用户编号
     *
     * @param request
     * @return
     */
    public static String getCurrentUserNum(HttpServletRequest request) {
        Object userNum = request.getAttribute(AuthorizationInterceptor.REQUEST_CURRENT_USER_NUM);
        return userNum == null ? null : userNum.toString();
    }

    /**
     * 获取当前登录用户账号
     *
     * @param request
     * @return
     */
    public static String getCurrentAccount(HttpServletRequest request) {
        Object account = request.getAttribute(AuthorizationInterceptor.REQUEST_CURRENT_ACCOUNT);
        return account == null ? null : account.toString();
    }
}
