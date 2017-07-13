package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.constants.VerifyCodePurposeEnum;
import com.lawu.eshop.mall.srv.bo.SmsRecordBO;
import com.lawu.eshop.mall.srv.domain.SmsRecordDO;
import com.lawu.eshop.mall.srv.domain.VerifyCodeDO;
import com.lawu.eshop.mall.srv.mapper.SmsRecordDOMapper;
import com.lawu.eshop.mall.srv.mapper.VerifyCodeDOMapper;
import com.lawu.eshop.mall.srv.service.SmsRecordService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/7/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class SmsRecordServiceImplTest {

    @Autowired
    private SmsRecordService smsRecordService;
    @Autowired
    private SmsRecordDOMapper smsRecordDOMapper;

    @Autowired
    private VerifyCodeDOMapper verifyCodeDOMapper;

    @Ignore
    @Transactional
    @Rollback
    @Test
    public  void verifySendSms() throws ParseException {

        String mobile = "123456789";
        String ip = "121.35.101.59";
        SmsRecordDO smsRecordDO ;
        smsRecordDO = new SmsRecordDO();
        smsRecordDO.setIp(ip);
        smsRecordDO.setMobile(mobile);
        smsRecordDO.setIsSuccess(true);
        smsRecordDO.setGmtCreate(new Date());
        smsRecordDOMapper.insert(smsRecordDO);

        smsRecordDO = new SmsRecordDO();
        smsRecordDO.setIp(ip);
        smsRecordDO.setMobile(mobile);
        smsRecordDO.setIsSuccess(true);
        smsRecordDO.setGmtCreate(new Date());
        smsRecordDOMapper.insert(smsRecordDO);

        smsRecordDO = new SmsRecordDO();
        smsRecordDO.setIp(ip);
        smsRecordDO.setMobile(mobile);
        smsRecordDO.setIsSuccess(true);
        smsRecordDO.setGmtCreate(new Date());
        smsRecordDOMapper.insert(smsRecordDO);

        smsRecordDO = new SmsRecordDO();
        smsRecordDO.setIp(ip);
        smsRecordDO.setMobile(mobile);
        smsRecordDO.setIsSuccess(true);
        smsRecordDO.setGmtCreate(new Date());
        smsRecordDOMapper.insert(smsRecordDO);

        int code = smsRecordService.verifySendSms(mobile,ip);
        Assert.assertEquals(ResultCode.SUCCESS,code);


        //同手机号
        smsRecordDO = new SmsRecordDO();
        smsRecordDO.setIp(ip);
        smsRecordDO.setMobile(mobile);
        smsRecordDO.setIsSuccess(true);
        smsRecordDO.setGmtCreate(new Date());
        smsRecordDOMapper.insert(smsRecordDO);
        //同手机号
        smsRecordDO = new SmsRecordDO();
        smsRecordDO.setIp(ip);
        smsRecordDO.setMobile(mobile);
        smsRecordDO.setIsSuccess(true);
        smsRecordDO.setGmtCreate(new Date());
        smsRecordDOMapper.insert(smsRecordDO);
        int code2 = smsRecordService.verifySendSms(mobile,ip);

        Assert.assertEquals(ResultCode.SMS_SEND_MOBILE_LIMIT,code2);


    }

    @Ignore
    @Transactional
    @Rollback
    @Test
    public void saveSmsRecord(){
        String mobile = "123456789";
        String ip = "121.35.101.59";
        SmsRecordBO smsRecordBO = smsRecordService.saveSmsRecord(
                mobile,ip, VerifyCodePurposeEnum.USER_REGISTER,"123456");
        List<SmsRecordDO> smsRecordDOList = smsRecordDOMapper.selectByExample(null);
        Assert.assertNotNull(smsRecordDOList);
        Assert.assertEquals(1,smsRecordDOList.size());
        Assert.assertEquals(smsRecordBO.getId(),smsRecordDOList.get(0).getId());

        List<VerifyCodeDO> verifyCodeDOS = verifyCodeDOMapper.selectByExample(null);
        Assert.assertNotNull(verifyCodeDOS);
        Assert.assertEquals(1,verifyCodeDOS.size());
        Assert.assertEquals(smsRecordBO.getVirifyCodeId(),verifyCodeDOS.get(0).getId());

    }

    @Ignore
    @Transactional
    @Rollback
    @Test
    public void updateSmsRecordResult(){
        String mobile = "123456789";
        String ip = "121.35.101.59";
        SmsRecordDO smsRecordDO = new SmsRecordDO();
        smsRecordDO.setIp(ip);
        smsRecordDO.setMobile(mobile);
        smsRecordDO.setIsSuccess(true);
        smsRecordDO.setGmtCreate(new Date());
        smsRecordDOMapper.insert(smsRecordDO);
        smsRecordService.updateSmsRecordResult(smsRecordDO.getId(),false,"fail");
        List<SmsRecordDO> list = smsRecordDOMapper.selectByExample(null);
        Assert.assertNotNull(list);
        Assert.assertEquals(1,list.size());
        Assert.assertEquals(false,list.get(0).getIsSuccess());
    }
}
