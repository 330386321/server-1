package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Leach
 * @date 2017/3/20
 */
@FeignClient(value = "cache-srv")
public interface MerchantTokenService {

    @RequestMapping(value = "userToken/setMerchantTokenOneToOne", method = RequestMethod.PUT)
    void setMerchantTokenOneToOne(@RequestParam("account") String account, @RequestParam("token") String token, @RequestParam("expireSeconds") Integer expireSeconds);

    @RequestMapping(value = "userToken/setMerchantTokenOneToMany", method = RequestMethod.PUT)
    void setMerchantTokenOneToMany(@RequestParam("account") String account, @RequestParam("token") String token, @RequestParam("expireSeconds") Integer expireSeconds);

    @RequestMapping(value = "userToken/getMerchantAccount", method = RequestMethod.GET)
    String getMerchantAccount(@RequestParam("token") String token, @RequestParam("flushExpireAfterOperation") Boolean flushExpireAfterOperation, @RequestParam("expireSeconds") Integer expireSeconds, @RequestParam("singleTokenWithUser") Boolean singleTokenWithUser);

    @RequestMapping(value = "userToken/delMerchantRelationshipByAccount", method = RequestMethod.DELETE)
    void delMerchantRelationshipByAccount(@RequestParam("account") String account);

    @RequestMapping(value = "userToken/delMerchantRelationshipByToken", method = RequestMethod.DELETE)
    void delMerchantRelationshipByToken(@RequestParam("token") String token, @RequestParam("singleTokenWithUser") Boolean singleTokenWithUser);
}
