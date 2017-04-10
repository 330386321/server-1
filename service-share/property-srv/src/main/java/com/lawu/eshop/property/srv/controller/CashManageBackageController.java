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
	private CashManageFrontService cashManageService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result save(@RequestBody CashDataParam cash) {
		return successCreated(cashManageService.save(cash));
	}
	
}
