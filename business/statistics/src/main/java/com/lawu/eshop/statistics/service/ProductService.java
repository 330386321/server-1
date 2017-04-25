package com.lawu.eshop.statistics.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.product.dto.ProductInfoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/25.
 */
@FeignClient(value = "product-srv")
public interface ProductService {

    /**
     * 查询所有上架的商品
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "product/listProduct")
    Result<List<ProductInfoDTO>> listProduct();

    /**
     * 更新商品日均销量
     *
     * @param id
     * @param averageDailySales
     */
    @RequestMapping(method = RequestMethod.PUT, value = "product/updateAverageDailySales/{id}")
    void updateAverageDailySalesById(@PathVariable("id") Long id, @RequestParam("averageDailySales") BigDecimal averageDailySales);
}
