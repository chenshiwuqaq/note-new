package org.ash.demos.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.ash.demos.Entity.user;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CurrentUserUtils {

    /**
     * 获取当前用户ID
     */
    public static Integer getCurrentUserId() {
        HttpServletRequest request = getRequest();
        if (request == null) return null;

        HttpSession session = request.getSession(false);
        if (session != null) {
            Object userId = session.getAttribute("userId");
            if (userId instanceof Integer) {
                return (Integer) userId;
            }
        }
        return null;
    }

    /**
     * 获取当前用户账号
     */
    public static String getCurrentUserAccount() {
        HttpServletRequest request = getRequest();
        if (request == null) return null;

        HttpSession session = request.getSession(false);
        if (session != null) {
            Object account = session.getAttribute("userAccount");
            if (account != null) {
                return account.toString();
            }
        }
        return null;
    }

    /**
     * 获取当前用户完整信息
     */
    public static user getCurrentUser() {
        HttpServletRequest request = getRequest();
        if (request == null) return null;

        HttpSession session = request.getSession(false);
        if (session != null) {
            Object userObj = session.getAttribute("user");
            if (userObj instanceof user) {
                return (user) userObj;
            }
        }
        return null;
    }

    /**
     * 获取当前请求
     */
    private static HttpServletRequest getRequest() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes)
                    RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                return attributes.getRequest();
            }
        } catch (Exception e) {
            // 非Web环境，如单元测试
        }
        return null;
    }
}