package com.lawu.eshop.product.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.ProductEditInfoDTO;
import com.lawu.eshop.product.dto.ProductInfoDTO;
import com.lawu.eshop.product.dto.ProductQueryDTO;
import com.lawu.eshop.product.dto.ProductRecommendDTO;
import com.lawu.eshop.product.param.EditProductDataParam;
import com.lawu.eshop.product.param.ProductRecommendParam;
import com.lawu.eshop.product.query.ProductDataQuery;
import com.lawu.eshop.product.srv.bo.ProductEditInfoBO;
import com.lawu.eshop.product.srv.bo.ProductInfoBO;
import com.lawu.eshop.product.srv.bo.ProductQueryBO;
import com.lawu.eshop.product.srv.bo.ProductRecommendBO;
import com.lawu.eshop.product.srv.converter.ProductConverter;
import com.lawu.eshop.product.srv.converter.ProductRecommendConverter;
import com.lawu.eshop.product.srv.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yangqh
 * @date 2017/3/13
 */
@RestController
@RequestMapping(value = "product/")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    /**
     * 查询商品列表
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "selectProduct", method = RequestMethod.POST)
    public Result<Page<ProductQueryDTO>> selectProduct(@RequestBody ProductDataQuery query) {
        Page<ProductQueryBO> page = productService.selectProduct(query);
        List<ProductQueryBO> list = page.getRecords();
        List<ProductQueryDTO> dtos = ProductConverter.convertDTOS(list);

        Page<ProductQueryDTO> retPage = new Page<>();
        retPage.setCurrentPage(query.getCurrentPage());
        retPage.setTotalCount(page.getTotalCount());
        retPage.setRecords(dtos);

        return successCreated(retPage);
    }

    /**
     * 商品批量操作
     *
     * @param ids           商品ID字符串
     * @param productStatus 目标修改的状态
     * @return
     */
    @SuppressWarnings({"rawtypes"})
    @RequestMapping(value = "updateProductStatus", method = RequestMethod.PUT)
    public Result updateProductStatus(@RequestParam String ids, @RequestParam ProductStatusEnum productStatus) {
        int counts = productService.updateProductStatus(ids, productStatus);
        if (counts == 0 || counts != ids.split(",").length) {
            return successCreated(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successCreated(ResultCode.SUCCESS);
    }

    /**
     * 用户端商品详情，根据ID查询商品详情
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "selectProductById", method = RequestMethod.GET)
    public Result<ProductInfoDTO> selectProductById(@RequestParam Long productId) {
        if (productId == null) {
            return successCreated(ResultCode.ID_EMPTY);
        }

        // 商品基本信息
        ProductInfoBO productBO = productService.selectProductById(productId);
        if (productBO == null) {
            return successCreated(ResultCode.RESOURCE_NOT_FOUND);
        }
        ProductInfoDTO productDTO = ProductConverter.convertInfoDTO(productBO);

        return successCreated(productDTO);
    }

    /**
     * 商家端编辑商品时，根据ID查询商品
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "selectEditProductById", method = RequestMethod.GET)
    public Result<ProductEditInfoDTO> selectEditProductById(@RequestParam Long productId) {
        if (productId == null) {
            return successCreated(ResultCode.ID_EMPTY, null);
        }

        // 商品基本信息
        ProductEditInfoBO productBO = productService.selectEditProductById(productId);
        if (productBO == null) {
            return successCreated(ResultCode.RESOURCE_NOT_FOUND, null);
        }
        ProductEditInfoDTO productDTO = ProductConverter.convertEditInfoDTO(productBO);

        return successCreated(productDTO);
    }

    /**
     * 添加、编辑商品
     *
     * @param productId
     * @param product
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "saveProduct", method = RequestMethod.POST)
    public Result saveProduct(@RequestParam Long productId, @RequestBody EditProductDataParam product) {
        productService.eidtProduct(productId, product);
        return successCreated(ResultCode.SUCCESS);
    }

    /**
     * 同类型商品推荐
     *
     * @param categoryId
     * @param productId
     * @return
     */
    @RequestMapping(value = "recommend/{categoryId}", method = RequestMethod.GET)
    public Result<List<ProductRecommendDTO>> recommend(@PathVariable Integer categoryId, @RequestParam Long productId) {
        ProductRecommendParam productRecommendParam = new ProductRecommendParam();
        productRecommendParam.setCategoryId(categoryId);
        productRecommendParam.setProductId(productId);
        productRecommendParam.setProductStatus(ProductStatusEnum.PRODUCT_STATUS_UP.val);
        productRecommendParam.setProductModelStatus(true);
        List<ProductRecommendBO> productRecommendBOS = productService.listProductBycategoryId(productRecommendParam);
        if (productRecommendBOS.isEmpty()) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successGet(ProductRecommendConverter.convertDTO(productRecommendBOS));
    }

}
