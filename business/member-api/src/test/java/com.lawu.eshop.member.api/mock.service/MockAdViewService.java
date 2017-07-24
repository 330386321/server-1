package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.AdViewService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Service
public class MockAdViewService implements AdViewService {


	@Override
	public Result setAdView(@RequestParam("adId") String adId, @RequestParam("memberId") String memberId) {
		return null;
	}

	@Override
	public Result<Set<String>> getAdviews(@RequestParam("adId") String adId) {
		return null;
	}
}
