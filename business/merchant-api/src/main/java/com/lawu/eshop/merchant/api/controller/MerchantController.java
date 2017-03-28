package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.MerchantService;
import com.lawu.eshop.merchant.api.service.PropertyInfoService;
import com.lawu.eshop.merchant.api.service.SmsRecordService;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.dto.MerchantDTO;
import com.lawu.eshop.user.param.RegisterParam;
import com.lawu.eshop.utils.IpUtil;
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
    private SmsRecordService smsRecordService;

    @ApiOperation(value = "修改登录密码", notes = "根据商户ID修改登录密码。[1006] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "updateLoginPwd/{id}", method = RequestMethod.PUT)
    public Result updateLoginPwd(@PathVariable @ApiParam(required = true, value = "id") Long id,
                                 @RequestParam @ApiParam(required = true, value = "原始密码") String originalPwd,
                                 @RequestParam @ApiParam(required = true, value = "新密码") String newPwd) {
        return merchantService.updateLoginPwd(id, originalPwd, newPwd);
    }

    @ApiOperation(value = "修改支付密码", notes = "根据商户编号修改支付密码。[1006] (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "updatePayPwd/{userNo}", method = RequestMethod.PUT)
    public Result updatePayPwd(@PathVariable @ApiParam(required = true, value = "商户编号") String userNo,
                               @RequestParam @ApiParam(required = true, value = "原始密码") String originalPwd,
                               @RequestParam @ApiParam(required = true, value = "新密码") String newPwd) {
        return propertyInfoService.updatePayPwd(userNo, originalPwd, newPwd);
    }

    @ApiOperation(value = "查询邀请人", notes = "根据账号查询邀请人信息。(梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getInviter/{account}", method = RequestMethod.GET)
    public Result<InviterDTO> getInviterByAccount(@PathVariable @ApiParam(required = true, value = "邀请人账号") String account) {
        return merchantService.getInviterByAccount(account);
    }

    @ApiOperation(value = "注册", notes = "商户注册。(梅述全)", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Result getInviterByAccount(@ModelAttribute @ApiParam(required = true, value = "注册信息") RegisterParam registerParam) {
        return merchantService.register(registerParam);
    }

    @ApiOperation(value = "根据账号查询商户信息", notes = "根据账号查询商户信息。(梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getMerchant/{account}", method = RequestMethod.GET)
    public Result<MerchantDTO> getMerchantByAccount(@PathVariable @ApiParam(required = true, value = "商户账号") String account) {
        return merchantService.getMerchantByAccount(account);
    }

    @ApiOperation(value = "发送短信", notes = "发送短信。[1000|1001|1003|1004|1005] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "sendSms/{mobile}", method = RequestMethod.GET)
    public Result sendSms(@PathVariable @ApiParam(required = true, value = "手机号码") String mobile,
                                        @RequestParam @ApiParam(required = true, value = "短信类型") Integer type) {
        String ip = IpUtil.getIpAddress(getRequest());
        return smsRecordService.sendSms(mobile, ip, type);
    }

}
