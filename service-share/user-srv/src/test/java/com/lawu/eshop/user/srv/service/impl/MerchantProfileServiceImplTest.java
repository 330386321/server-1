package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.param.MerchantProfileParam;
import com.lawu.eshop.user.srv.bo.MerchantProfileBO;
import com.lawu.eshop.user.srv.bo.MerchantSizeLinkBO;
import com.lawu.eshop.user.srv.domain.MerchantDO;
import com.lawu.eshop.user.srv.domain.MerchantProfileDO;
import com.lawu.eshop.user.srv.mapper.MerchantDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantProfileDOMapper;
import com.lawu.eshop.user.srv.service.MerchantProfileService;
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

/**
 * @author meishuquan
 * @date 2017/7/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class MerchantProfileServiceImplTest {

    @Autowired
    private MerchantProfileService merchantProfileService;

    @Autowired
    private MerchantProfileDOMapper merchantProfileDOMapper;

    @Autowired
    private MerchantDOMapper merchantDOMapper;

    @Transactional
    @Rollback
    @Test
    public void updateMerchantSizeLink() {
        //初始化商家
        MerchantDO merchantDO = new MerchantDO();
        merchantDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MERCHANT_NUM_TAG));
        merchantDO.setAccount("13888888888");
        merchantDO.setPwd("123456");
        merchantDO.setMobile("13888888888");
        merchantDO.setStatus(DataTransUtil.intToByte(1));
        merchantDO.setGmtCreate(new Date());
        merchantDOMapper.insertSelective(merchantDO);

        //初始化商家扩展信息
        MerchantProfileDO profileDO = new MerchantProfileDO();
        profileDO.setId(merchantDO.getId());
        merchantProfileDOMapper.insertSelective(profileDO);

        MerchantProfileParam param = new MerchantProfileParam();
        param.setJdUrl("pic");
        merchantProfileService.updateMerchantSizeLink(param, profileDO.getId());
        MerchantProfileDO merchantProfileDO = merchantProfileDOMapper.selectByPrimaryKey(profileDO.getId());
        Assert.assertNotNull(merchantProfileDO);
        Assert.assertTrue("pic".equals(merchantProfileDO.getJdUrl()));
    }

    @Transactional
    @Rollback
    @Test
    public void findMerchantProfileInfo() {
        //初始化商家
        MerchantDO merchantDO = new MerchantDO();
        merchantDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MERCHANT_NUM_TAG));
        merchantDO.setAccount("13888888888");
        merchantDO.setPwd("123456");
        merchantDO.setMobile("13888888888");
        merchantDO.setStatus(DataTransUtil.intToByte(1));
        merchantDO.setGmtCreate(new Date());
        merchantDOMapper.insertSelective(merchantDO);

        //初始化商家扩展信息
        MerchantProfileDO profileDO = new MerchantProfileDO();
        profileDO.setId(merchantDO.getId());
        merchantProfileDOMapper.insertSelective(profileDO);

        MerchantProfileBO merchantProfileBO = merchantProfileService.findMerchantProfileInfo(profileDO.getId());
        Assert.assertNotNull(merchantProfileBO);
    }

    @Transactional
    @Rollback
    @Test
    public void getMerchantSizeLink() {
        //初始化商家
        MerchantDO merchantDO = new MerchantDO();
        merchantDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MERCHANT_NUM_TAG));
        merchantDO.setAccount("13888888888");
        merchantDO.setPwd("123456");
        merchantDO.setMobile("13888888888");
        merchantDO.setStatus(DataTransUtil.intToByte(1));
        merchantDO.setGmtCreate(new Date());
        merchantDOMapper.insertSelective(merchantDO);

        //初始化商家扩展信息
        MerchantProfileDO profileDO = new MerchantProfileDO();
        profileDO.setId(merchantDO.getId());
        merchantProfileDOMapper.insertSelective(profileDO);

        MerchantSizeLinkBO merchantSizeLinkBO = merchantProfileService.getMerchantSizeLink(profileDO.getId());
        Assert.assertNotNull(merchantSizeLinkBO);
    }

    @Transactional
    @Rollback
    @Test
    public void getMerchantProfile() {
        //初始化商家
        MerchantDO merchantDO = new MerchantDO();
        merchantDO.setNum(RandomUtil.getTableNumRandomString(UserCommonConstant.MERCHANT_NUM_TAG));
        merchantDO.setAccount("13888888888");
        merchantDO.setPwd("123456");
        merchantDO.setMobile("13888888888");
        merchantDO.setStatus(DataTransUtil.intToByte(1));
        merchantDO.setGmtCreate(new Date());
        merchantDOMapper.insertSelective(merchantDO);

        //初始化商家扩展信息
        MerchantProfileDO profileDO = new MerchantProfileDO();
        profileDO.setId(merchantDO.getId());
        merchantProfileDOMapper.insertSelective(profileDO);

        MerchantProfileBO merchantProfileBO = merchantProfileService.getMerchantProfile(profileDO.getId());
        Assert.assertNotNull(merchantProfileBO);
    }

}
