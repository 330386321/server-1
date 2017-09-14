package com.lawu.eshop.mall.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.AppVersionDTO;
import com.lawu.eshop.mall.srv.bo.AppVersionBO;
import com.lawu.eshop.mall.srv.service.AppVersionService;

@RestController
@RequestMapping(value = "appVersion")
public class AppVersionController extends BaseController{

	
	@Autowired
    private AppVersionService appVersionService;

    @RequestMapping(value = "getAppVersion/{appType}", method = RequestMethod.GET)
    public Result<AppVersionDTO> getAppVersion(@PathVariable("appType") Integer appType) {
    	AppVersionBO bo = appVersionService.getVersion(appType);
    	AppVersionDTO dto = new AppVersionDTO();
    	if(bo != null) {
    		dto.setAppBigVersion(bo.getAppBigVersion());
    		dto.setAppVersion(bo.getAppVersion());
    		dto.setIsForce(bo.getIsForce());
    		dto.setUpdateDesc(bo.getUpdateDesc());
    	}
        return successCreated(dto);
    }
}
