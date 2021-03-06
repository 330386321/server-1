package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.AdViewService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Service
public class MockAdViewService extends BaseController implements AdViewService {


	@Override
	public Result setAdView(@RequestParam("adId") String adId, @RequestParam("memberId") String memberId) {
		return successCreated();
	}

	@Override
	public Result<Set<String>> getAdviews(@RequestParam("adId") String adId) {
		Set<String> set = new HashSet<>();
		set.add("1");
		return successCreated(set);
	}
}
