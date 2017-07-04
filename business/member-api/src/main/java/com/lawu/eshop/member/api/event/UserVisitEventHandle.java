package com.lawu.eshop.member.api.event;

import com.lawu.eshop.framework.core.event.AsyncEventHandle;
import com.lawu.eshop.framework.web.interceptor.UserVisitEvent;
import com.lawu.eshop.member.api.service.UserVisitService;
import com.lawu.eshop.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/7/2
 */
@Component
public class UserVisitEventHandle implements AsyncEventHandle<UserVisitEvent> {

    @Autowired
    private UserVisitService userVisitService;

    @Override
    public void execute(UserVisitEvent event) {
        String nowTimeStr = DateUtil.getIntDate();

        userVisitService.addUserVisitCount(event.getUserNum(),nowTimeStr,event.getUserId());

        // 记录到缓存
    }
}
