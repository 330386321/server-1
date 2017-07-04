package com.lawu.eshop.merchant.api.event;

import com.lawu.eshop.framework.core.event.AsyncEventHandle;
import com.lawu.eshop.framework.web.interceptor.UserVisitEvent;
import com.lawu.eshop.merchant.api.service.UserVisitService;
import com.lawu.eshop.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhangyong
 * @date 2017/7/3.
 */
public class UserVisitEventHandle implements AsyncEventHandle<UserVisitEvent> {

    @Autowired
    private UserVisitService userVisitService;
    @Override
    public void execute(UserVisitEvent event) {
        String nowTimeStr = DateUtil.getIntDate();

        userVisitService.addUserVisitCount(event.getUserNum(),nowTimeStr);
    }
}
