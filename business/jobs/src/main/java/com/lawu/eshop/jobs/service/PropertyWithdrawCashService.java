package com.lawu.eshop.jobs.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.WithdrawCashReportDTO;
import com.lawu.eshop.property.param.WithdrawCashReportParam;

@FeignClient(value= "property-srv")
public interface PropertyWithdrawCashService {

	/**
	 * 获取某天提现成功的记录
	 * @param param
	 * @return
	 * @author yangqh
	 * @date 2017年6月28日 下午4:17:17
	 */
	@RequestMapping(method = RequestMethod.POST, value = "cashBackage/selectWithdrawCashListByDateAndStatus")
	Result<List<WithdrawCashReportDTO>> selectWithdrawCashListByDateAndStatus(@RequestBody WithdrawCashReportParam param);

	
}