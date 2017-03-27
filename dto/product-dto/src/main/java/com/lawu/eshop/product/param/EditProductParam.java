package com.lawu.eshop.product.param;

import io.swagger.annotations.ApiModelProperty;

public class EditProductParam {

	@ApiModelProperty(value = "商家ID", required = true)
	private Long merchantId;

	@ApiModelProperty(value = "商品类型ID", required = true)
	private Integer categoryId;

	@ApiModelProperty(value = "商品编号", required = true)
	private String num;

	@ApiModelProperty(value = "商品名称", required = true)
	private String name;

	@ApiModelProperty(value = "商品描述", required = true)
	private String content;

	@ApiModelProperty(value = "商品特征图片", required = false)
	private String featureImage;

	@ApiModelProperty(value = "商品型号信息", required = true)
	private String spec;
	
	@ApiModelProperty(value = "商品图片", required = true)
	private String imageUrls;

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
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

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(String imageUrls) {
		this.imageUrls = imageUrls;
	}

}
