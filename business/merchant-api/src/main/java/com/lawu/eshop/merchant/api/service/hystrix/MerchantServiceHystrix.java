package com.lawu.eshop.merchant.api.service.hystrix;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.MerchantService;
import com.lawu.eshop.user.param.RegisterParam;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/3/22
 */
@Component
public class MerchantServiceHystrix  implements MerchantService {


    @Override
    public Result updateLoginPwd(@RequestParam("id") Long id, @RequestParam("originalPwd") String originalPwd, @RequestParam("newPwd") String newPwd) {
        return null;
    }

    @Override
    public Result getInviterByAccount(@RequestParam("account") String account) {
        return null;
    }

    @Override
    public Result register(@RequestBody RegisterParam registerParam) {
        return null;
    }
}
