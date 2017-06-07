package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.constants.VerifyCodePurposeEnum;
import com.lawu.eshop.mall.dto.VerifyCodeDTO;
import com.eshop.sms.util.SmsConfigParam;
import com.lawu.eshop.mall.srv.MallSrvConfig;
import com.lawu.eshop.mall.srv.bo.SmsRecordBO;
import com.lawu.eshop.mall.srv.service.SmsRecordService;
import com.lawu.eshop.utils.RandomUtil;
import com.lawu.eshop.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.eshop.sms.util.SmsUtil;

import java.text.ParseException;
import java.util.Map;

/**
 * @author meishuquan
 * @date 2017/3/27
 */
@RestController
@RequestMapping(value = "smsRecord/")
public class SmsRecordController extends BaseController {

    @Autowired
    private SmsRecordService smsRecordService;

    @Autowired
    private MallSrvConfig mallSrvConfig;

    /**
     * 发送短信
     *
     * @param mobile  手机号码
     * @param ip      IP
     * @param purpose 短信类型
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "sendSms/{mobile}", method = RequestMethod.GET)
    public Result sendSms(@PathVariable String mobile, @RequestParam String ip, @RequestParam VerifyCodePurposeEnum purpose) throws Exception {
        int errorCode = smsRecordService.verifySendSms(mobile, ip);
        if (errorCode != ResultCode.SUCCESS) {
            return successGet(errorCode);
        }
        String smsCode = RandomUtil.getRandomString(1, 6);
        SmsRecordBO smsRecordBO  = smsRecordService.saveSmsRecord(mobile, ip, purpose, smsCode);
        VerifyCodeDTO verifyCodeDTO = new VerifyCodeDTO();
        verifyCodeDTO.setId(smsRecordBO.getVirifyCodeId());
        if (!mallSrvConfig.getIsSend()) {
            return successCreated(verifyCodeDTO);
        }
        SmsConfigParam smsConfigParam = new SmsConfigParam();
        smsConfigParam.setSmsUrl(mallSrvConfig.getSmsUrl());
        smsConfigParam.setSmsEncoding(mallSrvConfig.getSmsEncoding());
        smsConfigParam.setSmsSpCode(mallSrvConfig.getSmsSpCode());
        smsConfigParam.setSmsLoginName(mallSrvConfig.getSmsLoginName());
        smsConfigParam.setSmsPassword(mallSrvConfig.getSmsPassword());
        smsConfigParam.setSmsSerialNumber(mallSrvConfig.getSmsSerialNumber());
        smsConfigParam.setSmsF(mallSrvConfig.getSmsF());
        smsConfigParam.setSmsTemplate(StringUtil.getUtf8String(mallSrvConfig.getSmsTemplate()));
        Map<String, Object> returnMap = SmsUtil.sendSms(smsCode, mobile, ip, smsConfigParam);
        smsRecordService.updateSmsRecordResult(smsRecordBO.getId(),(Boolean) returnMap.get("sendCode"),returnMap.get("sendResult").toString());
        if (!(Boolean) returnMap.get("sendCode")) {
            return successGet(ResultCode.SMS_SEND_FAIL);
        }
        return successCreated(verifyCodeDTO);
    }

}
