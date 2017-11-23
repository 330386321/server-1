package com.lawu.eshop.ad.srv.service;

import java.util.List;

import com.lawu.eshop.ad.srv.bo.AdRateSettingBO;

/**
 * 咻一咻中奖率配置
 * 
 * @author zhangrc
 * @date 2017/11/23
 *
 */
public interface AdRateSettingService {
	
	/**
	 * 中奖率配置数据
	 * @return
	 */
	List<AdRateSettingBO> queryAdRateSetting();

}
