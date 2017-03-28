package com.lawu.eshop.member.api.service;

import com.lawu.eshop.framework.web.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/3/28
 */
@FeignClient(value = "mall-srv")
public interface SmsRecordService {

    /**
     * 发送短信
     *
     * @param mobile 手机号码
     * @param ip     IP
     * @param type   短信类型
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "smsRecord/sendSms/{mobile}")
    Result sendSms(@PathVariable("mobile") String mobile, @RequestParam("ip") String ip, @RequestParam("type") Integer type);

    /**
     * 校验手机验证码
     * @param id    ID
     * @param smsCode      输入的手机验证码
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "smsRecord/verifySmsRecord/{id}")
    Result verifySmsRecord(@PathVariable("id") Long id,@RequestParam("smsCode") String smsCode);
}
