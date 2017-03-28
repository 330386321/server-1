package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.constants.UserStatusConstant;
import com.lawu.eshop.user.param.RegisterParam;
import com.lawu.eshop.user.query.MerchantInviterParam;
import com.lawu.eshop.user.srv.bo.MerchantBO;
import com.lawu.eshop.user.srv.bo.MerchantInfoBO;
import com.lawu.eshop.user.srv.bo.MerchantInviterBO;
import com.lawu.eshop.user.srv.converter.MerchantConverter;
import com.lawu.eshop.user.srv.converter.MerchantInviterConverter;
import com.lawu.eshop.user.srv.domain.*;
import com.lawu.eshop.user.srv.domain.MerchantDOExample.Criteria;
import com.lawu.eshop.user.srv.mapper.*;
import com.lawu.eshop.user.srv.service.MerchantService;
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

    @Override
    public void updateLoginPwd(Long id, String originalPwd, String newPwd) {

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
    public MerchantInfoBO findMerchantInfo(Long merchantProfileId) {
        MerchantDO merchantDO = merchantDOMapper.selectByPrimaryKey(merchantProfileId);

        return MerchantConverter.convertInfoBO(merchantDO);
    }

    @Override
    @Transactional
    public void register(RegisterParam registerParam) {
        //推荐人id
        long invitedUserId = 0;
        if (registerParam.getInviterId() != null) {
            invitedUserId = registerParam.getInviterId();
        }
        //插入商户信息
        MerchantDO merchantDO = new MerchantDO();
        merchantDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MERCHANT_NUM_TAG));
        merchantDO.setAccount(registerParam.getAccount());
        merchantDO.setPwd(MD5.MD5Encode(registerParam.getPwd()));
        merchantDO.setMobile(registerParam.getAccount());
        merchantDO.setStatus(UserStatusConstant.MEMBER_STATUS_VALID);
        merchantDO.setLevel(UserCommonConstant.LEVEL_1);
        merchantDO.setGmtCreate(new Date());
        if (registerParam.getInviterId() != null) {
            merchantDO.setInviterId(registerParam.getInviterId());
        }
        if (registerParam.getInviterType() != null) {
            merchantDO.setInviterType(registerParam.getInviterType());
        }
        merchantDOMapper.insertSelective(merchantDO);
        long merchantId = merchantDO.getId();

        //插入商户扩展信息
        MerchantProfileDO merchantProfileDO = new MerchantProfileDO();
        merchantProfileDO.setId(merchantId);
        merchantProfileDO.setInviteMemberCount(0);
        merchantProfileDO.setInviteMerchantCount(0);
        merchantProfileDO.setGmtCreate(new Date());
        merchantProfileDOMapper.insertSelective(merchantProfileDO);

        //插入商户推荐关系
        InviteRelationDO inviteRelationDO = new InviteRelationDO();
        inviteRelationDO.setUserId(merchantId);
        inviteRelationDO.setInvitedUserId(merchantId);
        inviteRelationDO.setDepth(0);
        inviteRelationDO.setType(UserCommonConstant.INVITER_TYPE_MERCHANT);
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
                    inviteRelationDO.setInvitedUserId(merchantId);
                    inviteRelationDO.setDepth(inviteRelationDO1.getDepth() + 1);
                    inviteRelationDO.setType(inviteRelationDO1.getType());
                    inviteRelationDOMapper.insert(inviteRelationDO);
                }
            }

            //查询商户的三级推荐人
            inviteRelationDOExample = new InviteRelationDOExample();
            inviteRelationDOExample.createCriteria().andInvitedUserIdEqualTo(merchantId).andDepthBetween(1, 3);
            inviteRelationDOS = inviteRelationDOMapper.selectByExample(inviteRelationDOExample);
            if (!inviteRelationDOS.isEmpty()) {
                for (InviteRelationDO inviteRelationDO1 : inviteRelationDOS) {
                    //查询商户推荐人的推荐人数
                    inviteRelationDOExample = new InviteRelationDOExample();
                    inviteRelationDOExample.createCriteria().andUserIdEqualTo(inviteRelationDO1.getUserId()).andDepthBetween(1, 3);
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
                    if (inviteRelationDO1.getType() == UserCommonConstant.INVITER_TYPE_MEMBER) {
                        MemberDO memberDO = new MemberDO();
                        memberDO.setLevel(level);
                        memberDO.setId(inviteRelationDO1.getUserId());
                        memberDOMapper.updateByPrimaryKeySelective(memberDO);
                    } else {
                        merchantDO = new MerchantDO();
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
                    //更新商户扩展信息
                    merchantProfileDO = new MerchantProfileDO();
                    merchantProfileDO.setId(inviteRelationDO1.getUserId());
                    merchantProfileDO.setInviteMemberCount(inviteMemberCount);
                    merchantProfileDO.setInviteMerchantCount(inviteMerchantCount);
                    merchantProfileDO.setGmtModified(new Date());
                    merchantProfileDOMapper.updateByPrimaryKeySelective(merchantProfileDO);
                }
            }
        }
    }

    @Override
    public MerchantBO getMerchantBOById(Long id) {
        MerchantDO merchantDO = merchantDOMapper.selectByPrimaryKey(id);
        return MerchantConverter.convertBO(merchantDO);
    }

    @Override
    public Page<MerchantInviterBO> getMerchantByInviter(Long userId, MerchantInviterParam pageParam) {
        MerchantDOExample example = new MerchantDOExample();
        Criteria c1 = example.createCriteria();
        c1.andInviterIdEqualTo(userId).andStatusEqualTo(new Byte("1"));
        int totalCount = merchantDOMapper.countByExample(example); //总记录数
        if (pageParam.getAccount() != null) { //存在模糊查询
            c1.andAccountLike("%" + pageParam.getAccount() + "%");
        }
        RowBounds rowBounds = new RowBounds(pageParam.getOffset(), pageParam.getPageSize());
        //推荐的商家
        List<MerchantDO> merchantDOS = merchantDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        //店铺
        List<MerchantStoreDO> storeList = new ArrayList<MerchantStoreDO>();
        for (MerchantDO merchantDO : merchantDOS) {
            MerchantStoreDOExample msExample = new MerchantStoreDOExample();
            msExample.createCriteria().andMerchantIdEqualTo(merchantDO.getId())
                    .andStatusEqualTo(new Byte("1"));
            List<MerchantStoreDO> list = merchantStoreDOMapper.selectByExample(msExample);
            if (!list.isEmpty()) {
                MerchantStoreDO merchantStoreDO = list.get(0);
                storeList.add(merchantStoreDO);
            }
        }

        Page<MerchantInviterBO> pageMerchantInviter = new Page<MerchantInviterBO>();
        pageMerchantInviter.setTotalCount(totalCount);
        List<MerchantInviterBO> memberBOS = MerchantInviterConverter.convertMerchantInviterBOS(merchantDOS, storeList);
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
}
