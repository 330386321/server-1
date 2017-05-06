package com.lawu.eshop.operator.api.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.mall.constants.MessageTypeEnum;
import com.lawu.eshop.mall.param.MessageInfoParam;
import com.lawu.eshop.mall.param.MessageTempParam;
import com.lawu.eshop.operator.api.service.CashManageBackageService;
import com.lawu.eshop.operator.api.service.MessageService;
import com.lawu.eshop.property.constants.CashOperEnum;
import com.lawu.eshop.property.dto.WithdrawCashBackageQueryDTO;
import com.lawu.eshop.property.dto.WithdrawCashBackageQuerySumDTO;
import com.lawu.eshop.property.param.CashBackageOperDataParam;
import com.lawu.eshop.property.param.CashBackageOperParam;
import com.lawu.eshop.property.param.CashBackageQueryDataParam;
import com.lawu.eshop.property.param.CashBackageQueryDetailParam;
import com.lawu.eshop.property.param.CashBackageQueryParam;
import com.lawu.eshop.property.param.CashBackageQuerySumParam;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.dto.UserDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 运营后台提现管理
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月10日 下午2:08:11
 *
 */
@Api(tags = "cashBackage")
@RestController
@RequestMapping(value = "cashBackage/")
public class CashManageBackageController extends BaseController {

	@Autowired
	private CashManageBackageService cashManageBackageService;
	@Autowired
	private MessageService messageService;

	/**
	 * 运营平台财务提现管理
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PageBody
	@ApiOperation(value = "商家、用户提现管理", notes = "商家、用户提现明细查询,[]（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "findCashInfo", method = RequestMethod.POST)
	public Result<Page<WithdrawCashBackageQueryDTO>> findCashInfo(@RequestBody CashBackageQueryParam param)
			throws Exception {
		CashBackageQueryDataParam dparam = new CashBackageQueryDataParam();
		dparam.setContent(param.getContent());
		dparam.setRegionPath(param.getRegionPath());
		dparam.setBeginDate(param.getBeginDate());
		dparam.setEndDate(param.getEndDate());
		dparam.setCashStatsuEnum(param.getCashStatsuEnum());
		dparam.setUserTypeEnum(param.getUserTypeEnum());
		dparam.setCurrentPage(param.getCurrentPage());
		dparam.setPageSize(param.getPageSize());

		Result<Page<WithdrawCashBackageQueryDTO>> dtos = cashManageBackageService.findCashInfo(dparam);
		return dtos;
	}

	@ApiOperation(value = "商家、用户提现管理总数统计", notes = "商家、用户提现明细查询总数统计,[]（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "getTotalNum", method = RequestMethod.POST)
	public Result<WithdrawCashBackageQuerySumDTO> getTotalNum(
			@ModelAttribute @ApiParam CashBackageQuerySumParam param) {
		return cashManageBackageService.getTotalNum(param);
	}

	@PageBody
	@ApiOperation(value = "商家、用户提现详情", notes = "商家、用户提现详情查询,[]（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "findCashInfoDetail", method = RequestMethod.POST)
	public Result<Page<WithdrawCashBackageQueryDTO>> findCashInfoDetail(
			@RequestBody @ApiParam CashBackageQueryDetailParam param) {

		Result<Page<WithdrawCashBackageQueryDTO>> dtos = cashManageBackageService.findCashInfoDetail(param);
		return dtos;
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "商家、用户提现数据操作", notes = "商家、用户提现数据操作,[]（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "updateWithdrawCash", method = RequestMethod.POST)
	public Result updateWithdrawCash(@Valid CashBackageOperParam param, BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuffer es = new StringBuffer();
			for (FieldError e : errors) {
				String msg = e.getDefaultMessage();
				es.append(msg);
			}
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, es.toString());
		}
		CashBackageOperDataParam dparam = new CashBackageOperDataParam();
		dparam.setId(param.getId());
		dparam.setCashOperEnum(param.getCashOperEnum());
		dparam.setFailReason(param.getFailReason());
		// TODO
		dparam.setAuditUserId(UserUtil.getCurrentUserId(getRequest()));
		dparam.setAuditUserName("super");
		Result result1 = cashManageBackageService.updateWithdrawCash(dparam);
		if (ResultCode.SUCCESS != result1.getRet()) {
			return result1;
		}

		// 发送站内消息
		MessageInfoParam messageInfoParam = new MessageInfoParam();
		messageInfoParam.setRelateId(0L);
		if (CashOperEnum.ACCEPT.val.equals(param.getCashOperEnum().val)) {
			messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_WITHDRAW_APPLY);
		} else if (CashOperEnum.SUCCESS.val.equals(param.getCashOperEnum().val)) {
			messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_WITHDRAW_SUCCESS);
		} else if (CashOperEnum.FAILURE.val.equals(param.getCashOperEnum().val)) {
			messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_WITHDRAW_FAIL);
		}
		MessageTempParam messageTempParam = new MessageTempParam();
		List<String> userNums = Arrays.asList(param.getUserNum().split(","));
		for (String userNum : userNums) {
			if (userNum.startsWith(UserCommonConstant.MEMBER_NUM_TAG)) {
				messageTempParam.setUserName("E店会员");
			} else if (userNum.startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
				messageTempParam.setUserName("E店商家");
			}
			messageInfoParam.setMessageParam(messageTempParam);
			messageService.saveMessage(userNum, messageInfoParam);
		}
		return successCreated();
	}
}
