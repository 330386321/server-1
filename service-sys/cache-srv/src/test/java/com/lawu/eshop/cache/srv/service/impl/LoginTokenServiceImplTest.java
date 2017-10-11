package com.lawu.eshop.cache.srv.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lawu.eshop.cache.srv.CacheSrvApplicationTest;
import com.lawu.eshop.cache.srv.EmbeddedRedis;
import com.lawu.eshop.cache.srv.service.LoginTokenService;

/**
 * @author Leach
 * @date 2017/10/11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CacheSrvApplicationTest.class)
public class LoginTokenServiceImplTest extends EmbeddedRedis {

    @Autowired
    private LoginTokenService loginTokenService;

    @Test
    public void setTokenOneToOne() {
        int userType = 1;
        String account = "11111111111";
        String token = "token11111111111";
        loginTokenService.setTokenOneToOne(userType, account, token, 3600);
        String userAccount = loginTokenService.getAccount(userType, token, true, 3600, true);
        Assert.assertEquals(account, userAccount);
    }

    @Test
    public void setTokenOneToMany() {
        int userType = 1;
        String account = "22222222222";
        String token = "token22222222222";
        loginTokenService.setTokenOneToMany(userType, account, token, 3600);
        String userAccount = loginTokenService.getAccount(userType, token, true, 3600, false);
        Assert.assertEquals(account, userAccount);
    }

    @Test
    public void delRelationshipByAccount() {

        int userType = 1;
        String account = "33333333333";
        String token = "token33333333333";
        loginTokenService.setTokenOneToOne(userType, account, token, 3600);

        String userAccount = loginTokenService.getAccount(userType, token, true, 3600, true);
        Assert.assertEquals(account, userAccount);

        loginTokenService.delRelationshipByAccount(userType, account);

        String userAccount2 = loginTokenService.getAccount(userType, token, true, 3600, true);
        Assert.assertNull(userAccount2);
    }

    @Test
    public void delRelationshipByToken() {

        int userType = 1;
        String account = "44444444444";
        String token = "token44444444444";
        loginTokenService.setTokenOneToOne(userType, account, token, 3600);

        String userAccount = loginTokenService.getAccount(userType, token, true, 3600, true);
        Assert.assertEquals(account, userAccount);

        loginTokenService.delRelationshipByToken(userType, token, true);

        String userAccount2 = loginTokenService.getAccount(userType, token, true, 3600, true);
        Assert.assertNull(userAccount2);
    }
}
