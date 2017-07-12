package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.UserTypeEnum;
import com.lawu.eshop.property.param.CashDataParam;
import com.lawu.eshop.property.srv.domain.BankAccountDO;
import com.lawu.eshop.property.srv.domain.BankDO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDOExample;
import com.lawu.eshop.property.srv.mapper.BankAccountDOMapper;
import com.lawu.eshop.property.srv.mapper.BankDOMapper;
import com.lawu.eshop.property.srv.mapper.PropertyInfoDOMapper;
import com.lawu.eshop.property.srv.service.CashManageFrontService;
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
import java.util.List;

/**
 * @author yangqh
 * @date 2017/7/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class CashManageFrontServiceImplTest {

    @Autowired
    private BankDOMapper bankDOMapper;

    @Autowired
    private BankAccountDOMapper bankAccountDOMapper;

    @Autowired
    private PropertyInfoDOMapper propertyInfoDOMapper;

    @Autowired
    protected CashManageFrontService cashManageFrontService;

    @Transactional
    @Rollback
    @Test
    public void save(){
        BankDO bdo = new BankDO();
        bdo.setName("test");
        bdo.setIconUrl("/1.jpg");
        bdo.setStatus(new Byte("1"));
        bdo.setOrdinal(1);
        bdo.setGmtCreate(new Date());
        bankDOMapper.insert(bdo);

        BankAccountDO bankAccountDO=new BankAccountDO();
        bankAccountDO.setUserNum("10001");
        bankAccountDO.setAccountName("习大大");
        bankAccountDO.setAccountNumber("6217852000014838927");
        bankAccountDO.setBankId(bdo.getId());
        bankAccountDO.setSubBranchName("南山支行");
        bankAccountDO.setStatus(new Byte("1"));
        bankAccountDO.setNote("南山支行");
        bankAccountDOMapper.insertSelective(bankAccountDO);

        PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
        propertyInfoDO.setUserNum("10001");
        propertyInfoDO.setGmtCreate(new Date());
        propertyInfoDO.setPayPassword(PwdUtil.generate("123456"));
        propertyInfoDO.setBalance(new BigDecimal("100"));
        propertyInfoDOMapper.insertSelective(propertyInfoDO);

        CashDataParam param = new CashDataParam();
        param.setAccount("17512036361");
        param.setUserNum("10001");
        param.setAreaId(111);
        param.setCashNumber("1111111111111111111");
        param.setCityId(11);
        param.setName("习大大");
        param.setProvinceId(1);
        param.setRegionFullName("北京市/县辖/东城区");
        param.setTransactionType(MerchantTransactionTypeEnum.WITHDRAW.getValue());
        param.setUserType(UserTypeEnum.MEMCHANT.getVal());
        param.setBusinessBankAccountId(bankAccountDO.getId());
        param.setCashMoney("10");
        param.setPayPwd("123456");
        int ret = cashManageFrontService.save(param);
        Assert.assertEquals(ResultCode.SUCCESS,ret);

        PropertyInfoDOExample example = new PropertyInfoDOExample();
        example.createCriteria().andUserNumEqualTo("10001");
        List<PropertyInfoDO> rntList = propertyInfoDOMapper.selectByExample(example);
        Assert.assertNotNull(rntList);
        PropertyInfoDO pdo1 = rntList.get(0);
        Assert.assertEquals(90,pdo1.getBalance().intValue());

    }


}
