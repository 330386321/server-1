package com.lawu.eshop.agent.srv.service;

import com.lawu.eshop.agent.param.AgentUserParam;
import com.lawu.eshop.agent.srv.bo.AgentUserBO;

/**
 * @author zhangyong
 * @date 2017/8/9.
 */
public interface AgentUserService {

    AgentUserBO find(String account, String pwd);

    void addAgentUser(AgentUserParam param);

    boolean findUserByAccount(String account);

    boolean findUserByMobile(String mobile);
}
