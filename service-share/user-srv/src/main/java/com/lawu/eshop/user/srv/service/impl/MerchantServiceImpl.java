package com.lawu.eshop.user.srv.service.impl;

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
import com.lawu.eshop.framework.web.constants.FileDirConstant;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.constants.UserInviterTypeEnum;
import com.lawu.eshop.user.constants.UserStatusEnum;
import com.lawu.eshop.user.param.MerchantInviterParam;
import com.lawu.eshop.user.param.RegisterRealParam;
import com.lawu.eshop.user.srv.bo.MerchantBO;
import com.lawu.eshop.user.srv.bo.MerchantInfoBO;
import com.lawu.eshop.user.srv.bo.MerchantInviterBO;
import com.lawu.eshop.user.srv.converter.MerchantConverter;
import com.lawu.eshop.user.srv.converter.MerchantInviterConverter;
import com.lawu.eshop.user.srv.domain.InviteRelationDO;
import com.lawu.eshop.user.srv.domain.InviteRelationDOExample;
import com.lawu.eshop.user.srv.domain.MemberDO;
import com.lawu.eshop.user.srv.domain.MemberDOExample;
import com.lawu.eshop.user.srv.domain.MerchantDO;
import com.lawu.eshop.user.srv.domain.MerchantDOExample;
import com.lawu.eshop.user.srv.domain.MerchantProfileDO;
import com.lawu.eshop.user.srv.domain.extend.InviterMerchantDOView;
import com.lawu.eshop.user.srv.mapper.InviteRelationDOMapper;
import com.lawu.eshop.user.srv.mapper.MemberDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantProfileDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.mapper.extend.InviterMerchantDOMapperExtend;
import com.lawu.eshop.user.srv.rong.models.TokenResult;
import com.lawu.eshop.user.srv.rong.service.RongMerchantService;
import com.lawu.eshop.user.srv.service.MerchantService;
import com.lawu.eshop.user.srv.strategy.PasswordStrategy;
import com.lawu.eshop.utils.MD5;
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
    private RongMerchantService rongMerchantService;

    @Override
    @Transactional
    public void updateLoginPwd(Long id, String newPwd) {
        MerchantDO merchantDO = new MerchantDO();
        merchantDO.setId(id);
        merchantDO.setPwd(MD5.MD5Encode(newPwd));
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
        merchantDO.setPwd(MD5.MD5Encode(registerRealParam.getPwd()));
        merchantDO.setMobile(registerRealParam.getAccount());
        merchantDO.setStatus(UserStatusEnum.MEMBER_STATUS_VALID.val);
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
                    if (inviteRelationDO1.getType() == UserInviterTypeEnum.INVITER_TYPE_MEMBER.val) {
                        MemberDO memberDO = new MemberDO();
                        memberDO.setLevel(level);
                        MemberDOExample memberDOExample = new MemberDOExample();
                        memberDOExample.createCriteria().andNumEqualTo(inviteRelationDO1.getUserNum());
                        memberDOMapper.updateByExampleSelective(memberDO, memberDOExample);
                    } else {
                        MerchantDOExample merchantDOExample = new MerchantDOExample();
                        merchantDOExample.createCriteria().andNumEqualTo(inviteRelationDO1.getUserNum());
                        List<MerchantDO> merchantDOS = merchantDOMapper.selectByExample(merchantDOExample);
                        Long inviterInviteId = merchantDOS.get(0).getId();
                        merchantDO = new MerchantDO();
                        merchantDO.setLevel(level);
                        merchantDO.setId(inviterInviteId);
                        merchantDOMapper.updateByPrimaryKeySelective(merchantDO);

                        //查询推荐的一级会员总人数
                        inviteRelationDOExample = new InviteRelationDOExample();
                        inviteRelationDOExample.createCriteria().andUserNumEqualTo(inviteRelationDO1.getUserNum()).andTypeEqualTo(UserInviterTypeEnum.INVITER_TYPE_MEMBER.val).andDepthEqualTo(UserCommonConstant.DEPTH_1);
                        int inviteMemberCount = inviteRelationDOMapper.countByExample(inviteRelationDOExample);
                        //查询推荐的二级会员总人数
                        inviteRelationDOExample = new InviteRelationDOExample();
                        inviteRelationDOExample.createCriteria().andUserNumEqualTo(inviteRelationDO1.getUserNum()).andTypeEqualTo(UserInviterTypeEnum.INVITER_TYPE_MEMBER.val).andDepthEqualTo(UserCommonConstant.DEPTH_2);
                        int inviteMemberCount2 = inviteRelationDOMapper.countByExample(inviteRelationDOExample);
                        //查询推荐的三级会员总人数
                        inviteRelationDOExample = new InviteRelationDOExample();
                        inviteRelationDOExample.createCriteria().andUserNumEqualTo(inviteRelationDO1.getUserNum()).andTypeEqualTo(UserInviterTypeEnum.INVITER_TYPE_MEMBER.val).andDepthEqualTo(UserCommonConstant.DEPTH_3);
                        int inviteMemberCount3 = inviteRelationDOMapper.countByExample(inviteRelationDOExample);
                        //查询推荐的一级商户总人数
                        inviteRelationDOExample = new InviteRelationDOExample();
                        inviteRelationDOExample.createCriteria().andUserNumEqualTo(inviteRelationDO1.getUserNum()).andTypeEqualTo(UserInviterTypeEnum.INVITER_TYPE_MERCHANT.val).andDepthEqualTo(UserCommonConstant.DEPTH_1);
                        int inviteMerchantCount = inviteRelationDOMapper.countByExample(inviteRelationDOExample);
                        //查询推荐的二级商户总人数
                        inviteRelationDOExample = new InviteRelationDOExample();
                        inviteRelationDOExample.createCriteria().andUserNumEqualTo(inviteRelationDO1.getUserNum()).andTypeEqualTo(UserInviterTypeEnum.INVITER_TYPE_MERCHANT.val).andDepthEqualTo(UserCommonConstant.DEPTH_2);
                        int inviteMerchantCount2 = inviteRelationDOMapper.countByExample(inviteRelationDOExample);
                        //查询推荐的三级商户总人数
                        inviteRelationDOExample = new InviteRelationDOExample();
                        inviteRelationDOExample.createCriteria().andUserNumEqualTo(inviteRelationDO1.getUserNum()).andTypeEqualTo(UserInviterTypeEnum.INVITER_TYPE_MERCHANT.val).andDepthEqualTo(UserCommonConstant.DEPTH_3);
                        int inviteMerchantCount3 = inviteRelationDOMapper.countByExample(inviteRelationDOExample);
                        //更新商户扩展信息
                        merchantProfileDO = new MerchantProfileDO();
                        merchantProfileDO.setId(inviterInviteId);
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
        TokenResult tokenResult = rongMerchantService.getRongToken(userNum, "E店商家", FileDirConstant.DEFAULT_PIC);
        if (StringUtils.isNotEmpty(tokenResult.getToken())) {
            MemberDO memberDO2 = new MemberDO();
            memberDO2.setRyToken(tokenResult.getToken());
            memberDO2.setId(merchantId);
            memberDOMapper.updateByPrimaryKeySelective(memberDO2);
        }
        transactionMainService.sendNotice(merchantId);
    }

    @Override
    public MerchantBO getMerchantBOById(Long id) {
        MerchantDO merchantDO = merchantDOMapper.selectByPrimaryKey(id);
        return MerchantConverter.convertBO(merchantDO);
    }

    @Override
    public Page<MerchantInviterBO> getMerchantByInviter(Long userId, MerchantInviterParam pageParam) {
        InviterMerchantDOView inviterMerchantDO = new InviterMerchantDOView();
        inviterMerchantDO.setInviterId(userId);
        inviterMerchantDO.setMobileAndName(pageParam.getMobileOrName());
        RowBounds rowBounds = new RowBounds(pageParam.getOffset(), pageParam.getPageSize());
        //推荐的商家
        List<InviterMerchantDOView> inviterMerchantDOS = inviterMerchantDOMapper.selectInviterMerchantByRowbounds(inviterMerchantDO, rowBounds);
        Page<MerchantInviterBO> pageMerchantInviter = new Page<MerchantInviterBO>();
        pageMerchantInviter.setTotalCount(inviterMerchantDOS.size());
        List<MerchantInviterBO> memberBOS = MerchantInviterConverter.convertMerchantInviterBOS(inviterMerchantDOS);
        pageMerchantInviter.setRecords(memberBOS);
        return pageMerchantInviter;
    }


    @Override
    public MerchantBO find(String account, String pwd) {


        MerchantDOExample example = new MerchantDOExample();
        example.createCriteria().andAccountEqualTo(account).andPwdEqualTo(passwordStrategy.encode(pwd));
        List<MerchantDO> merchantDOs = merchantDOMapper.selectByExample(example);

        return merchantDOs.isEmpty() ? null : MerchantConverter.convertBO(merchantDOs.get(0));
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
}
