package com.lawu.eshop.merchant.api.service;

import com.lawu.eshop.merchant.api.service.hystrix.ProductCategoryServiceHystrix;
import com.lawu.eshop.product.dto.ProductCategoryDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 产品服务接口
 *
 * @author Leach
 * @date 2017/3/22
 */
@FeignClient(value= "product-srv", fallback = ProductCategoryServiceHystrix.class)
public interface ProductCategoryService {

    /**
     * 查询所有商品类型
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "productCategory/findAll")
    List<ProductCategoryDTO> findAll();
}
