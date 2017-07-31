package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.ShoppingProductService;
import com.lawu.eshop.product.dto.ProductSearchDTO;
import com.lawu.eshop.product.param.ListShoppingProductParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
public class MockShoppingProductService extends BaseController implements ShoppingProductService {

    @Override
    public Result<Page<ProductSearchDTO>> listHotProduct(@ModelAttribute ListShoppingProductParam listShoppingProductParam) {
        return successCreated();
    }

    @Override
    public Result<Page<ProductSearchDTO>> listAllProduct(@ModelAttribute ListShoppingProductParam listShoppingProductParam) {
        return successCreated();
    }

    @Override
    public Result<Page<ProductSearchDTO>> listNewProduct(@ModelAttribute ListShoppingProductParam listShoppingProductParam) {
        return null;
    }
}
