package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.merchant.api.service.PayOrderService;
import com.lawu.eshop.order.dto.MerchantPayOrderListDTO;
import com.lawu.eshop.order.param.MerchantPayOrderListParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyong
 * @date 2017/6/22.
 */
@Api(tags = "payOrder")
@RestController
@RequestMapping(value = "payOrder/")
public class PayOrderController extends BaseController{

    @Autowired
    private PayOrderService payOrderService;

    @ApiOperation(value = "买单列表", notes = "买单列表 [1000]（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getMerchantPayOrderList", method = RequestMethod.GET)
    public Result<Page<MerchantPayOrderListDTO>> getMerchantPayOrderList(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute MerchantPayOrderListParam param){
        if(param == null || param.getCurrentPage() <1){
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Long userId = UserUtil.getCurrentUserId(getRequest());
        Result<Page<MerchantPayOrderListDTO>> result =  payOrderService.getMerchantPayOrderList(userId,param);
        return result;
    }
}