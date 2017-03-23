package com.lawu.eshop.product.srv.converter;

import com.lawu.eshop.product.dto.ProductCategoryDTO;
import com.lawu.eshop.product.srv.bo.ProductCategoryBO;
import com.lawu.eshop.product.srv.domain.ProductCategoryeDO;

/**
 *
 * 会员信息转换器
 *
 * @author Leach
 * @date 2017/3/13
 */
public class ProductCategoryConverter {

    /**
     * BO转换
     *
     * @param productCategoryeDO
     * @return
     */
    public static ProductCategoryBO convertBO(ProductCategoryeDO productCategoryeDO) {
        if (productCategoryeDO == null) {
            return null;
        }

        ProductCategoryBO productCategoryBO = new ProductCategoryBO();
        productCategoryBO.setId(productCategoryeDO.getId());
        productCategoryBO.setName(productCategoryeDO.getName());
        productCategoryBO.setLevel(Utils.byteToInt(productCategoryeDO.getLevel()));
        productCategoryBO.setParentId(productCategoryeDO.getParentId());
        productCategoryBO.setPath(productCategoryeDO.getPath());
        productCategoryBO.setType(Utils.byteToInt(productCategoryeDO.getType()));
        return productCategoryBO;
    }

    /**
     * DTO转换
     *
     * @param productCategoryBO
     * @return
     */
    public static ProductCategoryDTO convertDTO(ProductCategoryBO productCategoryBO) {
        if (productCategoryBO == null) {
            return null;
        }

        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
        productCategoryDTO.setId(productCategoryBO.getId());
        productCategoryDTO.setName(productCategoryBO.getName());
        productCategoryDTO.setType(productCategoryBO.getType());
        productCategoryDTO.setPath(productCategoryBO.getPath());
        productCategoryDTO.setParentId(productCategoryBO.getParentId());
        productCategoryDTO.setLevel(productCategoryBO.getLevel());
        return productCategoryDTO;
    }
}
