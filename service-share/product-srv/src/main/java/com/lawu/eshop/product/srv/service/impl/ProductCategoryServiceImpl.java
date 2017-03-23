package com.lawu.eshop.product.srv.service.impl;

import com.lawu.eshop.product.srv.bo.ProductCategoryBO;
import com.lawu.eshop.product.srv.converter.ProductCategoryConverter;
import com.lawu.eshop.product.srv.domain.ProductCategoryeDO;
import com.lawu.eshop.product.srv.domain.ProductCategoryeDOExample;
import com.lawu.eshop.product.srv.mapper.ProductCategoryeDOMapper;
import com.lawu.eshop.product.srv.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shoubao-016 on 2017/3/22.
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryeDOMapper productCategoryeDOMapper;

    @Override
    public List<ProductCategoryBO> findAll() {
        ProductCategoryeDOExample example = new ProductCategoryeDOExample();
        List<ProductCategoryeDO>  productCategoryeDOS = productCategoryeDOMapper.selectByExample(example);

        return ProductCategoryConverter.convertBOS(productCategoryeDOS);
    }

	@Override
	public ProductCategoryBO getById(Integer id) {
		ProductCategoryeDOExample example = new ProductCategoryeDOExample();
        example.createCriteria().andIdEqualTo(id);
        List<ProductCategoryeDO>  productCategoryeDOS = productCategoryeDOMapper.selectByExample(example);
        return productCategoryeDOS.isEmpty() ? null :  ProductCategoryConverter.convertBO(productCategoryeDOS.get(0));
	}

    

}
