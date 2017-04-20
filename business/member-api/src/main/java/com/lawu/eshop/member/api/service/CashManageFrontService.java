package com.lawu.eshop.member.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.WithdrawCashDetailDTO;
import com.lawu.eshop.property.dto.WithdrawCashQueryDTO;
import com.lawu.eshop.property.dto.WithdrawCashStatusDTO;
import com.lawu.eshop.property.param.CashBillDataParam;
import com.lawu.eshop.property.param.CashDataParam;

/**
 * 
 * <p>
 * Description: 前端用户提现
 * </p>
 * @author Yangqh
 * @date 2017年4月5日 下午6:18:44
 *
 */
@FeignClient(value= "property-srv")
public interface CashManageFrontService {
	
	/**
	 * 用户提现
	 * @param cash
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "cashFront/save")
	Result save(@RequestBody CashDataParam cash);

	/**
	 * 用户提现明细
	 * @param cparam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "cashFront/findCashList")
	Result<Page<WithdrawCashQueryDTO>> findCashList(@RequestBody CashBillDataParam cparam);
	
	/**
	 * 用户提现详情
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "cashFront/cashDetail")
	Result<WithdrawCashDetailDTO> cashDetail(@RequestParam("id") Long id);
	
	/**
	 * 查询交易明细 如果交易类型为提现，需要知道提现的状态 查询提现明细状态
	 * 
	 * @param ids
	 *            提现id列表
	 * @return
	 */
	@RequestMapping(value = "cashFront/findCashDetailStatus", method = RequestMethod.GET)
	Result<List<WithdrawCashStatusDTO>> findCashDetailStatus(@RequestParam("ids") List<Long> ids);
}
