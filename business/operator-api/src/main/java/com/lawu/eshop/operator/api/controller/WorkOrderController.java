package com.lawu.eshop.operator.api.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.WorkOrderDTO;
import com.lawu.eshop.mall.param.DealWorkOrderParam;
import com.lawu.eshop.mall.query.WorkOrderQuery;
import com.lawu.eshop.operator.api.service.WorkOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
@Api(tags = "workOrder", value = "工单接口")
@RestController
@RequestMapping(value = "workOrder/")
public class WorkOrderController extends BaseController{

	@Autowired
	private WorkOrderService workOrderService;
	
	@ApiOperation(value = "处理工单", notes = "处理工单,[]（洪钦明）", httpMethod = "PUT")
	@Authorization
	@ApiResponse(code = HttpCode.SC_CREATED, message = "success")
	@RequestMapping(value = "updateWorkOrder", method = RequestMethod.PUT)
	public Result updateWorkOrder(@ModelAttribute DealWorkOrderParam dealWorkOrderParam) {
		return workOrderService.updateWorkOrder(dealWorkOrderParam);
	}
	
	@ApiOperation(value = "查询工单", notes = "查询工单,[]（洪钦明）", httpMethod = "GET")
	@Authorization
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "selectWorkOrder", method = RequestMethod.GET)
	public Result<Page<WorkOrderDTO>> selectWorkOrder(@ModelAttribute WorkOrderQuery workOrderQuery) {
		return workOrderService.selectWorkOrder(workOrderQuery);
	}
}
