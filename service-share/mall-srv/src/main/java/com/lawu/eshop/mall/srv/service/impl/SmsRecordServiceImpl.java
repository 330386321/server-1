package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.constants.SmsRecordConstant;
import com.lawu.eshop.mall.srv.bo.SmsRecordBO;
import com.lawu.eshop.mall.srv.converter.SmsRecordConverter;
import com.lawu.eshop.mall.srv.domain.SmsRecordDO;
import com.lawu.eshop.mall.srv.domain.SmsRecordDOExample;
import com.lawu.eshop.mall.srv.mapper.SmsRecordDOMapper;
import com.lawu.eshop.mall.srv.service.SmsRecordService;
import com.lawu.eshop.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public int verifySendSms(String mobile, String ip) throws ParseException {
        DateFormat dfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH");
        String date = dfs.format(new Date());
        String startDate = date + ":00:00";
        String endDate = date + ":59:59";
        SmsRecordDOExample smsRecordDOExample = new SmsRecordDOExample();
        smsRecordDOExample.createCriteria().andMobileEqualTo(mobile).andGmtCreateGreaterThanOrEqualTo(dfd.parse(startDate)).andGmtCreateLessThanOrEqualTo(dfd.parse(endDate)).andIsSuccessEqualTo(SmsRecordConstant.SMS_SEND_SUCCESS);
        long hourCount = smsRecordDOMapper.countByExample(smsRecordDOExample);
        if (hourCount >= 2) {
            return ResultCode.SMS_SEND_HOUR_LIMIT;
        }

        date = DateUtil.getDate();
        startDate = date + " 00:00:00";
        endDate = date + " 23:59:59";
        if (ip != null) {
            smsRecordDOExample = new SmsRecordDOExample();
            smsRecordDOExample.createCriteria().andIpEqualTo(ip).andGmtCreateGreaterThanOrEqualTo(dfd.parse(startDate)).andGmtCreateLessThanOrEqualTo(dfd.parse(endDate)).andIsSuccessEqualTo(SmsRecordConstant.SMS_SEND_SUCCESS);
            long ipCount = smsRecordDOMapper.countByExample(smsRecordDOExample);
            if (ipCount >= 5) {
                return ResultCode.SMS_SEND_IP_LIMIT;
            }
        }

        smsRecordDOExample = new SmsRecordDOExample();
        smsRecordDOExample.createCriteria().andMobileEqualTo(mobile).andGmtCreateGreaterThanOrEqualTo(dfd.parse(startDate)).andGmtCreateLessThanOrEqualTo(dfd.parse(endDate)).andIsSuccessEqualTo(SmsRecordConstant.SMS_SEND_SUCCESS);
        long dayCount = smsRecordDOMapper.countByExample(smsRecordDOExample);
        if (dayCount >= 5) {
            return ResultCode.SMS_SEND_MOBILE_LIMIT;
        }
        return SmsRecordConstant.SMS_SEND_CODE;
    }

    @Override
    public int saveSmsRecord(String mobile, String ip, byte type, String smsCode, int errorCode) {
        SmsRecordDO smsRecordDO = new SmsRecordDO();
        smsRecordDO.setMobile(mobile);
        smsRecordDO.setContent(SmsRecordConstant.SMS_TEMPLATE.replace("{smsCode}", smsCode));
        smsRecordDO.setIp(ip);
        smsRecordDO.setType(type);
        smsRecordDO.setIsSuccess(SmsRecordConstant.SMS_SEND_FAIL);
        smsRecordDO.setFailReason(ResultCode.get(errorCode));
        smsRecordDO.setGmtCreate(new Date());
        return smsRecordDOMapper.insertSelective(smsRecordDO);
    }

    @Override
    public void updateSmsRecordSuccess(int id) {
        SmsRecordDO smsRecordDO = new SmsRecordDO();
        smsRecordDO.setId(Long.valueOf(id));
        smsRecordDO.setIsSuccess(SmsRecordConstant.SMS_SEND_SUCCESS);
        smsRecordDOMapper.updateByPrimaryKeySelective(smsRecordDO);
    }

    @Override
    public SmsRecordBO getSmsRecordById(Long id) {
        SmsRecordDO smsRecordDO = smsRecordDOMapper.selectByPrimaryKey(id);
        return smsRecordDO == null ? null : SmsRecordConverter.convertBO(smsRecordDO);
    }

}
