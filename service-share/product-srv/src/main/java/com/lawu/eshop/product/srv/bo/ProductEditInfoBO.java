package com.lawu.eshop.product.srv.bo;

/**
 * 产品类别BO
 *
 * @author Yangqh
 * @date 2017/3/22
 */
public class ProductEditInfoBO {

    private Long id;
    private Long merchantId;
    private Long category;
    private String categoryName;
    private String name;
    private String content;
    private String featureImage;
    private String imagesUrl;
    private String spec;
    private String imageContent;
    private String imageDetailUrl;
    private boolean isAllowRefund;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getFeatureImage() {
		return featureImage;
	}
	public void setFeatureImage(String featureImage) {
		this.featureImage = featureImage;
	}
	public String getImagesUrl() {
		return imagesUrl;
	}
	public void setImagesUrl(String imagesUrl) {
		this.imagesUrl = imagesUrl;
	}
	public Long getCategory() {
		return category;
	}
	public void setCategory(Long category) {
		this.category = category;
	}
	public String getImageContent() {
		return imageContent;
	}
	public void setImageContent(String imageContent) {
		this.imageContent = imageContent;
	}
	public String getImageDetailUrl() {
		return imageDetailUrl;
	}
	public void setImageDetailUrl(String imageDetailUrl) {
		this.imageDetailUrl = imageDetailUrl;
	}
	public boolean isAllowRefund() {
		return isAllowRefund;
	}
	public void setAllowRefund(boolean isAllowRefund) {
		this.isAllowRefund = isAllowRefund;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}
