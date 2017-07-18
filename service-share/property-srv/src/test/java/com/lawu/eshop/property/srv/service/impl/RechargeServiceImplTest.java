package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.*;
import com.lawu.eshop.property.dto.RechargeSaveDTO;
import com.lawu.eshop.property.dto.ThirdPayCallBackQueryPayOrderDTO;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.param.RechargeQueryDataParam;
import com.lawu.eshop.property.param.RechargeReportParam;
import com.lawu.eshop.property.param.RechargeSaveDataParam;
import com.lawu.eshop.property.srv.bo.BalanceAndPointListQueryBO;
import com.lawu.eshop.property.srv.bo.RechargeReportBO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDO;
import com.lawu.eshop.property.srv.domain.RechargeDO;
import com.lawu.eshop.property.srv.mapper.PropertyInfoDOMapper;
import com.lawu.eshop.property.srv.mapper.RechargeDOMapper;
import com.lawu.eshop.property.srv.service.RechargeService;
import com.lawu.eshop.utils.DateUtil;
import com.lawu.eshop.utils.PwdUtil;
import com.lawu.eshop.utils.StringUtil;
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
 * @author yangqh
 * @date 2017/7/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class RechargeServiceImplTest {

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    private PropertyInfoDOMapper propertyInfoDOMapper;

    @Autowired
    private RechargeDOMapper rechargeDOMapper;


    @Transactional
    @Rollback
    @Test
    public void save(){
        PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
        propertyInfoDO.setUserNum("M10001");
        propertyInfoDO.setGmtCreate(new Date());
        propertyInfoDO.setPayPassword(PwdUtil.generate("123456"));
        propertyInfoDO.setBalance(new BigDecimal("200"));
        propertyInfoDO.setPoint(new BigDecimal("200"));
        propertyInfoDO.setLoveAccount(new BigDecimal("200"));
        propertyInfoDO.setFreeze(FreezeStatusEnum.RELEASE.getVal());
        propertyInfoDOMapper.insertSelective(propertyInfoDO);

        RechargeSaveDataParam param = new RechargeSaveDataParam();
        param.setRechargeMoney("100");
        param.setPayTypeEnum(PayTypeEnum.BALANCE);
        param.setTransactionPayTypeEnum(TransactionPayTypeEnum.ALIPAY);
        param.setPayPwd("123456");
        param.setRechargeScale("1");
        param.setUserNum("M10001");
        RechargeSaveDTO dto = rechargeService.save(param);
        Assert.assertNotEquals(0,dto.getId().intValue());
    }

    @Transactional
    @Rollback
    @Test
    public void doHandleRechargeNotify(){
        RechargeDO recharge = new RechargeDO();
        recharge.setUserNum("M10001");
        recharge.setRechargeMoney(new BigDecimal("20"));
        recharge.setCurrentScale("1");
        double dCurrentScale = Double.parseDouble("1");
        double dRechargeMoney = Double.parseDouble("20");
        double money = dRechargeMoney * dCurrentScale;
        recharge.setMoney(BigDecimal.valueOf(money));
        recharge.setRechargeType(PayTypeEnum.BALANCE.getVal());
        recharge.setChannel(TransactionPayTypeEnum.ALIPAY.getVal());
        recharge.setStatus(ThirdPayStatusEnum.PAYING.getVal());
        recharge.setRechargeNumber(StringUtil.getRandomNum(""));
        recharge.setGmtCreate(new Date());
        rechargeDOMapper.insertSelective(recharge);

        NotifyCallBackParam param1 = new NotifyCallBackParam();
        param1.setUserNum("M10001");
        param1.setBizIds(recharge.getId().toString());
        param1.setBizFlag("5");
        param1.setTotalFee("20");
        param1.setOutTradeNo("1111111");
        param1.setBuyerLogonId("1232**656");
        param1.setTransactionPayTypeEnum(TransactionPayTypeEnum.ALIPAY);
        param1.setTradeNo("222222");
        Result result = rechargeService.doHandleRechargeNotify(param1);
        Assert.assertEquals(ResultCode.SUCCESS,result.getRet());

    }

    @Transactional
    @Rollback
    @Test
    public void getRechargeMoney(){
        RechargeDO recharge = new RechargeDO();
        recharge.setUserNum("M10001");
        recharge.setRechargeMoney(new BigDecimal("20"));
        recharge.setCurrentScale("1");
        double dCurrentScale = Double.parseDouble("1");
        double dRechargeMoney = Double.parseDouble("20");
        double money = dRechargeMoney * dCurrentScale;
        recharge.setMoney(BigDecimal.valueOf(money));
        recharge.setRechargeType(PayTypeEnum.BALANCE.getVal());
        recharge.setChannel(TransactionPayTypeEnum.ALIPAY.getVal());
        recharge.setStatus(ThirdPayStatusEnum.PAYING.getVal());
        recharge.setRechargeNumber(StringUtil.getRandomNum(""));
        recharge.setGmtCreate(new Date());
        rechargeDOMapper.insertSelective(recharge);

        ThirdPayCallBackQueryPayOrderDTO dto = rechargeService.getRechargeMoney(recharge.getId().toString());
        Assert.assertNotNull(dto);
    }

    @Transactional
    @Rollback
    @Test
    public void selectPropertyinfoList(){
        RechargeDO recharge = new RechargeDO();
        recharge.setUserNum("M10001");
        recharge.setRechargeMoney(new BigDecimal("20"));
        recharge.setCurrentScale("1");
        double dCurrentScale = Double.parseDouble("1");
        double dRechargeMoney = Double.parseDouble("20");
        double money = dRechargeMoney * dCurrentScale;
        recharge.setMoney(BigDecimal.valueOf(money));
        recharge.setRechargeType(PayTypeEnum.BALANCE.getVal());
        recharge.setChannel(TransactionPayTypeEnum.ALIPAY.getVal());
        recharge.setStatus(ThirdPayStatusEnum.PAYING.getVal());
        recharge.setRechargeNumber(StringUtil.getRandomNum(""));
        recharge.setGmtCreate(new Date());
        rechargeDOMapper.insertSelective(recharge);

        RechargeQueryDataParam param = new RechargeQueryDataParam();
        param.setUserType(UserTypeEnum.MEMBER);
        param.setStatus(ThirdPayStatusEnum.PAYING);
        param.setRechargeNumber(recharge.getRechargeNumber());
        param.setPageSize(10);
        param.setCurrentPage(1);
        Page<BalanceAndPointListQueryBO> rtnPage = rechargeService.selectPropertyinfoList(param);
        Assert.assertEquals(1,rtnPage.getTotalCount().intValue());
    }

    @Transactional
    @Rollback
    @Test
    public void getRechargePayType(){
        RechargeDO recharge = new RechargeDO();
        recharge.setUserNum("M10001");
        recharge.setRechargeMoney(new BigDecimal("20"));
        recharge.setCurrentScale("1");
        double dCurrentScale = Double.parseDouble("1");
        double dRechargeMoney = Double.parseDouble("20");
        double money = dRechargeMoney * dCurrentScale;
        recharge.setMoney(BigDecimal.valueOf(money));
        recharge.setRechargeType(PayTypeEnum.BALANCE.getVal());
        recharge.setChannel(TransactionPayTypeEnum.ALIPAY.getVal());
        recharge.setStatus(ThirdPayStatusEnum.PAYING.getVal());
        recharge.setRechargeNumber(StringUtil.getRandomNum(""));
        recharge.setGmtCreate(new Date());
        rechargeDOMapper.insertSelective(recharge);

        String rtn = rechargeService.getRechargePayType(recharge.getId());
        Assert.assertNotEquals("",rtn);

    }

    @Transactional
    @Rollback
    @Test
    public void selectWithdrawCashListByDateAndStatus(){
        RechargeDO recharge = new RechargeDO();
        recharge.setUserNum("M10001");
        recharge.setRechargeMoney(new BigDecimal("20"));
        recharge.setCurrentScale("1");
        double dCurrentScale = Double.parseDouble("1");
        double dRechargeMoney = Double.parseDouble("20");
        double money = dRechargeMoney * dCurrentScale;
        recharge.setMoney(BigDecimal.valueOf(money));
        recharge.setRechargeType(PayTypeEnum.BALANCE.getVal());
        recharge.setChannel(TransactionPayTypeEnum.ALIPAY.getVal());
        recharge.setStatus(ThirdPayStatusEnum.PAYING.getVal());
        recharge.setRechargeNumber(StringUtil.getRandomNum(""));
        recharge.setGmtCreate(new Date());
        recharge.setGmtModified(new Date());
        rechargeDOMapper.insertSelective(recharge);

        RechargeReportParam param = new RechargeReportParam();
        param.setDate(DateUtil.getDateFormat(new Date(),"yyyy-MM-dd"));
        param.setRechargeType(PayTypeEnum.BALANCE.getVal());
        param.setStatus(ThirdPayStatusEnum.PAYING.getVal());
        List<RechargeReportBO> rtnList = rechargeService.selectWithdrawCashListByDateAndStatus(param);
        Assert.assertEquals(1,rtnList.size());
    }
}
