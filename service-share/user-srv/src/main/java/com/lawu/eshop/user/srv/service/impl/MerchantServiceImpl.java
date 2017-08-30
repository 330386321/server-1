package com.lawu.eshop.user.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.constants.UserInviterTypeEnum;
import com.lawu.eshop.user.constants.UserStatusEnum;
import com.lawu.eshop.user.dto.CertifTypeEnum;
import com.lawu.eshop.user.dto.MerchantStatusEnum;
import com.lawu.eshop.user.dto.MerchantStoreImageEnum;
import com.lawu.eshop.user.dto.MerchantStoreTypeEnum;
import com.lawu.eshop.user.param.AccountParam;
import com.lawu.eshop.user.param.MerchantInviterParam;
import com.lawu.eshop.user.param.RegisterRealParam;
import com.lawu.eshop.user.srv.UserSrvConfig;
import com.lawu.eshop.user.srv.bo.MerchantBO;
import com.lawu.eshop.user.srv.bo.MerchantBaseInfoBO;
import com.lawu.eshop.user.srv.bo.MerchantDetailBO;
import com.lawu.eshop.user.srv.bo.MerchantInfoBO;
import com.lawu.eshop.user.srv.bo.MerchantInviterBO;
import com.lawu.eshop.user.srv.bo.MessagePushBO;
import com.lawu.eshop.user.srv.bo.RongYunBO;
import com.lawu.eshop.user.srv.converter.MerchantConverter;
import com.lawu.eshop.user.srv.converter.MerchantInviterConverter;
import com.lawu.eshop.user.srv.domain.InviteRelationDO;
import com.lawu.eshop.user.srv.domain.InviteRelationDOExample;
import com.lawu.eshop.user.srv.domain.MemberDO;
import com.lawu.eshop.user.srv.domain.MemberDOExample;
import com.lawu.eshop.user.srv.domain.MemberProfileDO;
import com.lawu.eshop.user.srv.domain.MerchantDO;
import com.lawu.eshop.user.srv.domain.MerchantDOExample;
import com.lawu.eshop.user.srv.domain.MerchantProfileDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDOExample;
import com.lawu.eshop.user.srv.domain.MerchantStoreImageDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreImageDOExample;
import com.lawu.eshop.user.srv.domain.MerchantStoreProfileDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreProfileDOExample;
import com.lawu.eshop.user.srv.domain.extend.InviterMerchantDOView;
import com.lawu.eshop.user.srv.domain.extend.MerchantDOView;
import com.lawu.eshop.user.srv.domain.extend.MerchantPushView;
import com.lawu.eshop.user.srv.mapper.InviteRelationDOMapper;
import com.lawu.eshop.user.srv.mapper.MemberDOMapper;
import com.lawu.eshop.user.srv.mapper.MemberProfileDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantProfileDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreImageDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreProfileDOMapper;
import com.lawu.eshop.user.srv.mapper.extend.InviterMerchantDOMapperExtend;
import com.lawu.eshop.user.srv.mapper.extend.MerchantDOMapperExtend;
import com.lawu.eshop.user.srv.mapper.extend.MerchantStoreDOMapperExtend;
import com.lawu.eshop.user.srv.rong.models.TokenResult;
import com.lawu.eshop.user.srv.rong.service.RongUserService;
import com.lawu.eshop.user.srv.service.MerchantService;
import com.lawu.eshop.user.srv.strategy.PasswordStrategy;
import com.lawu.eshop.utils.PwdUtil;
import com.lawu.eshop.utils.RandomUtil;

/**
 * 商户服务实现
 *
 * @author meishuquan
 * @date 2017/3/22
 */
@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantDOMapper merchantDOMapper;

    @Autowired
    private MemberDOMapper memberDOMapper;

    @Autowired
    private MerchantProfileDOMapper merchantProfileDOMapper;

    @Autowired
    private InviteRelationDOMapper inviteRelationDOMapper;

    @Autowired
    private PasswordStrategy passwordStrategy;

    @Autowired
    private MerchantStoreDOMapper merchantStoreDOMapper;

    @Autowired
    @Qualifier("merchantRegTransactionMainServiceImpl")
    private TransactionMainService transactionMainService;

    @Autowired
    private InviterMerchantDOMapperExtend inviterMerchantDOMapper;

    @Autowired
    private RongUserService rongUserService;

    @Autowired
    private MerchantStoreDOMapperExtend merchantStoreDOMapperExtend;

    @Autowired
    private UserSrvConfig userSrvConfig;

    @Autowired
    private MerchantStoreProfileDOMapper merchantStoreProfileDOMapper;

    @Autowired
    private MemberProfileDOMapper memberProfileDOMapper;
    
    @Autowired
    private MerchantStoreImageDOMapper merchantStoreImageDOMapper;

    @Autowired
    private MerchantDOMapperExtend merchantDOMapperExtend;


    @Override
    @Transactional
    public void updateLoginPwd(Long id, String newPwd) {
        MerchantDO merchantDO = new MerchantDO();
        merchantDO.setId(id);
        merchantDO.setPwd(PwdUtil.generate(newPwd));
        merchantDOMapper.updateByPrimaryKeySelective(merchantDO);
    }

    @Override
    public MerchantBO getMerchantByAccount(String account) {
        MerchantDOExample example = new MerchantDOExample();
        example.createCriteria().andAccountEqualTo(account);
        List<MerchantDO> merchantDOS = merchantDOMapper.selectByExample(example);
        return merchantDOS.isEmpty() ? null : MerchantConverter.convertBO(merchantDOS.get(0));
    }

    @Override
    public MerchantInfoBO findMerchantInfo(Long merchantId) {
        MerchantDO merchantDO = merchantDOMapper.selectByPrimaryKey(merchantId);

        return MerchantConverter.convertInfoBO(merchantDO);
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

        //插入商户信息
        String userNum = RandomUtil.getTableNumRandomString(UserCommonConstant.MERCHANT_NUM_TAG);
        MerchantDO merchantDO = new MerchantDO();
        merchantDO.setNum(userNum);
        merchantDO.setAccount(registerRealParam.getAccount());
        merchantDO.setPwd(PwdUtil.generate(registerRealParam.getPwd()));
        merchantDO.setMobile(registerRealParam.getAccount());
        merchantDO.setStatus(UserStatusEnum.MEMBER_STATUS_VALID.val);
        merchantDO.setHeadimg(userSrvConfig.getMerchant_headimg());
        merchantDO.setInviterId(inviterId);
        merchantDO.setInviterType(inviterType);
        merchantDO.setLevel(UserCommonConstant.LEVEL_1);
        merchantDO.setGmtCreate(new Date());
        merchantDOMapper.insertSelective(merchantDO);
        long merchantId = merchantDO.getId();

        //插入商户扩展信息
        MerchantProfileDO merchantProfileDO = new MerchantProfileDO();
        merchantProfileDO.setId(merchantId);
        merchantProfileDO.setGmtCreate(new Date());
        merchantProfileDOMapper.insertSelective(merchantProfileDO);

        //插入商户推荐关系
        InviteRelationDO inviteRelationDO = new InviteRelationDO();
        inviteRelationDO.setUserNum(userNum);
        inviteRelationDO.setInviteUserNum(userNum);
        inviteRelationDO.setDepth(0);
        inviteRelationDO.setType(UserInviterTypeEnum.INVITER_TYPE_MERCHANT.val);
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

            //查询商户的三级推荐人
            inviteRelationDOExample = new InviteRelationDOExample();
            inviteRelationDOExample.createCriteria().andInviteUserNumEqualTo(userNum).andDepthBetween(1, 3);
            inviteRelationDOS = inviteRelationDOMapper.selectByExample(inviteRelationDOExample);
            if (!inviteRelationDOS.isEmpty()) {
                for (InviteRelationDO inviteRelationDO1 : inviteRelationDOS) {
                    //查询商户推荐人的推荐人数
                    inviteRelationDOExample = new InviteRelationDOExample();
                    inviteRelationDOExample.createCriteria().andUserNumEqualTo(inviteRelationDO1.getUserNum()).andDepthBetween(1, 3);
                    int inviteCount = inviteRelationDOMapper.countByExample(inviteRelationDOExample);
                    //更新商户推荐人的等级
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
                        MemberDO memberDO = new MemberDO();
                        memberDO.setLevel(level);
                        memberDO.setId(memberDOS.get(0).getId());
                        memberDOMapper.updateByPrimaryKeySelective(memberDO);

                        //更新会员扩展信息
                        MemberProfileDO memberProfileDO = new MemberProfileDO();
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
                        merchantDO = new MerchantDO();
                        merchantDO.setLevel(level);
                        merchantDO.setId(merchantDOS.get(0).getId());
                        merchantDOMapper.updateByPrimaryKeySelective(merchantDO);

                        //更新商户扩展信息
                        merchantProfileDO = new MerchantProfileDO();
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
        //获取融云token
        TokenResult tokenResult = rongUserService.getRongToken(userNum, "E店商家", userSrvConfig.getMerchant_headimg());
        if (tokenResult != null && StringUtils.isNotEmpty(tokenResult.getToken())) {
            MerchantDO merchantDO1 = new MerchantDO();
            merchantDO1.setRyToken(tokenResult.getToken());
            merchantDO1.setId(merchantId);
            merchantDOMapper.updateByPrimaryKeySelective(merchantDO1);
        }
        transactionMainService.sendNotice(merchantId);
    }

    @Override
    public MerchantBO getMerchantBOById(Long id) {
        MerchantDO merchantDO = merchantDOMapper.selectByPrimaryKey(id);
        return MerchantConverter.convertBO(merchantDO);
    }
    
    @Override
    public Page<MerchantInviterBO> getMerchantByInviter(Long userId, MerchantInviterParam pageParam, byte inviterType) {
    	
    	InviterMerchantDOView inviterMerchantDO = new InviterMerchantDOView();
        inviterMerchantDO.setInviterId(userId);
        inviterMerchantDO.setInviterType(inviterType);
        if (StringUtils.isNotEmpty(pageParam.getName())){
        	inviterMerchantDO.setName("%"+pageParam.getName()+"%");
        }
        int count=inviterMerchantDOMapper.selectInviterMerchantCount(inviterMerchantDO);
        RowBounds rowBounds = new RowBounds(pageParam.getOffset(), pageParam.getPageSize());
        //推荐的商家
        List<InviterMerchantDOView> inviterMerchantDOS = inviterMerchantDOMapper.selectInviterMerchantByRowbounds(inviterMerchantDO, rowBounds);
        for (InviterMerchantDOView inviterMerchantDOView : inviterMerchantDOS) {
        	 MerchantProfileDO merchantProfileDO =merchantProfileDOMapper.selectByPrimaryKey(inviterMerchantDOView.getId());
        	 if(merchantProfileDO == null){
        		 inviterMerchantDOView.setInviterCount(0);
        	 }else{
        		 inviterMerchantDOView.setInviterCount(merchantProfileDO.getInviteMerchantCount2()+merchantProfileDO.getInviteMerchantCount3());
        	 }
        	//获取门店logo
         	MerchantStoreImageDOExample msidExample=new MerchantStoreImageDOExample();
         	msidExample.createCriteria().andMerchantIdEqualTo(inviterMerchantDOView.getId()).andStatusEqualTo(true).andTypeEqualTo(new Byte("3"));
         	List<MerchantStoreImageDO>  msiList= merchantStoreImageDOMapper.selectByExample(msidExample);
         	if(!msiList.isEmpty()){
         		if(msiList.get(0).getPath()==null){
         			inviterMerchantDOView.setPath(userSrvConfig.getMerchant_headimg());
         		}else{
         			inviterMerchantDOView.setPath(msiList.get(0).getPath());
         		}
         		
         	}else{
         		inviterMerchantDOView.setPath(userSrvConfig.getMerchant_headimg());
         	}
		}
       
        Page<MerchantInviterBO> pageMerchantInviter = new Page<MerchantInviterBO>();
        pageMerchantInviter.setTotalCount(count);
        List<MerchantInviterBO> memberBOS = MerchantInviterConverter.convertMerchantInviterBOS(inviterMerchantDOS);
        pageMerchantInviter.setRecords(memberBOS);
        pageMerchantInviter.setCurrentPage(pageParam.getCurrentPage());
        return pageMerchantInviter;
    }

    @Override
    public MerchantBO find(String account, String pwd) {
        MerchantDOExample example = new MerchantDOExample();
        example.createCriteria().andAccountEqualTo(account);
        List<MerchantDO> merchantDOs = merchantDOMapper.selectByExample(example);
        if(!merchantDOs.isEmpty()){
        	if(PwdUtil.verify(pwd, merchantDOs.get(0).getPwd())){
        		return MerchantConverter.convertBO(merchantDOs.get(0));
        	}else{
        		return null;
        	}
        }else{
        	return null;
        }
    }

    @Override
    public Integer setGtAndRongYunInfo(Long id, String cid) {
        MerchantDO merchantDO = new MerchantDO();
        merchantDO.setId(id);
        merchantDO.setGtCid(cid);
        Integer row = merchantDOMapper.updateByPrimaryKeySelective(merchantDO);
        return row;
    }

    @Override
    public MerchantBO findMemberByNum(String userNum) {
        MerchantDOExample example = new MerchantDOExample();
        example.createCriteria().andNumEqualTo(userNum);
        List<MerchantDO> merchantDOS = merchantDOMapper.selectByExample(example);
        if (merchantDOS.isEmpty()) {
            return null;
        }
        return MerchantConverter.convertBO(merchantDOS.get(0));
    }

    @Override
    public MerchantBO selectMerchantInfo(Long merchantId) {
        MerchantDO merchantDO = merchantDOMapper.selectByPrimaryKey(merchantId);
        MerchantStoreDOExample example = new MerchantStoreDOExample();
        example.createCriteria().andMerchantIdEqualTo(merchantId);
        List<MerchantStoreDO> list = merchantStoreDOMapper.selectByExample(example);
        MerchantBO merchantBO = MerchantConverter.convertBO(merchantDO);
        if (!list.isEmpty()) {
            MerchantStoreDO merchantStoreDO = list.get(0);
            merchantBO.setPrincipalName(merchantStoreDO.getPrincipalName());
        }
        return merchantBO;
    }

    @Override
    public List<MessagePushBO> findMessagePushList(String area) {

        List<MerchantPushView> list = merchantStoreDOMapperExtend.selectPushInfo(area);
        if (list.isEmpty()) {
            return null;
        }
        List<MessagePushBO> messagePushBOS = new ArrayList<>();
        for (MerchantPushView pushView : list) {
            MessagePushBO messagePushBO = new MessagePushBO();
            messagePushBO.setUserNum(pushView.getNum());
            messagePushBO.setGtCid(pushView.getGtCid());
            messagePushBOS.add(messagePushBO);
        }
        return messagePushBOS;
    }

    @Override
    public MessagePushBO findMessagePushByMobile(String moblie) {
        MerchantDOExample example = new MerchantDOExample();
        example.createCriteria().andMobileEqualTo(moblie).andStatusEqualTo(UserStatusEnum.MEMBER_STATUS_VALID.val);
        List<MerchantDO> merchantDOS = merchantDOMapper.selectByExample(example);
        if(!merchantDOS.isEmpty()){
            MessagePushBO messagePushBO = new MessagePushBO();
            messagePushBO.setUserNum(merchantDOS.get(0).getNum());
            return messagePushBO;
        }
        return null;
    }

    @Override
    public void updateHeadImg(String headimg, Long merchantId) {
        MerchantDO merchantDO = new MerchantDO();
        merchantDO.setHeadimg(headimg);
        merchantDO.setId(merchantId);
        merchantDOMapper.updateByPrimaryKeySelective(merchantDO);
        MerchantStoreProfileDOExample example = new MerchantStoreProfileDOExample();
        example.createCriteria().andMerchantIdEqualTo(merchantId);
        MerchantDO old = merchantDOMapper.selectByPrimaryKey(merchantId);
        List<MerchantStoreProfileDO> olds = merchantStoreProfileDOMapper.selectByExample(example);
        if(!olds.isEmpty()){
            rongUserService.refreshUserInfo(old.getNum(), olds.get(0).getCompanyName(), headimg);
        }
    }

    @Override
    public RongYunBO getRongYunInfoByNum(String num) {
        MerchantDOExample merchantDOExample = new MerchantDOExample();
        merchantDOExample.createCriteria().andNumEqualTo(num);
        List<MerchantDO> merchantDOS = merchantDOMapper.selectByExample(merchantDOExample);
        if(merchantDOS.isEmpty()){
            return null;
        }
        RongYunBO rongYunBO = new RongYunBO();
        rongYunBO.setUserNum(num);
        rongYunBO.setNickName("E店商家");
        rongYunBO.setHeadImg(merchantDOS.get(0).getHeadimg());

        MerchantStoreDOExample merchantStoreDOExample = new MerchantStoreDOExample();
        merchantStoreDOExample.createCriteria().andMerchantIdEqualTo(merchantDOS.get(0).getId()).andStatusNotEqualTo(MerchantStatusEnum.MERCHANT_STATUS_CANCEL.val);
        List<MerchantStoreDO> merchantStoreDOS = merchantStoreDOMapper.selectByExample(merchantStoreDOExample);
        if(!merchantStoreDOS.isEmpty()){
            rongYunBO.setNickName(merchantStoreDOS.get(0).getName());
        }

        MerchantStoreImageDOExample merchantStoreImageDOExample = new MerchantStoreImageDOExample();
        merchantStoreImageDOExample.createCriteria().andMerchantIdEqualTo(merchantDOS.get(0).getId()).andStatusEqualTo(true).andTypeEqualTo(MerchantStoreImageEnum.STORE_IMAGE_LOGO.val);
        List<MerchantStoreImageDO> merchantStoreImageDOS = merchantStoreImageDOMapper.selectByExample(merchantStoreImageDOExample);
        if(!merchantStoreImageDOS.isEmpty() && StringUtils.isNotEmpty(merchantStoreImageDOS.get(0).getPath())){
            rongYunBO.setHeadImg(merchantStoreImageDOS.get(0).getPath());
        }

        return rongYunBO;
    }

	@Override
	public MerchantBaseInfoBO getMerchantById(Long merchantId) {
		MerchantDO merchantDO = merchantDOMapper.selectByPrimaryKey(merchantId);
		MerchantBaseInfoBO bo = new MerchantBaseInfoBO();
		if(merchantDO != null){
            bo.setUserNum(merchantDO.getNum());
        }
		return bo;
	}

    @Override
    public MerchantBO getMerchantByNum(String num) {
        MerchantDOExample example = new MerchantDOExample();
        example.createCriteria().andNumEqualTo(num);
        List<MerchantDO> merchantDOS = merchantDOMapper.selectByExample(example);
        return merchantDOS.isEmpty() ? null : MerchantConverter.convertBO(merchantDOS.get(0));
    }

	@Override
	public Boolean isRegister(String account) {
        MerchantDOExample example = new MerchantDOExample();
        example.createCriteria().andAccountEqualTo(account);
        int count = merchantDOMapper.countByExample(example);
        return count > 0;
    }

    @Override
    @Transactional
    public int delMerchantGtPush(Long merchantId) {
      int row =  merchantDOMapperExtend.delMerchantGtPush(merchantId);
      return row;
    }

    @Override
    public MerchantDOView getMerchantView(Long id) {
        return merchantDOMapperExtend.getMerchantViewById(id);
    }

    @Override
    public Integer getTotalCount() {
        MerchantDOExample example = new MerchantDOExample();
        example.createCriteria().andStatusEqualTo(UserStatusEnum.MEMBER_STATUS_VALID.val);
        Integer count = merchantDOMapper.countByExample(example);
        return count;
    }

    @Override
    public Page<MerchantBO> getAccountList(AccountParam param) {
        MerchantDOExample example = new MerchantDOExample();
        RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
        Page<MerchantBO> pages = new Page<>();
        List<MerchantBO> merchantBOS = new ArrayList<>();
        int total ;
        if (StringUtils.isEmpty(param.getAccount())) {
            example.setOrderByClause("id desc");
            List<MerchantDO> merchantDOS = merchantDOMapper.selectByExampleWithRowbounds(example, rowBounds);
            if (merchantDOS.isEmpty()) {
                return pages;
            }
            for (MerchantDO merchantDO : merchantDOS) {
                MerchantBO merchantBO = MerchantConverter.convertBO(merchantDO);
                merchantBOS.add(merchantBO);
            }
            total = merchantDOMapper.countByExample(example);
        } else {
            example.createCriteria().andAccountEqualTo(param.getAccount());
            List<MerchantDO> merchantDOS = merchantDOMapper.selectByExample(example);
            if (merchantDOS.isEmpty()) {
                return pages;
            }
            MerchantBO memberBO = MerchantConverter.convertBO(merchantDOS.get(0));
            merchantBOS.add(memberBO);

            total = 1;
        }
        pages.setCurrentPage(param.getCurrentPage());
        pages.setTotalCount(total);
        pages.setRecords(merchantBOS);
        return pages;
    }

    @Override
    @Transactional
    public void freezeAccount(String num, Boolean isFreeze,String freezeReason) {
        MerchantDO merchantDO = new MerchantDO();
        merchantDO.setIsFreeze(isFreeze);
        merchantDO.setFreezeReason(freezeReason);
        MerchantDOExample example = new MerchantDOExample();
        example.createCriteria().andNumEqualTo(num);
        merchantDOMapper.updateByExampleSelective(merchantDO, example);
    }

    @Override
    public MerchantDetailBO getMerchantDetailById(Long merchantId) {
        MerchantDetailBO detailBO = new MerchantDetailBO();
        MerchantStoreDOExample storeDOExample = new MerchantStoreDOExample();
        storeDOExample.createCriteria().andMerchantIdEqualTo(merchantId);
        List<MerchantStoreDO> storeDOS = merchantStoreDOMapper.selectByExample(storeDOExample);
        if (!storeDOS.isEmpty()) {
            MerchantStoreDO merchantStoreDO = storeDOS.get(0);
            detailBO.setName(merchantStoreDO.getName());
            detailBO.setRegionName(merchantStoreDO.getRegionName());
            detailBO.setAddress(merchantStoreDO.getAddress());
            detailBO.setIndustryName(merchantStoreDO.getIndustryName());
            detailBO.setKeywords(merchantStoreDO.getKeywords());
            detailBO.setIntro(merchantStoreDO.getIntro());
            detailBO.setStatusEnum(MerchantStatusEnum.getEnum(merchantStoreDO.getStatus()));
            detailBO.setPrincipalName(merchantStoreDO.getPrincipalName());
            detailBO.setPrincipalMobile(merchantStoreDO.getPrincipalMobile());
        }

        MerchantStoreProfileDOExample profileDOExample = new MerchantStoreProfileDOExample();
        profileDOExample.createCriteria().andMerchantIdEqualTo(merchantId);
        List<MerchantStoreProfileDO> profileDOS = merchantStoreProfileDOMapper.selectByExample(profileDOExample);
        if (!profileDOS.isEmpty()) {
            MerchantStoreProfileDO profileDO = profileDOS.get(0);
            detailBO.setCompanyName(profileDO.getCompanyName());
            detailBO.setRegNumber(profileDO.getRegNumber());
            detailBO.setCompanyAddress(profileDO.getCompanyAddress());
            detailBO.setLicenseIndate(profileDO.getLicenseIndate());
            detailBO.setManageType(MerchantStoreTypeEnum.getEnum(profileDO.getManageType()));
            detailBO.setCertifType(CertifTypeEnum.getEnum(profileDO.getCertifType()));
            detailBO.setOperatorCardId(profileDO.getOperatorCardId());
            detailBO.setOperatorName(profileDO.getOperatorName());
        }

        MerchantStoreImageDOExample imageDOExample = new MerchantStoreImageDOExample();
        imageDOExample.createCriteria().andMerchantIdEqualTo(merchantId).andStatusEqualTo(true);
        List<MerchantStoreImageDO> imageDOS = merchantStoreImageDOMapper.selectByExample(imageDOExample);
        if (!imageDOS.isEmpty()) {
            List<String> storeUrl = new ArrayList<>();
            List<String> environmentUrl = new ArrayList<>();
            List<String> idcardUrl = new ArrayList<>();
            List<String> licenseUrl = new ArrayList<>();
            List<String> otherUrl = new ArrayList<>();
            for (MerchantStoreImageDO imageDO : imageDOS) {
                if (StringUtils.isEmpty(imageDO.getPath())) {
                    continue;
                }
                if (imageDO.getType().equals(MerchantStoreImageEnum.STORE_IMAGE_LOGO.val)) {
                    detailBO.setLogoUrl(imageDO.getPath());
                } else {
                    String[] imgArr = imageDO.getPath().split(",");
                    for (String img : imgArr) {
                        if (imageDO.getType().equals(MerchantStoreImageEnum.STORE_IMAGE_STORE.val)) {
                            storeUrl.add(img);
                        } else if (imageDO.getType().equals(MerchantStoreImageEnum.STORE_IMAGE_ENVIRONMENT.val)) {
                            environmentUrl.add(img);
                        } else if (imageDO.getType().equals(MerchantStoreImageEnum.STORE_IMAGE_LICENSE.val)) {
                            licenseUrl.add(img);
                        } else if (imageDO.getType().equals(MerchantStoreImageEnum.STORE_IMAGE_OTHER.val)) {
                            otherUrl.add(img);
                        } else if (imageDO.getType().equals(MerchantStoreImageEnum.STORE_IMAGE_IDCARD.val)) {
                            idcardUrl.add(img);
                        }
                    }
                }
            }
            detailBO.setStoreUrl(storeUrl);
            detailBO.setEnvironmentUrl(environmentUrl);
            detailBO.setIdcardUrl(idcardUrl);
            detailBO.setLicenseUrl(licenseUrl);
            detailBO.setOtherUrl(otherUrl);
        }
        return detailBO;
    }
}
