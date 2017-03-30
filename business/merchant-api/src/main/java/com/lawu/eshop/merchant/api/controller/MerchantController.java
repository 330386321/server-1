package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.mall.dto.VerifyCodeDTO;
import com.lawu.eshop.merchant.api.service.MerchantService;
import com.lawu.eshop.merchant.api.service.PropertyInfoService;
import com.lawu.eshop.merchant.api.service.VerifyCodeService;
import com.lawu.eshop.user.dto.MerchantDTO;
import com.lawu.eshop.user.param.RegisterParam;
import com.lawu.eshop.user.param.UpdatePwdParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author meishuquan
 * @date 2017/3/22
 */
@Api(tags = "merchant")
@RestController
@RequestMapping(value = "merchant/")
public class MerchantController extends BaseController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private PropertyInfoService propertyInfoService;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @ApiOperation(value = "修改登录密码", notes = "根据商户ID修改登录密码。[1009|1013] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "updateLoginPwd", method = RequestMethod.PUT)
    public Result updateLoginPwd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                 @ModelAttribute @ApiParam(required = true, value = "修改密码信息") UpdatePwdParam updatePwdParam) {
        Result smsResult = verifyCodeService.verifySmsCode(updatePwdParam.getVerifyCodeId(), updatePwdParam.getSmsCode());
        if (!isSuccess(smsResult)) {
            return successGet(ResultCode.VERIFY_SMS_CODE_FAIL);
        }
        long id = UserUtil.getCurrentUserId(getRequest());
        return merchantService.updateLoginPwd(id, updatePwdParam.getOriginalPwd(), updatePwdParam.getNewPwd(), updatePwdParam.getType());
    }

    @ApiOperation(value = "修改支付密码", notes = "根据商户编号修改支付密码。[1009|1013] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "updatePayPwd", method = RequestMethod.PUT)
    public Result updatePayPwd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                               @ModelAttribute @ApiParam(required = true, value = "修改密码信息") UpdatePwdParam updatePwdParam) {
        Result smsResult = verifyCodeService.verifySmsCode(updatePwdParam.getVerifyCodeId(), updatePwdParam.getSmsCode());
        if (!isSuccess(smsResult)) {
            return successGet(ResultCode.VERIFY_SMS_CODE_FAIL);
        }
        String userNo = UserUtil.getCurrentUserNum(getRequest());
        return propertyInfoService.updatePayPwd(userNo, updatePwdParam.getOriginalPwd(), updatePwdParam.getNewPwd(), updatePwdParam.getType());
    }

    @ApiOperation(value = "注册", notes = "商户注册。[1012|1013|1016] (梅述全)", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "register/{verifyCodeId}", method = RequestMethod.POST)
    public Result register(@PathVariable @ApiParam(required = true, value = "手机验证码ID") Long verifyCodeId,
                                      @ModelAttribute @ApiParam(required = true, value = "注册信息") RegisterParam registerParam) {
        Result accountResult = merchantService.getMerchantByAccount(registerParam.getAccount());
        if (isSuccess(accountResult)) {
            return successGet(ResultCode.RECORD_EXIST);
        }
        Result smsResult = verifyCodeService.verifySmsCode(verifyCodeId, registerParam.getSmsCode());
        if (!isSuccess(smsResult)) {
            return successGet(ResultCode.VERIFY_SMS_CODE_FAIL);
        }
        VerifyCodeDTO verifyCodeDTO = (VerifyCodeDTO) smsResult.getModel();
        if (!registerParam.getAccount().equals(verifyCodeDTO.getMobile())) {
            return successGet(ResultCode.NOT_SEND_SMS_MOBILE);
        }
        return merchantService.register(registerParam);
    }

    @ApiOperation(value = "根据账号查询商户信息", notes = "根据账号查询商户信息。[1002] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getMerchant", method = RequestMethod.GET)
    public Result<MerchantDTO> getMerchantByAccount(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        String account = UserUtil.getCurrentAccount(getRequest());
        return merchantService.getMerchantByAccount(account);
    }

}
