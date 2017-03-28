package com.lawu.eshop.merchant.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.constants.VerifyCodePurposeEnum;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/3/28.
 */
@FeignClient(value = "mall-srv")
public interface VerifyCodeService {

    /**
     * 保存验证码
     *
     * @param mobile 手机号码
     * @param mobile 验证码
     * @param mobile 用途
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "verifyCode/save/{mobile}")
    Result saveVerifyCode(@PathVariable("mobile") String mobile, @RequestParam("verifyCode") String verifyCode, @RequestParam("purpose") VerifyCodePurposeEnum purpose);

    /**
     * 校验验证码
     *
     * @param id ID
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "verifyCode/getVerifyCode/{id}")
    Result verifyPicCode(@PathVariable("id") Long id);
}
