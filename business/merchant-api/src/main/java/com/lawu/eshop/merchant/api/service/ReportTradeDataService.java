package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.ReportRiseRateDTO;
import com.lawu.eshop.user.param.ReportDataParam;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年5月3日 下午3:59:18
 *
 */
@FeignClient(value= "order-srv")
public interface ReportTradeDataService {

	/**
	 * 买单交易数据报表
	 * @param dparam
	 * @return
	 * @author yangqh
	 * @date 2017年5月3日 下午4:00:48
	 */
	@RequestMapping(method = RequestMethod.POST, value = "reportPay/payVolumeRiseRate")
	Result<ReportRiseRateDTO> payVolumeRiseRate(@RequestBody ReportDataParam dparam);
    
}
