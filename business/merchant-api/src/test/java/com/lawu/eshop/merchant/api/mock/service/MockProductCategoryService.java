package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.merchant.api.service.ProductCategoryService;
import com.lawu.eshop.product.dto.ProductCategoryDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockProductCategoryService extends BaseController implements ProductCategoryService {
    @Override
    public List<ProductCategoryDTO> findAll() {
        return null;
    }

    @Override
    public ProductCategoryDTO getById(@RequestParam("id") Integer id) {
        return null;
    }
}
