package com.lawu.eshop.property.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.param.CashDataParam;
import com.lawu.eshop.property.srv.service.CashManageFrontService;

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

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result save(@RequestBody CashDataParam cash) {
		return successCreated(cashManageService.save(cash));
	}
	
}
