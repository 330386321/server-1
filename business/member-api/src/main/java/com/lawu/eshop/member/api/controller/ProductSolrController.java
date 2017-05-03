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
import com.lawu.eshop.product.dto.*;
import com.lawu.eshop.product.param.ProductSearchParam;
import com.lawu.eshop.product.param.ProductSearchRealParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result<Page<ProductSearchDTO>> listProductByCategoryId(@ModelAttribute @ApiParam ProductSearchParam productSearchParam,
                                                                  @RequestParam @ApiParam(required = true, value = "商品类别ID") Integer categoryId) {
        ProductSearchRealParam param = new ProductSearchRealParam();
        param.setCurrentPage(productSearchParam.getCurrentPage());
        param.setPageSize(productSearchParam.getPageSize());
        param.setCategoryId(categoryId);
        return productSolrService.listProductByCategoryId(param);
    }

    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @ApiOperation(value = "商品详情为你推荐", notes = "商品详情为你推荐(同类别按销量排行)。[1100] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listRecommendProduct", method = RequestMethod.GET)
    public Result<Page<ProductSearchDTO>> listRecommendProduct(@ModelAttribute @ApiParam ProductSearchParam productSearchParam,
                                                               @RequestParam @ApiParam(required = true, value = "商品类别ID") Integer categoryId) {
        ProductSearchRealParam param = new ProductSearchRealParam();
        param.setCurrentPage(productSearchParam.getCurrentPage());
        param.setPageSize(productSearchParam.getPageSize());
        param.setCategoryId(categoryId);
        return productSolrService.listRecommendProduct(param);
    }

    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @ApiOperation(value = "会员APP商品搜索", notes = "会员APP商品搜索。[1100] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listProductByName", method = RequestMethod.GET)
    public Result<Page<ProductSearchDTO>> listProductByName(@ModelAttribute @ApiParam ProductSearchParam productSearchParam,
                                                            @RequestParam @ApiParam(required = true, value = "商品名称") String name) {
        ProductSearchRealParam param = new ProductSearchRealParam();
        param.setCurrentPage(productSearchParam.getCurrentPage());
        param.setPageSize(productSearchParam.getPageSize());
        param.setName(name);
        return productSolrService.listProductByName(param);
    }

    @Audit(date = "2017-04-21", reviewer = "孙林青")
    @ApiOperation(value = "搜索词关联词频查询", notes = "根据搜索词推荐关联词和频率查询。 (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listProductSearchWord", method = RequestMethod.GET)
    public Result<List<ProductSearchWordDTO>> listProductSearchWord(@RequestParam @ApiParam(name = "name", required = true, value = "商品名称") String name) {
        return productSolrService.listProductSearchWord(name);
    }

    @Audit(date = "2017-04-21", reviewer = "孙林青")
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
                    productSearchDTO.setSalesVolume(productInfoDTOResult.getModel().getTotalSalesVolume());
                    productSearchDTO.setOriginalPrice(Double.valueOf(productInfoDTOResult.getModel().getMaxPrice()));
                    productSearchDTO.setPrice(Double.valueOf(productInfoDTOResult.getModel().getMinPrice()));
                    productSearchDTOS.add(productSearchDTO);
                }
            }
        }
        shoppingProductDTO.setRecommendProduct(productSearchDTOS);

        //E店必购
        productSearchDTOS = new ArrayList<>();
        Result<List<AdPlatformDTO>> buyResult = adPlatformService.getAdPlatformByTypePosition(TypeEnum.TYPE_PRODUCT, PositionEnum.SHOPPING_BUY);
        if (isSuccess(buyResult)) {
            for (AdPlatformDTO adPlatformDTO : buyResult.getModel()) {
                Result<ProductInfoDTO> productInfoDTOResult = productService.getProductById(adPlatformDTO.getProductId());
                if (isSuccess(productInfoDTOResult)) {
                    ProductSearchDTO productSearchDTO = new ProductSearchDTO();
                    productSearchDTO.setProductId(adPlatformDTO.getProductId());
                    productSearchDTO.setFeatureImage(adPlatformDTO.getMediaUrl());
                    productSearchDTO.setName(adPlatformDTO.getTitle());
                    productSearchDTO.setSalesVolume(productInfoDTOResult.getModel().getTotalSalesVolume());
                    productSearchDTO.setOriginalPrice(Double.valueOf(productInfoDTOResult.getModel().getMaxPrice()));
                    productSearchDTO.setPrice(Double.valueOf(productInfoDTOResult.getModel().getMinPrice()));
                    productSearchDTOS.add(productSearchDTO);
                }
            }
        }
        shoppingProductDTO.setBuyProduct(productSearchDTOS);

        //特色好货
        productSearchDTOS = new ArrayList<>();
        Result<List<AdPlatformDTO>> featureResult = adPlatformService.getAdPlatformByTypePosition(TypeEnum.TYPE_PRODUCT, PositionEnum.SHOPPING_GOODS);
        if (isSuccess(featureResult)) {
            for (AdPlatformDTO adPlatformDTO : featureResult.getModel()) {
                Result<ProductInfoDTO> productInfoDTOResult = productService.getProductById(adPlatformDTO.getProductId());
                if (isSuccess(productInfoDTOResult)) {
                    ProductSearchDTO productSearchDTO = new ProductSearchDTO();
                    productSearchDTO.setProductId(adPlatformDTO.getProductId());
                    productSearchDTO.setFeatureImage(adPlatformDTO.getMediaUrl());
                    productSearchDTO.setName(adPlatformDTO.getTitle());
                    productSearchDTO.setSalesVolume(productInfoDTOResult.getModel().getTotalSalesVolume());
                    productSearchDTO.setOriginalPrice(Double.valueOf(productInfoDTOResult.getModel().getMaxPrice()));
                    productSearchDTO.setPrice(Double.valueOf(productInfoDTOResult.getModel().getMinPrice()));
                    productSearchDTOS.add(productSearchDTO);
                }
            }
        }
        shoppingProductDTO.setFeatureProduct(productSearchDTOS);

        //实惠单品
        productSearchDTOS = new ArrayList<>();
        Result<List<AdPlatformDTO>> benefitResult = adPlatformService.getAdPlatformByTypePosition(TypeEnum.TYPE_PRODUCT, PositionEnum.SHOPPING_BENEFIT);
        if (isSuccess(benefitResult)) {
            for (AdPlatformDTO adPlatformDTO : benefitResult.getModel()) {
                Result<ProductInfoDTO> productInfoDTOResult = productService.getProductById(adPlatformDTO.getProductId());
                if (isSuccess(productInfoDTOResult)) {
                    ProductSearchDTO productSearchDTO = new ProductSearchDTO();
                    productSearchDTO.setProductId(adPlatformDTO.getProductId());
                    productSearchDTO.setFeatureImage(adPlatformDTO.getMediaUrl());
                    productSearchDTO.setName(adPlatformDTO.getTitle());
                    productSearchDTO.setSalesVolume(productInfoDTOResult.getModel().getTotalSalesVolume());
                    productSearchDTO.setOriginalPrice(Double.valueOf(productInfoDTOResult.getModel().getMaxPrice()));
                    productSearchDTO.setPrice(Double.valueOf(productInfoDTOResult.getModel().getMinPrice()));
                    productSearchDTOS.add(productSearchDTO);
                }
            }
        }
        shoppingProductDTO.setBenefitProduct(productSearchDTOS);
        return successGet(shoppingProductDTO);
    }

    @Audit(date = "2017-05-02", reviewer = "孙林青")
    @ApiOperation(value = "要购物首页猜你喜欢", notes = "要购物首页猜你喜欢。[1100] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listYouLikeProduct", method = RequestMethod.GET)
    public Result<Page<ProductSearchDTO>> listYouLikeProduct(@ModelAttribute @ApiParam ProductSearchParam productSearchParam) {
        return productSolrService.listYouLikeProduct(productSearchParam);
    }

    @Audit(date = "2017-05-02", reviewer = "孙林青")
    @ApiOperation(value = "查询商品推荐类别", notes = "查询商品类别。[1100] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listRecommendProductCategory", method = RequestMethod.GET)
    public Result<List<RecommendProductCategoryDTO>> listRecommendProductCategory() {
        return recommendProductCategoryService.listAllRecommendProductCategory();
    }

}
