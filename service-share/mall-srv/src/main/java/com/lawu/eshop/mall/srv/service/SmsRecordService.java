package com.lawu.eshop.mall.srv.service;

import com.lawu.eshop.mall.constants.VerifyCodePurposeEnum;
import com.lawu.eshop.mall.srv.bo.SmsRecordBO;

import java.text.ParseException;

/**
 * 短信服务接口
 *
 * @author meishuquan
 * @date 2017/3/27
 */
public interface SmsRecordService {

    /**
     * 验证是否符合发短信条件
     *
     * @param mobile 手机号码
     * @param ip     IP
     * @return
     * @throws ParseException
     */
    int verifySendSms(String mobile, String ip) throws ParseException;

    /**
     * 保存发送短信记录
     *
     * @param mobile    手机号码
     * @param ip        IP
     * @param purpose   短信类型
     * @param smsCode   短信码
     * @return
     */
    SmsRecordBO saveSmsRecord(String mobile, String ip, VerifyCodePurposeEnum purpose, String smsCode);

    /**
     * 更新短信发送成功
     *
     * @param id         ID
     * @param isSuccess  发送结果
     * @param failReason 失败原因
     */
    void updateSmsRecordResult(Long id, Boolean isSuccess, String failReason);

}
