package com.lawu.eshop.product.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/21.
 */
public class ShoppingProductDTO {

    @ApiModelProperty(value = "商品分类")
    private List<RecommendProductCategoryDTO> recommendProductCategoryDTOS;

    @ApiModelProperty(value = "顶部推荐")
    private List<ProductSearchDTO> topProduct;

    @ApiModelProperty(value = "今日推荐")
    private List<ProductSearchDTO> recommendProduct;

    @ApiModelProperty(value = "精品推荐")
    private List<ProductSearchDTO> goodsProduct;

    public List<ProductSearchDTO> getRecommendProduct() {
        return recommendProduct;
    }

    public void setRecommendProduct(List<ProductSearchDTO> recommendProduct) {
        this.recommendProduct = recommendProduct;
    }

    public List<ProductSearchDTO> getGoodsProduct() {
        return goodsProduct;
    }

    public void setGoodsProduct(List<ProductSearchDTO> goodsProduct) {
        this.goodsProduct = goodsProduct;
    }

    public List<ProductSearchDTO> getTopProduct() {
        return topProduct;
    }

    public void setTopProduct(List<ProductSearchDTO> topProduct) {
        this.topProduct = topProduct;
    }

    public List<RecommendProductCategoryDTO> getRecommendProductCategoryDTOS() {
        return recommendProductCategoryDTOS;
    }

    public void setRecommendProductCategoryDTOS(List<RecommendProductCategoryDTO> recommendProductCategoryDTOS) {
        this.recommendProductCategoryDTOS = recommendProductCategoryDTOS;
    }
}
