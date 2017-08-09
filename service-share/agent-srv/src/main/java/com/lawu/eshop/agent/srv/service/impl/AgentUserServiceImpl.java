package com.lawu.eshop.agent.srv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.agent.constants.StatusEnum;
import com.lawu.eshop.agent.srv.bo.AgentUserBO;
import com.lawu.eshop.agent.srv.converter.AgentUserConverter;
import com.lawu.eshop.agent.srv.domain.AgentUserDO;
import com.lawu.eshop.agent.srv.domain.AgentUserDOExample;
import com.lawu.eshop.agent.srv.mapper.AgentUserDOMapper;
import com.lawu.eshop.agent.srv.service.AgentUserService;
import com.lawu.eshop.utils.PwdUtil;

/**
 * @author zhangyong
 * @date 2017/8/9.
 */
@Service
public class AgentUserServiceImpl implements AgentUserService {

    @Autowired
    private AgentUserDOMapper agentUserDOMapper;

    @Override
    public AgentUserBO find(String account, String pwd) {
        AgentUserDOExample example = new AgentUserDOExample();
        example.createCriteria().andAccountEqualTo(account).andStatusEqualTo(StatusEnum.STATUS_VALID.getVal());
        List<AgentUserDO> userDOS = agentUserDOMapper.selectByExample(example);
        if (!userDOS.isEmpty()) {
            if (PwdUtil.verify(pwd, userDOS.get(0).getPwd())) {
                return AgentUserConverter.convertBO(userDOS.get(0));
            } else {
                return null;
            }
        }
        return null;
    }
}
