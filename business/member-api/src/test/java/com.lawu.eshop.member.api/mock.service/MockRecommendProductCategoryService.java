package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.RecommendProductCategoryService;
import com.lawu.eshop.product.dto.RecommendProductCategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
class MockRecommendProductCategoryService implements RecommendProductCategoryService {


    @Override
    public Result<List<RecommendProductCategoryDTO>> listAllRecommendProductCategory() {
        return null;
    }
}
