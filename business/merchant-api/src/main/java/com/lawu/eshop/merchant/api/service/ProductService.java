package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.ProductEditInfoDTO;
import com.lawu.eshop.product.dto.ProductQueryDTO;
import com.lawu.eshop.product.param.EditProductDataParam;
import com.lawu.eshop.product.query.ProductDataQuery;

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
	 * @param status
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "product/updateProductStatus")
    Result updateProductStatus(@RequestParam("ids") String ids, @RequestParam("productStatus") ProductStatusEnum productStatus);
    
    /**
     * 根据商品ID查询商品详情信息
     * @param id 商品ID
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "product/selectEditProductById")
    Result<ProductEditInfoDTO> selectEditProductById(@RequestParam("productId") Long productId);

    /**
     * 
     * @param product
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "product/saveProduct")
	Result saveProduct(@RequestParam("productId") Long productId,@RequestBody EditProductDataParam product);
}
