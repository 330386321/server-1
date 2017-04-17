package com.lawu.eshop.operator.api.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.order.param.foreign.ShoppingOrderUpdateInfomationForeignParam;

/**
 * @author Sunny
 * @date 2017/04/15
 */
public interface ShoppingOrderExtendService {
	
	@SuppressWarnings("rawtypes")
	Result updateInformation(@PathVariable Long id , @RequestBody ShoppingOrderUpdateInfomationForeignParam param);
}
