package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.user.dto.ReportFansRiseRateDTO;
import com.lawu.eshop.user.param.ReportFansDataParam;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年5月2日 下午2:47:13
 *
 */
public interface ReportFansService {

	/**
	 * 粉丝增长量
	 * @param dparam
	 * @return
	 * @author yangqh
	 * @date 2017年5月2日 下午2:48:18
	 */
	ReportFansRiseRateDTO fansRiseRate(ReportFansDataParam dparam);

    
}
