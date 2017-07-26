package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.merchant.api.service.MerchantTokenService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockMerchantTokenService implements MerchantTokenService {
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
