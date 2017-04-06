package com.lawu.eshop.product.srv.converter;

import com.lawu.eshop.product.dto.ProductRecommendDTO;
import com.lawu.eshop.product.srv.bo.ProductRecommendBO;
import com.lawu.eshop.product.srv.domain.extend.ProductDOView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
public class ProductRecommendConverter {

    /**
     * BO转换
     *
     * @param productDOViewList
     * @return
     */
    public static List<ProductRecommendBO> convertBO(List<ProductDOView> productDOViewList) {
        if (productDOViewList.isEmpty()) {
            return null;
        }

        List<ProductRecommendBO> productRecommendBOS = new ArrayList<ProductRecommendBO>(productDOViewList.size());
        for (ProductDOView productDOView : productDOViewList) {
            ProductRecommendBO productRecommendBO = new ProductRecommendBO();
            productRecommendBO.setContent(productDOView.getContent());
            productRecommendBO.setFeatureImage(productDOView.getFeatureImage());
            productRecommendBO.setName(productDOView.getName());
            productRecommendBO.setOriginalPrice(productDOView.getOriginalPrice());
            productRecommendBO.setPrice(productDOView.getPrice());
            productRecommendBOS.add(productRecommendBO);
        }
        return productRecommendBOS;
    }

    /**
     * DTO转换
     *
     * @param productRecommendBOList
     * @return
     */
    public static List<ProductRecommendDTO> convertDTO(List<ProductRecommendBO> productRecommendBOList) {
        if (productRecommendBOList.isEmpty()) {
            return null;
        }

        List<ProductRecommendDTO> productRecommendDTOS = new ArrayList<ProductRecommendDTO>(productRecommendBOList.size());
        for (ProductRecommendBO productRecommendBO : productRecommendBOList) {
            ProductRecommendDTO productRecommendDTO = new ProductRecommendDTO();
            productRecommendDTO.setPrice(productRecommendBO.getPrice());
            productRecommendDTO.setOriginalPrice(productRecommendBO.getOriginalPrice());
            productRecommendDTO.setName(productRecommendBO.getName());
            productRecommendDTO.setFeatureImage(productRecommendBO.getFeatureImage());
            productRecommendDTO.setContent(productRecommendBO.getContent());
            productRecommendDTOS.add(productRecommendDTO);
        }
        return productRecommendDTOS;
    }
}
