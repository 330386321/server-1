package com.lawu.eshop.property.srv.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.dto.WithdrawCashDetailDTO;
import com.lawu.eshop.property.dto.WithdrawCashQueryDTO;
import com.lawu.eshop.property.param.CashBillDataParam;
import com.lawu.eshop.property.param.CashDataParam;
import com.lawu.eshop.property.srv.bo.WithdrawCashDetailBO;
import com.lawu.eshop.property.srv.bo.WithdrawCashQueryBO;
import com.lawu.eshop.property.srv.service.CashManageFrontService;
import com.lawu.eshop.utils.BeanUtil;

/**
 * 
 * <p>
 * Description: 前端用户提现管理
 * </p>
 * @author Yangqh
 * @date 2017年4月5日 下午2:44:35
 *
 */
@RestController
@RequestMapping(value = "cashFront/")
public class CashManageFrontController extends BaseController{
	
	@Autowired
	private CashManageFrontService cashManageService;

	/**
	 * 用户、商家提现操作
	 * @param cash
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result save(@RequestBody CashDataParam cash) {
		return successCreated(cashManageService.save(cash));
	}
	
	/**
	 * 用户、商家提现明细
	 * @param cparam
	 * @return
	 */
	@RequestMapping(value = "findCashList", method = RequestMethod.POST)
	public Result<Page<WithdrawCashQueryDTO>> findCashList(@RequestBody CashBillDataParam cparam) {
		Page<WithdrawCashQueryBO> page = cashManageService.findCashList(cparam);
		List<WithdrawCashQueryBO> cbos = page.getRecords();
		List<WithdrawCashQueryDTO> dtos = new ArrayList<WithdrawCashQueryDTO>();
		for(WithdrawCashQueryBO bo : cbos){
			WithdrawCashQueryDTO dto = new WithdrawCashQueryDTO();
			dto.setId(bo.getId());
			dto.setCashMoney(bo.getCashMoney());
			dto.setCashStatusEnum(bo.getCashStatusEnum());
			dto.setCdate(bo.getCdate());
			dto.setTitle(bo.getTitle());
			dtos.add(dto);
		}
		Page<WithdrawCashQueryDTO> pageResult = new Page<WithdrawCashQueryDTO>();
		pageResult.setTotalCount(page.getTotalCount());
		pageResult.setCurrentPage(page.getCurrentPage());
		pageResult.setRecords(dtos);
		return successCreated(pageResult);
	}

	/**
	 * 用户、商家提现详情
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "cashDetail", method = RequestMethod.GET)
	public Result<WithdrawCashDetailDTO> cashDetail(@RequestParam Long id) throws Exception {
		if(id == null){
			return successGet(ResultCode.ID_EMPTY);
		}
		WithdrawCashDetailBO cashDetailBO = cashManageService.cashDetail(id);
		if(cashDetailBO == null){
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}
		WithdrawCashDetailDTO dto = new WithdrawCashDetailDTO();
		BeanUtil.copyProperties(cashDetailBO, dto);
		
		return successGet(dto);
	}
}
