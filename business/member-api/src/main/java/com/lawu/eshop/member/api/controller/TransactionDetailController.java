package com.lawu.eshop.member.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.member.api.service.TransactionDetailService;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.dto.TransactionDetailDTO;
import com.lawu.eshop.property.param.TransactionDetailQueryParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author Sunny 
 * @date 2017/3/29
 */
@Api(tags = "transactionDetail")
@RestController
@RequestMapping(value = "transactionDetail/")
public class TransactionDetailController extends BaseController {

    @Autowired
    private TransactionDetailService transactionDetailService;
    
    /**
     * 根据用户编号分页获取交易明细列表。
     * 
     * @param token
     * @param transactionType
     * @param param
     * @return
     * @audit  sunlinqing 2016.03.29
     */
    @ApiOperation(value = "获取交易明细列表", notes = "根据用户编号分页获取交易明细列表。[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public Result<Page<TransactionDetailDTO>> page(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
    		@RequestParam(name = "transactionType", required = false) @ApiParam(name = "transactionType", value = "交易类型<br/>默认全部<br/>RECHARGE_THE_BALANCE 充值余额<br/>WITHDRAW 提现<br/>WITHDRAW_FAILURE 提现失败<br>PAY_FOR_MERCHANDISE_ORDERS 支付商品订单") MemberTransactionTypeEnum transactionType, 
    		@ModelAttribute @ApiParam(name = "param", value = "查询资料") TransactionDetailQueryParam param) {
    	String userNum = UserUtil.getCurrentUserNum(getRequest());
    	
    	Byte type = null;
    	if (transactionType != null) {
    		type = transactionType.getValue();
    	}
    	
    	return successGet(transactionDetailService.findPageByUserNum(userNum, type, param));
    }
    
    
}
