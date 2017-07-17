package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.property.param.ListFansInviteDetailParam;
import com.lawu.eshop.property.srv.bo.FansInviteDetailBO;
import com.lawu.eshop.property.srv.domain.FansInviteDetailDO;
import com.lawu.eshop.property.srv.mapper.FansInviteDetailDOMapper;
import com.lawu.eshop.property.srv.service.FansInviteDetailService;
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

/**
 * @author yangqh
 * @date 2017/7/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class FansInviteDetailServiceImplTest {

    @Autowired
    private FansInviteDetailService cansInviteDetailService;

    @Autowired
    private FansInviteDetailDOMapper fansInviteDetailDOMapper;

    @Transactional
    @Rollback
    @Test
    public void listFansInviteDetail(){
        FansInviteDetailDO fdo = new FansInviteDetailDO();
        fdo.setMerchantId(1L);
        fdo.setPointNum("10086");
        fdo.setSex(new Byte("1"));
        fdo.setAge("10-90");
        fdo.setInviteFansCount(100);
        fdo.setConsumePoint(new BigDecimal(100));
        fdo.setRegionName("牛栏山");
        fdo.setGmtCreate(new Date());
        int ret = fansInviteDetailDOMapper.insert(fdo);
        Assert.assertEquals(1,ret);

        ListFansInviteDetailParam param = new ListFansInviteDetailParam();
        param.setCurrentPage(1);
        param.setPageSize(10);
        Page<FansInviteDetailBO> rntResult = cansInviteDetailService.listFansInviteDetail(1L,param);
        Assert.assertEquals(1,rntResult.getTotalCount().intValue());
    }

    @Transactional
    @Rollback
    @Test
    public void getFansInviteDetailByPointNum(){
        FansInviteDetailDO fdo = new FansInviteDetailDO();
        fdo.setMerchantId(1L);
        fdo.setPointNum("10086");
        fdo.setSex(new Byte("1"));
        fdo.setAge("10-90");
        fdo.setInviteFansCount(100);
        fdo.setConsumePoint(new BigDecimal(100));
        fdo.setRegionName("牛栏山");
        fdo.setGmtCreate(new Date());
        int ret = fansInviteDetailDOMapper.insert(fdo);
        Assert.assertEquals(1,ret);

        FansInviteDetailBO bo = cansInviteDetailService.getFansInviteDetailByPointNum("10086");
        Assert.assertNotNull(bo);
    }
}
