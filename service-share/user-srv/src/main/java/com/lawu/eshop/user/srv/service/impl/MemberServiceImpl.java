package com.lawu.eshop.user.srv.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.lawu.eshop.user.dto.UserDTO;
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
    public void updateMemberInfo(UserDTO memberParam) {
        MemberDOExample example = new MemberDOExample();
        if (memberParam.getId() != null) {
            example.createCriteria().andIdEqualTo(memberParam.getId());
        } else if (!StringUtils.isEmpty(memberParam.getNickname())) {//昵称
            example.createCriteria().andNicknameEqualTo(memberParam.getNickname());
        } else if (!StringUtils.isEmpty(memberParam.getHeadimg())) {//头像
            example.createCriteria().andHeadimgEqualTo(memberParam.getHeadimg());
        } else if (!StringUtils.isEmpty(memberParam.getName())) {//名称
            example.createCriteria().andNameEqualTo(memberParam.getName());
        } else if (!StringUtils.isEmpty(memberParam.getNum())) {
            example.createCriteria().andNumEqualTo(memberParam.getNum());
        } else if (!StringUtils.isEmpty(memberParam.getAccount())) {//账号
            example.createCriteria().andAccountEqualTo(memberParam.getAccount());
        } else if (!StringUtils.isEmpty(memberParam.getMobile())) {//手机号
            example.createCriteria().andMobileEqualTo(memberParam.getMobile());
        } else if (memberParam.getSex() != null) {//性别
            example.createCriteria().andSexEqualTo(memberParam.getSex());
        } else if (memberParam.getBirthday() != null) {//生日
            example.createCriteria().andBirthdayEqualTo(memberParam.getBirthday());
        }
        MemberBO memberBO = MemberConverter.convertDTOOther(memberParam);
        MemberDO memberDO = MemberConverter.convertDO(memberBO);
        memberDOMapper.updateByExampleSelective(memberDO, example);
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
