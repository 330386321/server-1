package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.param.WorkOrderParam;
import com.lawu.eshop.member.api.service.WorkOrderService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 工单接口
 * 
 * @author hongqm
 * @date 2017/07/27
 *
 */
@Service
public class MockWorkOrderService extends BaseController implements WorkOrderService {


	@Override
	public Result saveWorkOrder(@RequestBody WorkOrderParam workOrderParam) {
		return null;
	}
}
