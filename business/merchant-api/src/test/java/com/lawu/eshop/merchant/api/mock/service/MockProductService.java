package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.ProductService;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.dto.CommentProductInfoDTO;
import com.lawu.eshop.product.dto.ProductEditInfoDTO;
import com.lawu.eshop.product.dto.ProductInfoDTO;
import com.lawu.eshop.product.dto.ProductQueryDTO;
import com.lawu.eshop.product.dto.ProductRelateAdInfoDTO;
import com.lawu.eshop.product.param.EditProductDataParam;
import com.lawu.eshop.product.query.ProductDataQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockProductService extends BaseController implements ProductService {
    @Override
    public Result<Page<ProductQueryDTO>> selectProduct(@RequestBody ProductDataQuery query) {
        return successCreated();
    }

    @Override
    public Result updateProductStatus(@RequestParam("ids") String ids, @RequestParam("productStatus") ProductStatusEnum productStatus, @RequestParam("merchantId") Long merchantId) {
        return successCreated();
    }

    @Override
    public Result<ProductEditInfoDTO> selectEditProductById(@RequestParam("productId") Long productId) {
        return successGet();
    }

    @Override
    public Result saveProduct_bak(@RequestBody EditProductDataParam product) {
        return successCreated();
    }

    @Override
    public Result saveProduct(@RequestBody EditProductDataParam product) {
        return successCreated();
    }

    @Override
    public Result<CommentProductInfoDTO> selectCommentProductInfo(@PathVariable("productModelId") Long productModelId) {
        CommentProductInfoDTO dto = new CommentProductInfoDTO();
        return successGet(dto);
    }

    @Override
    public Result<ProductInfoDTO> getProduct(@PathVariable("id") Long id) {
        ProductInfoDTO dto = new ProductInfoDTO();
        dto.setName("test");
        dto.setFeatureImage("pic");
        return successGet(dto);
    }

    @Override
    public Result updateKeywordsById(@PathVariable("id") Long id, @RequestParam("keywords") Long merchantId, @RequestParam("keywords") String keywords) {
        return successCreated();
    }

    @Override
    public Result<Integer> selectProductCount(@RequestParam("merchantId") Long merchantId) {
        return successCreated(new Integer(1));
    }

	@Override
	public Result<ProductRelateAdInfoDTO> selectProductRelateAdInfo(Long id) {
		return successGet(new ProductRelateAdInfoDTO());
	}

}
