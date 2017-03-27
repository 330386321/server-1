package com.lawu.eshop.member.api.service.hystrix;

import org.springframework.stereotype.Component;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.ProductService;
import com.lawu.eshop.product.dto.ProductInfoDTO;

/**
 * @author Leach
 * @date 2017/3/13
 */
@Component
public class ProductServiceHystrix implements ProductService {

	@Override
	public Result<ProductInfoDTO> selectProductById(Long productId) {
		return null;
	}

   
}
