package com.lawu.eshop.authorization.impl;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Leach
 * @date 2017/10/11
 */
@FeignClient(value = "cache-srv", path = "loginToken/")
public interface LoginTokenService {

    @RequestMapping(value = "setTokenOneToOne", method = RequestMethod.PUT)
    void setTokenOneToOne(@RequestParam("userType") int userType, @RequestParam("account") String account, @RequestParam("token") String token, @RequestParam("expireSeconds") Integer expireSeconds);

    @RequestMapping(value = "setTokenOneToMany", method = RequestMethod.PUT)
    void setTokenOneToMany(@RequestParam("userType") int userType, @RequestParam("account") String account, @RequestParam("token") String token, @RequestParam("expireSeconds") Integer expireSeconds);

    @RequestMapping(value = "getAccount", method = RequestMethod.GET)
    String getAccount(@RequestParam("userType") int userType, @RequestParam("token") String token, @RequestParam("flushExpireAfterOperation") Boolean flushExpireAfterOperation, @RequestParam("expireSeconds") Integer expireSeconds, @RequestParam("singleTokenWithUser") Boolean singleTokenWithUser);

    @RequestMapping(value = "delRelationshipByAccount", method = RequestMethod.DELETE)
    void delRelationshipByAccount(@RequestParam("userType") int userType, @RequestParam("account") String account);

    @RequestMapping(value = "delRelationshipByToken", method = RequestMethod.DELETE)
    void delRelationshipByToken(@RequestParam("userType") int userType, @RequestParam("token") String token, @RequestParam("singleTokenWithUser") Boolean singleTokenWithUser);
}
