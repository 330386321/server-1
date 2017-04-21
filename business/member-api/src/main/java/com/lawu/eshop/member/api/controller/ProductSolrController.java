package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.constants.TypeEnum;
import com.lawu.eshop.ad.dto.AdPlatformDTO;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.member.api.service.AdPlatformService;
import com.lawu.eshop.member.api.service.ProductService;
import com.lawu.eshop.member.api.service.ProductSolrService;
import com.lawu.eshop.member.api.service.RecommendProductCategoryService;
import com.lawu.eshop.product.dto.ProductInfoDTO;
import com.lawu.eshop.product.dto.ProductSearchDTO;
import com.lawu.eshop.product.dto.RecommendProductCategoryDTO;
import com.lawu.eshop.product.dto.ShoppingProductDTO;
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

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private RecommendProductCategoryService recommendProductCategoryService;

    @Autowired
    private AdPlatformService adPlatformService;

    @Autowired
    private ProductService productService;

    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @ApiOperation(value = "根据商品类别查询商品信息", notes = "会员APP首页商品分类。[1100] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listProductByCategoryId", method = RequestMethod.GET)
    public Result<Page<ProductSearchDTO>> listProductByCategoryId(@ModelAttribute @ApiParam ProductSolrParam productSolrParam) {
        return productSolrService.listProductByCategoryId(productSolrParam);
    }

    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @ApiOperation(value = "商品详情为你推荐", notes = "商品详情为你推荐(同类别按销量排行)。[1100] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listRecommendProduct", method = RequestMethod.GET)
    public Result<Page<ProductSearchDTO>> listRecommendProduct(@ModelAttribute @ApiParam ProductSolrParam productSolrParam) {
        return productSolrService.listRecommendProduct(productSolrParam);
    }

    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @ApiOperation(value = "会员APP商品搜索", notes = "会员APP商品搜索。[1100] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listProductByName", method = RequestMethod.GET)
    public Result<Page<ProductSearchDTO>> listProductByName(@ModelAttribute @ApiParam ProductSolrParam productSolrParam) {
        return productSolrService.listProductByName(productSolrParam);
    }

    @ApiOperation(value = "要购物首页", notes = "要购物首页。(梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listProduct", method = RequestMethod.GET)
    public Result<ShoppingProductDTO> listProduct() {
        ShoppingProductDTO shoppingProductDTO = new ShoppingProductDTO();
        List<ProductSearchDTO> productSearchDTOS = new ArrayList<>();

        //商品分类
        Result<List<RecommendProductCategoryDTO>> recProCatResult = recommendProductCategoryService.listAllRecommendProductCategory();
        if (isSuccess(recProCatResult)) {
            shoppingProductDTO.setRecommendProductCategoryDTOS(recProCatResult.getModel());
        } else {
            shoppingProductDTO.setRecommendProductCategoryDTOS(new ArrayList<>());
        }

        //顶部推荐
        Result<List<AdPlatformDTO>> topResult = adPlatformService.getAdPlatformByTypePosition(TypeEnum.TYPE_PRODUCT, PositionEnum.POSITON_SHOP_TOP);
        if (isSuccess(topResult)) {
            for (AdPlatformDTO adPlatformDTO : topResult.getModel()) {
                ProductSearchDTO productSearchDTO = new ProductSearchDTO();
                productSearchDTO.setProductId(adPlatformDTO.getProductId());
                productSearchDTO.setFeatureImage(adPlatformDTO.getMediaUrl());
                productSearchDTOS.add(productSearchDTO);
            }
        }
        shoppingProductDTO.setTopProduct(productSearchDTOS);

        //今日推荐
        productSearchDTOS = new ArrayList<>();
        Result<List<AdPlatformDTO>> chooseResult = adPlatformService.getAdPlatformByTypePosition(TypeEnum.TYPE_PRODUCT, PositionEnum.POSITON_SHOP_CHOOSE);
        if (isSuccess(chooseResult)) {
            for (AdPlatformDTO adPlatformDTO : chooseResult.getModel()) {
                Result<ProductInfoDTO> productInfoDTOResult = productService.getProductById(adPlatformDTO.getProductId());
                if (isSuccess(productInfoDTOResult)) {
                    ProductSearchDTO productSearchDTO = new ProductSearchDTO();
                    productSearchDTO.setProductId(adPlatformDTO.getProductId());
                    productSearchDTO.setFeatureImage(adPlatformDTO.getMediaUrl());
                    productSearchDTO.setName(adPlatformDTO.getTitle());
                    productSearchDTO.setSalesVolume(productInfoDTOResult.getModel().getTotalSales());
                    productSearchDTO.setOriginalPrice(Double.valueOf(productInfoDTOResult.getModel().getPriceMax()));
                    productSearchDTO.setPrice(Double.valueOf(productInfoDTOResult.getModel().getPriceMin()));
                    productSearchDTOS.add(productSearchDTO);
                }
            }
        }
        shoppingProductDTO.setRecommendProduct(productSearchDTOS);

        //精品推荐
        productSearchDTOS = new ArrayList<>();
        Result<List<AdPlatformDTO>> goodsResult = adPlatformService.getAdPlatformByTypePosition(TypeEnum.TYPE_PRODUCT, PositionEnum.POSITON_SHOP_GOODS);
        if (isSuccess(goodsResult)) {
            for (AdPlatformDTO adPlatformDTO : goodsResult.getModel()) {
                Result<ProductInfoDTO> productInfoDTOResult = productService.getProductById(adPlatformDTO.getProductId());
                if (isSuccess(productInfoDTOResult)) {
                    ProductSearchDTO productSearchDTO = new ProductSearchDTO();
                    productSearchDTO.setProductId(adPlatformDTO.getProductId());
                    productSearchDTO.setFeatureImage(adPlatformDTO.getMediaUrl());
                    productSearchDTO.setName(adPlatformDTO.getTitle());
                    productSearchDTO.setSalesVolume(productInfoDTOResult.getModel().getTotalSales());
                    productSearchDTO.setOriginalPrice(Double.valueOf(productInfoDTOResult.getModel().getPriceMax()));
                    productSearchDTO.setPrice(Double.valueOf(productInfoDTOResult.getModel().getPriceMin()));
                    productSearchDTOS.add(productSearchDTO);
                }
            }
        }
        shoppingProductDTO.setGoodsProduct(productSearchDTOS);
        return successGet(shoppingProductDTO);
    }

}
