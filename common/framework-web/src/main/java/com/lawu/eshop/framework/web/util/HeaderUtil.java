package com.lawu.eshop.framework.web.util;

import javax.servlet.http.HttpServletRequest;

import com.lawu.eshop.framework.web.interceptor.VisitConstants;

public class HeaderUtil {

	
	/**
     * 获取当前登录用户channel
     *
     * @param request
     * @return
     */
    public static String getRequestChannel(HttpServletRequest request) {
        Object channel = request.getAttribute(VisitConstants.REQUEST_CHANNEL);
        if (channel == null) {
            return "";
        }
        return channel.toString();
    }
    
    /**
     * 获取当前登录用户platform
     *
     * @param request
     * @return
     */
    public static byte getRequestPlatform(HttpServletRequest request) {
        Object platform = request.getAttribute(VisitConstants.HEADER_PLATFORM);
        if (platform == null) {
            return 0;
        }
        return (byte)platform;
    }
}
