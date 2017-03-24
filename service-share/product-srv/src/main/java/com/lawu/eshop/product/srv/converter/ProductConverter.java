package com.lawu.eshop.product.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.product.dto.ProductDTO;
import com.lawu.eshop.product.srv.bo.ProductBO;
import com.lawu.eshop.product.srv.domain.ProductDO;
import com.lawu.eshop.utils.DateUtil;

/**
 *
 * 会员信息转换器
 *
 * @author Yangqh
 * @date 2017/3/13
 */
public class ProductConverter {

    /**
     * BO转换
     *
     * @param productCategoryeDO
     * @return
     */
    public static List<ProductDTO> convertDTOS(List<ProductBO> productBOS) {
        if (productBOS == null) {
            return null;
        }
        
        List<ProductDTO> productDTOS = new ArrayList<ProductDTO>();
        for(ProductBO productBO : productBOS){
        	ProductDTO productDTO = new ProductDTO();
            productDTO.setId(productBO.getId());
            productDTO.setName(productBO.getName());
            productDTO.setCategory(productBO.getCategory());
            productDTO.setFeatureImage(productBO.getFeatureImage());
            productDTO.setGmtCreate(productBO.getGmtCreate());
            productDTO.setImagesUrl(productBO.getImagesUrl());
            productDTO.setSpec(productBO.getSpec());
            productDTO.setStatus(productBO.getStatus());   
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    /**
     * BO转换
     * @param productDO
     * @return
     */
	public static ProductBO convertBO(ProductDO productDO) {
		ProductBO productBO = new ProductBO();
		productBO.setId(productDO.getId());
		productBO.setName(productDO.getName());
		productBO.setFeatureImage(productDO.getFeatureImage());
		productBO.setGmtCreate(DateUtil.getDateFormat(productDO.getGmtCreate(), "yyyy-MM-dd HH:mm:ss"));
		productBO.setStatus(Utils.byteToInt(productDO.getStatus())); 
		return productBO;
	}

    
}
