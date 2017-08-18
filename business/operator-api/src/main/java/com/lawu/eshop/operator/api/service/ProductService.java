package com.lawu.eshop.operator.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.product.dto.ProductInfoDTO;
import com.lawu.eshop.product.dto.ProductPlatDTO;
import com.lawu.eshop.product.param.ListProductParam;
import com.lawu.eshop.product.param.ProductParam;

/**
 * 广告查收所有的商品
 *
 * @author zhangrc
 * @date 2017/4/21
 */
@FeignClient(value = "product-srv")
public interface ProductService {


    /**
     * 查询所有上架的商品
     *
     * @param param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "product/selectProductByPlat")
    Result<List<ProductPlatDTO>> selectProductByPlat(@ModelAttribute ProductParam param);

    /**
     * 查询所有上架的商品
     *
     * @param listProductParam
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "product/listProduct")
    Result<List<ProductInfoDTO>> listProduct(@ModelAttribute ListProductParam listProductParam);

    /**
     * 重建商品索引
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "product/rebuildProductIndex")
    Result rebuildProductIndex();

    /**
     * 删除无效商品索引
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "product/delInvalidProductIndex")
    Result delInvalidProductIndex();

    @RequestMapping(method = RequestMethod.PUT, value = "product/soldOutProductByMerchantId")
    Result soldOutProductByMerchantId(@RequestParam(value = "id") Long id);
}
