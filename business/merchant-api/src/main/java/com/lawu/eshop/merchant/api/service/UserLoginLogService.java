package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.param.UserLoginLogParam;

/**
 * @author meishuquan
 * @date 2017/9/8.
 */
@FeignClient(value = "user-srv")
public interface UserLoginLogService {

    /**
     * 保存商家登录日志
     *
     * @param loginLogParam
     * @return
     * @author meishuquan
     */
    @RequestMapping(value = "merchant/saveLoginLog", method = RequestMethod.POST)
    Result saveLoginLog(@RequestBody UserLoginLogParam loginLogParam);

}
