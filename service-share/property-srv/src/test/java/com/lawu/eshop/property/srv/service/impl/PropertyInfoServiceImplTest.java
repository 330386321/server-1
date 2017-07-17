package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.FreezeStatusEnum;
import com.lawu.eshop.property.srv.bo.PropertyBalanceBO;
import com.lawu.eshop.property.srv.bo.PropertyInfoBO;
import com.lawu.eshop.property.srv.bo.PropertyPointAndBalanceBO;
import com.lawu.eshop.property.srv.bo.PropertyPointBO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDO;
import com.lawu.eshop.property.srv.mapper.PointDetailDOMapper;
import com.lawu.eshop.property.srv.mapper.PropertyInfoDOMapper;
import com.lawu.eshop.property.srv.service.CommissionUtilService;
import com.lawu.eshop.utils.PwdUtil;
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
public class PropertyInfoServiceImplTest {

    @Autowired
    private com.lawu.eshop.property.srv.service.PropertyInfoService propertyInfoService;

    @Autowired
    private PropertyInfoDOMapper propertyInfoDOMapper;


    @Transactional
    @Rollback
    @Test
    public void getPropertyInfoByUserNum(){
        PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
        propertyInfoDO.setUserNum("M10001");
        propertyInfoDO.setGmtCreate(new Date());
        propertyInfoDO.setPayPassword(PwdUtil.generate("123456"));
        propertyInfoDO.setBalance(new BigDecimal("200"));
        propertyInfoDO.setPoint(new BigDecimal("200"));
        propertyInfoDO.setLoveAccount(new BigDecimal("0"));
        propertyInfoDO.setFreeze(FreezeStatusEnum.FREEZE.getVal());
        propertyInfoDOMapper.insertSelective(propertyInfoDO);

        PropertyInfoBO bo = propertyInfoService.getPropertyInfoByUserNum("M10001");
        Assert.assertNotNull(bo);

    }

    @Transactional
    @Rollback
    @Test
    public void updatePayPwd(){
        PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
        propertyInfoDO.setUserNum("M10001");
        propertyInfoDO.setGmtCreate(new Date());
        propertyInfoDO.setPayPassword(PwdUtil.generate("123456"));
        propertyInfoDO.setBalance(new BigDecimal("200"));
        propertyInfoDO.setPoint(new BigDecimal("200"));
        propertyInfoDO.setLoveAccount(new BigDecimal("0"));
        propertyInfoDO.setFreeze(FreezeStatusEnum.FREEZE.getVal());
        propertyInfoDOMapper.insertSelective(propertyInfoDO);

        propertyInfoService.updatePayPwd("M10001","111111");

        PropertyInfoDO pdo = propertyInfoDOMapper.selectByPrimaryKey(propertyInfoDO.getId());
        Boolean bool = PwdUtil.verify("111111",pdo.getPayPassword());
        Assert.assertEquals(true,bool);
    }

    @Transactional
    @Rollback
    @Test
    public void getPropertyBalanceByUserNum(){
        PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
        propertyInfoDO.setUserNum("M10001");
        propertyInfoDO.setGmtCreate(new Date());
        propertyInfoDO.setPayPassword(PwdUtil.generate("123456"));
        propertyInfoDO.setBalance(new BigDecimal("200"));
        propertyInfoDO.setPoint(new BigDecimal("200"));
        propertyInfoDO.setLoveAccount(new BigDecimal("0"));
        propertyInfoDO.setFreeze(FreezeStatusEnum.FREEZE.getVal());
        propertyInfoDOMapper.insertSelective(propertyInfoDO);

        PropertyBalanceBO bo = propertyInfoService.getPropertyBalanceByUserNum("M10001");
        Assert.assertNotNull(bo);
    }

    @Transactional
    @Rollback
    @Test
    public void getPropertyPointByUserNum(){
        PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
        propertyInfoDO.setUserNum("M10001");
        propertyInfoDO.setGmtCreate(new Date());
        propertyInfoDO.setPayPassword(PwdUtil.generate("123456"));
        propertyInfoDO.setBalance(new BigDecimal("200"));
        propertyInfoDO.setPoint(new BigDecimal("200"));
        propertyInfoDO.setLoveAccount(new BigDecimal("0"));
        propertyInfoDO.setFreeze(FreezeStatusEnum.FREEZE.getVal());
        propertyInfoDOMapper.insertSelective(propertyInfoDO);

        PropertyPointBO bo = propertyInfoService.getPropertyPointByUserNum("M10001");
        Assert.assertNotNull(bo);
    }

    @Transactional
    @Rollback
    @Test
    public void updatePropertyNumbers(){
        PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
        propertyInfoDO.setUserNum("M10001");
        propertyInfoDO.setGmtCreate(new Date());
        propertyInfoDO.setPayPassword(PwdUtil.generate("123456"));
        propertyInfoDO.setBalance(new BigDecimal("200"));
        propertyInfoDO.setPoint(new BigDecimal("200"));
        propertyInfoDO.setLoveAccount(new BigDecimal("200"));
        propertyInfoDO.setFreeze(FreezeStatusEnum.RELEASE.getVal());
        propertyInfoDOMapper.insertSelective(propertyInfoDO);

        int ret1 = propertyInfoService.updatePropertyNumbers("M10001","B","A",new BigDecimal("5"));
        PropertyInfoDO pdo1 = propertyInfoDOMapper.selectByPrimaryKey(propertyInfoDO.getId());
        Assert.assertEquals(ResultCode.SUCCESS,ret1);
        Assert.assertEquals(205,pdo1.getBalance().intValue());

        int ret2 = propertyInfoService.updatePropertyNumbers("M10001","B","M",new BigDecimal("5"));
        PropertyInfoDO pdo2 = propertyInfoDOMapper.selectByPrimaryKey(propertyInfoDO.getId());
        Assert.assertEquals(ResultCode.SUCCESS,ret2);
        Assert.assertEquals(200,pdo2.getBalance().intValue());

        int ret3 = propertyInfoService.updatePropertyNumbers("M10001","P","A",new BigDecimal("5"));
        PropertyInfoDO pdo3 = propertyInfoDOMapper.selectByPrimaryKey(propertyInfoDO.getId());
        Assert.assertEquals(ResultCode.SUCCESS,ret3);
        Assert.assertEquals(205, pdo3.getPoint().intValue());

        int ret4 = propertyInfoService.updatePropertyNumbers("M10001","P","M",new BigDecimal("5"));
        PropertyInfoDO pdo4 = propertyInfoDOMapper.selectByPrimaryKey(propertyInfoDO.getId());
        Assert.assertEquals(ResultCode.SUCCESS,ret4);
        Assert.assertEquals(200,pdo4.getPoint().intValue());

        int ret5 = propertyInfoService.updatePropertyNumbers("M10001","L","A",new BigDecimal("5"));
        PropertyInfoDO pdo5 = propertyInfoDOMapper.selectByPrimaryKey(propertyInfoDO.getId());
        Assert.assertEquals(ResultCode.SUCCESS,ret5);
        Assert.assertEquals(205, pdo5.getLoveAccount().intValue());

        int ret6 = propertyInfoService.updatePropertyNumbers("M10001","L","M",new BigDecimal("5"));
        PropertyInfoDO pdo6 = propertyInfoDOMapper.selectByPrimaryKey(propertyInfoDO.getId());
        Assert.assertEquals(ResultCode.SUCCESS,ret6);
        Assert.assertEquals(200,pdo6.getLoveAccount().intValue());

    }

    @Transactional
    @Rollback
    @Test
    public void validateBalance(){
        PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
        propertyInfoDO.setUserNum("M10001");
        propertyInfoDO.setGmtCreate(new Date());
        propertyInfoDO.setPayPassword(PwdUtil.generate("123456"));
        propertyInfoDO.setBalance(new BigDecimal("200"));
        propertyInfoDO.setPoint(new BigDecimal("200"));
        propertyInfoDO.setLoveAccount(new BigDecimal("200"));
        propertyInfoDO.setFreeze(FreezeStatusEnum.RELEASE.getVal());
        propertyInfoDOMapper.insertSelective(propertyInfoDO);

        int ret = propertyInfoService.validateBalance("M10001","200",true,"123456");
        Assert.assertEquals(ResultCode.SUCCESS,ret);
    }

    @Transactional
    @Rollback
    @Test
    public void validatePoint(){
        PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
        propertyInfoDO.setUserNum("M10001");
        propertyInfoDO.setGmtCreate(new Date());
        propertyInfoDO.setPayPassword(PwdUtil.generate("123456"));
        propertyInfoDO.setBalance(new BigDecimal("200"));
        propertyInfoDO.setPoint(new BigDecimal("200"));
        propertyInfoDO.setLoveAccount(new BigDecimal("200"));
        propertyInfoDO.setFreeze(FreezeStatusEnum.RELEASE.getVal());
        propertyInfoDOMapper.insertSelective(propertyInfoDO);

        int ret = propertyInfoService.validatePoint("M10001","200");
        Assert.assertEquals(ResultCode.SUCCESS,ret);
    }

    @Transactional
    @Rollback
    @Test
    public void getPropertyInfoMoney(){
        PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
        propertyInfoDO.setUserNum("M10001");
        propertyInfoDO.setGmtCreate(new Date());
        propertyInfoDO.setPayPassword(PwdUtil.generate("123456"));
        propertyInfoDO.setBalance(new BigDecimal("200"));
        propertyInfoDO.setPoint(new BigDecimal("200"));
        propertyInfoDO.setLoveAccount(new BigDecimal("200"));
        propertyInfoDO.setFreeze(FreezeStatusEnum.RELEASE.getVal());
        propertyInfoDOMapper.insertSelective(propertyInfoDO);

        PropertyPointAndBalanceBO bo = propertyInfoService.getPropertyInfoMoney("M10001");
        Assert.assertNotNull(bo);
        Assert.assertEquals(200,bo.getBalance().intValue());

    }
}
