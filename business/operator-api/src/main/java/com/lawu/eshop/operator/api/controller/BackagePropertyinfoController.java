package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.operator.api.service.MemberService;
import com.lawu.eshop.operator.api.service.MerchantService;
import com.lawu.eshop.operator.api.service.PropertyinfoService;
import com.lawu.eshop.operator.api.service.RechargeService;
import com.lawu.eshop.property.constants.PropertyinfoFreezeEnum;
import com.lawu.eshop.property.constants.UserTypeEnum;
import com.lawu.eshop.property.dto.BalanceAndPointListQueryDTO;
import com.lawu.eshop.property.dto.PropertyinfoFreezeInfoDTO;
import com.lawu.eshop.property.param.BackagePropertyinfoDataParam;
import com.lawu.eshop.property.param.BackagePropertyinfoParam;
import com.lawu.eshop.property.param.RechargeQueryDataParam;
import com.lawu.eshop.property.param.RechargeQueryParam;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.MerchantDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

	@PageBody
	@ApiOperation(value = "余额、积分明细查询", notes = "余额、积分明细查询[]（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "selectPropertyinfoList", method = RequestMethod.POST)
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

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "充值余额或积分", notes = "充值余额或积分[]（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "updateBalanceAndPoint", method = RequestMethod.POST)
	public Result updateBalanceAndPoint(@Valid BackagePropertyinfoParam param, BindingResult result) {
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
	@RequestMapping(value = "updatePropertyinfoFreeze", method = RequestMethod.POST)
	public Result updatePropertyinfoFreeze(@RequestParam @ApiParam(required = true, value = "用户编号") String userNum,
			@RequestParam @ApiParam(required = true, value = "冻结标记(NO-解冻、YES-冻结)") PropertyinfoFreezeEnum freeze) {
		return propertyinfoService.updatePropertyinfoFreeze(userNum, freeze);
	}
}
