package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.product.dto.ProductInfoDTO;

/**
 * 
 * <p>
 * Description: 商品
 * </p>
 * @author Yangqh
 * @date 2017年3月27日 下午2:42:22
 *
 */
@FeignClient(value = "product-srv")
public interface ProductService {

    /**
     * 根据商品ID查询商品详情信息
     * @param id 商品ID
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "product/selectProductById")
    Result<ProductInfoDTO> selectProductById(@RequestParam("productId") Long productId);

}
