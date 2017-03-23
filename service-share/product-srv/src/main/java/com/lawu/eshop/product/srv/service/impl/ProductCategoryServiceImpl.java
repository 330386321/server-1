package com.lawu.eshop.product.srv.service.impl;

import com.lawu.eshop.product.srv.bo.ProductCategoryBO;
import com.lawu.eshop.product.srv.converter.ProductCategoryConverter;
import com.lawu.eshop.product.srv.domain.ProductCategoryeDO;
import com.lawu.eshop.product.srv.domain.ProductCategoryeDOExample;
import com.lawu.eshop.product.srv.mapper.ProductCategoryeDOMapper;
import com.lawu.eshop.product.srv.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        List<ProductCategoryBO> productCategoryeBOS = null;
        if(productCategoryeDOS == null || productCategoryeDOS.isEmpty()){
            return null;
        }else{
            productCategoryeBOS = new ArrayList<ProductCategoryBO>();
            for(ProductCategoryeDO d : productCategoryeDOS){
                ProductCategoryBO bo = ProductCategoryConverter.convertBO(d);
                productCategoryeBOS.add(bo);
            }
        }
        return productCategoryeBOS;
    }
}
