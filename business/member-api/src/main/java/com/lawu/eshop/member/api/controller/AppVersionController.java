package com.lawu.eshop.member.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.util.HeaderUtil;
import com.lawu.eshop.mall.dto.AppVersionDTO;
import com.lawu.eshop.member.api.MemberApiConfig;
import com.lawu.eshop.member.api.service.AppVersionService;

@RestController
@RequestMapping(value = "appVersion/")
public class AppVersionController extends BaseController{

	@Autowired
	private AppVersionService appVersionService;
	
	@Autowired
	private MemberApiConfig memberApiConfig;
	
	@RequestMapping(value = "getVersion", method = RequestMethod.GET)
	Result<AppVersionDTO> getVersion() {
		Result<AppVersionDTO> result = appVersionService.getAppVersion(1);
		String downUrl = memberApiConfig.getDownloadUrl();
		String channel = HeaderUtil.getRequestChannel(getRequest());
		downUrl = downUrl.replace("{channel}", channel);
		if(result.getModel() != null && result.getModel().getAppVersion() != null)
		downUrl = downUrl.replace("{version}", result.getModel().getAppVersion());
		result.getModel().setDownloadUrl(downUrl);
		return result;
	}
	
}
