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
import com.lawu.eshop.property.dto.PropertyInfoDTO;
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
		return rechargeService.selectPropertyinfoList(dparam);
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
				if(StringUtils.isNotEmpty(param.getAccount())){
					Result<MemberDTO> member = memberService.getMemberByAccount(param.getAccount());
					if (member.getRet() == ResultCode.SUCCESS) {
						userNum = member.getModel().getNum();
					}
				}
				realParam.setMemberTransactionType(MemberTransactionTypeEnum.BACKAGE);
			} else if (UserTypeEnum.MEMCHANT.val.equals(param.getUserType().val)) {
				if(StringUtils.isNotEmpty(param.getAccount())){
					Result<MerchantDTO> merchant = merchantService.getMerchantByAccount(param.getAccount());
					if (merchant.getRet() == ResultCode.SUCCESS) {
						userNum = merchant.getModel().getNum();
					}
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
				if(StringUtils.isNotEmpty(param.getAccount())){
					Result<MemberDTO> member = memberService.getMemberByAccount(param.getAccount());
					if (member.getRet() == ResultCode.SUCCESS) {
						userNum = member.getModel().getNum();
					}
				}
				realParam.setMemberTransactionType(MemberTransactionTypeEnum.BACKAGE);
			} else if (UserTypeEnum.MEMCHANT.val.equals(param.getUserType().val)) {
				if(StringUtils.isNotEmpty(param.getAccount())){
					Result<MerchantDTO> merchant = merchantService.getMerchantByAccount(param.getAccount());
					if (merchant.getRet() == ResultCode.SUCCESS) {
						userNum = merchant.getModel().getNum();
					}
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
			StringBuilder es = new StringBuilder();
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
			StringBuilder es = new StringBuilder();
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

	@PageBody
	@ApiOperation(value = "查询用户资产信息", notes = "查询用户资产信息 []（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "getPropertyinfoPageList", method = RequestMethod.POST)
	@RequiresPermissions("property:list")
	public Result<Page<PropertyInfoDTO>> getPropertyinfoPageList(@RequestBody PropertyInfoBackageParam param) {
		String userNum = "";
		if(param.getUserType().val == UserTypeEnum.MEMCHANT_PC.val){
			param.setUserType(null);
		}else{
			if (UserTypeEnum.MEMBER.val.equals(param.getUserType().val)) {
				if(StringUtils.isNotEmpty(param.getAccount())){
					Result<MemberDTO> member = memberService.getMemberByAccount(param.getAccount());
					if (member.getRet() == ResultCode.SUCCESS) {
						userNum = member.getModel().getNum();
					}
				}
			} else if (UserTypeEnum.MEMCHANT.val.equals(param.getUserType().val)) {
				if(StringUtils.isNotEmpty(param.getAccount())){
					Result<MerchantDTO> merchant = merchantService.getMerchantByAccount(param.getAccount());
					if (merchant.getRet() == ResultCode.SUCCESS) {
						userNum = merchant.getModel().getNum();
					}
				}
			}
			if(StringUtils.isEmpty(userNum)){
				param.setUserNum(param.getAccount());
			}else {
				param.setUserNum(userNum);
			}
		}
		Result<Page<PropertyInfoDTO>> result = propertyinfoService.getPropertyinfoPageList(param);
		List<PropertyInfoDTO> propertyInfoDTOS = result.getModel().getRecords();
		if(!propertyInfoDTOS.isEmpty()){
			for(PropertyInfoDTO propertyInfoDTO : propertyInfoDTOS){
				if(propertyInfoDTO.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
					propertyInfoDTO.setAccountType(UserCommonConstant.MEMBER_NUM_TAG);
					Result<UserDTO> userDTOResult = memberService.getMemberByNum(propertyInfoDTO.getUserNum());
					if(isSuccess(userDTOResult)){
						propertyInfoDTO.setAccount(userDTOResult.getModel().getAccount());
					}
				}else{
					propertyInfoDTO.setAccountType(UserCommonConstant.MERCHANT_NUM_TAG);
					Result<MerchantDTO> merchantDTOResult = merchantService.getMerchantByNum(propertyInfoDTO.getUserNum());
					if(isSuccess(merchantDTOResult)){
						propertyInfoDTO.setAccount(merchantDTOResult.getModel().getAccount());
					}
				}
			}
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "冻结账号", notes = "冻结解冻账号[]（杨清华）", httpMethod = "PUT")
	@RequiresPermissions("account:freeze")
	@RequestMapping(value = "updatePropertyinfoFreeze", method = RequestMethod.PUT)
	public Result updatePropertyinfoFreeze(@RequestParam @ApiParam(required = true, value = "用户编号") String userNum) {
		return propertyinfoService.updatePropertyinfoFreeze(userNum, PropertyinfoFreezeEnum.YES);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "解冻账号", notes = "冻结解冻账号[]（杨清华）", httpMethod = "PUT")
	@RequiresPermissions("account:unfreeze")
	@RequestMapping(value = "updatePropertyinfoUnFreeze", method = RequestMethod.PUT)
	public Result updatePropertyinfoUnFreeze(@RequestParam @ApiParam(required = true, value = "用户编号") String userNum) {
		return propertyinfoService.updatePropertyinfoFreeze(userNum, PropertyinfoFreezeEnum.NO);
	}

}
