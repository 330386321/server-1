package com.lawu.eshop.jobs.mock.service;

import java.util.Set;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.jobs.service.AdViewService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class MockAdViewServiceImpl implements AdViewService {


	@Override
	public Result<Set<String>> getAdviews(@RequestParam("adId") String adId) {
		return null;
	}
}
