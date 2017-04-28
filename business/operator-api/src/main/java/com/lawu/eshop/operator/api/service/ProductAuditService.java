package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.ProductEditInfoDTO;
import com.lawu.eshop.product.dto.ProductQueryDTO;
import com.lawu.eshop.product.param.ListProductParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
@FeignClient(value = "product-srv")
public interface ProductAuditService {

    /**
     * 查询所有门店商品
     *
     * @param listProductParam
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "product/listAllProduct")
    Result<Page<ProductQueryDTO>> listProduct(@RequestBody ListProductParam listProductParam);

    /**
     * 更新商品状态
     *
     * @param ids
     * @param productStatus
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "product/updateProductStatus")
    Result updateProductStatus(@RequestParam("ids") String ids, @RequestParam("productStatus") ProductStatusEnum productStatus);

    /**
     * 根据ID查询商品详情
     *
     * @param productId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "product/selectEditProductById")
    Result<ProductEditInfoDTO> selectEditProductById(@RequestParam("productId") Long productId);
}
