package com.lawu.eshop.agent.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.agent.constants.AgentUserCommonConstant;
import com.lawu.eshop.agent.constants.StatusEnum;
import com.lawu.eshop.agent.param.AgentUserParam;
import com.lawu.eshop.agent.srv.bo.AgentUserBO;
import com.lawu.eshop.agent.srv.converter.AgentUserConverter;
import com.lawu.eshop.agent.srv.domain.AgentUserDO;
import com.lawu.eshop.agent.srv.domain.AgentUserDOExample;
import com.lawu.eshop.agent.srv.mapper.AgentUserDOMapper;
import com.lawu.eshop.agent.srv.service.AgentUserService;
import com.lawu.eshop.utils.PwdUtil;
import com.lawu.eshop.utils.RandomUtil;

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

    @Override
    @Transactional
    public void addAgentUser(AgentUserParam param) {
        String userNum = RandomUtil.getTableNumRandomString(AgentUserCommonConstant.AGENT_NUM_TAG);
        AgentUserDO agentUserDO = new AgentUserDO();
        agentUserDO.setMobile(param.getMobile());
        agentUserDO.setGmtCreate(new Date());
        agentUserDO.setGmtModified(new Date());
        agentUserDO.setStatus(StatusEnum.STATUS_VALID.getVal());
        agentUserDO.setRegionName(param.getRegionName());
        agentUserDO.setAccount(param.getAccount());
        agentUserDO.setName(param.getName());
        agentUserDO.setRegionPath(param.getRegionPath());
        agentUserDO.setNum(userNum);
        agentUserDO.setPwd(PwdUtil.generate(param.getPwd()));
        agentUserDOMapper.insertSelective(agentUserDO);
    }

    @Override
    public boolean findUserByAccount(String account) {
        AgentUserDOExample example = new AgentUserDOExample();
        example.createCriteria().andAccountEqualTo(account).andStatusEqualTo(StatusEnum.STATUS_VALID.getVal());
        List<AgentUserDO> userDOS = agentUserDOMapper.selectByExample(example);
        if(!userDOS.isEmpty()){
            //存在
            return true;
        }
        return false;
    }

    @Override
    public boolean findUserByMobile(String mobile) {
        AgentUserDOExample example = new AgentUserDOExample();
        example.createCriteria().andMobileEqualTo(mobile).andStatusEqualTo(StatusEnum.STATUS_VALID.getVal());
        List<AgentUserDO> userDOS = agentUserDOMapper.selectByExample(example);
        if(!userDOS.isEmpty()){
            //存在
            return true;
        }
        return false;
    }
}
