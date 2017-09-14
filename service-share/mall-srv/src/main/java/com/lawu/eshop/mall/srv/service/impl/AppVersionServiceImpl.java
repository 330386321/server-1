package com.lawu.eshop.mall.srv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.mall.srv.bo.AppVersionBO;
import com.lawu.eshop.mall.srv.domain.AppVersionDO;
import com.lawu.eshop.mall.srv.domain.AppVersionDOExample;
import com.lawu.eshop.mall.srv.mapper.AppVersionDOMapper;
import com.lawu.eshop.mall.srv.service.AppVersionService;

@Service
public class AppVersionServiceImpl implements AppVersionService{

	@Autowired
	private AppVersionDOMapper appVersionDOMapper;
	
	@Override
	public AppVersionBO getVersion(int appType) {
		AppVersionDOExample example = new AppVersionDOExample();
		example.createCriteria().andStatusEqualTo((byte)1).andAppTypeEqualTo((byte)appType);
		List<AppVersionDO> list = appVersionDOMapper.selectByExample(example);
		AppVersionBO bo = new AppVersionBO();
		if(list != null && !list.isEmpty()) {
			AppVersionDO DO = list.get(0);
			bo.setAppBigVersion(DO.getAppBigVersion());
			bo.setAppVersion(DO.getAppVersion());
			bo.setStatus(DO.getStatus());
			bo.setUpdateDesc(DO.getUpdateDesc());
			bo.setIsForce(DO.getIsForce());
		}
		return bo;
	}
}
