package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.constants.FileDirConstant;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.constants.UserInviterTypeEnum;
import com.lawu.eshop.user.constants.UserSexEnum;
import com.lawu.eshop.user.constants.UserStatusEnum;
import com.lawu.eshop.user.param.RegisterRealParam;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.query.MemberQuery;
import com.lawu.eshop.user.srv.bo.CashUserInfoBO;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.converter.MemberConverter;
import com.lawu.eshop.user.srv.domain.*;
import com.lawu.eshop.user.srv.domain.MemberDOExample.Criteria;
import com.lawu.eshop.user.srv.mapper.*;
import com.lawu.eshop.user.srv.rong.models.TokenResult;
import com.lawu.eshop.user.srv.rong.service.RongUserService;
import com.lawu.eshop.user.srv.service.MemberService;
import com.lawu.eshop.user.srv.strategy.PasswordStrategy;
import com.lawu.eshop.utils.MD5;
import com.lawu.eshop.utils.RandomUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private PasswordStrategy passwordStrategy;

    @Autowired
    private TransactionMainService transactionMainService;

    @Autowired
    private FansMerchantDOMapper fansMerchantDOMapper;

    @Autowired
    private RongUserService rongUserService;

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
    @Transactional
    public int updateMemberInfo(UserParam memberParam, Long id) {

        MemberDO memberDO = MemberConverter.convertDOOther(memberParam);
        memberDO.setId(id);
        int result = memberDOMapper.updateByPrimaryKeySelective(memberDO);
        if(!"".equals(memberDO.getNickname())){
            MemberDO old = memberDOMapper.selectByPrimaryKey(id);
            String headImg = FileDirConstant.DEFAULT_PIC;
            if(!"".equals(old.getHeadimg())){
                headImg = old.getHeadimg();
            }
            rongUserService.refreshUserInfo(old.getNum(),memberDO.getNickname(),headImg);
        }
        return result;

    }

    @Override
    @Transactional
    public void updateLoginPwd(Long id, String newPwd) {
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
    public Page<MemberBO> findMemberListByUser(Long inviterId, MemberQuery memberQuery) {
        MemberDOExample example = new MemberDOExample();
        Byte status = 1;
        Criteria c1 = example.createCriteria();
        c1.andInviterIdEqualTo(inviterId).andStatusEqualTo(status);
        int totalCount = memberDOMapper.countByExample(example); //总记录数
        if (memberQuery.getAccountOrNickName() != null) { //存在模糊查询
            Criteria c2 = example.createCriteria();
            c2.andAccountLike("%" + memberQuery.getAccountOrNickName() + "%");
            Criteria c3 = example.createCriteria();
            c3.andNicknameLike("%" + memberQuery.getAccountOrNickName() + "%");
            example.or(c2);
            example.or(c3);
        }
        RowBounds rowBounds = new RowBounds(memberQuery.getOffset(), memberQuery.getPageSize());
        List<MemberDO> memberDOS = memberDOMapper.selectByExampleWithRowbounds(example, rowBounds);

        List<MemberProfileDO> mpList = new ArrayList<MemberProfileDO>();
        for (MemberDO memberDO : memberDOS) {
            MemberProfileDOExample mpExample = new MemberProfileDOExample();
            MemberProfileDO memberProfileDO = memberProfileDOMapper.selectByPrimaryKey(memberDO.getId());
            if(memberProfileDO!=null)
            mpList.add(memberProfileDO);
        }
        Page<MemberBO> pageMember = new Page<MemberBO>();
        pageMember.setTotalCount(totalCount);
        List<MemberBO> memberBOS = MemberConverter.convertListBOS(memberDOS, mpList);
        pageMember.setRecords(memberBOS);
        pageMember.setCurrentPage(memberQuery.getCurrentPage());
        return pageMember;
    }

    @Override
    @Transactional
    public void register(RegisterRealParam registerRealParam) {
        //推荐人id
        long inviterId = 0;
        if (registerRealParam.getInviterId() != null) {
            inviterId = registerRealParam.getInviterId();
        }
        //推荐人类型
        byte inviterType = UserInviterTypeEnum.INVITER_TYPE_NULL.val;
        if (registerRealParam.getUserNum() != null) {
            if (registerRealParam.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)) {
                inviterType = UserInviterTypeEnum.INVITER_TYPE_MEMBER.val;
            } else {
                inviterType = UserInviterTypeEnum.INVITER_TYPE_MERCHANT.val;
            }
        }

        //插入会员信息
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount(registerRealParam.getAccount());
        memberDO.setPwd(MD5.MD5Encode(registerRealParam.getPwd()));
        memberDO.setMobile(registerRealParam.getAccount());
        memberDO.setSex(UserSexEnum.SEX_SECRET.val);
        memberDO.setStatus(UserStatusEnum.MEMBER_STATUS_VALID.val);
        memberDO.setInviterId(inviterId);
        memberDO.setInviterType(inviterType);
        memberDO.setLevel(UserCommonConstant.LEVEL_1);
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);
        long memberId = memberDO.getId();

        //插入会员扩展信息
        MemberProfileDO memberProfileDO = new MemberProfileDO();
        memberProfileDO.setId(memberId);
        memberProfileDO.setInviteMemberCount(0);
        memberProfileDO.setInviteMerchantCount(0);
        memberProfileDO.setGmtCreate(new Date());
        memberProfileDOMapper.insertSelective(memberProfileDO);

        //注册会员成为商户粉丝
        if (inviterId > 0 && inviterType == UserInviterTypeEnum.INVITER_TYPE_MERCHANT.val) {
            FansMerchantDO fansMerchantDO=new FansMerchantDO();
            fansMerchantDO.setMerchantId(inviterId);
            fansMerchantDO.setMemberId(memberId);
            fansMerchantDO.setGmtCreate(new Date());
            fansMerchantDOMapper.insertSelective(fansMerchantDO);
        }

        //插入会员推荐关系
        InviteRelationDO inviteRelationDO = new InviteRelationDO();
        inviteRelationDO.setUserId(memberId);
        inviteRelationDO.setInvitedUserId(memberId);
        inviteRelationDO.setDepth(0);
        inviteRelationDO.setType(UserInviterTypeEnum.INVITER_TYPE_MEMBER.val);
        inviteRelationDOMapper.insert(inviteRelationDO);
        if (inviterId > 0) {
            //查询推荐人推荐关系
            InviteRelationDOExample inviteRelationDOExample = new InviteRelationDOExample();
            inviteRelationDOExample.createCriteria().andInvitedUserIdEqualTo(inviterId);
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
                    if (inviteRelationDO1.getType() == UserInviterTypeEnum.INVITER_TYPE_MEMBER.val) {
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

                    //查询推荐的一级会员总人数
                    inviteRelationDOExample = new InviteRelationDOExample();
                    inviteRelationDOExample.createCriteria().andUserIdEqualTo(inviteRelationDO1.getUserId()).andTypeEqualTo(UserInviterTypeEnum.INVITER_TYPE_MEMBER.val).andDepthEqualTo(UserCommonConstant.DEPTH_1);
                    int inviteMemberCount = inviteRelationDOMapper.countByExample(inviteRelationDOExample);
                    //查询推荐的二级级会员总人数
                    inviteRelationDOExample = new InviteRelationDOExample();
                    inviteRelationDOExample.createCriteria().andUserIdEqualTo(inviteRelationDO1.getUserId()).andTypeEqualTo(UserInviterTypeEnum.INVITER_TYPE_MEMBER.val).andDepthEqualTo(UserCommonConstant.DEPTH_2);
                    int inviteMemberCount2 = inviteRelationDOMapper.countByExample(inviteRelationDOExample);
                    //查询推荐的三级会员总人数
                    inviteRelationDOExample = new InviteRelationDOExample();
                    inviteRelationDOExample.createCriteria().andUserIdEqualTo(inviteRelationDO1.getUserId()).andTypeEqualTo(UserInviterTypeEnum.INVITER_TYPE_MEMBER.val).andDepthEqualTo(UserCommonConstant.DEPTH_3);
                    int inviteMemberCount3 = inviteRelationDOMapper.countByExample(inviteRelationDOExample);
                    //查询推荐的一级商户总人数
                    inviteRelationDOExample = new InviteRelationDOExample();
                    inviteRelationDOExample.createCriteria().andUserIdEqualTo(inviteRelationDO1.getUserId()).andTypeEqualTo(UserInviterTypeEnum.INVITER_TYPE_MERCHANT.val).andDepthEqualTo(UserCommonConstant.DEPTH_1);
                    int inviteMerchantCount = inviteRelationDOMapper.countByExample(inviteRelationDOExample);
                    //查询推荐的二级商户总人数
                    inviteRelationDOExample = new InviteRelationDOExample();
                    inviteRelationDOExample.createCriteria().andUserIdEqualTo(inviteRelationDO1.getUserId()).andTypeEqualTo(UserInviterTypeEnum.INVITER_TYPE_MERCHANT.val).andDepthEqualTo(UserCommonConstant.DEPTH_2);
                    int inviteMerchantCount2 = inviteRelationDOMapper.countByExample(inviteRelationDOExample);
                    //查询推荐的三级级商户总人数
                    inviteRelationDOExample = new InviteRelationDOExample();
                    inviteRelationDOExample.createCriteria().andUserIdEqualTo(inviteRelationDO1.getUserId()).andTypeEqualTo(UserInviterTypeEnum.INVITER_TYPE_MERCHANT.val).andDepthEqualTo(UserCommonConstant.DEPTH_3);
                    int inviteMerchantCount3 = inviteRelationDOMapper.countByExample(inviteRelationDOExample);
                    //更新会员扩展信息
                    memberProfileDO = new MemberProfileDO();
                    memberProfileDO.setId(inviteRelationDO1.getUserId());
                    memberProfileDO.setInviteMemberCount(inviteMemberCount);
                    memberProfileDO.setInviteMemberCount2(inviteMemberCount2);
                    memberProfileDO.setInviteMemberCount3(inviteMemberCount3);
                    memberProfileDO.setInviteMerchantCount(inviteMerchantCount);
                    memberProfileDO.setInviteMerchantCount2(inviteMerchantCount2);
                    memberProfileDO.setInviteMerchantCount3(inviteMerchantCount3);
                    memberProfileDO.setGmtModified(new Date());
                    memberProfileDOMapper.updateByPrimaryKeySelective(memberProfileDO);
                }
            }
        }
        //获取ryToken
        TokenResult tokenResult = rongUserService.getRongToken(memberDO.getNum(),memberDO.getMobile(), FileDirConstant.DEFAULT_PIC);
        if(!"".equals(tokenResult.getToken())){
            MemberDO memberDO2 = new MemberDO();
            memberDO2.setRyToken(tokenResult.getToken());
            memberDO2.setId(memberDO.getId());
            memberDOMapper.updateByPrimaryKeySelective(memberDO2);
        }
        transactionMainService.sendNotice(memberId);
    }

    @Override
    public MemberBO getMemberById(Long id) {
        MemberDO memberDO = memberDOMapper.selectByPrimaryKey(id);
        return MemberConverter.convertBO(memberDO);
    }

    @Override
    @Transactional
    public void updateMemberHeadImg(String headimg, Long mermberId) {
        MemberDO memberDO = new MemberDO();
        memberDO.setHeadimg(headimg);
        memberDO.setId(mermberId);
        memberDOMapper.updateByPrimaryKeySelective(memberDO);
        MemberDO old = memberDOMapper.selectByPrimaryKey(mermberId);
        rongUserService.refreshUserInfo(old.getNum(),memberDO.getNickname(),headimg);
    }

	public CashUserInfoBO findCashUserInfo(Long id) {
		MemberDO mdo = memberDOMapper.selectByPrimaryKey(id);
		if (mdo == null) {
			return null;
		} else if(mdo.getRegionPath() == null || mdo.getRegionPath().split("/").length != 3){
			return null;
		}
		CashUserInfoBO cashUserInfoBO = new CashUserInfoBO();
		cashUserInfoBO.setName(mdo.getName());
		cashUserInfoBO.setProvinceId(Integer.valueOf(mdo.getRegionPath().split("/")[0]));
		cashUserInfoBO.setCityId(Integer.valueOf(mdo.getRegionPath().split("/")[1]));
		cashUserInfoBO.setAreaId(Integer.valueOf(mdo.getRegionPath().split("/")[2]));
		return cashUserInfoBO;
	}
	
	public Integer findMemberCount(String regionPath) {
		MemberDOExample example = new MemberDOExample();
        Criteria c1 = example.createCriteria();
        c1.andStatusEqualTo(UserStatusEnum.MEMBER_STATUS_VALID.val);
        int count = 0;
		if(regionPath==null){ //所有用户
			count=memberDOMapper.countByExample(example);
			return count;
		}else{
			String[] path=regionPath.split("/");
			List<MemberDO> list=memberDOMapper.selectByExample(example);
			for (MemberDO memberDO : list) {
				if(memberDO.getRegionPath()!=null){
					String[] memberPath=memberDO.getRegionPath().split("/");
					for (String s : memberPath) {
						for (String p : path) {
							if(s.equals(p))
								count++;
						}
						
					}
				}
				
			}
			return count;
		}
		
	}

    @Override
    @Transactional
    public Integer setGtAndRongYunInfo(Long id, String cid) {
        MemberDO memberDO = new MemberDO();
        memberDO.setId(id);
        memberDO.setGtCid(cid);
        Integer row = memberDOMapper.updateByPrimaryKeySelective(memberDO);
        return row;
    }

    @Override
    public MemberBO findMemberByNum(String userNum) {
        MemberDOExample example = new MemberDOExample();
        example.createCriteria().andNumEqualTo(userNum);
        List<MemberDO> memberDOS = memberDOMapper.selectByExample(example);
        if(memberDOS.isEmpty()){
            return null;
        }
        return MemberConverter.convertBO(memberDOS.get(0));
    }

}
