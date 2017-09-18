package com.lawu.eshop.mall.srv.service;

import com.lawu.eshop.mall.srv.bo.AppVersionBO;

public interface AppVersionService {

	AppVersionBO getVersion(int appType);
	
}
