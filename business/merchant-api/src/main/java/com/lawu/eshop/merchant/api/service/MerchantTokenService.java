package com.lawu.eshop.merchant.api.service;

import com.lawu.eshop.merchant.api.service.hystrix.MerchantTokenServiceHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Leach
 * @date 2017/3/20
 */
@FeignClient(value = "cache-srv", fallback = MerchantTokenServiceHystrix.class)
public interface MerchantTokenService {

    @RequestMapping(value = "merchantToken/setMerchantTokenOneToOne", method = RequestMethod.PUT)
    void setMerchantTokenOneToOne(@RequestParam("account") String account, @RequestParam("token") String token, @RequestParam("expireSeconds") Integer expireSeconds);

    @RequestMapping(value = "merchantToken/setMerchantTokenOneToMany", method = RequestMethod.PUT)
    void setMerchantTokenOneToMany(@RequestParam("account") String account, @RequestParam("token") String token, @RequestParam("expireSeconds") Integer expireSeconds);

    @RequestMapping(value = "merchantToken/getMerchantAccount", method = RequestMethod.GET)
    String getMerchantAccount(@RequestParam("token") String token, @RequestParam("flushExpireAfterOperation") Boolean flushExpireAfterOperation, @RequestParam("expireSeconds") Integer expireSeconds, @RequestParam("singleTokenWithUser") Boolean singleTokenWithUser);

    @RequestMapping(value = "merchantToken/delMerchantRelationshipByAccount", method = RequestMethod.DELETE)
    void delMerchantRelationshipByAccount(@RequestParam("account") String account);

    @RequestMapping(value = "merchantToken/delMerchantRelationshipByToken", method = RequestMethod.DELETE)
    void delMerchantRelationshipByToken(@RequestParam("token") String token, @RequestParam("singleTokenWithUser") Boolean singleTokenWithUser);
}
