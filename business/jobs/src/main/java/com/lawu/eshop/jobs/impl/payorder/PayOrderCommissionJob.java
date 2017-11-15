package com.lawu.eshop.jobs.impl.payorder;

import java.util.List;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.jobs.service.OrderSrvService;
import com.lawu.eshop.jobs.service.SaleAndVolumeCommissionService;
import com.lawu.eshop.order.dto.ShoppingOrderCommissionDTO;
import com.lawu.jobsextend.AbstractWholePageJob;
import com.lawu.jobsextend.JobsExtendPageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * Description: 买单提成
 *
 * @author yangqh
 * @date 2017年4月24日 下午3:31:10
 *
 */
public class PayOrderCommissionJob extends AbstractWholePageJob<ShoppingOrderCommissionDTO> {

	private static Logger logger = LoggerFactory.getLogger(PayOrderCommissionJob.class);

	@Autowired
	private OrderSrvService orderSrvService;
	@Autowired
	private SaleAndVolumeCommissionService saleAndVolumeCommissionService;

	@Override
	public boolean isStatusData() {
		return true;
	}

	@Override
	public boolean continueWhenSinglePageFail() {
		return true;
	}

	@Override
	public void executePage(List<ShoppingOrderCommissionDTO> dataPage) throws JobsExtendPageException {
		saleAndVolumeCommissionService.commission(dataPage,1, "买单提成");
	}

	@Override
	public List<ShoppingOrderCommissionDTO> queryPage(int offset, int pageSize) {
		Result<List<ShoppingOrderCommissionDTO>> ordersResult = orderSrvService.selectNotCommissionOrder(offset, pageSize);
		return ordersResult.getModel();
	}
}
