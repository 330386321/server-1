package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.ProductSolrService;
import com.lawu.eshop.product.dto.ProductSolrDTO;
import com.lawu.eshop.product.param.ProductSolrParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meishuquan
 * @date 2017/3/30.
 */
@Api(tags = "productSolr")
@RestController
@RequestMapping(value = "productSolr/")
public class ProductSolrController extends BaseController {

    @Autowired
    private ProductSolrService productSolrService;

    @ApiOperation(value = "根据商品类别查询商品信息", notes = "会员APP首页商品分类。[1002] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listProductByCategoryId", method = RequestMethod.GET)
    public Result<Page<ProductSolrDTO>> listProductByCategoryId(@ModelAttribute @ApiParam ProductSolrParam productSolrParam) {
        return productSolrService.listProductByCategoryId(productSolrParam);
    }

    @ApiOperation(value = "商品详情为你推荐", notes = "商品详情为你推荐(同类别按销量排行)。[1002] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listRecommendProduct", method = RequestMethod.GET)
    public Result<Page<ProductSolrDTO>> listRecommendProduct(@ModelAttribute @ApiParam ProductSolrParam productSolrParam) {
        return productSolrService.listRecommendProduct(productSolrParam);
    }

    @ApiOperation(value = "会员APP商品搜索", notes = "会员APP商品搜索。[1002] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listProductByName", method = RequestMethod.GET)
    public Result<Page<ProductSolrDTO>> listProductByName(@ModelAttribute @ApiParam ProductSolrParam productSolrParam) {
        return productSolrService.listProductByName(productSolrParam);
    }
}
