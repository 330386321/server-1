package com.lawu.eshop.user.srv.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.dto.param.UserParam;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.converter.MemberConverter;
import com.lawu.eshop.user.srv.domain.MemberDO;
import com.lawu.eshop.user.srv.domain.MemberDOExample;
import com.lawu.eshop.user.srv.mapper.MemberDOMapper;
import com.lawu.eshop.user.srv.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员信息服务实现
 *
 * @author Leach
 * @date 2017/3/13
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDOMapper memberDOMapper;

    @Override
    public MemberBO find(String account, String pwd) {

        MemberDOExample example = new MemberDOExample();
        example.createCriteria().andAccountEqualTo(account).andPwdEqualTo(pwd);
        List<MemberDO> memberDOS = memberDOMapper.selectByExample(example);

        return memberDOS.isEmpty() ? null : MemberConverter.convertBO(memberDOS.get(0));
    }

    @Override
    public MemberBO findMemberInfoById(Long id) {

        MemberDOExample example = new MemberDOExample();
        example.createCriteria().andIdEqualTo(id);
        MemberDO memberDO = memberDOMapper.selectByPrimaryKey(id);

        return MemberConverter.convertBO(memberDO);
    }

    @Override
    public void updateMemberInfo(UserParam memberParam) {

        MemberDO memberDO = MemberConverter.convertDOOther(memberParam);
        memberDOMapper.updateByPrimaryKeySelective(memberDO);
        System.out.println("member 资料修改成功");

    }

    @Override
    public void updatePwd(Long id, String pwd) {
        MemberDO member = new MemberDO();
        member.setId(id);
        member.setPwd(pwd);
        memberDOMapper.updateByPrimaryKeySelective(member);
    }

}
