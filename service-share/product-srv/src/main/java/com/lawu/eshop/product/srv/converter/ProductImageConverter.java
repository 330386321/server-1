package com.lawu.eshop.product.srv.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.lawu.eshop.product.dto.ProductImageDTO;
import com.lawu.eshop.product.srv.bo.ProductImageBO;
import com.lawu.eshop.product.srv.domain.ProductDO;

/**
 * 
 * <p>
 * Description: 商家图片信息转换器
 * </p>
 * @author Yangqh
 * @date 2017年3月27日 下午7:57:25
 *
 */
public class ProductImageConverter {

    /**
     * BO转换
     * 
     * @param productDO
     * @return
     */
	public static ProductImageBO convert(ProductDO productDO) {
		if (productDO == null || productDO.getId() == null) {
			return null;
		}
		
		ProductImageBO productImageBO = new ProductImageBO();
		BeanUtils.copyProperties(productDO, productImageBO);
		
		return productImageBO;
	}
	
	/**
	 * BOS转换
	 * 
	 * @param productDOS
	 * @return
	 */
	public static List<ProductImageBO> convertBOS(List<ProductDO> productDOS) {
		if (productDOS == null || productDOS.isEmpty()) {
			return null;
		}
		
		List<ProductImageBO> productImageBOS = new ArrayList<ProductImageBO>();
		for (ProductDO productDO : productDOS) {
			productImageBOS.add(convert(productDO));
		}
		
		return productImageBOS;
	}
	
	/**
	 * DTO转换
	 * 
	 * @param productImageBO
	 * @return
	 */
	public static ProductImageDTO convert(ProductImageBO productImageBO) {
		if (productImageBO == null) {
			return null;
		}
		
		ProductImageDTO productImageDTO = new ProductImageDTO();
		BeanUtils.copyProperties(productImageBO, productImageDTO);
		return productImageDTO;
	}
	
	/**
	 * DTOS转换
	 * 
	 * @param productImageBOS
	 * @return
	 */
	public static List<ProductImageDTO> convertDTOS(List<ProductImageBO> productImageBOS) {
		if (productImageBOS == null || productImageBOS.isEmpty()) {
			return null;
		}
		
		List<ProductImageDTO> productImageDTOS = new ArrayList<ProductImageDTO>();
		for (ProductImageBO productImageBO : productImageBOS) {
			productImageDTOS.add(convert(productImageBO));
		}
		
		return productImageDTOS;
	}
	
}
