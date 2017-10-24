package com.lawu.eshop.merchant.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.type.UserType;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.merchant.api.service.BankAccountService;
import com.lawu.eshop.merchant.api.service.CashManageFrontService;
import com.lawu.eshop.merchant.api.service.PropertyInfoService;
import com.lawu.eshop.property.dto.BankAccountDTO;
import com.lawu.eshop.property.param.BankAccountParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
/**
 * 商家银行卡管理
 * @author zhangrc
 * @date 2017/03/30
 *
 */
@Api(tags = "bankAccount")
@RestController
@RequestMapping(value = "bankAccount/")
public class BankAccountController extends BaseController{
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@Autowired
	private PropertyInfoService propertyInfoService;
	
	@Autowired
	private CashManageFrontService cashManageFrontService;
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	@Audit(date = "2017-04-01", reviewer = "孙林青")
	@Authorization
    @ApiOperation(value = "我绑定的银行卡", notes = "查询当前用户绑定的银行卡[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Result<List<BankAccountDTO>> selectMyBank(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        String userNum = UserUtil.getCurrentUserNum(getRequest());
        return bankAccountService.selectMyBank(userNum);
    }
	
	/**
	 * 绑定银行卡
	 * @param token
	 * @param bankAccountParam
	 * @return
	 */
	@Audit(date = "2017-04-01", reviewer = "孙林青")
	@Authorization
    @ApiOperation(value = "添加银行卡", notes = "添加银行卡[6000|1022|1002|6021]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "saveBankAccount", method = RequestMethod.POST)
    public Result saveBankAccount(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
    						 @RequestParam @ApiParam(required = true, value = "支付密码") String payPwd,
                             @ModelAttribute @ApiParam(required = true, value = "银行卡信息") BankAccountParam bankAccountParam) {
		if(!bankAccountParam.getAccountNumber().matches("^[0-9]*$")){
    		return successCreated(ResultCode.BANK_ACCOUNT_ERROR);
    	}
    	if(bankAccountParam.getAccountNumber().length() > 26 || bankAccountParam.getAccountNumber().length() < 12) {
    		return successCreated(ResultCode.BANK_ACCOUNT_LENTH_OUT_OF_RANGE);
    	}
		String userNum = UserUtil.getCurrentUserNum(getRequest());
		Result flag=propertyInfoService.varifyPayPwd(userNum, payPwd);
		if(flag.getModel()!=null && (Boolean)flag.getModel()){
			 bankAccountParam.setUserType(UserType.MERCHANT);
			 return bankAccountService.saveBankAccount(userNum, bankAccountParam);
		}else{
			 return successCreated(ResultCode.PAY_PWD_ERROR);
		}
        
    }
	
	/**
	 * 删除已经绑定的银行卡
	 * @param token
	 * @param id
	 * @return
	 */
	@Audit(date = "2017-04-12", reviewer = "孙林青")
	@Authorization
    @ApiOperation(value = "删除银行卡", notes = "删除银行卡[1002]（张荣成）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "remove/{id}", method = RequestMethod.DELETE)
    public Result remove(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                         @PathVariable @ApiParam(required = true, value = "id") Long id) {
		String userNum = UserUtil.getCurrentUserNum(getRequest());
    	Result<Boolean> bankRs= cashManageFrontService.isExistCash(userNum, id);
    	if(isSuccess(bankRs)){
    		if(bankRs.getModel()){
    			return successCreated(ResultCode.BANK_CASH_EXIST);
    		}else{
    			bankAccountService.delete(id);
    		}
    	}
        return successDelete();
    }
	
	@Audit(date = "2017-04-12", reviewer = "孙林青")
    @SuppressWarnings("unchecked")
	@Authorization
    @ApiOperation(value = "单个查询", notes = "单个查询（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "selectAccount/{id}", method = RequestMethod.GET)
    public Result<BankAccountDTO> selectAccount(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                         @PathVariable @ApiParam(required = true, value = "id") Long id) {
        return successCreated(bankAccountService.selectAccount(id));
    }

	@Audit(date = "2017-04-12", reviewer = "孙林青")
    @Authorization
    @ApiOperation(value = "修改银行卡", notes = "修改银行卡[6000|6021]（张荣成）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "updateBankAccount/{id}", method = RequestMethod.PUT)
    public Result<BankAccountDTO> updateBankAccount(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                         @PathVariable @ApiParam(required = true, value = "id") Long id,
                         @RequestParam @ApiParam(required = true, value = "支付密码") String payPwd,
                         @ModelAttribute @ApiParam(required = true, value = "银行卡信息") BankAccountParam bankAccountParam) {
		
		if(!bankAccountParam.getAccountNumber().matches("^[0-9]*$")){
    		return successCreated(ResultCode.BANK_ACCOUNT_ERROR);
    	}
    	if(bankAccountParam.getAccountNumber().length() > 26 || bankAccountParam.getAccountNumber().length() < 12) {
    		return successCreated(ResultCode.BANK_ACCOUNT_LENTH_OUT_OF_RANGE);
    	}
    	String userNum = UserUtil.getCurrentUserNum(getRequest());
    	Result<Boolean> bankRs= cashManageFrontService.isExistCash(userNum, id);
    	if(!isSuccess(bankRs)){
    		 return successCreated(bankRs.getRet());
    	}
    	if(bankRs.getModel()){
			return successCreated(ResultCode.BANK_CASH_EXIST);
		}else{
			Result flag=propertyInfoService.varifyPayPwd(userNum, payPwd);
			if(flag.getModel()!=null && (Boolean)flag.getModel()){
				 bankAccountService.updateBankAccount(id,userNum, bankAccountParam);
				 return successCreated(ResultCode.SUCCESS);
			}else{
				 return successCreated(ResultCode.PAY_PWD_ERROR);
			}
		}

    }
	
	
	@SuppressWarnings("unchecked")
	@Authorization
    @ApiOperation(value = "获取银行卡用户名称", notes = "获取银行卡用户名称（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "selectBankName", method = RequestMethod.GET)
    public Result<BankAccountDTO> selectBankName(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token){
    	String userNum = UserUtil.getCurrentUserNum(getRequest());
    	return successCreated(bankAccountService.selectBankName(userNum));
    }
}
