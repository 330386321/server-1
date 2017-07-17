package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.mall.constants.MerchantFavoredTypeEnum;
import com.lawu.eshop.mall.constants.StatusEnum;
import com.lawu.eshop.mall.param.MerchantFavoredParam;
import com.lawu.eshop.mall.srv.bo.MerchantFavoredBO;
import com.lawu.eshop.mall.srv.domain.MerchantFavoredDO;
import com.lawu.eshop.mall.srv.mapper.MerchantFavoredDOMapper;
import com.lawu.eshop.mall.srv.service.MerchantFavoredService;
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
 * @author zhangyong
 * @date 2017/7/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class MerchantFavoredServiceImplTest {

    @Autowired
    private MerchantFavoredService merchantFavoredService;

    @Autowired
    private MerchantFavoredDOMapper merchantFavoredDOMapper;

    @Transactional
    @Rollback
    @Test
    public void saveMerchantFavoredInfo(){
        MerchantFavoredParam param = new MerchantFavoredParam();
        param.setDiscountRate(BigDecimal.ONE);
        param.setFavoredAmount(BigDecimal.valueOf(20));
        param.setReachAmount(BigDecimal.valueOf(100));
        param.setEntireBeginTime(new Date());
        param.setEntireEndTime(new Date());
        param.setTypeEnum(MerchantFavoredTypeEnum.TYPE_DISCOUNT);
        param.setValidDayBeginTime("07:00");
        param.setValidDayEndTime("18:00");
        param.setValidWeekTime("每周");
        merchantFavoredService.saveMerchantFavoredInfo(1L,param);
        List<MerchantFavoredDO> list = merchantFavoredDOMapper.selectByExample(null);
        Assert.assertNotNull(list);
        Assert.assertEquals(1,list.size());
    }


    @Transactional
    @Rollback
    @Test
    public void findFavoredByMerchantId(){
        MerchantFavoredDO merchantFavoredDO = new MerchantFavoredDO();
        merchantFavoredDO.setDiscountRate(BigDecimal.ONE);
        merchantFavoredDO.setStatus(StatusEnum.STATUS_VALID.val);
        merchantFavoredDO.setMerchantId(1L);
        merchantFavoredDO.setType(MerchantFavoredTypeEnum.TYPE_DISCOUNT.val);
        merchantFavoredDOMapper.insert(merchantFavoredDO);
        MerchantFavoredBO merchantFavoredBO =merchantFavoredService.findFavoredByMerchantId(1L);
        Assert.assertNotNull(merchantFavoredBO);
        Assert.assertEquals(MerchantFavoredTypeEnum.TYPE_DISCOUNT.val,merchantFavoredBO.getType());
    }

    @Transactional
    @Rollback
    @Test
    public void delMerchantFavoredInfoById(){
        MerchantFavoredDO merchantFavoredDO = new MerchantFavoredDO();
        merchantFavoredDO.setDiscountRate(BigDecimal.ONE);
        merchantFavoredDO.setStatus(StatusEnum.STATUS_VALID.val);
        merchantFavoredDO.setMerchantId(1L);
        merchantFavoredDO.setType(MerchantFavoredTypeEnum.TYPE_DISCOUNT.val);
        merchantFavoredDOMapper.insert(merchantFavoredDO);
        merchantFavoredService.delMerchantFavoredInfoById(merchantFavoredDO.getId(),1L);
        List<MerchantFavoredDO> list = merchantFavoredDOMapper.selectByExample(null);
        Assert.assertNotNull(list);
        Assert.assertEquals(1,list.size());
        Assert.assertEquals(StatusEnum.STATUS_INVALID.val,list.get(0).getStatus());
    }

    @Transactional
    @Rollback
    @Test
    public void findFavoredById(){
        MerchantFavoredDO merchantFavoredDO = new MerchantFavoredDO();
        merchantFavoredDO.setDiscountRate(BigDecimal.ONE);
        merchantFavoredDO.setStatus(StatusEnum.STATUS_VALID.val);
        merchantFavoredDO.setMerchantId(1L);
        merchantFavoredDO.setType(MerchantFavoredTypeEnum.TYPE_DISCOUNT.val);
        merchantFavoredDOMapper.insert(merchantFavoredDO);

        MerchantFavoredBO merchantFavoredBO = merchantFavoredService.findFavoredById(merchantFavoredDO.getId());
        Assert.assertNotNull(merchantFavoredBO);
        Assert.assertEquals(MerchantFavoredTypeEnum.TYPE_DISCOUNT.val,merchantFavoredBO.getType());
    }

    @Transactional
    @Rollback
    @Test
    public  void updateMerchantFavoredInfo(){
        MerchantFavoredDO merchantFavoredDO = new MerchantFavoredDO();
        merchantFavoredDO.setDiscountRate(BigDecimal.ONE);
        merchantFavoredDO.setStatus(StatusEnum.STATUS_VALID.val);
        merchantFavoredDO.setMerchantId(1L);
        merchantFavoredDO.setType(MerchantFavoredTypeEnum.TYPE_DISCOUNT.val);
        merchantFavoredDOMapper.insert(merchantFavoredDO);

        MerchantFavoredParam param = new MerchantFavoredParam();
        param.setDiscountRate(BigDecimal.TEN);
        param.setTypeEnum(MerchantFavoredTypeEnum.TYPE_DISCOUNT);
        param.setValidDayBeginTime("07:00");
        param.setValidDayEndTime("18:00");
        param.setValidWeekTime("每周");
        merchantFavoredService.updateMerchantFavoredInfo(1L,param);
        List<MerchantFavoredDO> list = merchantFavoredDOMapper.selectByExample(null);
        Assert.assertNotNull(list);
        Assert.assertEquals(1,list.size());
        Assert.assertEquals(0,BigDecimal.TEN.compareTo(list.get(0).getDiscountRate()));
        param.setTypeEnum(MerchantFavoredTypeEnum.TYPE_FULL_REDUCE);
        param.setReachAmount(BigDecimal.valueOf(100));
        param.setFavoredAmount(BigDecimal.valueOf(20));
        merchantFavoredService.updateMerchantFavoredInfo(1L,param);
        List<MerchantFavoredDO> merchantFavoredDOS = merchantFavoredDOMapper.selectByExample(null);
        Assert.assertNotNull(merchantFavoredDOS);
        Assert.assertEquals(1,merchantFavoredDOS.size());
        Assert.assertEquals(MerchantFavoredTypeEnum.TYPE_FULL_REDUCE.val,merchantFavoredDOS.get(0).getType());

    }
}