package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.constants.VerifyCodePurposeEnum;
import com.lawu.eshop.mall.srv.bo.VerifyCodeBO;
import com.lawu.eshop.mall.srv.converter.VerifyCodeConverter;
import com.lawu.eshop.mall.srv.service.VerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author meishuquan
 * @date 2017/3/28
 */
@RestController
@RequestMapping(value = "verifyCode/")
public class VerifyCodeController extends BaseController {

    @Autowired
    private VerifyCodeService verifyCodeService;

    /**
     * 保存图形验证码
     *
     * @param mobile  手机号码
     * @param picCode 图形验证码
     * @param purpose 用途
     * @return
     */
    @RequestMapping(value = "savePicCode/{mobile}", method = RequestMethod.POST)
    public Result savePicCode(@PathVariable String mobile, @RequestParam String picCode, @RequestParam VerifyCodePurposeEnum purpose) {
        verifyCodeService.savePicCode(mobile, picCode, purpose);
        return successCreated();
    }

    /**
     * 校验手机验证码
     *
     * @param id      ID
     * @param smsCode 手机验证码
     * @return
     */
    @RequestMapping(value = "verifySmsCode/{id}", method = RequestMethod.GET)
    public Result verifySmsCode(@PathVariable Long id, @RequestParam String smsCode) {
        VerifyCodeBO verifyCodeBO = verifyCodeService.getVerifyCodeById(id);
        if (verifyCodeBO == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        if (!verifyCodeBO.getCode().equals(smsCode)) {
            return successGet(ResultCode.VERIFY_SMS_CODE_FAIL);
        }
        return successGet(VerifyCodeConverter.convertDTO(verifyCodeBO));
    }

    /**
     * 校验图形验证码
     *
     * @param mobile  手机号码
     * @param picCode 图形验证码
     * @return
     */
    @RequestMapping(value = "verifyPicCode/{mobile}", method = RequestMethod.GET)
    public Result verifyPicCode(@PathVariable String mobile, @RequestParam String picCode) {
        VerifyCodeBO verifyCodeBO = verifyCodeService.getLastPicVerifyCode(mobile);
        if (verifyCodeBO == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        if (!verifyCodeBO.getCode().equals(picCode)) {
            return successGet(ResultCode.VERIFY_PIC_CODE_FAIL);
        }
        return successGet();
    }

}
