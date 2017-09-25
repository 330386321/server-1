package com.lawu.eshop.merchant.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.framework.web.util.HeaderUtil;
import com.lawu.eshop.mall.constants.AppTypeEnum;
import com.lawu.eshop.mall.constants.MobileTypeEnum;
import com.lawu.eshop.mall.dto.AppVersionDTO;
import com.lawu.eshop.merchant.api.MerchantApiConfig;
import com.lawu.eshop.merchant.api.service.AppVersionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping(value = "appVersion/")
public class AppVersionController extends BaseController{

	@Autowired
	private AppVersionService appVersionService;
	
	@Autowired
	private MerchantApiConfig memberApiConfig;

	@Audit(date = "2017-09-14", reviewer = "孙林青")
	@ApiOperation(value = "app版本获取", notes = "app版本获取（张荣成）", httpMethod = "GET")
	@RequestMapping(value = "getVersion", method = RequestMethod.GET)
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	Result<AppVersionDTO> getVersion() {
		byte platform = HeaderUtil.getRequestPlatform(getRequest());
		Result<AppVersionDTO> result = appVersionService.getAppVersion(AppTypeEnum.MERCHANT.val,platform);
		String downUrl ="";
		if(platform==MobileTypeEnum.Android.val){
			downUrl = memberApiConfig.getDownloadUrl();
			String channel = HeaderUtil.getRequestChannel(getRequest());
			downUrl = downUrl.replace("{channel}", channel);
			if(result.getModel() != null && result.getModel().getAppVersion() != null)
			downUrl = downUrl.replace("{version}", result.getModel().getAppVersion());
		}
		result.getModel().setDownloadUrl(downUrl);
		return result;
	}
	
}
