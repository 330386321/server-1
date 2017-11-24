package com.lawu.eshop.ad.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.srv.bo.AdRateSettingBO;
import com.lawu.eshop.ad.srv.domain.AdRateSettingDO;
import com.lawu.eshop.ad.srv.mapper.AdRateSettingDOMapper;
import com.lawu.eshop.ad.srv.service.AdRateSettingService;

@Service
public class AdRateSettingServiceImpl implements AdRateSettingService {
	
	@Autowired
	private AdRateSettingDOMapper adRateSettingDOMapper;

	@Override
	public List<AdRateSettingBO> queryAdRateSetting() {
		List<AdRateSettingDO> list = adRateSettingDOMapper.selectByExample(null);
		
		List<AdRateSettingBO>  listSetting = new ArrayList<>();
		for (AdRateSettingDO adRateSettingDO : list) {
			AdRateSettingBO adRateSetting = new AdRateSettingBO();
			adRateSetting.setGameTime(adRateSettingDO.getGameTime());
			adRateSetting.setRate(adRateSettingDO.getRate());
			adRateSetting.setId(adRateSettingDO.getId());
			listSetting.add(adRateSetting);
		}
		return listSetting;
	}

	@Override
	public void saveRateSetting(int gameTime, int rate) {
		
		AdRateSettingDO record = new AdRateSettingDO();
		record.setGameTime(gameTime);
		record.setGmtCreate(new Date());
		record.setRate(rate);
		adRateSettingDOMapper.insertSelective(record);
		
	}

	@Override
	public void deleteRateSetting(Long id) {
		
		adRateSettingDOMapper.deleteByPrimaryKey(id);
		
	}

}
