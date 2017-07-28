package com.lawu.eshop.mall.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.param.DealWorkOrderParam;
import com.lawu.eshop.mall.param.WorkOrderParam;
import com.lawu.eshop.mall.query.WorkOrderQuery;
import com.lawu.eshop.mall.srv.bo.WorkOrderBO;

public interface WorkOrderService {
	
	Integer saveWorkOrder(WorkOrderParam param);
	
	Integer updateWorkOrder(DealWorkOrderParam dealWorkOrderParam);

	Page<WorkOrderBO> selectWorkOrder(WorkOrderQuery workOrderQuery);
}
