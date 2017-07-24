package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.ProductSolrService;
import com.lawu.eshop.product.dto.ProductSearchDTO;
import com.lawu.eshop.product.dto.ProductSearchWordDTO;
import com.lawu.eshop.product.param.ProductSearchParam;
import com.lawu.eshop.product.param.ProductSearchRealParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class MockProductSolrService implements ProductSolrService {

    @Override
    public Result<Page<ProductSearchDTO>> listProductByCategoryId(@ModelAttribute ProductSearchRealParam param) {
        return null;
    }

    @Override
    public Result<Page<ProductSearchDTO>> listRecommendProduct(@ModelAttribute ProductSearchRealParam param) {
        return null;
    }

    @Override
    public Result<Page<ProductSearchDTO>> listYouLikeProduct(@ModelAttribute ProductSearchParam productSearchParam) {
        return null;
    }

    @Override
    public Result<Page<ProductSearchDTO>> listProductByName(@ModelAttribute ProductSearchRealParam param) {
        return null;
    }

    @Override
    public Result<List<ProductSearchWordDTO>> listProductSearchWord(@RequestParam("name") String name) {
        return null;
    }

    @Override
    public List<ProductSearchDTO> findProductSearchList(@RequestBody ProductSearchParam searchParam) {
        return null;
    }
}
