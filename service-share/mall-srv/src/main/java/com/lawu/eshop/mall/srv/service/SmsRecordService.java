package com.lawu.eshop.mall.srv.service;

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
     * @param type      短信类型
     * @param smsCode   短信码
     * @param errorCode 验证发送短信错误码
     * @return
     */
    Long saveSmsRecord(String mobile, String ip, byte type, String smsCode, int errorCode);

    /**
     * 更新短信发送成功
     *
     * @param id ID
     */
    void updateSmsRecordSuccess(Long id);

    /**
     * 根据ID查询短信记录
     *
     * @param id ID
     * @return
     */
    SmsRecordBO getSmsRecordById(Long id);

}
