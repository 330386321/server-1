package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.constants.VerifyCodePurposeEnum;
import com.lawu.eshop.mall.dto.VerifyCodeDTO;
import com.lawu.eshop.mall.srv.bo.SmsRecordBO;
import com.lawu.eshop.mall.srv.service.SmsRecordService;
import com.lawu.eshop.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.SmsUtil;

import java.text.ParseException;
import java.util.Map;

/**
 * @author meishuquan
 * @date 2017/3/27
 */
@RestController
@RequestMapping(value = "smsRecord/")
public class SmsRecordController extends BaseController {

    //是否发送短信
    private static final boolean isSend = false;

    @Autowired
    private SmsRecordService smsRecordService;

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
        String smsCode = RandomUtil.getRandomString(1, 6);
        int errorCode = smsRecordService.verifySendSms(mobile, ip);
        SmsRecordBO smsRecordBO  = smsRecordService.saveSmsRecord(mobile, ip, purpose, smsCode, errorCode);
        if (errorCode != ResultCode.SUCCESS) {
            return failCreated(errorCode);
        }
        if (!isSend) {
            return successCreated();
        }
        Map<String, Object> returnMap = SmsUtil.sendSms(mobile, smsCode, ip);
        smsRecordService.updateSmsRecordResult(smsRecordBO.getId(),(Boolean) returnMap.get("sendCode"),returnMap.get("sendResult").toString());
        if (!(Boolean) returnMap.get("sendCode")) {
            return failCreated(ResultCode.FAIL);
        }
        VerifyCodeDTO verifyCodeDTO = new VerifyCodeDTO();
        verifyCodeDTO.setId(smsRecordBO.getVirifyCodeId());
        return successCreated(verifyCodeDTO);
    }

}
