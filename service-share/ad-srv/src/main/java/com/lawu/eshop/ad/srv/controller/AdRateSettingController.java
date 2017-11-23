package com.lawu.eshop.ad.srv.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.dto.AdRateSettingDTO;
import com.lawu.eshop.ad.srv.bo.AdRateSettingBO;
import com.lawu.eshop.ad.srv.service.AdRateSettingService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;

/**
 * 咻一咻中奖率配置接口
 * 
 * @author zhangrc
 * @date 2017/11/23
 *
 */
@RestController
@RequestMapping(value = "adRateSetting/")
public class AdRateSettingController extends BaseController{
	
	@Autowired
	private AdRateSettingService adRateSettingService;
	
	
	/**
	 * 咻一咻中奖率配置数据
	 * @return
	 */
	@RequestMapping(value = "queryAdRateSetting", method = RequestMethod.GET)
	public Result<List<AdRateSettingDTO>> queryAdRateSetting() {

		List<AdRateSettingBO> list = adRateSettingService.queryAdRateSetting();

		List<AdRateSettingDTO> rateList = new ArrayList<>();
		for (AdRateSettingBO adRateSettingBO : list) {
			AdRateSettingDTO adRateSetting = new AdRateSettingDTO();
			adRateSetting.setGameTime(adRateSettingBO.getGameTime());
			adRateSetting.setRate(adRateSettingBO.getRate());
			rateList.add(adRateSetting);
		}

		return successGet(rateList);

	}

}
