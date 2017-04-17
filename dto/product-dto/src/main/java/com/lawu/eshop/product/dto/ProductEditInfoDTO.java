package com.lawu.eshop.product.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * <p>
 * Description: 商家端修改商品DTO
 * </p>
 * @author Yangqh
 * @date 2017年4月17日 下午7:13:05
 *
 */
public class ProductEditInfoDTO {

	@ApiModelProperty(value = "商品ID")
	private Long id;
	
	@ApiModelProperty(value = "商家ID")
    private Long merchantId;
	
	@ApiModelProperty(value = "商品分类ID")
    private Long category;
	
	@ApiModelProperty(value = "商品名称")
    private String name;
	
	@ApiModelProperty(value = "商品描述")
    private String content;
	
	@ApiModelProperty(value = "商品特征图片")
    private String featureImage;
	
	@ApiModelProperty(value = "商品描述图片,格式：url1,url2,url3....")
    private String imagesUrl;
	
	@ApiModelProperty(value = "商品型号,格式：[{id,name,originalPrice,price,inventory,inventoryTrans,isDelete,salesVolume},{},...],修改时inventoryTrans透传不做修改,isDelete(0-否1-是)")
    private String spec;
	
	@ApiModelProperty(value = "商品详情,格式：[\"xxxxxx\",\"zzzzzzz\",...]")
    private String imageContent;
	
	@ApiModelProperty(value = "商品详情图片,json格式：{\"productDetailImage-1\":[\"xxxx-1-1,xxxx-1-2\",\"zzzz-2-1,zzzz-2-2\"],\"productDetailImage-2\":[],...}")
    private String imageDetailUrl;
	
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
}
