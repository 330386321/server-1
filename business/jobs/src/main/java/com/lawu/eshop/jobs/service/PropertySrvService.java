package com.lawu.eshop.jobs.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.ad.param.CommissionJobParam;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.ReportAdEarningsPointDTO;
import com.lawu.eshop.property.param.ReportAdEarningsPointParam;

@FeignClient(value= "property-srv")
public interface PropertySrvService {

	/**
	 * 查询系统配置参数
	 * @param name
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.GET, value = "property/getValue")
	Result getValue(@RequestParam("name") String name);
	
	/**
	 * 计算上级提成
	 * @param param
	 * @return
	 * @author yangqh
	 */
	@RequestMapping(method = RequestMethod.POST, value = "commission/calculation")
	int calculation(@RequestBody CommissionJobParam param);
	
	/**
	 * 用户广告总收益
	 * @param reportAdEarningsPointParam
	 * @return
	 */
	@RequestMapping(value = "reportAdEarningsPoint/getReportAdEarningsPoint", method = RequestMethod.GET)
	public Result<ReportAdEarningsPointDTO> getReportAdEarningsPoint(@RequestBody ReportAdEarningsPointParam reportAdEarningsPointParam);
}
