package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.operator.api.service.*;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.PropertyinfoFreezeEnum;
import com.lawu.eshop.property.constants.UserTypeEnum;
import com.lawu.eshop.property.dto.BalanceAndPointListQueryDTO;
import com.lawu.eshop.property.dto.PointDetailBackageDTO;
import com.lawu.eshop.property.dto.PropertyinfoFreezeInfoDTO;
import com.lawu.eshop.property.dto.TransactionDetailBackageDTO;
import com.lawu.eshop.property.param.*;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.MerchantDTO;
import com.lawu.eshop.user.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 * <p>
 * Description: 运营平台余额积分充值，资金管理
 * </p>
 * 
 * @author Yangqh
 * @date 2017年5月16日 下午2:06:48
 *
 */
@Api(tags = "backagePropertyinfo")
@RestController
@RequestMapping(value = "backagePropertyinfo/")
public class BackagePropertyinfoController extends BaseController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private PropertyinfoService propertyinfoService;
	@Autowired
	private RechargeService rechargeService;
	@Autowired
	private TransactionDetailService transactionDetailService;
	@Autowired
	private PointDetailService pointDetailService;

	@PageBody
	@ApiOperation(value = "余额、积分明细查询", notes = "余额、积分明细查询[]（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "selectPropertyinfoList", method = RequestMethod.POST)
	@RequiresAuthentication
	public Result<Page<BalanceAndPointListQueryDTO>> selectPropertyinfoList(@RequestBody RechargeQueryParam param) {
		RechargeQueryDataParam dparam = new RechargeQueryDataParam();
		String userNum = "";
		if (UserTypeEnum.MEMBER.val.equals(param.getUserType().val)) {
			Result<MemberDTO> member = memberService.getMemberByAccount(param.getAccount());
			if (member.getRet() == ResultCode.SUCCESS) {
				userNum = member.getModel().getNum();
			}
		} else if (UserTypeEnum.MEMCHANT.val.equals(param.getUserType().val)) {
			Result<MerchantDTO> merchant = merchantService.getMerchantByAccount(param.getAccount());
			if (merchant.getRet() == ResultCode.SUCCESS) {
				userNum = merchant.getModel().getNum();
			}
		}
		dparam.setUserNum(userNum);
		dparam.setRechargeNumber(param.getRechargeNumber());
		dparam.setStatus(param.getStatus());
		dparam.setCurrentPage(param.getCurrentPage());
		dparam.setPageSize(param.getPageSize());
		Result<Page<BalanceAndPointListQueryDTO>> result = rechargeService.selectPropertyinfoList(dparam);
		return result;
	}

	@PageBody
	@ApiOperation(value = "余额充值列表", notes = "余额充值列表。（梅述全）", httpMethod = "POST")
	@RequestMapping(value = "getBackageRechargePageList", method = RequestMethod.POST)
	@RequiresPermissions("balance:list")
	public Result<Page<TransactionDetailBackageDTO>> getBackageRechargePageList(@RequestBody RechargeQueryParam param) {
		String userNum = "";
		TransactionDetailQueryForBackageParam realParam = new TransactionDetailQueryForBackageParam();
		if(!param.getUserType().val.equals(UserTypeEnum.MEMCHANT_PC.val)){
			if (UserTypeEnum.MEMBER.val.equals(param.getUserType().val)) {
				Result<MemberDTO> member = memberService.getMemberByAccount(param.getAccount());
				if (member.getRet() == ResultCode.SUCCESS) {
					userNum = member.getModel().getNum();
				}
				realParam.setMemberTransactionType(MemberTransactionTypeEnum.BACKAGE);
			} else if (UserTypeEnum.MEMCHANT.val.equals(param.getUserType().val)) {
				Result<MerchantDTO> merchant = merchantService.getMerchantByAccount(param.getAccount());
				if (merchant.getRet() == ResultCode.SUCCESS) {
					userNum = merchant.getModel().getNum();
				}
				realParam.setMerchantTransactionType(MerchantTransactionTypeEnum.BACKAGE);
			}
			if(StringUtils.isEmpty(userNum)){
				realParam.setUserNum(param.getAccount());
			}else {
				realParam.setUserNum(userNum);
			}
		}
		realParam.setCurrentPage(param.getCurrentPage());
		realParam.setPageSize(param.getPageSize());
		Result<Page<TransactionDetailBackageDTO>> result = transactionDetailService.getBackageRechargePageList(realParam);
		List<TransactionDetailBackageDTO> transactionDetailBackageDTOS = result.getModel().getRecords();
		if(!transactionDetailBackageDTOS.isEmpty()){
			for(TransactionDetailBackageDTO transactionDetailBackageDTO : transactionDetailBackageDTOS){
				if(transactionDetailBackageDTO.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
					transactionDetailBackageDTO.setAccountType(UserCommonConstant.MEMBER_NUM_TAG);
					Result<UserDTO> userDTOResult = memberService.getMemberByNum(transactionDetailBackageDTO.getUserNum());
					if(isSuccess(userDTOResult)){
						transactionDetailBackageDTO.setAccount(userDTOResult.getModel().getAccount());
					}
				}else{
					transactionDetailBackageDTO.setAccountType(UserCommonConstant.MERCHANT_NUM_TAG);
					Result<MerchantDTO> merchantDTOResult = merchantService.getMerchantByNum(transactionDetailBackageDTO.getUserNum());
					if(isSuccess(merchantDTOResult)){
						transactionDetailBackageDTO.setAccount(merchantDTOResult.getModel().getAccount());
					}
				}
			}
		}
		return result;
	}

	@PageBody
	@ApiOperation(value = "积分充值列表", notes = "积分充值列表。（梅述全）", httpMethod = "POST")
	@RequestMapping(value = "getBackagePointPageList", method = RequestMethod.POST)
	@RequiresPermissions("point:list")
	public Result<Page<PointDetailBackageDTO>> getBackagePointPageList(@RequestBody RechargeQueryParam param) {
		String userNum = "";
		TransactionDetailQueryForBackageParam realParam = new TransactionDetailQueryForBackageParam();
		if(!param.getUserType().val.equals(UserTypeEnum.MEMCHANT_PC.val)){
			if (UserTypeEnum.MEMBER.val.equals(param.getUserType().val)) {
				Result<MemberDTO> member = memberService.getMemberByAccount(param.getAccount());
				if (member.getRet() == ResultCode.SUCCESS) {
					userNum = member.getModel().getNum();
				}
				realParam.setMemberTransactionType(MemberTransactionTypeEnum.BACKAGE);
			} else if (UserTypeEnum.MEMCHANT.val.equals(param.getUserType().val)) {
				Result<MerchantDTO> merchant = merchantService.getMerchantByAccount(param.getAccount());
				if (merchant.getRet() == ResultCode.SUCCESS) {
					userNum = merchant.getModel().getNum();
				}
				realParam.setMerchantTransactionType(MerchantTransactionTypeEnum.BACKAGE);
			}
			if(StringUtils.isEmpty(userNum)){
				realParam.setUserNum(param.getAccount());
			}else {
				realParam.setUserNum(userNum);
			}
		}
		realParam.setCurrentPage(param.getCurrentPage());
		realParam.setPageSize(param.getPageSize());
		Result<Page<PointDetailBackageDTO>> result = pointDetailService.getBackagePointPageList(realParam);
		List<PointDetailBackageDTO> pointDetailBackageDTOS = result.getModel().getRecords();
		if(!pointDetailBackageDTOS.isEmpty()){
			for(PointDetailBackageDTO pointDetailBackageDTO : pointDetailBackageDTOS){
				if(pointDetailBackageDTO.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
					pointDetailBackageDTO.setAccountType(UserCommonConstant.MEMBER_NUM_TAG);
					Result<UserDTO> userDTOResult = memberService.getMemberByNum(pointDetailBackageDTO.getUserNum());
					if(isSuccess(userDTOResult)){
						pointDetailBackageDTO.setAccount(userDTOResult.getModel().getAccount());
					}
				}else{
					pointDetailBackageDTO.setAccountType(UserCommonConstant.MERCHANT_NUM_TAG);
					Result<MerchantDTO> merchantDTOResult = merchantService.getMerchantByNum(pointDetailBackageDTO.getUserNum());
					if(isSuccess(merchantDTOResult)){
						pointDetailBackageDTO.setAccount(merchantDTOResult.getModel().getAccount());
					}
				}
			}
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "充值余额", notes = "充值余额[]（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "updateBalance", method = RequestMethod.POST)
	@RequiresPermissions("balance:recharge")
	public Result updateBalance(@Valid BackagePropertyinfoParam param, BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}

		BackagePropertyinfoDataParam dparam = new BackagePropertyinfoDataParam();
		dparam.setMoney(param.getMoney());
		dparam.setPayTypeEnum(param.getPayTypeEnum());
		if (UserTypeEnum.MEMBER.val.equals(param.getUserTypeEnum().val)) {
			Result<MemberDTO> member = memberService.getMemberByAccount(param.getAccount());
			if (member.getRet() != ResultCode.SUCCESS) {
				return member;
			}
			dparam.setUserNum(member.getModel().getNum());
		} else if (UserTypeEnum.MEMCHANT.val.equals(param.getUserTypeEnum().val)) {
			Result<MerchantDTO> merchant = merchantService.getMerchantByAccount(param.getAccount());
			if (merchant.getRet() != ResultCode.SUCCESS) {
				return merchant;
			}
			dparam.setUserNum(merchant.getModel().getNum());
		}

		return propertyinfoService.updateBalanceAndPoint(dparam);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "充值积分", notes = "充值积分[]（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "updatePoint", method = RequestMethod.POST)
	@RequiresPermissions("point:recharge")
	public Result updatePoint(@Valid BackagePropertyinfoParam param, BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}

		BackagePropertyinfoDataParam dparam = new BackagePropertyinfoDataParam();
		dparam.setMoney(param.getMoney());
		dparam.setPayTypeEnum(param.getPayTypeEnum());
		if (UserTypeEnum.MEMBER.val.equals(param.getUserTypeEnum().val)) {
			Result<MemberDTO> member = memberService.getMemberByAccount(param.getAccount());
			if (member.getRet() != ResultCode.SUCCESS) {
				return member;
			}
			dparam.setUserNum(member.getModel().getNum());
		} else if (UserTypeEnum.MEMCHANT.val.equals(param.getUserTypeEnum().val)) {
			Result<MerchantDTO> merchant = merchantService.getMerchantByAccount(param.getAccount());
			if (merchant.getRet() != ResultCode.SUCCESS) {
				return merchant;
			}
			dparam.setUserNum(merchant.getModel().getNum());
		}

		return propertyinfoService.updateBalanceAndPoint(dparam);
	}

	@ApiOperation(value = "查询用户资产信息（是否冻结）", notes = "查询用户资产信息（是否冻结）[]（杨清华）", httpMethod = "GET")
	@RequestMapping(value = "getPropertyinfoFreeze", method = RequestMethod.GET)
	@RequiresPermissions("property:manage")
	public Result<PropertyinfoFreezeInfoDTO> getPropertyinfoFreeze(
			@RequestParam @ApiParam(required = true, value = "用户账号") String account,
			@RequestParam @ApiParam(required = true, value = "用户类型") UserTypeEnum userType) {
		String userNum = "";
		if (UserTypeEnum.MEMBER.val.equals(userType.val)) {
			Result<MemberDTO> member = memberService.getMemberByAccount(account);
			if (member.getRet() == ResultCode.SUCCESS) {
				userNum = member.getModel().getNum();
			}
		} else if (UserTypeEnum.MEMCHANT.val.equals(userType.val)) {
			Result<MerchantDTO> merchant = merchantService.getMerchantByAccount(account);
			if (merchant.getRet() == ResultCode.SUCCESS) {
				userNum = merchant.getModel().getNum();
			}
		}else{
			return successCreated(ResultCode.FAIL,"用户类型参数错误");
		}
		return propertyinfoService.getPropertyinfoFreeze(userNum);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "冻结解冻账号", notes = "冻结解冻账号[]（杨清华）", httpMethod = "POST")
	@RequiresPermissions("account:freeze")
	@RequestMapping(value = "updatePropertyinfoFreeze", method = RequestMethod.POST)
	public Result updatePropertyinfoFreeze(@RequestParam @ApiParam(required = true, value = "用户编号") String userNum,
			@RequestParam @ApiParam(required = true, value = "冻结标记(NO-解冻、YES-冻结)") PropertyinfoFreezeEnum freeze) {
		return propertyinfoService.updatePropertyinfoFreeze(userNum, freeze);
	}
}
