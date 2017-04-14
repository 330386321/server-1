package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.member.api.service.PropertyInfoService;
import com.lawu.eshop.property.dto.PropertyBalanceDTO;
import com.lawu.eshop.property.dto.PropertyPointDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sunny 
 * @date 2017/3/29
 */
@Api(tags = "propertyInfo")
@RestController
@RequestMapping(value = "propertyInfo/")
public class PropertyInfoController extends BaseController {

    @Autowired
    private PropertyInfoService propertyInfoService;

    @SuppressWarnings("unchecked")
	@Audit(date = "2017-03-29", reviewer = "孙林青")
    @ApiOperation(value = "获取资产余额", notes = "根据用户编号获取资产余额。[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "balance", method = RequestMethod.GET)
    public Result<PropertyBalanceDTO> getPropertyBalance(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
    	String userNum = UserUtil.getCurrentUserNum(getRequest());
    	return successGet(propertyInfoService.getPropertyBalance(userNum));
    }
    
    /**
     *根据用户编号获取积分。
     *
     * @param token
     * @return
     */
    @SuppressWarnings("unchecked")
	@Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "获取积分", notes = "根据用户编号获取积分。[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "point", method = RequestMethod.GET)
    public Result<PropertyPointDTO> getPropertyPoint(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
    	String userNum = UserUtil.getCurrentUserNum(getRequest());
    	return successGet(propertyInfoService.getPropertyPoint(userNum));
    }

    @SuppressWarnings("unchecked")
	@ApiOperation(value = "获取资产余额积分", notes = "根据用户编号获取资产余额积分。[]（杨清华）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getPropertyInfoMoney", method = RequestMethod.GET)
    public Result<PropertyBalanceDTO> getPropertyInfoMoney(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
    	String userNum = UserUtil.getCurrentUserNum(getRequest());
    	return successGet(propertyInfoService.getPropertyInfoMoney(userNum));
    }

    @ApiOperation(value = "验证支付密码", notes = "验证支付密码(true--正确，false--错误)。[1002|1022]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "varifyPayPwd", method = RequestMethod.GET)
    public Result varifyPayPwd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                               @RequestParam @ApiParam(required = true, value = "支付密码") String payPwd) {
        String userNum = UserUtil.getCurrentUserNum(getRequest());
        return propertyInfoService.varifyPayPwd(userNum, payPwd);
    }

}
