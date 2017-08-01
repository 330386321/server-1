package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.RecommendProductCategoryService;
import com.lawu.eshop.product.dto.RecommendProductCategoryDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
class MockRecommendProductCategoryService extends BaseController implements RecommendProductCategoryService {


    @Override
    public Result<List<RecommendProductCategoryDTO>> listAllRecommendProductCategory() {
        RecommendProductCategoryDTO dto = new RecommendProductCategoryDTO();
        dto.setId(1L);
        dto.setCategoryId(1);
        dto.setCategoryName("name");
        dto.setGmtCreate(new Date());
        List<RecommendProductCategoryDTO> list = new ArrayList<>();
        list.add(dto);
        return successCreated(list);
    }
}
