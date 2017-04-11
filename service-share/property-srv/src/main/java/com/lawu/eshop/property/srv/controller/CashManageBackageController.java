package com.lawu.eshop.property.srv.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.dto.WithdrawCashBackageQueryDTO;
import com.lawu.eshop.property.dto.WithdrawCashBackageQuerySumDTO;
import com.lawu.eshop.property.param.CashBackageQueryDataParam;
import com.lawu.eshop.property.param.CashBackageQuerySumParam;
import com.lawu.eshop.property.srv.bo.WithdrawCashBackageQueryBO;
import com.lawu.eshop.property.srv.bo.WithdrawCashBackageQuerySumBO;
import com.lawu.eshop.property.srv.service.CashManageBackageService;
import com.lawu.eshop.utils.BeanUtil;

/**
 * 
 * <p>
 * Description: 运营后台提现管理
 * </p>
 * @author Yangqh
 * @date 2017年4月10日 下午2:08:11
 *
 */
@RestController
@RequestMapping(value = "cashBackage/")
public class CashManageBackageController extends BaseController{
	
	@Autowired
	private CashManageBackageService cashManageBackageService;

	/**
	 * 运营平台财务提现管理
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "findCashInfo", method = RequestMethod.POST)
	public Result<Page<WithdrawCashBackageQueryDTO>> findCashInfo(@RequestBody CashBackageQueryDataParam param) throws Exception {
		if(param.getUserTypeEnum() == null){
			return successCreated(ResultCode.CASH_BACKAGE_USER_TYPE_NULL);
		}
		if(param.getBeginDate() == null || param.getEndDate() == null){
			return successCreated(ResultCode.DATE_RANGE_NULL);
		}
		Page<WithdrawCashBackageQueryBO> page = cashManageBackageService.findCashInfo(param);
		List<WithdrawCashBackageQueryBO> cbos = page.getRecords();
		List<WithdrawCashBackageQueryDTO> dtos = new ArrayList<WithdrawCashBackageQueryDTO>();
		for(WithdrawCashBackageQueryBO bo : cbos){
			WithdrawCashBackageQueryDTO dto = new WithdrawCashBackageQueryDTO();
			BeanUtil.copyProperties(bo, dto);
			dtos.add(dto);
		}
		Page<WithdrawCashBackageQueryDTO> pageResult = new Page<WithdrawCashBackageQueryDTO>();
		pageResult.setTotalCount(page.getTotalCount());
		pageResult.setCurrentPage(page.getCurrentPage());
		pageResult.setRecords(dtos);
		return successCreated(pageResult);
	}
	
	/**
	 * 运营平台提现管理统计成功总笔数和成功总金额
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getTotalNum", method = RequestMethod.POST)
	public Result<WithdrawCashBackageQuerySumDTO> getTotalNum(@RequestBody CashBackageQuerySumParam param) throws Exception {
		if(param.getUserTypeEnum() == null){
			return successCreated(ResultCode.CASH_BACKAGE_USER_TYPE_NULL);
		}
		WithdrawCashBackageQuerySumDTO dto = new WithdrawCashBackageQuerySumDTO();
		WithdrawCashBackageQuerySumBO bo = cashManageBackageService.getTotalNum(param);
		BeanUtil.copyProperties(bo, dto);
		if(dto.getSuccessMoney() == null){
			dto.setSuccessMoney(new BigDecimal(0));
		}
		return successCreated(dto);
	}
}
