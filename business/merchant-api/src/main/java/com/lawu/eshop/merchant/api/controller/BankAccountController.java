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
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.merchant.api.service.BankAccountService;
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
        Result<List<BankAccountDTO>> bankAccountDTOS = bankAccountService.selectMyBank(userNum);
        return bankAccountDTOS;
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
		String userNum = UserUtil.getCurrentUserNum(getRequest());
		Result flag=propertyInfoService.varifyPayPwd(userNum, payPwd);
		if(flag.getModel()!=null && (Boolean)flag.getModel()){
			 Result rs = bankAccountService.saveBankAccount(userNum, bankAccountParam);
			 return rs;
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
        Result rs = bankAccountService.delete(id);
        return successDelete();
    }

}
