package com.lawu.eshop.member.api.mock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.dto.AdRateSettingDTO;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.AdRateSettingService;

@Service
public class MockAdRateSettingService extends BaseController implements AdRateSettingService {

    @Override
    public Result<List<AdRateSettingDTO>> queryAdRateSetting() {
        return null;
    }
}
