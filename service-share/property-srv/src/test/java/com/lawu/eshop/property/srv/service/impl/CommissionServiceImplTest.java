package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.ad.param.CommissionJobParam;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.srv.service.CommissionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author yangqh
 * @date 2017/7/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class CommissionServiceImplTest {

    @Autowired
    private CommissionService commissionService;

    @Transactional
    @Rollback
    @Test
    public void calculation(){
        CommissionJobParam param = new CommissionJobParam();
        param.setBizId(1L);
        param.setActureMoneyIn(new BigDecimal("0.07976"));
        param.setActureLoveIn(new BigDecimal("0.00024"));
        param.setUserNum("M10001");
        param.setLast(true);
        param.setTypeVal(new Byte("1"));
        param.setTypeName("看广告");
        param.setLoveTypeVal(new Byte("1"));
        param.setLoveTypeName("看广告");
        int ret = commissionService.calculation(param);
        Assert.assertEquals(ResultCode.SUCCESS,ret);

        CommissionJobParam param1 = new CommissionJobParam();
        param1.setBizId(2L);
        param1.setActureMoneyIn(new BigDecimal("0.07976"));
        param1.setActureLoveIn(new BigDecimal("0.00024"));
        param1.setUserNum("B10001");
        param1.setLast(true);
        param1.setTypeVal(new Byte("1"));
        param1.setTypeName("邀请E友收益");
        param1.setLoveTypeVal(new Byte("1"));
        param.setLoveTypeName("邀请E友收益");
        int ret1 = commissionService.calculation(param1);

        CommissionJobParam param2 = new CommissionJobParam();
        param2.setBizId(3L);
        param2.setActureMoneyIn(new BigDecimal("0.07976"));
        param2.setActureLoveIn(new BigDecimal("0.00024"));
        param2.setUserNum("M10002");
        param2.setLast(false);
        param2.setTypeVal(new Byte("1"));
        param2.setTypeName("看广告");
        param2.setLoveTypeVal(new Byte("1"));
        param2.setLoveTypeName("看广告");
        int ret2 = commissionService.calculation(param2);
        Assert.assertEquals(ResultCode.SUCCESS,ret2);


    }
}
