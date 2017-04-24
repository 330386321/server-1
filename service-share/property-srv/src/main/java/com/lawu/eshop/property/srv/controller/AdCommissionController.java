package com.lawu.eshop.property.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.param.AdCommissionJobParam;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.property.srv.service.AdService;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月24日 下午7:48:55
 *
 */
@RestController
@RequestMapping(value = "adCommission/")
public class AdCommissionController extends BaseController {

	@Autowired
	private AdService adService;

	/**
	 * 用户、商家提现详情
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "calculation", method = RequestMethod.POST)
	public int calculation(@RequestBody AdCommissionJobParam param) {
		int retCode = adService.calculation(param);
		return retCode;
	}

	
}
