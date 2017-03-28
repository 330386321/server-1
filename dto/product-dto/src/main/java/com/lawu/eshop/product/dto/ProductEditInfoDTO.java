package com.lawu.eshop.product.dto;

/**
 * 用户端商品详情封装DTO
 *
 * @author Leach
 * @date 2017/3/22
 */
public class ProductEditInfoDTO {

    private Long id;
    private Long merchantId;
    private Long category;
    private String name;
	private String content;
    private String featureImage;
    private String imagesUrl;
    private String spec;
	public Long getMerchantId() {
		return merchantId;
	}
	public Long getCategory() {
		return category;
	}
	public void setCategory(Long category) {
		this.category = category;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	
    
}
