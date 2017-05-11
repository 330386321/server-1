package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.constants.VerifyCodePurposeEnum;
import com.lawu.eshop.mall.srv.MallSrvConfig;
import com.lawu.eshop.mall.srv.bo.SmsRecordBO;
import com.lawu.eshop.mall.srv.domain.SmsRecordDO;
import com.lawu.eshop.mall.srv.domain.SmsRecordDOExample;
import com.lawu.eshop.mall.srv.domain.VerifyCodeDO;
import com.lawu.eshop.mall.srv.mapper.SmsRecordDOMapper;
import com.lawu.eshop.mall.srv.mapper.VerifyCodeDOMapper;
import com.lawu.eshop.mall.srv.service.SmsRecordService;
import com.lawu.eshop.utils.DateUtil;
import com.lawu.eshop.utils.StringUtil;
import constants.SmsConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 发送短信服务实现
 *
 * @author meishuquan
 * @date 2017/3/27
 */
@Service
public class SmsRecordServiceImpl implements SmsRecordService {

    @Autowired
    private SmsRecordDOMapper smsRecordDOMapper;

    @Autowired
    private VerifyCodeDOMapper verifyCodeDOMapper;

    @Autowired
    private MallSrvConfig mallSrvConfig;

    @Override
    public int verifySendSms(String mobile, String ip) throws ParseException {
        DateFormat dfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH");
        String date = dfs.format(new Date());
        String startDate = date + ":00:00";
        String endDate = date + ":59:59";
        SmsRecordDOExample smsRecordDOExample = new SmsRecordDOExample();
        smsRecordDOExample.createCriteria().andMobileEqualTo(mobile).andGmtCreateGreaterThanOrEqualTo(dfd.parse(startDate)).andGmtCreateLessThanOrEqualTo(dfd.parse(endDate)).andIsSuccessEqualTo(SmsConstant.SMS_SEND_SUCCESS);
        long hourCount = smsRecordDOMapper.countByExample(smsRecordDOExample);
        if (hourCount >= 2) {
            return ResultCode.SMS_SEND_HOUR_LIMIT;
        }

        date = DateUtil.getDate();
        startDate = date + " 00:00:00";
        endDate = date + " 23:59:59";
        if (ip != null) {
            smsRecordDOExample = new SmsRecordDOExample();
            smsRecordDOExample.createCriteria().andIpEqualTo(ip).andGmtCreateGreaterThanOrEqualTo(dfd.parse(startDate)).andGmtCreateLessThanOrEqualTo(dfd.parse(endDate)).andIsSuccessEqualTo(SmsConstant.SMS_SEND_SUCCESS);
            long ipCount = smsRecordDOMapper.countByExample(smsRecordDOExample);
            if (ipCount >= 5) {
                return ResultCode.SMS_SEND_IP_LIMIT;
            }
        }

        smsRecordDOExample = new SmsRecordDOExample();
        smsRecordDOExample.createCriteria().andMobileEqualTo(mobile).andGmtCreateGreaterThanOrEqualTo(dfd.parse(startDate)).andGmtCreateLessThanOrEqualTo(dfd.parse(endDate)).andIsSuccessEqualTo(SmsConstant.SMS_SEND_SUCCESS);
        long dayCount = smsRecordDOMapper.countByExample(smsRecordDOExample);
        if (dayCount >= 5) {
            return ResultCode.SMS_SEND_MOBILE_LIMIT;
        }
        return ResultCode.SUCCESS;
    }

    @Override
    @Transactional
    public SmsRecordBO saveSmsRecord(String mobile, String ip, VerifyCodePurposeEnum purpose, String smsCode, int errorCode) {
        //插入短信记录
        SmsRecordDO smsRecordDO = new SmsRecordDO();
        smsRecordDO.setMobile(mobile);
        smsRecordDO.setContent(StringUtil.getUtf8String(mallSrvConfig.getSmsTemplate()).replace("{smsCode}", smsCode));
        smsRecordDO.setIp(ip);
        smsRecordDO.setType(purpose.val);
        smsRecordDO.setIsSuccess(SmsConstant.SMS_SEND_FAIL);
        smsRecordDO.setFailReason(ResultCode.get(errorCode));
        smsRecordDO.setGmtCreate(new Date());
        smsRecordDOMapper.insertSelective(smsRecordDO);

        //插入验证码记录
        VerifyCodeDO verifyCodeDO = new VerifyCodeDO();
        verifyCodeDO.setMobile(mobile);
        verifyCodeDO.setCode(smsCode);
        verifyCodeDO.setPurpose(purpose.val);
        verifyCodeDO.setGmtCreate(new Date());
        verifyCodeDOMapper.insertSelective(verifyCodeDO);

        SmsRecordBO smsRecordBO = new SmsRecordBO();
        smsRecordBO.setId(smsRecordDO.getId());
        smsRecordBO.setVirifyCodeId(verifyCodeDO.getId());
        return smsRecordBO;
    }

    @Override
    public void updateSmsRecordResult(Long id, Boolean isSuccess, String failReason) {
        SmsRecordDO smsRecordDO = new SmsRecordDO();
        smsRecordDO.setId(id);
        smsRecordDO.setIsSuccess(isSuccess);
        smsRecordDO.setFailReason(failReason);
        smsRecordDOMapper.updateByPrimaryKeySelective(smsRecordDO);
    }

}
