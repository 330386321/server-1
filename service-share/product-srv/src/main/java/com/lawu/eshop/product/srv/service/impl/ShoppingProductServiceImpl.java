package com.lawu.eshop.product.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.param.ListShoppingProductParam;
import com.lawu.eshop.product.srv.bo.ProductSearchBO;
import com.lawu.eshop.product.srv.converter.ProductConverter;
import com.lawu.eshop.product.srv.domain.ProductDO;
import com.lawu.eshop.product.srv.domain.ProductDOExample;
import com.lawu.eshop.product.srv.mapper.ProductDOMapper;
import com.lawu.eshop.product.srv.service.ShoppingProductService;
import org.apache.ibatis.session.RowBounds;
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

    @Override
    public Page<ProductSearchBO> listHotProduct(ListShoppingProductParam listShoppingProductParam) {
        ProductDOExample productDOExample = new ProductDOExample();
        productDOExample.setOrderByClause("total_sales_volume desc");

        RowBounds rowBounds = new RowBounds(listShoppingProductParam.getOffset(), listShoppingProductParam.getPageSize());
        Page<ProductSearchBO> page = new Page<>();
        page.setTotalCount(productDOMapper.countByExample(productDOExample));
        page.setCurrentPage(listShoppingProductParam.getCurrentPage());

        List<ProductDO> productDOS = productDOMapper.selectByExampleWithRowbounds(productDOExample, rowBounds);
        page.setRecords(ProductConverter.convertBO(productDOS));
        return page;
    }

    @Override
    public Page<ProductSearchBO> listAllProduct(ListShoppingProductParam listShoppingProductParam) {
        ProductDOExample productDOExample = new ProductDOExample();

        RowBounds rowBounds = new RowBounds(listShoppingProductParam.getOffset(), listShoppingProductParam.getPageSize());
        Page<ProductSearchBO> page = new Page<>();
        page.setTotalCount(productDOMapper.countByExample(productDOExample));
        page.setCurrentPage(listShoppingProductParam.getCurrentPage());

        List<ProductDO> productDOS = productDOMapper.selectByExampleWithRowbounds(productDOExample, rowBounds);
        page.setRecords(ProductConverter.convertBO(productDOS));
        return page;
    }

    @Override
    public Page<ProductSearchBO> listNewProduct(ListShoppingProductParam listShoppingProductParam) {
        ProductDOExample productDOExample = new ProductDOExample();
        productDOExample.setOrderByClause("gmt_create desc");

        RowBounds rowBounds = new RowBounds(listShoppingProductParam.getOffset(), listShoppingProductParam.getPageSize());
        Page<ProductSearchBO> page = new Page<>();
        page.setTotalCount(productDOMapper.countByExample(productDOExample));
        page.setCurrentPage(listShoppingProductParam.getCurrentPage());

        List<ProductDO> productDOS = productDOMapper.selectByExampleWithRowbounds(productDOExample, rowBounds);
        page.setRecords(ProductConverter.convertBO(productDOS));
        return page;
    }
}
