package com.lawu.eshop.member.api.mock.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.AppVersionDTO;
import com.lawu.eshop.member.api.service.AppVersionService;

@Service
public class MockAppVersionService extends BaseController implements AppVersionService {

    @Override
    public Result<AppVersionDTO> getAppVersion(@PathVariable("appType") byte appType, @RequestParam("mobileType") byte mobileType) {
        return null;
    }
}
