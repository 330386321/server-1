package com.lawu.eshop.product.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.product.dto.ProductCategoryDTO;
import com.lawu.eshop.product.srv.bo.ProductCategoryBO;
import com.lawu.eshop.product.srv.domain.ProductCategoryeDO;
import com.lawu.eshop.utils.DataTransUtil;

/**
 *
 * 会员信息转换器
 *
 * @author Yangqh
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
        productCategoryBO.setLevel(DataTransUtil.byteToInt(productCategoryeDO.getLevel()));
        productCategoryBO.setParentId(productCategoryeDO.getParentId());
        productCategoryBO.setPath(productCategoryeDO.getPath());
        productCategoryBO.setType(DataTransUtil.byteToInt(productCategoryeDO.getType()));
        return productCategoryBO;
    }

    /**
     * DO转BO，集合
     * @param productCategoryeDOS
     * @return
     */
    public static List<ProductCategoryBO> convertBOS(List<ProductCategoryeDO>  productCategoryeDOS){
        List<ProductCategoryBO> productCategoryeBOS = new ArrayList<ProductCategoryBO>();
        for(ProductCategoryeDO d : productCategoryeDOS){
            ProductCategoryBO bo = ProductCategoryConverter.convertBO(d);
            productCategoryeBOS.add(bo);
        }
        return productCategoryeBOS;
    }

    /**
     * 转换DTO,集合
     * @param productCategoryBOS
     * @return
     */
    public static List<ProductCategoryDTO> convertDTOS( List<ProductCategoryBO> productCategoryBOS){
        List<ProductCategoryDTO> productCategoryeDTOS = new ArrayList<ProductCategoryDTO>();
        for(ProductCategoryBO bo : productCategoryBOS){
            ProductCategoryDTO dto = ProductCategoryConverter.convertDTO(bo);
            productCategoryeDTOS.add(dto);
        }
        return productCategoryeDTOS;
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
