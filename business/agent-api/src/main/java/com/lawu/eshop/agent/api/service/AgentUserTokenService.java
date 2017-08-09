package com.lawu.eshop.agent.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhangyong
 * @date 2017/8/9.
 */
@FeignClient(value = "cache-srv")
public interface AgentUserTokenService {

    @RequestMapping(value = "userToken/setMemberTokenOneToOne", method = RequestMethod.PUT)
    void setMemberTokenOneToOne(@RequestParam("account") String account, @RequestParam("token") String token, @RequestParam("expireSeconds") Integer expireSeconds);

    @RequestMapping(value = "userToken/setMemberTokenOneToMany", method = RequestMethod.PUT)
    void setMemberTokenOneToMany(@RequestParam("account") String account, @RequestParam("token") String token, @RequestParam("expireSeconds") Integer expireSeconds);

    @RequestMapping(value = "userToken/getMemberAccount", method = RequestMethod.GET)
    String getMemberAccount(@RequestParam("token") String token, @RequestParam("flushExpireAfterOperation") Boolean flushExpireAfterOperation, @RequestParam("expireSeconds") Integer expireSeconds, @RequestParam("singleTokenWithUser") Boolean singleTokenWithUser);

    @RequestMapping(value = "userToken/delMemberRelationshipByAccount", method = RequestMethod.DELETE)
    void delMemberRelationshipByAccount(@RequestParam("account") String account);

    @RequestMapping(value = "userToken/delMemberRelationshipByToken", method = RequestMethod.DELETE)
    void delMemberRelationshipByToken(@RequestParam("token") String token, @RequestParam("singleTokenWithUser") Boolean singleTokenWithUser);
}
