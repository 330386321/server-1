package com.lawu.eshop.framework.web.interceptor;

import com.lawu.eshop.authorization.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户访问拦截器
 *
 * @author Leach
 * @date 2016/6/30
 */
public class UserVisitInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserVisitEventPublish userVisitEventPublish;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 将header中的值存放到request中
        request.setAttribute(VisitConstants.REQUEST_IMEI, request.getHeader(VisitConstants.HEADER_IMEI));
        request.setAttribute(VisitConstants.REQUEST_PLATFORM, request.getHeader(VisitConstants.HEADER_PLATFORM));
        request.setAttribute(VisitConstants.REQUEST_PLATFORM_VERSION, request.getHeader(VisitConstants.HEADER_PLATFORM_VERSION));
        request.setAttribute(VisitConstants.REQUEST_APP_VERSION, request.getHeader(VisitConstants.HEADER_APP_VERSION));
        request.setAttribute(VisitConstants.REQUEST_LOCATION_PATH, request.getHeader(VisitConstants.HEADER_LOCATION_PATH));
        request.setAttribute(VisitConstants.REQUEST_CHANNEL, request.getHeader(VisitConstants.HEADER_CHANNEL));

        String currentUserNum = UserUtil.getCurrentUserNum(request);

        if (StringUtils.isNotBlank(currentUserNum)) {

            userVisitEventPublish.publishUserVisitEvent(currentUserNum);
        }

        return true;
    }
}
