package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.RecommendStoreCacheService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class MockRecommendStoreCacheService extends BaseController implements RecommendStoreCacheService {

    @Override
    public Result<String> getNewMerchant(@RequestParam("regionPath") String regionPath) {
        return null;
    }

    @Override
    public Result<String> getRecommendFoodConsume(@RequestParam("regionPath") String regionPath) {
        return null;
    }

    @Override
    public Result<String> getRecommendFoodComment(@RequestParam("regionPath") String regionPath) {
        return null;
    }
}
