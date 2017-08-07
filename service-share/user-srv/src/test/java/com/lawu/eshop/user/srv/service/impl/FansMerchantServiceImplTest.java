package com.lawu.eshop.user.srv.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.constants.FansMerchantChannelEnum;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.constants.UserSexEnum;
import com.lawu.eshop.user.param.ListFansParam;
import com.lawu.eshop.user.param.ListInviteFansParam;
import com.lawu.eshop.user.param.PageListInviteFansParam;
import com.lawu.eshop.user.srv.bo.FansMerchantBO;
import com.lawu.eshop.user.srv.domain.FansMerchantDO;
import com.lawu.eshop.user.srv.domain.MemberDO;
import com.lawu.eshop.user.srv.mapper.FansInviteResultDOMapper;
import com.lawu.eshop.user.srv.mapper.FansMerchantDOMapper;
import com.lawu.eshop.user.srv.mapper.MemberDOMapper;
import com.lawu.eshop.user.srv.service.FansMerchantService;
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

import java.util.Date;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/7/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class FansMerchantServiceImplTest {

    @Autowired
    private FansMerchantService fansMerchantService;

    @Autowired
    private FansMerchantDOMapper fansMerchantDOMapper;

    @Autowired
    private MemberDOMapper memberDOMapper;
    
    @Autowired
    private FansInviteResultDOMapper fansInviteResultDOMapper;

    @Transactional
    @Rollback
    @Test
    public void listInviteFans() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd("123456");
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        ListInviteFansParam param = new ListInviteFansParam();
        param.setUserSexEnum(UserSexEnum.SEX_SECRET);
        param.setIsAgeLimit(false);
        param.setInviteCount(100);
        List<FansMerchantBO> fansMerchantBOS = fansMerchantService.listInviteFans(200L, param);
        Assert.assertNotNull(fansMerchantBOS);
        Assert.assertEquals(1, fansMerchantBOS.size());
    }

    @Transactional
    @Rollback
    @Test
    public void pageListInviteFans() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd("123456");
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd("123456");
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        FansMerchantDO fansMerchantDO = new FansMerchantDO();
        fansMerchantDO.setMerchantId(200L);
        fansMerchantDO.setMemberId(memberDO.getId());
        fansMerchantDOMapper.insertSelective(fansMerchantDO);

        PageListInviteFansParam param = new PageListInviteFansParam();
        param.setUserSexEnum(UserSexEnum.SEX_SECRET);
        param.setIsAgeLimit(false);
        Page<FansMerchantBO> fansMerchantBOPage = fansMerchantService.pageListInviteFans(200L, param);
        Assert.assertNotNull(fansMerchantBOPage.getRecords());
        Assert.assertEquals(1, fansMerchantBOPage.getTotalCount().intValue());
    }

    @Transactional
    @Rollback
    @Test
    public void listFans() {
        MemberDO memberDO = new MemberDO();
        memberDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MEMBER_NUM_TAG));
        memberDO.setAccount("13666666666");
        memberDO.setPwd("123456");
        memberDO.setMobile("13666666666");
        memberDO.setStatus(DataTransUtil.intToByte(1));
        memberDO.setGmtCreate(new Date());
        memberDOMapper.insertSelective(memberDO);

        FansMerchantDO fansMerchantDO = new FansMerchantDO();
        fansMerchantDO.setMerchantId(200L);
        fansMerchantDO.setMemberId(memberDO.getId());
        fansMerchantDO.setStatus((byte)1);
        fansMerchantDOMapper.insertSelective(fansMerchantDO);

        ListFansParam param = new ListFansParam();
        Page<FansMerchantBO> fansMerchantBOPage = fansMerchantService.listFans(200L, param);
        Assert.assertNotNull(fansMerchantBOPage.getRecords());
        Assert.assertEquals(1, fansMerchantBOPage.getTotalCount().intValue());
    }

    @Transactional
    @Rollback
    @Test
    public void getFansMerchant() {
        FansMerchantDO fansMerchantDO = new FansMerchantDO();
        fansMerchantDO.setMerchantId(200L);
        fansMerchantDO.setMemberId(100L);
        fansMerchantDOMapper.insertSelective(fansMerchantDO);
        FansMerchantBO fansMerchantBO = fansMerchantService.getFansMerchant(100L, 200L);
        Assert.assertNotNull(fansMerchantBO);
    }

    @Transactional
    @Rollback
    @Test
    public void findMerchant() {
        FansMerchantDO fansMerchantDO = new FansMerchantDO();
        fansMerchantDO.setMerchantId(200L);
        fansMerchantDO.setMemberId(100L);
        fansMerchantDOMapper.insertSelective(fansMerchantDO);

        List<Long> list = fansMerchantService.findMerchant(100L);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
    }

    @Transactional
    @Rollback
    @Test
    public void findFensCount() {
        FansMerchantDO fansMerchantDO = new FansMerchantDO();
        fansMerchantDO.setMerchantId(200L);
        fansMerchantDO.setMemberId(100L);
        fansMerchantDOMapper.insertSelective(fansMerchantDO);

        int result = fansMerchantService.findFensCount(200L);
        Assert.assertEquals(1, result);
    }

    @Transactional
    @Rollback
    @Test
    public void saveFansMerchant() {
        fansMerchantService.saveFansMerchant(200L, 100L, FansMerchantChannelEnum.REGISTER);

        List<FansMerchantDO> fansMerchantDOS = fansMerchantDOMapper.selectByExample(null);
        Assert.assertNotNull(fansMerchantDOS);
        Assert.assertEquals(1, fansMerchantDOS.size());
    }
    
    
    @Transactional
    @Rollback
    @Test
    public void saveFansMerchantFromInvite() {
        fansMerchantService.saveFansMerchantFromInvite(1L, 1L, 10L, true);
        List<FansMerchantDO> fansMerchantDOS = fansMerchantDOMapper.selectByExample(null);
        Assert.assertNotNull(fansMerchantDOS);
        Assert.assertEquals(1, fansMerchantDOS.size());
        int i = fansInviteResultDOMapper.countByExample(null);
        Assert.assertEquals(1, i);
    }
    
}
