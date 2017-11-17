package com.lawu.eshop.jobs.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.jobs.service.RongYunService;
import com.lawu.eshop.user.dto.RongYunHistoryMessageDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class MockRongYunServiceImpl implements RongYunService {


	@Override
	public Result<RongYunHistoryMessageDTO> getHistoryMessage(@RequestParam("date") String date) {
		return null;
	}
}
