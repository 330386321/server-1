package com.lawu.eshop.member.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.product.dto.CommentProductInfoDTO;
import com.lawu.eshop.product.dto.ProductInfoDTO;
import com.lawu.eshop.product.dto.ProductSearchDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * Description: 商品
 * </p>
 *
 * @author Yangqh
 * @date 2017年3月27日 下午2:42:22
 */
@FeignClient(value = "product-srv")
public interface ProductService {

    /**
     * 根据商品ID查询商品详情信息
     *
     * @param productId 商品ID
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "product/selectProductById")
    Result<ProductInfoDTO> selectProductById(@RequestParam("productId") Long productId);

    @RequestMapping(value = "productModel/selectCommentProductInfo/{productModelId}", method = RequestMethod.GET)
    Result<CommentProductInfoDTO> selectCommentProductInfo(@PathVariable("productModelId") Long productModelId);

    /**
     * 根据商品ID查询商品信息
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "product/getProduct/{id}")
    Result<ProductInfoDTO> getProductById(@PathVariable("id") Long id);
    
    /**
     * 查询商家上架的商品数量
     * @param merchantId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "product/selectProductCount")
    Result<Integer> selectProductCount(@RequestParam("merchantId") Long merchantId);

    /**
     * 根据ids查询商品信息
     *
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "product/listProductByIds")
    Result<List<ProductSearchDTO>> listProductByIds(@RequestParam("ids") List<Long> ids);

}
