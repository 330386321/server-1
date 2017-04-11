package com.lawu.eshop.operator.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.WithdrawCashBackageQueryDTO;
import com.lawu.eshop.property.param.CashBackageQueryDataParam;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月11日 上午11:46:48
 *
 */
@FeignClient(value = "property-srv")
public interface CashManageBackageService {

	/**
	 * 运营后台提现管理列表
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "cashBackage/findCashInfo",method = RequestMethod.POST)
	Result<Page<WithdrawCashBackageQueryDTO>> findCashInfo(@RequestBody CashBackageQueryDataParam param);
	
	

}
