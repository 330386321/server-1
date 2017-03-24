package com.lawu.eshop.merchant.api.service.hystrix;

import com.lawu.eshop.merchant.api.service.ProductCategoryService;
import com.lawu.eshop.product.dto.ProductCategoryDTO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Yangqh
 * @date 2017/3/13
 */
@Component
public class ProductCategoryServiceHystrix implements ProductCategoryService {

    @Override
    public List<ProductCategoryDTO> findAll() {
//        ProductCategoryDTO dto = new ProductCategoryDTO();
//        dto.setId(-1);
//        List<ProductCategoryDTO> dtos = new ArrayList<ProductCategoryDTO>();
//        dtos.add(dto);
        return null;
    }

	@Override
	public ProductCategoryDTO getById(Integer id) {
		ProductCategoryDTO dto = new ProductCategoryDTO();
        dto.setId(-1);
        return dto;
	}
}
