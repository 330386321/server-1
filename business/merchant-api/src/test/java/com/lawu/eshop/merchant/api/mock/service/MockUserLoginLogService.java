package com.lawu.eshop.merchant.api.mock.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.UserLoginLogService;
import com.lawu.eshop.user.param.UserLoginLogParam;

/**
 * @author meishuquan
 * @date 2017/9/8.
 */
@Service
public class MockUserLoginLogService extends BaseController implements UserLoginLogService {

    @Override
    public Result saveLoginLog(@RequestBody UserLoginLogParam loginLogParam) {
        return null;
    }
}
