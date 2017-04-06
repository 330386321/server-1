package com.lawu.eshop.product.param;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
public class ProductRecommendParam {

    private Integer categoryId;

    private Long productId;

    private Byte productStatus;

    private Boolean productModelStatus;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Byte getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Byte productStatus) {
        this.productStatus = productStatus;
    }

    public Boolean getProductModelStatus() {
        return productModelStatus;
    }

    public void setProductModelStatus(Boolean productModelStatus) {
        this.productModelStatus = productModelStatus;
    }
}
