package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.dto.CommissionInvitersUserDTO;
import com.lawu.eshop.user.srv.bo.InviterBO;
import com.lawu.eshop.user.srv.domain.InviteRelationDO;
import com.lawu.eshop.user.srv.domain.MemberDO;
import com.lawu.eshop.user.srv.domain.MerchantDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;
import com.lawu.eshop.user.srv.mapper.InviteRelationDOMapper;
import com.lawu.eshop.user.srv.mapper.MemberDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.service.CommonService;
import com.lawu.eshop.utils.DataTransUtil;
import com.lawu.eshop.utils.RandomUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/7/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class CommonServiceImplTest {

    @Autowired
    private CommonService commonService;

    @Autowired
    private MemberDOMapper memberDOMapper;

    @Autowired
    private MerchantDOMapper merchantDOMapper;

    @Autowired
    private MerchantStoreDOMapper merchantStoreDOMapper;

    @Autowired
    private InviteRelationDOMapper inviteRelationDOMapper;

    @Transactional
    @Rollback
    @Test
    public void getInviterByAccount() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd("123456");
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        MerchantDO merchantDO = new MerchantDO();
        merchantDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MERCHANT_NUM_TAG));
        merchantDO.setAccount("13888888888");
        merchantDO.setPwd("123456");
        merchantDO.setMobile("13888888888");
        merchantDO.setStatus(DataTransUtil.intToByte(1));
        merchantDO.setGmtCreate(new Date());
        merchantDOMapper.insertSelective(merchantDO);

        MerchantStoreDO merchantStoreDO = new MerchantStoreDO();
        merchantStoreDO.setMerchantId(merchantDO.getId());
        merchantStoreDO.setName("测试店铺");
        merchantStoreDO.setRegionPath("44/4403/440303");
        merchantStoreDO.setAddress("大冲商务中心");
        merchantStoreDO.setLongitude(new BigDecimal(114.2365889));
        merchantStoreDO.setLatitude(new BigDecimal(22.365244));
        merchantStoreDO.setIntro("测试店铺介绍");
        merchantStoreDO.setStatus(DataTransUtil.intToByte(1));
        merchantStoreDO.setIsNoReasonReturn(true);
        merchantStoreDOMapper.insertSelective(merchantStoreDO);

        InviterBO inviterBO = commonService.getInviterByAccount("13666666666");
        Assert.assertNotNull(inviterBO);

        inviterBO = commonService.getInviterByAccount("13888888888");
        Assert.assertNotNull(inviterBO);
    }

    @Transactional
    @Rollback
    @Test
    public void selectHigherLevelInviters() {
        InviteRelationDO inviteRelationDO = new InviteRelationDO();
        inviteRelationDO.setUserNum("M0001");
        inviteRelationDO.setInviteUserNum("M0002");
        inviteRelationDO.setDepth(1);
        inviteRelationDO.setType(DataTransUtil.intToByte(1));
        inviteRelationDOMapper.insertSelective(inviteRelationDO);

        List<CommissionInvitersUserDTO> invitersUserDTOS = commonService.selectHigherLevelInviters("M0002", 1, false);
        Assert.assertNotNull(invitersUserDTOS);
        Assert.assertEquals(1, invitersUserDTOS.size());

        invitersUserDTOS = commonService.selectHigherLevelInviters("M0002", 1, true);
        Assert.assertNotNull(invitersUserDTOS);
        Assert.assertEquals(1, invitersUserDTOS.size());
    }
}
