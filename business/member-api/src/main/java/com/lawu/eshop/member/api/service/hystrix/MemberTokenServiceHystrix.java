package com.lawu.eshop.member.api.service.hystrix;

import com.lawu.eshop.member.api.service.MemberTokenService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Leach
 * @date 2017/3/20
 */
@Component
public class MemberTokenServiceHystrix implements MemberTokenService {

    @Override
    public void setMemberTokenOneToOne(@RequestParam("account") String account, @RequestParam("token") String token, @RequestParam("expireSeconds") Integer expireSeconds) {

    }

    @Override
    public void setMemberTokenOneToMany(@RequestParam("account") String account, @RequestParam("token") String token, @RequestParam("expireSeconds") Integer expireSeconds) {

    }

    @Override
    public String getMemberAccount(@RequestParam("token") String token, @RequestParam("flushExpireAfterOperation") Boolean flushExpireAfterOperation, @RequestParam("expireSeconds") Integer expireSeconds, @RequestParam("singleTokenWithUser") Boolean singleTokenWithUser) {
        return null;
    }

    @Override
    public void delMemberRelationshipByAccount(@RequestParam("account") String account) {

    }

    @Override
    public void delMemberRelationshipByToken(@RequestParam("token") String token, @RequestParam("singleTokenWithUser") Boolean singleTokenWithUser) {

    }
}
