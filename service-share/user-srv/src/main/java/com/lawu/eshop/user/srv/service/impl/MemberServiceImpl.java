package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.param.RegisterParam;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.converter.MemberConverter;
import com.lawu.eshop.user.srv.domain.*;
import com.lawu.eshop.user.srv.mapper.InviteRelationDOMapper;
import com.lawu.eshop.user.srv.mapper.MemberDOMapper;
import com.lawu.eshop.user.srv.mapper.MemberProfileDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantDOMapper;
import com.lawu.eshop.user.srv.service.MemberService;

import org.apache.ibatis.session.RowBounds;
import com.lawu.eshop.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Autowired
    private MerchantDOMapper merchantDOMapper;

    @Autowired
    private InviteRelationDOMapper inviteRelationDOMapper;

    @Autowired
    private MemberProfileDOMapper memberProfileDOMapper;

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
    public void updateMemberInfo(com.lawu.eshop.user.dto.param.UserParam memberParam) {

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

    @Override
    public MemberBO getMemberByAccount(String account) {
        MemberDOExample example = new MemberDOExample();
        example.createCriteria().andAccountEqualTo(account);
        List<MemberDO> memberDOS = memberDOMapper.selectByExample(example);
        return memberDOS.isEmpty() ? null : MemberConverter.convertBO(memberDOS.get(0));
    }

	@Override
	public List<MemberBO> findMemberListByUser(Long inviterId) {
		 MemberDOExample example = new MemberDOExample();
		 example.createCriteria().andInviterIdEqualTo(inviterId);
		 //List<MemberDO> memberDOS=memberDOMapper.selectByExample(example);
		 RowBounds rowBounds = new RowBounds(0, 10);
		 List<MemberDO> memberDOS=memberDOMapper.selectByExampleWithRowbounds(example, rowBounds);

		return memberDOS.isEmpty() ? null : MemberConverter.convertListBOS(memberDOS);
	}

    @Override
    @Transactional
    public void register(RegisterParam registerParam) {
        //推荐人id
        long invitedUserId = 0;
        if (registerParam.getInviterId() != null) {
            invitedUserId = registerParam.getInviterId();
        }
        //插入会员信息
        MemberDO memberDO = new MemberDO();
        memberDO.setAccount(registerParam.getAccount());
        memberDO.setPwd(MD5.MD5Encode(registerParam.getPwd()));
        memberDO.setGmtCreate(new Date());
        if (registerParam.getInviterId() != null) {
            memberDO.setInviterId(registerParam.getInviterId());
        }
        if (registerParam.getInviterType() != null) {
            memberDO.setInviterType(registerParam.getInviterType());
        }
        long memberId = memberDOMapper.insertSelective(memberDO);

        //插入会员扩展信息
        MemberProfileDO memberProfileDO = new MemberProfileDO();
        memberProfileDO.setId(memberId);
        memberProfileDO.setInviteMemberCount(0);
        memberProfileDO.setInviteMerchantCount(0);
        memberProfileDO.setGmtCreate(new Date());
        memberProfileDOMapper.insertSelective(memberProfileDO);

        //插入会员推荐关系
        InviteRelationDO inviteRelationDO = new InviteRelationDO();
        inviteRelationDO.setUserId(memberId);
        inviteRelationDO.setInvitedUserId(memberId);
        inviteRelationDO.setDepth(0);
        inviteRelationDO.setType(UserCommonConstant.INVITER_TYPE_MEMBER);
        inviteRelationDOMapper.insert(inviteRelationDO);
        if (invitedUserId > 0) {
            //查询推荐人推荐关系
            InviteRelationDOExample inviteRelationDOExample = new InviteRelationDOExample();
            inviteRelationDOExample.createCriteria().andInvitedUserIdEqualTo(invitedUserId);
            List<InviteRelationDO> inviteRelationDOS = inviteRelationDOMapper.selectByExample(inviteRelationDOExample);
            if (!inviteRelationDOS.isEmpty()) {
                //更新推荐关系
                for (InviteRelationDO inviteRelationDO1 : inviteRelationDOS) {
                    inviteRelationDO = new InviteRelationDO();
                    inviteRelationDO.setUserId(inviteRelationDO1.getUserId());
                    inviteRelationDO.setInvitedUserId(memberId);
                    inviteRelationDO.setDepth(inviteRelationDO1.getDepth() + 1);
                    inviteRelationDO.setType(inviteRelationDO1.getType());
                    inviteRelationDOMapper.insert(inviteRelationDO);
                }
            }

            //查询会员的三级推荐人
            inviteRelationDOExample = new InviteRelationDOExample();
            inviteRelationDOExample.createCriteria().andInvitedUserIdEqualTo(memberId).andDepthBetween(1, 3);
            inviteRelationDOS = inviteRelationDOMapper.selectByExample(inviteRelationDOExample);
            if (!inviteRelationDOS.isEmpty()) {
                for (InviteRelationDO inviteRelationDO1 : inviteRelationDOS) {
                    //查询会员推荐人的推荐人数
                    inviteRelationDOExample = new InviteRelationDOExample();
                    inviteRelationDOExample.createCriteria().andUserIdEqualTo(inviteRelationDO1.getUserId()).andDepthBetween(1, 3);
                    int inviteCount = inviteRelationDOMapper.countByExample(inviteRelationDOExample);
                    //更新会员推荐人的等级
                    int level = UserCommonConstant.LEVEL_1;
                    if (inviteCount >= UserCommonConstant.LEVEL_5_FANS_CNT) {
                        level = UserCommonConstant.LEVEL_5;
                    } else if (inviteCount >= UserCommonConstant.LEVEL_4_FANS_CNT) {
                        level = UserCommonConstant.LEVEL_4;
                    } else if (inviteCount >= UserCommonConstant.LEVEL_3_FANS_CNT) {
                        level = UserCommonConstant.LEVEL_3;
                    } else if (inviteCount >= UserCommonConstant.LEVEL_2_FANS_CNT) {
                        level = UserCommonConstant.LEVEL_2;
                    }
                    if (inviteRelationDO1.getType() == UserCommonConstant.INVITER_TYPE_MEMBER) {
                        memberDO = new MemberDO();
                        memberDO.setLevel(level);
                        memberDO.setId(inviteRelationDO1.getUserId());
                        memberDOMapper.updateByPrimaryKeySelective(memberDO);
                    } else {
                        MerchantDO merchantDO = new MerchantDO();
                        merchantDO.setLevel(level);
                        merchantDO.setId(inviteRelationDO1.getUserId());
                        merchantDOMapper.updateByPrimaryKeySelective(merchantDO);
                    }
                }
            }
        }

        //查询会员的所有推荐人
        InviteRelationDOExample inviteRelationDOExample = new InviteRelationDOExample();
        inviteRelationDOExample.createCriteria().andInvitedUserIdEqualTo(memberId);
        List<InviteRelationDO> inviteRelationDOS = inviteRelationDOMapper.selectByExample(inviteRelationDOExample);
        if (!inviteRelationDOS.isEmpty()) {
            for (InviteRelationDO inviteRelationDO1 : inviteRelationDOS) {
                //查询推荐会员总人数
                inviteRelationDOExample = new InviteRelationDOExample();
                inviteRelationDOExample.createCriteria().andUserIdEqualTo(inviteRelationDO1.getUserId()).andTypeEqualTo(UserCommonConstant.INVITER_TYPE_MEMBER).andDepthBetween(1, 3);
                int inviteMemberCount = inviteRelationDOMapper.countByExample(inviteRelationDOExample);
                //查询推荐商户总人数
                inviteRelationDOExample = new InviteRelationDOExample();
                inviteRelationDOExample.createCriteria().andUserIdEqualTo(inviteRelationDO1.getUserId()).andTypeEqualTo(UserCommonConstant.INVITER_TYPE_MERCHANT).andDepthBetween(1, 3);
                int inviteMerchantCount = inviteRelationDOMapper.countByExample(inviteRelationDOExample);
                //更新会员扩展信息
                memberProfileDO = new MemberProfileDO();
                memberProfileDO.setId(inviteRelationDO1.getUserId());
                memberProfileDO.setInviteMemberCount(inviteMemberCount);
                memberProfileDO.setInviteMerchantCount(inviteMerchantCount);
                memberProfileDO.setGmtModified(new Date());
                memberProfileDOMapper.updateByPrimaryKeySelective(memberProfileDO);
            }
        }
    }

}
