package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.constants.SmsRecordConstant;
import com.lawu.eshop.mall.dto.SmsRecordDTO;
import com.lawu.eshop.mall.srv.bo.SmsRecordBO;
import com.lawu.eshop.mall.srv.service.SmsRecordService;
import com.lawu.eshop.utils.DataTransUtil;
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
     * @param mobile 手机号码
     * @param ip     IP
     * @param type   短信类型
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "sendSms/{mobile}", method = RequestMethod.GET)
    public Result sendSms(@PathVariable String mobile, @RequestParam String ip, @RequestParam Integer type) throws Exception {
        String smsCode = RandomUtil.getRandomString(1, 6);
        int errorCode = smsRecordService.verifySendSms(mobile, ip);
        long id = smsRecordService.saveSmsRecord(mobile, ip, DataTransUtil.intToByte(type), smsCode, errorCode);
        if (errorCode != SmsRecordConstant.SMS_SEND_CODE) {
            return failCreated(errorCode);
        }
        if(!isSend){
            return successCreated(errorCode);
        }
        Map<String, Object> returnMap = SmsUtil.sendSms(mobile, smsCode, ip);
        if (!(Boolean) returnMap.get("sendCode")) {
            return failCreated(ResultCode.FAIL);
        }
        smsRecordService.updateSmsRecordSuccess(id);
        SmsRecordDTO smsRecordDTO = new SmsRecordDTO();
        smsRecordDTO.setId(id);
        return successCreated(smsRecordDTO);
    }

    /**
     * 根据ID查询短信记录
     *
     * @param id      ID
     * @param smsCode 短信码
     * @return
     */
    @RequestMapping(value = "verifySmsRecord/{id}", method = RequestMethod.GET)
    public Result verifySmsRecord(@PathVariable Long id, @RequestParam String smsCode) {
        SmsRecordBO smsRecordBO = smsRecordService.getSmsRecordById(id);
        if (smsRecordBO == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        if (!smsRecordBO.getContent().contains(smsCode)) {
            return successGet(ResultCode.VERIFY_SMS_CODE_FAIL);
        }
        return successGet();
    }
}
