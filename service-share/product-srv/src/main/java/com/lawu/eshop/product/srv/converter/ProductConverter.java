package com.lawu.eshop.product.srv.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.ProductInfoDTO;
import com.lawu.eshop.product.dto.ProductQueryDTO;
import com.lawu.eshop.product.param.EditProductParam;
import com.lawu.eshop.product.srv.bo.ProductInfoBO;
import com.lawu.eshop.product.srv.bo.ProductQueryBO;
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
    public static List<ProductQueryDTO> convertDTOS(List<ProductQueryBO> productBOS) {
        if (productBOS == null) {
            return null;
        }
        
        List<ProductQueryDTO> productDTOS = new ArrayList<ProductQueryDTO>();
        for(ProductQueryBO productBO : productBOS){
        	ProductQueryDTO productDTO = new ProductQueryDTO();
            productDTO.setId(productBO.getId());
            productDTO.setName(productBO.getName());
            productDTO.setCategory(productBO.getCategory());
            productDTO.setFeatureImage(productBO.getFeatureImage());
            productDTO.setGmtCreate(productBO.getGmtCreate());
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
	public static ProductQueryBO convertQueryBO(ProductDO productDO) {
		ProductQueryBO productBO = new ProductQueryBO();
		productBO.setId(productDO.getId());
		productBO.setName(productDO.getName());
		productBO.setFeatureImage(productDO.getFeatureImage());
		productBO.setGmtCreate(DateUtil.getDateFormat(productDO.getGmtCreate(), "yyyy-MM-dd HH:mm:ss"));
		productBO.setStatus(ProductStatusEnum.getEnum(productDO.getStatus())); 
		return productBO;
	}

	public static ProductQueryDTO convertDTO(ProductQueryBO productBO) {
		ProductQueryDTO productDTO = new ProductQueryDTO();
        productDTO.setId(productBO.getId());
        productDTO.setName(productBO.getName());
        productDTO.setCategory(productBO.getCategory());
        productDTO.setFeatureImage(productBO.getFeatureImage());
        productDTO.setGmtCreate(productBO.getGmtCreate());
        productDTO.setSpec(productBO.getSpec());
        productDTO.setStatus(productBO.getStatus());   
		return productDTO;
	}

	/**
	 * 用户端商品详情BO转换
	 * @param productDO
	 * @return
	 */
	public static ProductInfoBO convertInfoBO(ProductDO productDO) {
		ProductInfoBO productInfoBO = new ProductInfoBO();
		productInfoBO.setId(productDO.getId());
		productInfoBO.setName(productDO.getName());
		productInfoBO.setFeatureImage(productDO.getFeatureImage());
		productInfoBO.setContent(productDO.getContent());
		productInfoBO.setMerchantId(productDO.getMerchantId());
		return productInfoBO;
	}

	/**
	 * 用户端商品详情DTO转换
	 * @param productBO
	 * @return
	 */
	public static ProductInfoDTO convertInfoDTO(ProductInfoBO productBO) {
		ProductInfoDTO productInfoDTO = new ProductInfoDTO();
		productInfoDTO.setId(productBO.getId());
		productInfoDTO.setMerchantId(productBO.getMerchantId());
		productInfoDTO.setName(productBO.getName());
		productInfoDTO.setFeatureImage(productBO.getFeatureImage());
		productInfoDTO.setContent(productBO.getContent());
		productInfoDTO.setImagesUrl(productBO.getImagesUrl());
		productInfoDTO.setSpec(productBO.getSpec());
		productInfoDTO.setTotalSales(productBO.getTotalSales());
		productInfoDTO.setPriceMax(productBO.getPriceMax());
		productInfoDTO.setPriceMin(productBO.getPriceMin());
		return productInfoDTO;
	}

	/**
	 * Param转DO
	 * @param product
	 * @return
	 */
	public static ProductDO convertDO(EditProductParam param) {
		ProductDO productDO = new ProductDO();
		productDO.setName(param.getName());
		productDO.setCategoryId(param.getCategoryId());
		productDO.setMerchantId(param.getMerchantId());
		productDO.setName(param.getNum());
		productDO.setContent(param.getContent());
		productDO.setStatus(ProductStatusEnum.PRODUCT_STATUS_UP.val);
		productDO.setGmtCreate(new Date());
		productDO.setGmtModified(new Date());
		return productDO;
	}

    
}
