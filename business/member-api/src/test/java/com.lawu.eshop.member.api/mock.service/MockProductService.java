package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.ProductService;
import com.lawu.eshop.product.dto.CommentProductInfoDTO;
import com.lawu.eshop.product.dto.ProductInfoDTO;
import com.lawu.eshop.product.dto.ProductSearchDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
class MockProductService extends BaseController implements ProductService {


    @Override
    public Result<ProductInfoDTO> selectProductById(@RequestParam("productId") Long productId) {
        return null;
    }

    @Override
    public Result<CommentProductInfoDTO> selectCommentProductInfo(@PathVariable("productModelId") Long productModelId) {
        CommentProductInfoDTO dto = new CommentProductInfoDTO();
        dto.setName("name");
        dto.setModelName("modelName");
        dto.setPrice("12.21");
        return successCreated(dto);
    }

    @Override
    public Result<ProductInfoDTO> getProductById(@PathVariable("id") Long id) {
        return null;
    }

    @Override
    public Result<Integer> selectProductCount(@RequestParam("merchantId") Long merchantId) {
        return successCreated(new Integer("1"));
    }

    @Override
    public Result<List<ProductSearchDTO>> listProductByIds(@RequestParam("ids") List<Long> ids) {
        return null;
    }
}
