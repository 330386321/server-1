package com.lawu.eshop.user.srv.service.impl;

import java.util.Date;
import java.util.List;

import com.lawu.eshop.user.srv.strategy.PasswordStrategy;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.constants.UserStatusConstant;
import com.lawu.eshop.user.param.RegisterParam;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.query.MemberQuery;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.converter.MemberConverter;
import com.lawu.eshop.user.srv.domain.InviteRelationDO;
import com.lawu.eshop.user.srv.domain.InviteRelationDOExample;
import com.lawu.eshop.user.srv.domain.MemberDO;
import com.lawu.eshop.user.srv.domain.MemberDOExample;
import com.lawu.eshop.user.srv.domain.MemberDOExample.Criteria;
import com.lawu.eshop.user.srv.domain.MemberProfileDO;
import com.lawu.eshop.user.srv.domain.MerchantDO;
import com.lawu.eshop.user.srv.mapper.InviteRelationDOMapper;
import com.lawu.eshop.user.srv.mapper.MemberDOMapper;
import com.lawu.eshop.user.srv.mapper.MemberProfileDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantDOMapper;
import com.lawu.eshop.user.srv.service.MemberService;
import com.lawu.eshop.utils.MD5;

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

    @Autowired
    private PasswordStrategy passwordStrategy;

    @Override
    public MemberBO find(String account, String pwd) {


        MemberDOExample example = new MemberDOExample();
        example.createCriteria().andAccountEqualTo(account).andPwdEqualTo(passwordStrategy.encode(pwd));
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
    public int updateMemberInfo(UserParam memberParam, Long id) {

        MemberDO memberDO = MemberConverter.convertDOOther(memberParam);
        memberDO.setId(id);
        int result = memberDOMapper.updateByPrimaryKeySelective(memberDO);
        return result;

    }

    @Override
    public void updateLoginPwd(Long id,String originalPwd, String newPwd) {

        MemberDO memberDO = new MemberDO();
        memberDO.setId(id);
        memberDO.setPwd(MD5.MD5Encode(newPwd));
        memberDOMapper.updateByPrimaryKeySelective(memberDO);
    }

    @Override
    public MemberBO getMemberByAccount(String account) {
        MemberDOExample example = new MemberDOExample();
        example.createCriteria().andAccountEqualTo(account);
        List<MemberDO> memberDOS = memberDOMapper.selectByExample(example);
        return memberDOS.isEmpty() ? null : MemberConverter.convertBO(memberDOS.get(0));
    }

	@Override
	public Page<MemberBO> findMemberListByUser(MemberQuery memberQuery) {
		 MemberDOExample example = new MemberDOExample();
		 Byte status=1;
		 Criteria c1=example.createCriteria();
		 c1.andInviterIdEqualTo(memberQuery.getInviterId()).andStatusEqualTo(status);
		 int totalCount= memberDOMapper.countByExample(example); //总记录数
		 if(memberQuery.getAccountOrNickName()!=null){ //存在模糊查询
			 Criteria c2=example.createCriteria();
			 c2.andAccountLike("%"+memberQuery.getAccountOrNickName()+"%");
			 Criteria c3=example.createCriteria();
			 c3.andNicknameLike("%"+memberQuery.getAccountOrNickName()+"%");
			 example.or(c2);
			 example.or(c3);
		 }
		 RowBounds rowBounds = new RowBounds(memberQuery.getCurrentPage(), memberQuery.getPageSize());
		 List<MemberDO> memberDOS=memberDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		 Page<MemberBO> pageMember =new Page<MemberBO>();
		 pageMember.setTotalCount(totalCount);
		 List<MemberBO> memberBOS= MemberConverter.convertListBOS(memberDOS);
		 pageMember.setRecords(memberBOS);
		 pageMember.setCurrentPage(memberQuery.getCurrentPage());
		return pageMember;
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
        memberDO.setName(UserCommonConstant.MEMBER_NUM_TAG);
        memberDO.setAccount(registerParam.getAccount());
        memberDO.setPwd(MD5.MD5Encode(registerParam.getPwd()));
        memberDO.setMobile(registerParam.getAccount());
        memberDO.setSex(UserCommonConstant.SEX_SECRET);
        memberDO.setStatus(UserStatusConstant.MEMBER_STATUS_VALID);
        memberDO.setLevel(UserCommonConstant.LEVEL_1);
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

    @Override
    public MemberBO getMemberById(Long id) {
        MemberDO memberDO=memberDOMapper.selectByPrimaryKey(id);
        return MemberConverter.convertBO(memberDO);
    }

}
