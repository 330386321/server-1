package com.lawu.eshop.cache.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.cache.srv.service.LoginTokenService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;

/**
 * @author Leach
 * @date 2017/10/11
 */
@RestController
@RequestMapping(value = "loginToken/")
public class LoginTokenController extends BaseController {

    @Autowired
    private LoginTokenService loginTokenService;

    @RequestMapping(value = "setTokenOneToOne", method = RequestMethod.PUT)
    public Result setTokenOneToOne(int userType, String account, String token, Integer expireSeconds) {
        loginTokenService.setTokenOneToOne(userType, account, token, expireSeconds);
        return successCreated();
    }

    @RequestMapping(value = "setTokenOneToMany", method = RequestMethod.PUT)
    public Result setTokenOneToMany(int userType, String account, String token, Integer expireSeconds) {
        loginTokenService.setTokenOneToMany(userType, account, token, expireSeconds);
        return successCreated();
    }

    @RequestMapping(value = "getAccount", method = RequestMethod.GET)
    public Result<String> getAccount(int userType, String token, Boolean flushExpireAfterOperation, Integer expireSeconds, Boolean singleTokenWithUser) {
        String account = loginTokenService.getAccount(userType, token, flushExpireAfterOperation, expireSeconds, singleTokenWithUser);
        return successGet(account);
    }

    @RequestMapping(value = "delRelationshipByAccount", method = RequestMethod.DELETE)
    public Result delRelationshipByAccount(int userType, String account) {
        loginTokenService.delRelationshipByAccount(userType, account);
        return successDelete();
    }

    @RequestMapping(value = "delRelationshipByToken", method = RequestMethod.DELETE)
    public Result delRelationshipByToken(int userType, String token, Boolean singleTokenWithUser) {
        loginTokenService.delRelationshipByToken(userType, token, singleTokenWithUser);
        return successDelete();
    }

}
