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
    private List<ProductSolrDTO> topProduct;

    @ApiModelProperty(value = "今日推荐")
    private List<ProductSolrDTO> recommendProduct;

    @ApiModelProperty(value = "精品推荐")
    private List<ProductSolrDTO> goodsProduct;

    public List<ProductSolrDTO> getRecommendProduct() {
        return recommendProduct;
    }

    public void setRecommendProduct(List<ProductSolrDTO> recommendProduct) {
        this.recommendProduct = recommendProduct;
    }

    public List<ProductSolrDTO> getGoodsProduct() {
        return goodsProduct;
    }

    public void setGoodsProduct(List<ProductSolrDTO> goodsProduct) {
        this.goodsProduct = goodsProduct;
    }

    public List<ProductSolrDTO> getTopProduct() {
        return topProduct;
    }

    public void setTopProduct(List<ProductSolrDTO> topProduct) {
        this.topProduct = topProduct;
    }

    public List<RecommendProductCategoryDTO> getRecommendProductCategoryDTOS() {
        return recommendProductCategoryDTOS;
    }

    public void setRecommendProductCategoryDTOS(List<RecommendProductCategoryDTO> recommendProductCategoryDTOS) {
        this.recommendProductCategoryDTOS = recommendProductCategoryDTOS;
    }
}
