package com.lawu.eshop.jobs.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.AdCommissionResultDTO;
import com.lawu.eshop.property.param.CommissionResultParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "property-srv")
public interface CommissionUtilService {

	/**
	 * 点击广告（平面、视频）提成计算结果
	 * @param param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "commissionUtil/getCommissionResult")
	Result<AdCommissionResultDTO> getCommissionResult(@RequestBody CommissionResultParam param);

}
