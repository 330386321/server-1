package com.lawu.eshop.cache.srv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.cache.srv.service.AdViewService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;

@RestController
@RequestMapping(value = "adView/")
public class AdViewController extends BaseController{
	
	@Autowired
	private AdViewService adViewService;
	
	
	@RequestMapping(value = "setAdView", method = RequestMethod.GET)
    public Result setAdView(String adId, String memberId) {
		adViewService.setAdView(adId, memberId);
		return successCreated();
    }

    @RequestMapping(value = "getAdviews", method = RequestMethod.GET)
    public Result<List<String>> getAdviews(String adId) {
        return successAccepted(adViewService.getAdviews(adId));
    }

}
