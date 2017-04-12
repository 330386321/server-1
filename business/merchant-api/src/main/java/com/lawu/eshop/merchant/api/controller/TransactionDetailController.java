package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.framework.web.doc.annotation.Audit;
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
import com.lawu.eshop.merchant.api.service.TransactionDetailService;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
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
     */
    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "获取交易明细列表", notes = "根据用户编号分页获取交易明细列表。[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "findPageByUserNum", method = RequestMethod.GET)
    public Result<Page<TransactionDetailDTO>> page(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, 
    		@RequestParam(name = "transactionType", required = false) @ApiParam(name = "transactionType", 
    		value = "交易类型<br/>默认全部<br/>PAY 买单<br/>ORDER 订单<br/>LOWER_INCOME 下级收益<br/>RECHARGE 充值<br/>PUT_ON 投放<br/>INTEGRAL_RECHARGE 积分充值<br/>REFUNDS 退款<br/>WITHDRAW 提现") 
    		MerchantTransactionTypeEnum transactionType, 
    		@ModelAttribute @ApiParam(name = "param", value = "查询资料") TransactionDetailQueryParam param) {
    	String userNum = UserUtil.getCurrentUserNum(getRequest());
    	
    	return successGet(transactionDetailService.findPageByUserNumForMerchant(userNum, transactionType, param));
    }
    
    
}
