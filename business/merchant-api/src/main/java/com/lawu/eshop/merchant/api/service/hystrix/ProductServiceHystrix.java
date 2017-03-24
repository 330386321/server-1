package com.lawu.eshop.merchant.api.service.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.lawu.eshop.merchant.api.service.ProductService;
import com.lawu.eshop.product.dto.ProductDTO;
import com.lawu.eshop.product.query.ProductQuery;

/**
 * @author Leach
 * @date 2017/3/13
 */
@Component
public class ProductServiceHystrix implements ProductService {

	@Override
	public List<ProductDTO> getProductList(ProductQuery query) {
//		ProductDTO dto = new ProductDTO();
//		dto.setId(-1L);
		
		return null;
	}

    
}
