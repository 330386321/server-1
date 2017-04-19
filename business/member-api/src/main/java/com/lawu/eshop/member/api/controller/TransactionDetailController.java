package com.lawu.eshop.member.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.lawu.eshop.member.api.service.TransactionDetailService;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.dto.TransactionDetailDTO;
import com.lawu.eshop.property.dto.TransactionTypeDTO;
import com.lawu.eshop.property.param.TransactionDetailQueryForMemberParam;

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
     * @param param 查询参数
     * @return
     */
    @SuppressWarnings("unchecked")
	@Audit(date = "2017-04-15", reviewer = "孙林青")
    @ApiOperation(value = "获取交易明细列表", notes = "根据用户编号分页获取交易明细列表。[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public Result<Page<TransactionDetailDTO>> page(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(name = "param", value = "查询资料") TransactionDetailQueryForMemberParam param) {
    	String userNum = UserUtil.getCurrentUserNum(getRequest());
    	
    	Result<Page<TransactionDetailDTO>> result = transactionDetailService.findPageByUserNumForMember(userNum, param);
    	if (!isSuccess(result)) {
    		return successGet(result.getRet());
    	}
    	
    	return successGet(result);
    }
    
    /**
     * 获取用户的所有交易类型。
     * 
     * @param token 
     * @param param 查询参数
     * @return
     */
    @ApiOperation(value = "获取用户的所有交易类型。", notes = "获取用户的所有交易类型。[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getAllTransactionType", method = RequestMethod.GET)
    public Result<List<TransactionTypeDTO>> page(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
    	List<TransactionTypeDTO> list = new ArrayList<TransactionTypeDTO>();
    	for (MemberTransactionTypeEnum item : MemberTransactionTypeEnum.values()) {
    		TransactionTypeDTO transactionTypeDTO = new TransactionTypeDTO();
    		transactionTypeDTO.setName(item.getName());
    		transactionTypeDTO.setValue(item.name());
    		list.add(transactionTypeDTO);
    	}
    	
    	return successGet(list);
    }
}
