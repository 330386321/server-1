package com.lawu.eshop.property.srv.controller;

import javax.validation.Valid;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.srv.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 广告
 */
@RestController
@RequestMapping(value = "properinfoAd/")
public class AdController extends BaseController {

    @Autowired
    private AdService adService;

    /**
     * 支付回调
     *
     * @param param
     * @param result
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "doHandleMerchantAdNotify", method = RequestMethod.POST)
    public Result doHandleMerchantAdNotify(@RequestBody @Valid NotifyCallBackParam param, BindingResult result) {
        String message = validate(result);
        if (message != null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
        }
        int retCode = adService.doHandleMerchantAdNotify(param);
        return successCreated(retCode);
    }

}
