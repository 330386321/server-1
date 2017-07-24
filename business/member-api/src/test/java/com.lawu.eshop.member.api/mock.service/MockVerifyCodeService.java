package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.constants.VerifyCodePurposeEnum;
import com.lawu.eshop.mall.dto.VerifyCodeDTO;
import com.lawu.eshop.member.api.service.VerifyCodeService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Service
class MockVerifyCodeService implements VerifyCodeService {

    @Override
    public void savePicCode(@PathVariable("mobile") String mobile, @RequestParam("picCode") String picCode, @RequestParam("purpose") VerifyCodePurposeEnum purpose) {

    }

    @Override
    public Result<VerifyCodeDTO> verifySmsCode(@PathVariable("id") Long id, @RequestParam("smsCode") String smsCode) {
        return null;
    }

    @Override
    public Result verifyPicCode(@PathVariable("mobile") String mobile, @RequestParam("picCode") String picCode) {
        return null;
    }

    @Override
    public Result getVerifyCodeById(@PathVariable("id") Long id) {
        return null;
    }
}
