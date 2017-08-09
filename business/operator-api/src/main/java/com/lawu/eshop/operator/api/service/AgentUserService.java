package com.lawu.eshop.operator.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lawu.eshop.agent.param.AgentUserParam;
import com.lawu.eshop.framework.web.Result;

/**
 * @author zhangyong
 * @date 2017/8/9.
 */
@FeignClient(value = "agent-srv")
public interface AgentUserService {

    @RequestMapping(value = "user/addAgentUser")
    Result addAgentUser(@RequestBody AgentUserParam param);
}
