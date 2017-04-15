package com.lawu.eshop.operator.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.ExpressCompanyDTO;
import com.lawu.eshop.mall.param.ShoppingOrderUpdateInfomationParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderUpdateInfomationForeignParam;
import com.lawu.eshop.operator.api.service.ExpressCompanyService;
import com.lawu.eshop.operator.api.service.ShoppingOrderExtendService;
import com.lawu.eshop.operator.api.service.ShoppingOrderService;

@Service
public class ShoppingOrderExtendServiceImpl extends BaseController implements ShoppingOrderExtendService {
	
	@Autowired
	private ExpressCompanyService expressCompanyService;
	
	@Autowired
	private ShoppingOrderService shoppingOrderService;
	
	@SuppressWarnings("rawtypes")
	@Transactional
	@Override
	public Result updateInformation(Long id, ShoppingOrderUpdateInfomationForeignParam param) {
		if (param == null) {
			
		}
		
		Result<ExpressCompanyDTO> resultExpressCompanyDTO = expressCompanyService.get(param.getExpressCompanyId());
		
		if (!isSuccess(resultExpressCompanyDTO)) {
			return successCreated(resultExpressCompanyDTO);
		}
		
		ShoppingOrderUpdateInfomationParam shoppingOrderUpdateInfomationParam = new ShoppingOrderUpdateInfomationParam();
		shoppingOrderUpdateInfomationParam.setConsigneeAddress(param.getConsigneeAddress());
		shoppingOrderUpdateInfomationParam.setConsigneeName(param.getConsigneeName());
		shoppingOrderUpdateInfomationParam.setConsigneeMobile(param.getConsigneeName());
		shoppingOrderUpdateInfomationParam.setExpressCompanyId(resultExpressCompanyDTO.getModel().getId());
		shoppingOrderUpdateInfomationParam.setExpressCompanyCode(resultExpressCompanyDTO.getModel().getCode());
		shoppingOrderUpdateInfomationParam.setExpressCompanyName(resultExpressCompanyDTO.getModel().getName());
		shoppingOrderUpdateInfomationParam.setOrderStatus(param.getOrderStatus());
		shoppingOrderUpdateInfomationParam.setWaybillNum(param.getWaybillNum());
		
		Result result = shoppingOrderService.updateInformation(id, shoppingOrderUpdateInfomationParam);
		
		if (!isSuccess(result)) {
			return successCreated(result.getRet());
		}
		
		return successCreated();
	}

}