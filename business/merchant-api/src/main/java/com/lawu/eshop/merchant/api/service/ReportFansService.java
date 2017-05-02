package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.ReportFansRiseRateDTO;
import com.lawu.eshop.user.param.ReportFansDataParam;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年5月2日 下午2:18:07
 *
 */
@FeignClient(value= "user-srv")
public interface ReportFansService {

	/**
	 * 粉丝数据，粉丝增长量
	 * @param dparam
	 * @return
	 * @author yangqh
	 * @date 2017年5月2日 下午2:34:33
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "reportFans/fansRiseRate")
	Result<ReportFansRiseRateDTO> fansRiseRate(@RequestBody ReportFansDataParam dparam);
    
}
