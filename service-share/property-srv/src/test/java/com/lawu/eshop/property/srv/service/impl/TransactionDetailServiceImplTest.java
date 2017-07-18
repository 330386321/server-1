package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.ConsumptionTypeEnum;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.PropertyInfoDirectionEnum;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.param.*;
import com.lawu.eshop.property.srv.bo.TransactionDetailBO;
import com.lawu.eshop.property.srv.mapper.TransactionDetailDOMapper;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
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
public class TransactionDetailServiceImplTest {

    @Autowired
    private TransactionDetailService transactionDetailService;

    @Autowired
    private TransactionDetailDOMapper transactionDetailDOMapper;



    @Transactional
    @Rollback
    @Test
    public void save(){
        TransactionDetailSaveDataParam param = new TransactionDetailSaveDataParam();
        param.setTitle("看广告");
        param.setTransactionNum("1111111");
        param.setUserNum("M10001");
        param.setTransactionType(MemberTransactionTypeEnum.AD_QZ.getValue());
        param.setTransactionAccount("121210");
        param.setTransactionAccountType(TransactionPayTypeEnum.ALIPAY.getVal());
        param.setAmount(new BigDecimal("100"));
        param.setBizId("1");
        param.setRemark("");
        param.setDirection(PropertyInfoDirectionEnum.IN.getVal());
        param.setBizNum("222");
        int ret = transactionDetailService.save(param);
        Assert.assertEquals(ResultCode.SUCCESS,ret);
    }

    @Transactional
    @Rollback
    @Test
    public void findPageByUserNumForMerchant(){
        TransactionDetailSaveDataParam param = new TransactionDetailSaveDataParam();
        param.setTitle("看广告");
        param.setTransactionNum("1111111");
        param.setUserNum("M10001");
        param.setTransactionType(MemberTransactionTypeEnum.AD_QZ.getValue());
        param.setTransactionAccount("121210");
        param.setTransactionAccountType(TransactionPayTypeEnum.ALIPAY.getVal());
        param.setAmount(new BigDecimal("100"));
        param.setBizId("1");
        param.setRemark("");
        param.setDirection(PropertyInfoDirectionEnum.IN.getVal());
        param.setBizNum("222");
        int ret = transactionDetailService.save(param);
        Assert.assertEquals(ResultCode.SUCCESS,ret);

        TransactionDetailQueryForMerchantParam query = new TransactionDetailQueryForMerchantParam();
        query.setConsumptionType(ConsumptionTypeEnum.INCOME);
        query.setCurrentPage(1);
        query.setPageSize(10);
        Page<TransactionDetailBO> rtnPage = transactionDetailService.findPageByUserNumForMerchant("M10001",query);
        Assert.assertEquals(1,rtnPage.getTotalCount().intValue());
    }

    @Transactional
    @Rollback
    @Test
    public void findPageByUserNumForMember(){
        TransactionDetailSaveDataParam param = new TransactionDetailSaveDataParam();
        param.setTitle("看广告");
        param.setTransactionNum("1111111");
        param.setUserNum("M10001");
        param.setTransactionType(MemberTransactionTypeEnum.AD_QZ.getValue());
        param.setTransactionAccount("121210");
        param.setTransactionAccountType(TransactionPayTypeEnum.ALIPAY.getVal());
        param.setAmount(new BigDecimal("100"));
        param.setBizId("1");
        param.setRemark("");
        param.setDirection(PropertyInfoDirectionEnum.IN.getVal());
        param.setBizNum("222");
        int ret = transactionDetailService.save(param);
        Assert.assertEquals(ResultCode.SUCCESS,ret);

        TransactionDetailQueryForMemberParam query = new TransactionDetailQueryForMemberParam();
        query.setTransactionType(MemberTransactionTypeEnum.AD_QZ);
        query.setCurrentPage(1);
        query.setPageSize(10);
        Page<TransactionDetailBO> rtnPage = transactionDetailService.findPageByUserNumForMember("M10001",query);
        Assert.assertEquals(1,rtnPage.getTotalCount().intValue());
    }

    @Transactional
    @Rollback
    @Test
    public void verifyOrderIsPaySuccess(){
        NotifyCallBackParam param = new NotifyCallBackParam();
        param.setOutTradeNo("4545121");
        param.setUserNum("M10001");
        Boolean bool = transactionDetailService.verifyOrderIsPaySuccess(param);
        Assert.assertEquals(false,bool);
    }

    @Transactional
    @Rollback
    @Test
    public void getBackageRechargePageList(){
        TransactionDetailSaveDataParam param = new TransactionDetailSaveDataParam();
        param.setTitle("看广告");
        param.setTransactionNum("1111111");
        param.setUserNum("M10001");
        param.setTransactionType(MemberTransactionTypeEnum.BACKAGE.getValue());
        param.setTransactionAccount("121210");
        param.setTransactionAccountType(TransactionPayTypeEnum.ALIPAY.getVal());
        param.setAmount(new BigDecimal("100"));
        param.setBizId("1");
        param.setRemark("");
        param.setDirection(PropertyInfoDirectionEnum.IN.getVal());
        param.setBizNum("222");
        int ret = transactionDetailService.save(param);
        Assert.assertEquals(ResultCode.SUCCESS,ret);

        TransactionDetailQueryForBackageParam query = new TransactionDetailQueryForBackageParam();
        query.setMemberTransactionType(MemberTransactionTypeEnum.AD_QZ);
        query.setUserNum("M10001");
        query.setCurrentPage(1);
        query.setPageSize(10);
        Page<TransactionDetailBO> rtnPage = transactionDetailService.getBackageRechargePageList(query);
        Assert.assertEquals(1,rtnPage.getTotalCount().intValue());

    }
}
