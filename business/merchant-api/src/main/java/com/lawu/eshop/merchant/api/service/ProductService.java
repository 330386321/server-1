package com.lawu.eshop.merchant.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.CommentProductInfoDTO;
import com.lawu.eshop.product.dto.ProductEditInfoDTO;
import com.lawu.eshop.product.dto.ProductInfoDTO;
import com.lawu.eshop.product.dto.ProductQueryDTO;
import com.lawu.eshop.product.param.EditProductDataParam;
import com.lawu.eshop.product.query.ProductDataQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 产品服务接口
 *
 * @author Yangqh
 * @date 2017/3/22
 */
@SuppressWarnings("rawtypes")
@FeignClient(value= "product-srv")
public interface ProductService {

    /**
     * 查询所有商品类型
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "product/selectProduct")
    Result<Page<ProductQueryDTO>> selectProduct(@RequestBody ProductDataQuery query);
    
    /**
	 * 批量处理
	 * @param ids
	 * @param productStatus
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "product/updateProductStatus")
    Result updateProductStatus(@RequestParam("ids") String ids, @RequestParam("productStatus") ProductStatusEnum productStatus,@RequestParam("merchantId") Long merchantId);
    
    /**
     * 根据商品ID查询商品详情信息
     * @param productId 商品ID
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "product/selectEditProductById")
    Result<ProductEditInfoDTO> selectEditProductById(@RequestParam("productId") Long productId);

    /**
     * 
     * @param product
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "product/saveProduct_bak")
	Result saveProduct_bak(@RequestBody EditProductDataParam product);
    
    /**
     * 
     * @param product
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "product/saveProduct")
	Result saveProduct(@RequestBody EditProductDataParam product);

    /**
     * 根据型号ID查询商品详情信息
     *
     * @param productModelId 型号ID
     * @return
     */
    @RequestMapping(value = "productModel/selectCommentProductInfo/{productModelId}", method = RequestMethod.GET)
    Result<CommentProductInfoDTO> selectCommentProductInfo(@PathVariable("productModelId") Long productModelId);

    /**
     * 根据商品ID查询商品详情信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "product/getProduct/{id}", method = RequestMethod.GET)
    Result<ProductInfoDTO> getProduct(@PathVariable("id") Long id);

    /**
     * 根据ID更新商品关键词
     *
     * @param id
     * @param merchantId
     * @param keywords
     * @return
     * @author meishuquan
     */
    @RequestMapping(value = "product/updateKeywordsById/{id}", method = RequestMethod.PUT)
    Result updateKeywordsById(@PathVariable("id") Long id, @RequestParam("keywords") Long merchantId, @RequestParam("keywords") String keywords);

     /* 查询商家上架商品的总数量
     *
     * @param merchantId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "product/selectProductCount")
    Result<Integer> selectProductCount(@RequestParam("merchantId") Long merchantId);

}
