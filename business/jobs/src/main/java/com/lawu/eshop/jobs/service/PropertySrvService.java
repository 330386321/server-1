package com.lawu.eshop.jobs.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.ad.param.CommissionJobParam;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.ReportAdEarningsPointDTO;
import com.lawu.eshop.property.dto.ReportAdPointGroupByAreaDTO;
import com.lawu.eshop.property.dto.ReportEarningsDTO;
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
	Result<ReportAdEarningsPointDTO> getReportAdEarningsPoint(@RequestBody ReportAdEarningsPointParam reportAdEarningsPointParam);
	
	/**
	 * 广告用户  爱心账户收益
	 * @param bzId
	 * @return
	 */
	@RequestMapping(value = "reportAdEarningsPoint/getReportEarnings", method = RequestMethod.GET)
	Result<ReportEarningsDTO> getReportEarnings(@RequestParam("bzId") Long bzId);
	
	/**
	 * 获取时间内的发广告的区域统计
	 * @param bdate
	 * @param edate
	 * @return
	 */
	@RequestMapping(value = "pointDetail/getReportAdPointGroupByArea",method = RequestMethod.GET)
	Result<List<ReportAdPointGroupByAreaDTO>> getReportAdPointGroupByArea(@RequestParam("bdate") String bdate, @RequestParam("edate") String edate);
}
