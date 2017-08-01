package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.ProductCategoryService;
import com.lawu.eshop.product.dto.ProductCategoryDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Service
class MockProductCategoryService implements ProductCategoryService {

    @Override
    public List<ProductCategoryDTO> findAll() {
        List<ProductCategoryDTO> list = new ArrayList<>();
        return list;
    }

    @Override
    public ProductCategoryDTO getById(@RequestParam("id") Integer id) {
        ProductCategoryDTO dto = new ProductCategoryDTO();
        return dto;
    }

    @Override
    public Result<List<ProductCategoryDTO>> find(@PathVariable("parentId") Integer parentId) {
        return null;
    }
}
