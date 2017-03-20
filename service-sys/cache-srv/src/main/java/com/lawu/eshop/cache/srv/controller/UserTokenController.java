package com.lawu.eshop.cache.srv.controller;

import com.lawu.eshop.cache.srv.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leach
 * @date 2017/3/20
 */
@RestController
@RequestMapping(value = "userToken/")
public class UserTokenController {

    @Autowired
    private UserTokenService userTokenService;

    @RequestMapping(value = "setMemberTokenOneToOne", method = RequestMethod.PUT)
    public void setMemberTokenOneToOne(String account, String token, Integer expireSeconds) {
        userTokenService.setMemberTokenOneToOne(account, token, expireSeconds);
    }

    @RequestMapping(value = "setMemberTokenOneToMany", method = RequestMethod.PUT)
    public void setMemberTokenOneToMany(String account, String token, Integer expireSeconds) {
        userTokenService.setMemberTokenOneToMany(account, token, expireSeconds);
    }

    @RequestMapping(value = "getMemberAccount", method = RequestMethod.GET)
    public String getMemberAccount(String token, Boolean flushExpireAfterOperation, Integer expireSeconds, Boolean singleTokenWithUser) {
        return userTokenService.getMemberAccount(token, flushExpireAfterOperation, expireSeconds, singleTokenWithUser);
    }

    @RequestMapping(value = "delMemberRelationshipByAccount", method = RequestMethod.DELETE)
    public void delMemberRelationshipByAccount(String account) {
        userTokenService.delMemberRelationshipByAccount(account);
    }

    @RequestMapping(value = "delMemberRelationshipByToken", method = RequestMethod.DELETE)
    public void delMemberRelationshipByToken(String token, Boolean singleTokenWithUser) {
        userTokenService.delMemberRelationshipByToken(token, singleTokenWithUser);
    }
}
