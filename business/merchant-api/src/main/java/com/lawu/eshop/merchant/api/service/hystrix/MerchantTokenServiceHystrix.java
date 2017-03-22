package com.lawu.eshop.merchant.api.service.hystrix;

import com.lawu.eshop.merchant.api.service.MerchantTokenService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Leach
 * @date 2017/3/20
 */
@Component
public class MerchantTokenServiceHystrix implements MerchantTokenService {

    @Override
    public void setMerchantTokenOneToOne(@RequestParam("account") String account, @RequestParam("token") String token, @RequestParam("expireSeconds") Integer expireSeconds) {

    }

    @Override
    public void setMerchantTokenOneToMany(@RequestParam("account") String account, @RequestParam("token") String token, @RequestParam("expireSeconds") Integer expireSeconds) {

    }

    @Override
    public String getMerchantAccount(@RequestParam("token") String token, @RequestParam("flushExpireAfterOperation") Boolean flushExpireAfterOperation, @RequestParam("expireSeconds") Integer expireSeconds, @RequestParam("singleTokenWithUser") Boolean singleTokenWithUser) {
        return null;
    }

    @Override
    public void delMerchantRelationshipByAccount(@RequestParam("account") String account) {

    }

    @Override
    public void delMerchantRelationshipByToken(@RequestParam("token") String token, @RequestParam("singleTokenWithUser") Boolean singleTokenWithUser) {

    }
}
