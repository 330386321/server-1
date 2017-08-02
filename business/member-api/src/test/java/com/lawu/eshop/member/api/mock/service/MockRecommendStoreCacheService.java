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
        return successCreated(new String("fdf"));
    }

    @Override
    public Result<String> getRecommendFoodConsume(@RequestParam("regionPath") String regionPath) {
        return successCreated("{\"address\":\"sdfd\",\"areaName\":\"fdfd\",\"averageConsumeAmount\":2,\"averageScore\":3,\"buyNumbers\":1,\"commentsCount\":1,\"discountPackage\":\"dd\",\"distance\":3,\"favoreInfo\":\"33\",\"industryName\":\"#3\",\"latitude\":33,\"longitude\":343,\"merchantId\":1,\"merchantStoreId\":1,\"name\":\"fdf\",\"regionName\":\"fdfd\",\"storePic\":\"fdfd\"}");
    }

    @Override
    public Result<String> getRecommendFoodComment(@RequestParam("regionPath") String regionPath) {
        return successCreated("{\"latitude\":33,\"longitude\":343}");
    }
}
