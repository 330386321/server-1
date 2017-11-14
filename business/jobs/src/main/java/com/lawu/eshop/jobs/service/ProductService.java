package com.lawu.eshop.jobs.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;

/**
 * @author meishuquan
 * @date 2017/4/25.
 */
@FeignClient(value = "product-srv")
public interface ProductService {

    /**
     * 更新商品日均销量
     *
     * @param pageSize
     */
    @RequestMapping(method = RequestMethod.PUT, value = "product/executeProductAverageDailySales")
    void executeProductAverageDailySales(@RequestParam("pageSize") Integer pageSize);

    /**
     * 重建商品索引
     *
     * @return
     * @author meishuquan
     */
    @RequestMapping(method = RequestMethod.GET, value = "product/rebuildProductIndex")
    Result rebuildProductIndex(@RequestParam("pageSize") Integer pageSize);

    /**
     * 删除无效的商品索引
     *
     * @return
     * @author meishuquan
     */
    @RequestMapping(method = RequestMethod.GET, value = "product/delInvalidProductIndex")
    Result delInvalidProductIndex();

}
