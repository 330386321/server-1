package com.lawu.eshop.product.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.param.ListShoppingProductParam;
import com.lawu.eshop.product.srv.bo.ProductSearchBO;
import com.lawu.eshop.product.srv.converter.ProductConverter;
import com.lawu.eshop.product.srv.domain.ProductDO;
import com.lawu.eshop.product.srv.domain.ProductDOExample;
import com.lawu.eshop.product.srv.domain.extend.ShoppingProductDOView;
import com.lawu.eshop.product.srv.mapper.ProductDOMapper;
import com.lawu.eshop.product.srv.mapper.extend.ProductDOMapperExtend;
import com.lawu.eshop.product.srv.service.ShoppingProductService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if (productDOViews == null || productDOViews.isEmpty()) {
            page.setRecords(new ArrayList<>());
            return page;
        } else {
            List<ProductSearchBO> productSearchBOS = new ArrayList<>();
            ProductSearchBO productSearchBO = null;
            for (ShoppingProductDOView shoppingProductDOView : productDOViews) {
                productSearchBO = new ProductSearchBO();
                productSearchBO.setName(shoppingProductDOView.getName());
                productSearchBO.setContent(shoppingProductDOView.getContent());
                productSearchBO.setFeatureImage(shoppingProductDOView.getFeatureImage());
                productSearchBO.setOriginalPrice(shoppingProductDOView.getMaxPrice().doubleValue());
                productSearchBO.setProductId(shoppingProductDOView.getProductId());
                productSearchBO.setPrice(shoppingProductDOView.getMinPrice().doubleValue());
                productSearchBO.setSalesVolume(shoppingProductDOView.getTotalSalesVolume());
                productSearchBOS.add(productSearchBO);
            }
            page.setRecords(productSearchBOS);
            return page;
        }
    }

    @Override
    public Page<ProductSearchBO> listAllProduct(ListShoppingProductParam listShoppingProductParam) {
        ProductDOExample productDOExample = new ProductDOExample();
        productDOExample.createCriteria().andMerchantIdEqualTo(listShoppingProductParam.getMerchantId()).andStatusEqualTo(ProductStatusEnum.PRODUCT_STATUS_UP.val);

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
        productDOExample.createCriteria().andMerchantIdEqualTo(listShoppingProductParam.getMerchantId()).andStatusEqualTo(ProductStatusEnum.PRODUCT_STATUS_UP.val);
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
