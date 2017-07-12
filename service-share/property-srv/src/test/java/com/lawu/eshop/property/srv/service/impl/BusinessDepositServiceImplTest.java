package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.BusinessDepositOperEnum;
import com.lawu.eshop.property.constants.BusinessDepositStatusEnum;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.dto.BusinessDepositInitDTO;
import com.lawu.eshop.property.param.*;
import com.lawu.eshop.property.srv.bo.BusinessDepositDetailBO;
import com.lawu.eshop.property.srv.bo.BusinessDepositQueryBO;
import com.lawu.eshop.property.srv.domain.BankAccountDO;
import com.lawu.eshop.property.srv.domain.BankDO;
import com.lawu.eshop.property.srv.domain.BusinessDepositDO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDO;
import com.lawu.eshop.property.srv.mapper.BankAccountDOMapper;
import com.lawu.eshop.property.srv.mapper.BankDOMapper;
import com.lawu.eshop.property.srv.mapper.BusinessDepositDOMapper;
import com.lawu.eshop.property.srv.mapper.PropertyInfoDOMapper;
import com.lawu.eshop.property.srv.service.BankAccountService;
import com.lawu.eshop.property.srv.service.BusinessDepositService;
import com.lawu.eshop.utils.DateUtil;
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
public class BusinessDepositServiceImplTest {

    @Autowired
    private BusinessDepositService businessDepositService;

    @Autowired
    private BusinessDepositDOMapper businessDepositDOMapper;

    @Autowired
    private BankDOMapper bankDOMapper;

    @Autowired
    private BankAccountDOMapper bankAccountDOMapper;

    @Autowired
    private PropertyInfoDOMapper propertyInfoDOMapper;



    @Transactional
    @Rollback
    @Test
    public void save(){

        BusinessDepositSaveDataParam param = new BusinessDepositSaveDataParam();
        param.setBusinessId(1L);
        param.setUserNum("10001");
        param.setBusinessAccount("17512036361");
        param.setBusinessName("张三");
        param.setDeposit("1000");
        param.setProvinceId("1");
        param.setCityId("101");
        param.setAreaId("10101");
        BusinessDepositInitDTO dto = businessDepositService.save(param);

        Assert.assertNotNull(dto.getId());
    }

    @Transactional
    @Rollback
    @Test
    public void doHandleDepositNotify(){
        BusinessDepositSaveDataParam param = new BusinessDepositSaveDataParam();
        param.setBusinessId(1L);
        param.setUserNum("10001");
        param.setBusinessAccount("17512036361");
        param.setBusinessName("张三");
        param.setDeposit("1000");
        param.setProvinceId("1");
        param.setCityId("101");
        param.setAreaId("10101");
        BusinessDepositInitDTO dto = businessDepositService.save(param);

        NotifyCallBackParam notifyParam = new NotifyCallBackParam();
        notifyParam.setBizIds("1");
        notifyParam.setUserNum("10001");
        notifyParam.setTotalFee("1000");
        notifyParam.setTradeNo("10000000001");
        notifyParam.setBuyerLogonId("yqh**01");
        notifyParam.setTransactionPayTypeEnum(TransactionPayTypeEnum.getEnum(new Byte("2")));
        notifyParam.setMerchantId(1L);
        Result result = businessDepositService.doHandleDepositNotify(notifyParam);

        Assert.assertEquals(ResultCode.SUCCESS,result.getRet());
    }

    @Transactional
    @Rollback
    @Test
    public void selectDepositList(){
        BusinessDepositDO bddo = new BusinessDepositDO();
        bddo.setBusinessId(1L);
        bddo.setUserNum("10001");
        bddo.setBusinessAccount("17512036361");
        bddo.setBusinessName("吕布");
        bddo.setDepositNumber("11111111111111111111");
        bddo.setAmount(new BigDecimal("1000"));
        bddo.setStatus(BusinessDepositStatusEnum.APPLY_REFUND.getVal());
        bddo.setProvinceId(1L);
        bddo.setCityId(11L);
        bddo.setAreaId(111L);
        bddo.setPaymentMethod(TransactionPayTypeEnum.ALIPAY.getVal());
        bddo.setGmtCreate(new Date());
        bddo.setGmtPay(new Date());
        businessDepositDOMapper.insertSelective(bddo);

        bddo.setBusinessId(1L);
        bddo.setUserNum("10001");
        bddo.setBusinessAccount("17512036361");
        bddo.setBusinessName("吕布");
        bddo.setDepositNumber("22222222222222222222");
        bddo.setAmount(new BigDecimal("1000"));
        bddo.setStatus(BusinessDepositStatusEnum.ACCPET_REFUND.getVal());
        bddo.setProvinceId(2L);
        bddo.setCityId(22L);
        bddo.setAreaId(222L);
        bddo.setPaymentMethod(TransactionPayTypeEnum.WX.getVal());
        bddo.setGmtCreate(new Date());
        bddo.setGmtPay(new Date());
        businessDepositDOMapper.insertSelective(bddo);

        BusinessDepositQueryDataParam param = new BusinessDepositQueryDataParam();
        param.setContent("11111111111111111111");
        param.setCurrentPage(1);
        param.setPageSize(10);
        Page<BusinessDepositQueryBO> page = businessDepositService.selectDepositList(param);
        Assert.assertEquals(1,page.getTotalCount().intValue());

        param = new BusinessDepositQueryDataParam();
        param.setBusinessDepositStatusEnum(BusinessDepositStatusEnum.getEnum(BusinessDepositStatusEnum.APPLY_REFUND.getVal()));
        param.setRegionPath("1/11/111");
        param.setBeginDate("2017-07-11");
        param.setEndDate("2017-07-13");
        param.setCurrentPage(1);
        param.setPageSize(10);
        Page<BusinessDepositQueryBO> page1 = businessDepositService.selectDepositList(param);
        Assert.assertEquals(1,page1.getTotalCount().intValue());

    }

    @Transactional
    @Rollback
    @Test
    public void updateBusinessDeposit(){
        BusinessDepositDO bddo = new BusinessDepositDO();
        bddo.setBusinessId(1L);
        bddo.setUserNum("10001");
        bddo.setBusinessAccount("17512036361");
        bddo.setBusinessName("吕布");
        bddo.setDepositNumber("11111111111111111111");
        bddo.setAmount(new BigDecimal("1000"));
        bddo.setStatus(BusinessDepositStatusEnum.ACCPET_REFUND.getVal());
        bddo.setProvinceId(1L);
        bddo.setCityId(11L);
        bddo.setAreaId(111L);
        bddo.setPaymentMethod(TransactionPayTypeEnum.ALIPAY.getVal());
        bddo.setGmtCreate(new Date());
        bddo.setGmtPay(new Date());
        businessDepositDOMapper.insertSelective(bddo);

        BusinessDepositOperDataParam param = new BusinessDepositOperDataParam();
        param.setOperUserId(1L);
        param.setOperUserName("admin");
        param.setBusinessDepositOperEnum(BusinessDepositOperEnum.getEnum(BusinessDepositOperEnum.REFUND_SUCCESS.getVal()));
        param.setBusinessId(1L);
        param.setId("1");
        param.setUserNum("10001");
        int ret = businessDepositService.updateBusinessDeposit(param);
        Assert.assertEquals(ResultCode.SUCCESS,ret);

        BusinessDepositQueryDataParam queryParam = new BusinessDepositQueryDataParam();
        queryParam.setBusinessDepositStatusEnum(BusinessDepositStatusEnum.getEnum(BusinessDepositStatusEnum.ACCPET_REFUND.getVal()));
        Page<BusinessDepositQueryBO> page = businessDepositService.selectDepositList(queryParam);
        Assert.assertEquals(0,page.getTotalCount().intValue());
    }

    @Transactional
    @Rollback
    @Test
    public void refundDeposit(){
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
        propertyInfoDOMapper.insertSelective(propertyInfoDO);

        BusinessDepositDO bddo = new BusinessDepositDO();
        bddo.setBusinessId(1L);
        bddo.setUserNum("10001");
        bddo.setBusinessAccount("17512036361");
        bddo.setBusinessName("吕布");
        bddo.setDepositNumber("11111111111111111111");
        bddo.setAmount(new BigDecimal("1000"));
        bddo.setStatus(BusinessDepositStatusEnum.VERIFYD.getVal());
        bddo.setProvinceId(1L);
        bddo.setCityId(11L);
        bddo.setAreaId(111L);
        bddo.setPaymentMethod(TransactionPayTypeEnum.ALIPAY.getVal());
        bddo.setGmtCreate(new Date());
        bddo.setGmtPay(new Date());
        bddo.setGmtModified(DateUtil.formatDate("2017-01-01 00:00:00","yyyy-MM-dd HH:mm:ss"));
        businessDepositDOMapper.insertSelective(bddo);

        BusinessRefundDepositDataParam param = new BusinessRefundDepositDataParam();
        param.setUserNum("10001");
        param.setId("1");
        param.setBusinessBankAccountId(bankAccountDO.getId().toString());
        param.setPayPwd("123456");
        int ret = businessDepositService.refundDeposit(param);
        Assert.assertEquals(ResultCode.SUCCESS,ret);

    }

    @Transactional
    @Rollback
    @Test
    public void selectDeposit(){
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

        BusinessDepositDO bddo = new BusinessDepositDO();
        bddo.setBusinessId(1L);
        bddo.setUserNum("10001");
        bddo.setBusinessAccount("17512036361");
        bddo.setBusinessName("吕布");
        bddo.setDepositNumber("11111111111111111111");
        bddo.setAmount(new BigDecimal("1000"));
        bddo.setStatus(BusinessDepositStatusEnum.VERIFYD.getVal());
        bddo.setProvinceId(1L);
        bddo.setCityId(11L);
        bddo.setAreaId(111L);
        bddo.setPaymentMethod(TransactionPayTypeEnum.ALIPAY.getVal());
        bddo.setGmtCreate(new Date());
        bddo.setGmtPay(new Date());
        bddo.setGmtModified(DateUtil.formatDate("2017-01-01 00:00:00","yyyy-MM-dd HH:mm:ss"));
        businessDepositDOMapper.insertSelective(bddo);

        BusinessDepositDetailBO bo = businessDepositService.selectDeposit("1");
        Assert.assertEquals(bddo.getId().intValue(),bo.getId().intValue());
    }
}
