package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.mall.dto.VerifyCodeDTO;
import com.lawu.eshop.merchant.api.service.InviterService;
import com.lawu.eshop.merchant.api.service.MerchantService;
import com.lawu.eshop.merchant.api.service.PropertyInfoService;
import com.lawu.eshop.merchant.api.service.VerifyCodeService;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.param.RegisterParam;
import com.lawu.eshop.user.param.RegisterRealParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang.StringUtils;
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

    @Autowired
    private InviterService inviterService;

    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "修改登录密码", notes = "根据商户ID修改登录密码。[1002|1009] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "updateLoginPwd", method = RequestMethod.PUT)
    public Result updateLoginPwd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                 @RequestParam @ApiParam(required = true, value = "原始密码") String originalPwd,
                                 @RequestParam @ApiParam(required = true, value = "新密码") String newPwd) {
        long id = UserUtil.getCurrentUserId(getRequest());
        return merchantService.updateLoginPwd(id,originalPwd, newPwd);
    }

    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "忘记登录密码", notes = "根据商户账号修改登录密码。[1100|1013] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "resetLoginPwd/{mobile}", method = RequestMethod.PUT)
    public Result resetLoginPwd(@PathVariable @ApiParam(required = true, value = "手机号码") String mobile,
                                @RequestParam @ApiParam(required = true, value = "手机验证码ID") Long verifyCodeId,
                                @RequestParam @ApiParam(required = true, value = "手机验证码") String smsCode,
                                @RequestParam @ApiParam(required = true, value = "新密码") String newPwd) {
        Result smsResult = verifyCodeService.verifySmsCode(verifyCodeId, smsCode);
        if (!isSuccess(smsResult)) {
            return successGet(ResultCode.VERIFY_SMS_CODE_FAIL);
        }
        return merchantService.updateLoginPwd(mobile,newPwd);
    }

    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "修改支付密码", notes = "根据商户编号修改支付密码。[1009|1100] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "updatePayPwd", method = RequestMethod.PUT)
    public Result updatePayPwd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                               @RequestParam @ApiParam(required = true, value = "原始密码") String originalPwd,
                               @RequestParam @ApiParam(required = true, value = "新密码") String newPwd) {
        String userNo = UserUtil.getCurrentUserNum(getRequest());
        return propertyInfoService.updatePayPwd(userNo, originalPwd, newPwd);
    }

    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "忘记支付密码", notes = "根据商户编号重置支付密码。[1100|1013] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "resetPayPwd", method = RequestMethod.PUT)
    public Result resetPayPwd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                              @RequestParam @ApiParam(required = true, value = "手机验证码ID") Long verifyCodeId,
                              @RequestParam @ApiParam(required = true, value = "手机验证码") String smsCode,
                              @RequestParam @ApiParam(required = true, value = "新密码") String newPwd) {
        Result smsResult = verifyCodeService.verifySmsCode(verifyCodeId, smsCode);
        if (!isSuccess(smsResult)) {
            return successGet(ResultCode.VERIFY_SMS_CODE_FAIL);
        }
        String userNo = UserUtil.getCurrentUserNum(getRequest());
        return propertyInfoService.resetPayPwd(userNo,  newPwd);
    }

    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "设置支付密码", notes = "根据商户编号设置支付密码。[1100] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "setPayPwd", method = RequestMethod.PUT)
    public Result setPayPwd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                              @RequestParam @ApiParam(required = true, value = "新密码") String newPwd) {
        String userNo = UserUtil.getCurrentUserNum(getRequest());
        return propertyInfoService.setPayPwd(userNo,  newPwd);
    }

    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "查询是否设置支付密码", notes = "查询是否设置支付密码(true--已设置，false--未设置)。[1100] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "isSetPayPwd", method = RequestMethod.GET)
    public Result isSetPayPwd(@RequestHeader (UserConstant.REQ_HEADER_TOKEN) String token) {
        String userNum = UserUtil.getCurrentUserNum(getRequest());
        return propertyInfoService.isSetPayPwd(userNum);
    }

    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "注册", notes = "商户注册。[1002|1012|1013|1016] (梅述全)", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "register/{verifyCodeId}", method = RequestMethod.POST)
    public Result register(@PathVariable @ApiParam(required = true, value = "手机验证码ID") Long verifyCodeId,
                           @ModelAttribute @ApiParam RegisterParam registerParam) {
        RegisterRealParam registerRealParam = new RegisterRealParam();
        if (StringUtils.isNotEmpty(registerParam.getInviterAccount())) {
            Result<InviterDTO> inviterResult = inviterService.getInviterByAccount(registerParam.getInviterAccount());
            if (!isSuccess(inviterResult)) {
                return successGet(ResultCode.RESOURCE_NOT_FOUND);
            }
            InviterDTO inviterDTO = inviterResult.getModel();
            registerRealParam.setInviterId(inviterDTO.getInviterId());
            registerRealParam.setUserNum(inviterDTO.getUserNum());
        }
        Result accountResult = merchantService.getMerchantByAccount(registerParam.getAccount());
        if (isSuccess(accountResult)) {
            return successGet(ResultCode.RECORD_EXIST);
        }
        Result<VerifyCodeDTO> smsResult = verifyCodeService.verifySmsCode(verifyCodeId, registerParam.getSmsCode());
        if (!isSuccess(smsResult)) {
            return successGet(ResultCode.VERIFY_SMS_CODE_FAIL);
        }
        VerifyCodeDTO verifyCodeDTO = smsResult.getModel();
        if (!registerParam.getAccount().equals(verifyCodeDTO.getMobile())) {
            return successGet(ResultCode.NOT_SEND_SMS_MOBILE);
        }
        registerRealParam.setAccount(registerParam.getAccount());
        registerRealParam.setPwd(registerParam.getPwd());
        return merchantService.register(registerRealParam);
    }

}
