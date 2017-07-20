package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.user.constants.ManageTypeEnum;
import com.lawu.eshop.user.param.FavoriteStoreParam;
import com.lawu.eshop.user.srv.bo.MemberProfileBO;
import com.lawu.eshop.user.srv.domain.MemberProfileDO;
import com.lawu.eshop.user.srv.mapper.MemberProfileDOMapper;
import com.lawu.eshop.user.srv.service.MemberProfileService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author meishuquan
 * @date 2017/7/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class MemberProfileServiceImplTest {

    @Autowired
    private MemberProfileService memberProfileService;

    @Autowired
    private MemberProfileDOMapper memberProfileDOMapper;

    @Transactional
    @Rollback
    @Test
    public void getMemberCount() {
        MemberProfileDO memberProfileDO = new MemberProfileDO();
        memberProfileDO.setId(100L);
        memberProfileDOMapper.insertSelective(memberProfileDO);

        int result = memberProfileService.getMemberCount(100L);
        Assert.assertEquals(0, result);
    }

    @Transactional
    @Rollback
    @Test
    public void getMerchantCount() {
        MemberProfileDO memberProfileDO = new MemberProfileDO();
        memberProfileDO.setId(200L);
        memberProfileDOMapper.insertSelective(memberProfileDO);

        int result = memberProfileService.getMerchantCount(200L);
        Assert.assertEquals(0, result);
    }

    @Transactional
    @Rollback
    @Test
    public void get() {
        MemberProfileDO memberProfileDO = new MemberProfileDO();
        memberProfileDO.setId(100L);
        memberProfileDOMapper.insertSelective(memberProfileDO);

        MemberProfileBO memberProfileBO = memberProfileService.get(100L);
        Assert.assertNotNull(memberProfileBO);
    }

}
