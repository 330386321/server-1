package com.lawu.eshop.merchant.api.service.hystrix;

import org.springframework.stereotype.Component;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.ProductService;
import com.lawu.eshop.product.dto.ProductDTO;
import com.lawu.eshop.product.query.ProductQuery;

/**
 * @author Yangqh
 * @date 2017/3/13
 */
@Component
public class ProductServiceHystrix implements ProductService {

	@Override
	public Result<Page<ProductDTO>> selectProduct(ProductQuery query) {
		
		return null;
	}

    
}
