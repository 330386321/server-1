package com.lawu.eshop.merchant.api.mock.service;

import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.param.WorkOrderParam;
import com.lawu.eshop.merchant.api.service.WorkOrderService;
@Service
public class MockWorkOrderService extends BaseController implements WorkOrderService {

	@Override
	public Result saveWorkOrder(WorkOrderParam workOrderParam) {
		return successCreated(true);
	}

}
