package com.lawu.eshop.product.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.param.ListShoppingProductParam;
import com.lawu.eshop.product.srv.bo.ProductSearchBO;
import com.lawu.eshop.product.srv.converter.ProductConverter;
import com.lawu.eshop.product.srv.domain.extend.ShoppingProductDOView;
import com.lawu.eshop.product.srv.mapper.ProductDOMapper;
import com.lawu.eshop.product.srv.mapper.extend.ProductDOMapperExtend;
import com.lawu.eshop.product.srv.service.ShoppingProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/21.
 */
@Service
public class ShoppingProductServiceImpl implements ShoppingProductService {

    @Autowired
    private ProductDOMapper productDOMapper;

    @Autowired
    private ProductDOMapperExtend productDOMapperExtend;

    @Override
    public Page<ProductSearchBO> listHotProduct(ListShoppingProductParam listShoppingProductParam) {
        List<ShoppingProductDOView> productDOViews = productDOMapperExtend.listHotProduct(listShoppingProductParam);
        Page<ProductSearchBO> page = new Page<>();
        page.setCurrentPage(listShoppingProductParam.getCurrentPage());
        page.setTotalCount(0);
        page.setRecords(ProductConverter.convertSearchBOS(productDOViews));
        return page;
    }

    @Override
    public Page<ProductSearchBO> listAllProduct(ListShoppingProductParam listShoppingProductParam) {
        List<ShoppingProductDOView> productDOViews = productDOMapperExtend.listAllProduct(listShoppingProductParam);
        Page<ProductSearchBO> page = new Page<>();
        page.setCurrentPage(listShoppingProductParam.getCurrentPage());
        page.setTotalCount(0);
        page.setRecords(ProductConverter.convertSearchBOS(productDOViews));
        return page;
    }

    @Override
    public Page<ProductSearchBO> listNewProduct(ListShoppingProductParam listShoppingProductParam) {
        List<ShoppingProductDOView> productDOViews = productDOMapperExtend.listNewProduct(listShoppingProductParam);
        Page<ProductSearchBO> page = new Page<>();
        page.setCurrentPage(listShoppingProductParam.getCurrentPage());
        page.setTotalCount(0);
        page.setRecords(ProductConverter.convertSearchBOS(productDOViews));
        return page;
    }
}
