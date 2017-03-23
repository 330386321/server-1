package com.lawu.eshop.product.srv.converter;

import com.lawu.eshop.product.srv.bo.ProductCategoryBO;
import com.lawu.eshop.product.srv.bo.ProductModelBO;
import com.lawu.eshop.product.srv.domain.ProductModelDO;

/**
 *
 * 会员信息转换器
 *
 * @author Leach
 * @date 2017/3/13
 */
public class ProductModelConverter {

    /**
     * BO转换
     *
     * @param productCategoryeDO
     * @return
     */
    public static ProductModelBO convertBO(ProductModelDO productModelDO) {
        if (productModelDO == null) {
            return null;
        }

        ProductModelBO productModelBO = new ProductModelBO();
        productModelBO.setName(productModelDO.getName());
        productModelBO.setOriginalPrice(productModelDO.getOriginalPrice());
        productModelBO.setPrice(productModelDO.getPrice());
        productModelBO.setInventory(productModelDO.getInventory());       
        return productModelBO;
    }

    
}
