package com.lawu.eshop.merchant.api.event;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.lawu.eshop.framework.core.type.UserType;
import com.lawu.eshop.framework.web.interceptor.UserVisitEvent;
import com.lawu.eshop.framework.web.interceptor.UserVisitEventPublish;
import com.lawu.eshop.framework.web.interceptor.VisitConstants;
import com.lawu.eshop.user.param.UserLoginLogParam;
import com.lawu.eshop.utils.DataTransUtil;
import com.lawu.eshop.utils.IpUtil;

/**
 * @author zhangyong
 * @date 2017/7/4.
 */
@Component
public class EventPublisher implements UserVisitEventPublish {
    @Autowired
    ApplicationContext applicationContext;

    public void publishLoginEvent(HttpServletRequest request, String userNum, String account) {
        UserLoginLogParam loginLogParam = new UserLoginLogParam();
        loginLogParam.setUserNum(userNum);
        loginLogParam.setAccount(account);
        loginLogParam.setUserType(UserType.MERCHANT.val);
        loginLogParam.setImei(request.getAttribute(VisitConstants.REQUEST_IMEI) == null ? "" : request.getAttribute(VisitConstants.REQUEST_IMEI).toString());
        loginLogParam.setPlatform(request.getAttribute(VisitConstants.REQUEST_PLATFORM) == null ? DataTransUtil.intToByte(0) : Byte.valueOf(request.getAttribute(VisitConstants.REQUEST_PLATFORM).toString()));
        loginLogParam.setPlatformVer(request.getAttribute(VisitConstants.REQUEST_PLATFORM_VERSION) == null ? "" : request.getAttribute(VisitConstants.REQUEST_PLATFORM_VERSION).toString());
        loginLogParam.setAppVer(request.getAttribute(VisitConstants.REQUEST_APP_VERSION) == null ? "" : request.getAttribute(VisitConstants.REQUEST_APP_VERSION).toString());
        loginLogParam.setCityId(request.getAttribute(VisitConstants.REQUEST_LOCATION_PATH) == null ? 0 : Integer.valueOf(request.getAttribute(VisitConstants.REQUEST_LOCATION_PATH).toString()));
        loginLogParam.setChannel(request.getAttribute(VisitConstants.REQUEST_CHANNEL) == null ? "" : request.getAttribute(VisitConstants.REQUEST_CHANNEL).toString());
        loginLogParam.setIpAddr(IpUtil.getIpAddress(request));
        applicationContext.publishEvent(new LoginEvent(this, loginLogParam));
    }

    @Override
    public void publishUserVisitEvent(String userNum,Long userId) {
        applicationContext.publishEvent(new UserVisitEvent(this, userNum, UserType.MERCHANT,userId));
    }
}
