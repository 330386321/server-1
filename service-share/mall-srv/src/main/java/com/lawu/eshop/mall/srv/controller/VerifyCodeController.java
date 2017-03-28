package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.constants.VerifyCodePurposeEnum;
import com.lawu.eshop.mall.dto.VerifyCodeDTO;
import com.lawu.eshop.mall.srv.bo.VerifyCodeBO;
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
     * 保存验证码
     *
     * @param mobile  手机号码
     * @param purpose 用途
     * @return
     */
    @RequestMapping(value = "save/{mobile}", method = RequestMethod.POST)
    public Result save(@PathVariable String mobile,@RequestParam String verifyCode, @RequestParam VerifyCodePurposeEnum purpose) {
        int id = verifyCodeService.save(mobile, verifyCode, purpose);
        VerifyCodeDTO verifyCodeDTO = new VerifyCodeDTO();
        verifyCodeDTO.setId(Long.valueOf(id));
        verifyCodeDTO.setCode(verifyCode);
        return successGet(verifyCodeDTO);
    }

    /**
     * 根据编号查询验证码
     *
     * @param id         ID
     * @param verifyCode 验证码
     * @return
     */
    @RequestMapping(value = "getVerifyCode/{id}", method = RequestMethod.GET)
    public Result getVerifyCode(@PathVariable Long id, @RequestParam String verifyCode) {
        VerifyCodeBO verifyCodeBO = verifyCodeService.getVerifyCodeById(id);
        if (verifyCodeBO == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        if (!verifyCodeBO.getCode().equals(verifyCode)) {
            return successGet(ResultCode.VERIFY_PIC_CODE_FAIL);
        }
        return successGet();
    }

}
