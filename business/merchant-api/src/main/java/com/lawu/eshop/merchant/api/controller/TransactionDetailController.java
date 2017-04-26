package com.lawu.eshop.merchant.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.merchant.api.service.CashManageFrontService;
import com.lawu.eshop.merchant.api.service.TransactionDetailService;
import com.lawu.eshop.property.dto.TransactionDetailDTO;
import com.lawu.eshop.property.dto.WithdrawCashStatusDTO;
import com.lawu.eshop.property.param.TransactionDetailQueryForMerchantParam;

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
    
    @Autowired
    private CashManageFrontService cashManageFrontService;
    
    /**
     * 根据用户编号分页获取交易明细列表。
     * 
     * @param token
     * @param param 查询参数
     * @return
     */
    @SuppressWarnings("unchecked")
	@Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "获取交易明细列表", notes = "根据用户编号分页获取交易明细列表。[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "findPageByUserNum", method = RequestMethod.GET)
    public Result<Page<TransactionDetailDTO>> page(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(name = "param", value = "查询参数") TransactionDetailQueryForMerchantParam param) {
    	String userNum = UserUtil.getCurrentUserNum(getRequest());
    	
    	Result<Page<TransactionDetailDTO>> result = transactionDetailService.findPageByUserNumForMerchant(userNum, param);
    	if (!isSuccess(result)) {
    		return successGet(result.getRet());
    	}
    	
    	// 如果交易类型为提现需要获取提现状态
    	List<TransactionDetailDTO> transactionDetailDTOList = result.getModel().getRecords();
    	
    	// 把所有需要查询的id放入set
    	List<Long> ids = new ArrayList<Long>();
    	for (TransactionDetailDTO item : transactionDetailDTOList) {
    		if (!StringUtils.isEmpty(item.getBizId())) {
    			ids.add(Long.valueOf(item.getBizId()));
    		}
    	}
    	
    	//如果list中不存在提现的记录，不需要查询，直接返回数据
    	if (ids.isEmpty()) {
    		return successGet(result);
    	}
    	
    	Result<List<WithdrawCashStatusDTO>> resultWithdrawCashStatusDTOList = cashManageFrontService.findCashDetailStatus(ids);
    	
    	if (!isSuccess(resultWithdrawCashStatusDTOList)) {
    		return successGet(resultWithdrawCashStatusDTOList.getRet());
    	}
    	
    	// 把数据缓存到Map
    	Map<String, WithdrawCashStatusDTO> withdrawCashStatusDTOMap = new HashMap<String, WithdrawCashStatusDTO>();
    	for (WithdrawCashStatusDTO item : resultWithdrawCashStatusDTOList.getModel()) {
    		withdrawCashStatusDTOMap.put(item.getId().toString(), item);
    	}
    	
    	// 组合数据
    	WithdrawCashStatusDTO withdrawCashStatusDTO = null;
    	for (TransactionDetailDTO item : transactionDetailDTOList) {
    		if (!StringUtils.isEmpty(item.getBizId())) {
    			withdrawCashStatusDTO = withdrawCashStatusDTOMap.get(item.getBizId());
    			if (withdrawCashStatusDTO != null) {
    				item.setStatus(withdrawCashStatusDTO.getStatus());
    			}
    		}
    	}
    	
    	return successGet(result);
    }
    
    
}
