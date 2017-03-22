package com.lawu.eshop.merchant.api.service.hystrix;

import com.lawu.eshop.merchant.api.service.MerchantService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/3/22
 */
@Component
public class MerchantServiceHystrix implements MerchantService{

    @Override
    public void updatePwd(@RequestParam("id") Long id, @RequestParam("pwd") String pwd) {

    }
}
