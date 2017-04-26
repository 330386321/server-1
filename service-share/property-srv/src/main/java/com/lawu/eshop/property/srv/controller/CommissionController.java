package com.lawu.eshop.property.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.param.CommissionJobParam;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.property.srv.service.CommissionService;

/**
 * 
 * <p>
 * Description: 计算提成
 * </p>
 * @author Yangqh
 * @date 2017年4月26日 下午8:22:53
 *
 */
@RestController
@RequestMapping(value = "commission/")
public class CommissionController extends BaseController {

	@Autowired
	private CommissionService commissionService;

	/**
	 * 计算提成
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "calculation", method = RequestMethod.POST)
	public int calculation(@RequestBody CommissionJobParam param) {
		int retCode = commissionService.calculation(param);
		return retCode;
	}

	
}
