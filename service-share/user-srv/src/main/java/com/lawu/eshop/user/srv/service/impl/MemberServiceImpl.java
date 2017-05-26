package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.constants.*;
import com.lawu.eshop.user.param.MemberQuery;
import com.lawu.eshop.user.param.RegisterRealParam;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.srv.UserSrvConfig;
import com.lawu.eshop.user.srv.bo.CashUserInfoBO;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.bo.MessagePushBO;
import com.lawu.eshop.user.srv.bo.RongYunBO;
import com.lawu.eshop.user.srv.converter.MemberConverter;
import com.lawu.eshop.user.srv.domain.*;
import com.lawu.eshop.user.srv.domain.MemberDOExample.Criteria;
import com.lawu.eshop.user.srv.mapper.*;
import com.lawu.eshop.user.srv.rong.models.TokenResult;
import com.lawu.eshop.user.srv.rong.service.RongUserService;
import com.lawu.eshop.user.srv.service.MemberService;
import com.lawu.eshop.user.srv.strategy.PasswordStrategy;
import com.lawu.eshop.utils.PwdUtil;
import com.lawu.eshop.utils.RandomUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("memberRegTransactionMainServiceImpl")
    private TransactionMainService transactionMainService;

    @Autowired
    private FansMerchantDOMapper fansMerchantDOMapper;

    @Autowired
    private RongUserService rongUserService;

    @Autowired
    private UserSrvConfig userSrvConfig;

    @Autowired
    private MerchantProfileDOMapper merchantProfileDOMapper;


    @Override
    public MemberBO find(String account, String pwd) {
        MemberDOExample example = new MemberDOExample();
        example.createCriteria().andAccountEqualTo(account);
        List<MemberDO> memberDOS = memberDOMapper.selectByExample(example);
        if(!memberDOS.isEmpty()){
        	if(PwdUtil.verify(pwd, memberDOS.get(0).getPwd())){
        		return MemberConverter.convertBO(memberDOS.get(0));
        	}else{
        		return null;
        	}
        }else{
        	return null;
        }
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
        if (StringUtils.isNotEmpty(memberDO.getNickname())) {
            MemberDO old = memberDOMapper.selectByPrimaryKey(id);
            String headImg = userSrvConfig.getDefaultHeadimg();
            if (StringUtils.isNotEmpty(old.getHeadimg())) {
                headImg = old.getHeadimg();
            }
            rongUserService.refreshUserInfo(old.getNum(), memberDO.getNickname(), headImg);
        }
        return result;

    }

    @Override
    @Transactional
    public void updateLoginPwd(Long id, String newPwd) {
        MemberDO memberDO = new MemberDO();
        memberDO.setId(id);
        memberDO.setPwd(PwdUtil.generate(newPwd));
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
    public Page<MemberBO> findMemberListByUser(Long inviterId, MemberQuery memberQuery, byte inviterType) {
        MemberDOExample example = new MemberDOExample();
        Byte status = 1;
        Criteria c1 = example.createCriteria();
        c1.andInviterIdEqualTo(inviterId).andStatusEqualTo(status).andInviterTypeEqualTo(inviterType);
        int count = memberDOMapper.countByExample(example);
        if (memberQuery.getAccountOrNickName() != null) { //存在模糊查询
            c1.andAccountLike("%" + memberQuery.getAccountOrNickName() + "%");
            Criteria c2 = example.createCriteria();
            c2.andInviterIdEqualTo(inviterId).andStatusEqualTo(status).andInviterTypeEqualTo(inviterType)
            .andNicknameLike("%" + memberQuery.getAccountOrNickName() + "%");
            example.or(c2);
        }
        RowBounds rowBounds = new RowBounds(memberQuery.getOffset(), memberQuery.getPageSize());
        List<MemberDO> memberDOS = memberDOMapper.selectByExampleWithRowbounds(example, rowBounds);

        List<MemberProfileDO> mpList = new ArrayList<MemberProfileDO>();
        for (MemberDO memberDO : memberDOS) {
        	if(memberDO.getHeadimg()==null){
        		memberDO.setHeadimg(userSrvConfig.getDefaultHeadimg());
        	}
            MemberProfileDO memberProfileDO = memberProfileDOMapper.selectByPrimaryKey(memberDO.getId());
            if (memberProfileDO != null)
                mpList.add(memberProfileDO);
        }
        Page<MemberBO> pageMember = new Page<MemberBO>();
        pageMember.setTotalCount(count);
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
        String userNum = RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG);
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(userNum);
        memberDO.setAccount(registerRealParam.getAccount());
        memberDO.setPwd(PwdUtil.generate(registerRealParam.getPwd()));
        memberDO.setMobile(registerRealParam.getAccount());
        memberDO.setSex(UserSexEnum.SEX_SECRET.val);
        memberDO.setStatus(UserStatusEnum.MEMBER_STATUS_VALID.val);
        memberDO.setHeadimg(userSrvConfig.getUser_headimg());
        memberDO.setInviterId(inviterId);
        memberDO.setInviterType(inviterType);
        memberDO.setLevel(UserCommonConstant.LEVEL_1);
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);
        long memberId = memberDO.getId();

        //插入会员扩展信息
        MemberProfileDO memberProfileDO = new MemberProfileDO();
        memberProfileDO.setId(memberId);
        memberProfileDO.setGmtCreate(new Date());
        memberProfileDOMapper.insertSelective(memberProfileDO);

        //注册会员成为商户粉丝
        if (inviterId > 0 && inviterType == UserInviterTypeEnum.INVITER_TYPE_MERCHANT.val) {
            FansMerchantDO fansMerchantDO = new FansMerchantDO();
            fansMerchantDO.setMerchantId(inviterId);
            fansMerchantDO.setMemberId(memberId);
            fansMerchantDO.setChannel(FansMerchantChannelEnum.REGISTER.getValue());
            fansMerchantDO.setGmtCreate(new Date());
            fansMerchantDOMapper.insertSelective(fansMerchantDO);
        }

        //插入会员推荐关系
        InviteRelationDO inviteRelationDO = new InviteRelationDO();
        inviteRelationDO.setUserNum(userNum);
        inviteRelationDO.setInviteUserNum(userNum);
        inviteRelationDO.setDepth(0);
        inviteRelationDO.setType(UserInviterTypeEnum.INVITER_TYPE_MEMBER.val);
        inviteRelationDOMapper.insert(inviteRelationDO);
        if (inviterId > 0) {
            //查询推荐人推荐关系
            InviteRelationDOExample inviteRelationDOExample = new InviteRelationDOExample();
            inviteRelationDOExample.createCriteria().andInviteUserNumEqualTo(registerRealParam.getUserNum());
            List<InviteRelationDO> inviteRelationDOS = inviteRelationDOMapper.selectByExample(inviteRelationDOExample);
            if (!inviteRelationDOS.isEmpty()) {
                //更新推荐关系
                for (InviteRelationDO inviteRelationDO1 : inviteRelationDOS) {
                    inviteRelationDO = new InviteRelationDO();
                    inviteRelationDO.setUserNum(inviteRelationDO1.getUserNum());
                    inviteRelationDO.setInviteUserNum(userNum);
                    inviteRelationDO.setDepth(inviteRelationDO1.getDepth() + 1);
                    inviteRelationDO.setType(inviteRelationDO1.getType());
                    inviteRelationDOMapper.insert(inviteRelationDO);
                }
            }

            //查询会员的三级推荐人
            inviteRelationDOExample = new InviteRelationDOExample();
            inviteRelationDOExample.createCriteria().andInviteUserNumEqualTo(userNum).andDepthBetween(1, 3);
            inviteRelationDOS = inviteRelationDOMapper.selectByExample(inviteRelationDOExample);
            if (!inviteRelationDOS.isEmpty()) {
                for (InviteRelationDO inviteRelationDO1 : inviteRelationDOS) {
                    //查询会员推荐人的推荐人数
                    inviteRelationDOExample = new InviteRelationDOExample();
                    inviteRelationDOExample.createCriteria().andUserNumEqualTo(inviteRelationDO1.getUserNum()).andDepthBetween(1, 3);
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

                    int inviteMemberCount = 0;
                    int inviteMemberCount2 = 0;
                    int inviteMemberCount3 = 0;
                    int inviteMerchantCount = 0;
                    int inviteMerchantCount2 = 0;
                    int inviteMerchantCount3 = 0;
                    //查询推荐的一级会员/商家总人数
                    inviteRelationDOExample = new InviteRelationDOExample();
                    inviteRelationDOExample.createCriteria().andUserNumEqualTo(inviteRelationDO1.getUserNum()).andDepthEqualTo(UserCommonConstant.DEPTH_1);
                    List<InviteRelationDO> inviteRelationDOList = inviteRelationDOMapper.selectByExample(inviteRelationDOExample);
                    if(!inviteRelationDOList.isEmpty()){
                        for(InviteRelationDO relationDO : inviteRelationDOList){
                            if(relationDO.getInviteUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
                                inviteMemberCount ++;
                            }else{
                                inviteMerchantCount ++;
                            }
                        }
                    }
                    //查询推荐的二级会员/商家总人数
                    inviteRelationDOExample = new InviteRelationDOExample();
                    inviteRelationDOExample.createCriteria().andUserNumEqualTo(inviteRelationDO1.getUserNum()).andDepthEqualTo(UserCommonConstant.DEPTH_2);
                    inviteRelationDOList = inviteRelationDOMapper.selectByExample(inviteRelationDOExample);
                    if(!inviteRelationDOList.isEmpty()){
                        for(InviteRelationDO relationDO : inviteRelationDOList){
                            if(relationDO.getInviteUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
                                inviteMemberCount2 ++;
                            }else{
                                inviteMerchantCount2 ++;
                            }
                        }
                    }
                    //查询推荐的三级会员/商家总人数
                    inviteRelationDOExample = new InviteRelationDOExample();
                    inviteRelationDOExample.createCriteria().andUserNumEqualTo(inviteRelationDO1.getUserNum()).andDepthEqualTo(UserCommonConstant.DEPTH_3);
                    inviteRelationDOList = inviteRelationDOMapper.selectByExample(inviteRelationDOExample);
                    if(!inviteRelationDOList.isEmpty()){
                        for(InviteRelationDO relationDO : inviteRelationDOList){
                            if(relationDO.getInviteUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
                                inviteMemberCount3 ++;
                            }else{
                                inviteMerchantCount3 ++;
                            }
                        }
                    }

                    if (inviteRelationDO1.getType() == UserInviterTypeEnum.INVITER_TYPE_MEMBER.val) {
                        MemberDOExample memberDOExample = new MemberDOExample();
                        memberDOExample.createCriteria().andNumEqualTo(inviteRelationDO1.getUserNum());
                        List<MemberDO> memberDOS = memberDOMapper.selectByExample(memberDOExample);
                        memberDO = new MemberDO();
                        memberDO.setLevel(level);
                        memberDO.setId(memberDOS.get(0).getId());
                        memberDOMapper.updateByPrimaryKeySelective(memberDO);

                        //更新会员扩展信息
                        memberProfileDO = new MemberProfileDO();
                        memberProfileDO.setId(memberDOS.get(0).getId());
                        memberProfileDO.setInviteMemberCount(inviteMemberCount);
                        memberProfileDO.setInviteMemberCount2(inviteMemberCount2);
                        memberProfileDO.setInviteMemberCount3(inviteMemberCount3);
                        memberProfileDO.setInviteMerchantCount(inviteMerchantCount);
                        memberProfileDO.setInviteMerchantCount2(inviteMerchantCount2);
                        memberProfileDO.setInviteMerchantCount3(inviteMerchantCount3);
                        memberProfileDO.setGmtModified(new Date());
                        memberProfileDOMapper.updateByPrimaryKeySelective(memberProfileDO);
                    } else {
                        MerchantDOExample merchantDOExample = new MerchantDOExample();
                        merchantDOExample.createCriteria().andNumEqualTo(inviteRelationDO1.getUserNum());
                        List<MerchantDO> merchantDOS = merchantDOMapper.selectByExample(merchantDOExample);
                        MerchantDO merchantDO = new MerchantDO();
                        merchantDO.setLevel(level);
                        merchantDO.setId(merchantDOS.get(0).getId());
                        merchantDOMapper.updateByPrimaryKeySelective(merchantDO);

                        //更新商户扩展信息
                        MerchantProfileDO merchantProfileDO = new MerchantProfileDO();
                        merchantProfileDO.setId(merchantDOS.get(0).getId());
                        merchantProfileDO.setInviteMemberCount(inviteMemberCount);
                        merchantProfileDO.setInviteMemberCount2(inviteMemberCount2);
                        merchantProfileDO.setInviteMemberCount3(inviteMemberCount3);
                        merchantProfileDO.setInviteMerchantCount(inviteMerchantCount);
                        merchantProfileDO.setInviteMerchantCount2(inviteMerchantCount2);
                        merchantProfileDO.setInviteMerchantCount3(inviteMerchantCount3);
                        merchantProfileDO.setGmtModified(new Date());
                        merchantProfileDOMapper.updateByPrimaryKeySelective(merchantProfileDO);
                    }
                }
            }
        }
        //获取ryToken
        TokenResult tokenResult = rongUserService.getRongToken(userNum, "E店会员", userSrvConfig.getUser_headimg());
        if (StringUtils.isNotEmpty(tokenResult.getToken())) {
            MemberDO memberDO2 = new MemberDO();
            memberDO2.setRyToken(tokenResult.getToken());
            memberDO2.setId(memberId);
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
        rongUserService.refreshUserInfo(old.getNum(), memberDO.getNickname(), headimg);
    }

    public CashUserInfoBO findCashUserInfo(Long id) {
        MemberDO mdo = memberDOMapper.selectByPrimaryKey(id);
        if (mdo == null) {
            return null;
        } else if (mdo.getRegionPath() == null || mdo.getRegionPath().split("/").length != 3) {
            return null;
        }
        CashUserInfoBO cashUserInfoBO = new CashUserInfoBO();
        cashUserInfoBO.setName(mdo.getName());
        cashUserInfoBO.setRegionFullName(mdo.getRegionName());
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
        if (regionPath.equals("ALL_PLACE")) { //所有用户
            count = memberDOMapper.countByExample(example);
            return count;
        } else {
            String[] path = regionPath.split(",");
            List<MemberDO> list = memberDOMapper.selectByExample(example);
            for (MemberDO memberDO : list) {
                if (memberDO.getRegionPath() != null) {
                    String[] memberPath = memberDO.getRegionPath().split("/");
                    for (String s : memberPath) {
                        for (String p : path) {
                            if (s.equals(p))
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
        if (memberDOS.isEmpty()) {
            return null;
        }
        return MemberConverter.convertBO(memberDOS.get(0));
    }

    @Override
    public List<MessagePushBO> findMessagePushList(String area) {
        MemberDOExample example = new MemberDOExample();
        if ("all".equals(area)) {
            example.createCriteria().andStatusEqualTo(UserStatusEnum.MEMBER_STATUS_VALID.val).andGtCidIsNotNull();
        } else {
            example.createCriteria().andStatusEqualTo(UserStatusEnum.MEMBER_STATUS_VALID.val).andGtCidIsNotNull().andRegionPathLike(area + "%");
        }
        example.setOrderByClause("id desc");
        List<MemberDO> list = memberDOMapper.selectByExample(example);
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        List<MessagePushBO> messagePushBOS = new ArrayList<>();
        for (MemberDO memberDO : list) {
            MessagePushBO messagePushBO = new MessagePushBO();
            messagePushBO.setUserNum(memberDO.getNum());
            messagePushBO.setGtCid(memberDO.getGtCid());
            messagePushBOS.add(messagePushBO);
        }
        return messagePushBOS;
    }

    @Override
    public MessagePushBO findMessagePushByMobile(String moblie) {
        MemberDOExample example = new MemberDOExample();
        example.createCriteria().andMobileEqualTo(moblie).andStatusEqualTo(UserStatusEnum.MEMBER_STATUS_VALID.val);
        List<MemberDO> memberDOS = memberDOMapper.selectByExample(example);
        if (!memberDOS.isEmpty()) {
            MessagePushBO messagePushBO = new MessagePushBO();
            messagePushBO.setUserNum(memberDOS.get(0).getNum());
            return messagePushBO;
        }
        return null;
    }

    @Override
    public MemberBO isRegister(String mobile) {
        MemberDOExample example = new MemberDOExample();
        example.createCriteria().andMobileEqualTo(mobile).andStatusEqualTo(UserStatusEnum.MEMBER_STATUS_VALID.val);
        List<MemberDO> memberDOS = memberDOMapper.selectByExample(example);
        if (!memberDOS.isEmpty()) {
        	MemberBO memberBO=new MemberBO();
        	memberBO.setId(memberDOS.get(0).getId());
        	memberBO.setNum(memberDOS.get(0).getNum());
            return memberBO;
        } else {
            return null;
        }

    }

    @Override
    public MemberBO getMemberByNum(String num) {
        MemberDOExample example = new MemberDOExample();
        example.createCriteria().andNumEqualTo(num);
        List<MemberDO> memberDOS = memberDOMapper.selectByExample(example);
        if (memberDOS.isEmpty()) {
            return null;
        }
        return MemberConverter.convertBO(memberDOS.get(0));
    }

    @Override
    public RongYunBO getRongYunInfoByNum(String num) {
        MemberDOExample example = new MemberDOExample();
        example.createCriteria().andNumEqualTo(num);
        List<MemberDO> memberDOS = memberDOMapper.selectByExample(example);
        if (memberDOS.isEmpty()) {
            return null;
        }
        RongYunBO rongYunBO = new RongYunBO();
        rongYunBO.setUserNum(num);
        rongYunBO.setNickName(StringUtils.isEmpty(memberDOS.get(0).getNickname()) ? "E店会员" : memberDOS.get(0).getNickname());
        rongYunBO.setHeadImg(memberDOS.get(0).getHeadimg());
        return rongYunBO;
    }

}
