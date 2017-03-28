package com.lawu.eshop.mall.srv.service;

import com.lawu.eshop.mall.constants.VerifyCodePurposeEnum;
import com.lawu.eshop.mall.srv.bo.VerifyCodeBO;

/**
 * @author meishuquan
 * @date 2017/3/28
 */
public interface VerifyCodeService {

    /**
     * 保存验证码
     *
     * @param mobile     手机号码
     * @param verifyCode 验证码
     * @param purpose    用途
     * @return
     */
    int save(String mobile, String verifyCode, VerifyCodePurposeEnum purpose);

    /**
     * 根据ID查询验证码
     *
     * @param id ID
     * @return
     */
    VerifyCodeBO getVerifyCodeById(Long id);
}
