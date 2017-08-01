package com.lawu.eshop.member.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.mall.constants.WorkOrderTypeEnum;
import com.lawu.eshop.mall.param.WorkOrderParam;
import com.lawu.eshop.member.api.service.WorkOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
/**
 * 描述：提交工单
 *
 * @author hongqm
 * @date 2017/07/27
 */
@Api(tags = "workOrder", value = "工单接口")
@RestController
@RequestMapping(value = "workOrder/")
public class WorkOrderController extends BaseController {

	@Autowired
	private WorkOrderService workOrderService;

	@Audit(date = "2017-08-01", reviewer = "孙林青")
	@ApiOperation(value = "用户提交工单", notes = "用户提交工单,[]（洪钦明）", httpMethod = "POST")
	@Authorization
	@ApiResponse(code = HttpCode.SC_CREATED, message = "success")
	@RequestMapping(value = "saveWorkOrder", method = RequestMethod.POST)
	public Result saveWorkOrder(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute WorkOrderParam workOrderParam) {
		workOrderParam.setWorkOrderTypeEnum(WorkOrderTypeEnum.MEMBER);
		return workOrderService.saveWorkOrder(workOrderParam);
	}
	
}
