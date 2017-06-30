package com.lawu.eshop.jobs.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.PointConsumeReportDTO;
import com.lawu.eshop.property.param.PointDetailReportParam;

@FeignClient(value= "property-srv")
public interface PropertyPointDetailService {

	/**
	 * 
	 * @param param
	 * @return
	 * @author yangqh
	 * @date 2017年6月30日 下午2:29:46
	 */
	@RequestMapping(method = RequestMethod.POST, value = "pointDetail/selectPointDetailListByDateAndDirection")
	Result<List<PointConsumeReportDTO>> selectPointDetailListByDateAndDirection(@RequestBody PointDetailReportParam param);

	/**
	 * 
	 * @param param
	 * @return
	 * @author yangqh
	 * @date 2017年6月30日 下午2:47:11
	 */
	@RequestMapping(method = RequestMethod.POST, value = "pointDetail/selectPointDetailListByDateAndDirectionAndPointType")
	Result<List<PointConsumeReportDTO>> selectPointDetailListByDateAndDirectionAndPointType(@RequestBody PointDetailReportParam param);

	
}
