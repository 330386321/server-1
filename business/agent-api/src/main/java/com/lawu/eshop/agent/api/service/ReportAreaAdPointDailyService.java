package com.lawu.eshop.agent.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.lawu.eshop.statistics.dto.ReportAreaAdPorintDailyByAreaIdDTO;
import com.lawu.eshop.statistics.param.AgentSelectAreaAdPointParam;
import com.lawu.eshop.framework.web.Result;

/**
 * @author hongqm
 * @date 2017/8/11
 */
@FeignClient(value = "statistics-srv")
public interface ReportAreaAdPointDailyService {

	
	
	@RequestMapping(value = "reportAreaAdPointDaily/selectReportAreaAdPointDaily", method = RequestMethod.GET)
	Result<List<ReportAreaAdPorintDailyByAreaIdDTO>> selectReportAreaAdPointDaily(@RequestBody AgentSelectAreaAdPointParam param);
	
}
