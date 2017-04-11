package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.member.api.service.MerchantStoreService;
import com.lawu.eshop.member.api.service.ProductService;
import com.lawu.eshop.product.dto.ProductInfoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Description:商品 </p>
 *
 * @author Yangqh
 * @date 2017年3月27日 下午2:40:23
 */
@Api(tags = "product")
@RestController
@RequestMapping(value = "product/")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;
    @Autowired
    private MerchantStoreService merchantStoreService;

    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @SuppressWarnings("rawtypes")
    @ApiOperation(value = "查询商品详情", notes = "根据商品ID查询商品详情信息，[1002|1003]，（杨清华）", httpMethod = "GET")
    @Authorization
    @RequestMapping(value = "{productId}", method = RequestMethod.GET)
    public Result<ProductInfoDTO> selectProductById(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                    @PathVariable @ApiParam(name = "productId", required = true, value = "商品ID") Long productId) {

        Result<ProductInfoDTO> result = productService.selectProductById(productId);
        if (result.getRet() != ResultCode.SUCCESS) {
            return result;
        }

        Result rb = merchantStoreService.findIsNoReasonReturnById(result.getModel().getMerchantId());
        if (rb.getRet() != ResultCode.SUCCESS) {
            return successCreated(rb.getRet());
        }
        result.getModel().setSupportEleven((boolean) rb.getModel());

        //TODO 商品评价额外调用服务

        return result;
    }

}
