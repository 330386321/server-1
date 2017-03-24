package com.lawu.eshop.merchant.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.merchant.api.service.hystrix.ProductServiceHystrix;
import com.lawu.eshop.product.dto.ProductDTO;
import com.lawu.eshop.product.query.ProductQuery;

/**
 * 产品服务接口
 *
 * @author Yangqh
 * @date 2017/3/22
 */
@FeignClient(value= "product-srv", fallback = ProductServiceHystrix.class)
public interface ProductService {

    /**
     * 查询所有商品类型
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "product/getProductList")
    List<ProductDTO> getProductList(@RequestBody ProductQuery query);
    
}
