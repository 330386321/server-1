package com.lawu.eshop.product.param;

import io.swagger.annotations.ApiModelProperty;

/**
 * api接收app提交参数对象
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年3月27日 下午8:21:09
 *
 */
public class EditProductParam {

	@ApiModelProperty(value = "商品类型ID", required = true)
	private Integer categoryId;

	@ApiModelProperty(value = "商品名称", required = true)
	private String name;

	@ApiModelProperty(value = "商品描述", required = true)
	private String content;

	@ApiModelProperty(value = "商品型号信息，json格式：[{id,name,originalPrice,price,inventory,inventoryTrans},{},...],修改时inventoryTrans透传不做修改", required = true)
	private String spec;
	
	@ApiModelProperty(value = "特征图片(非必填,如果为空则取滚动图片第一张作为特征图片)")
	private String featureImage;
	
	@ApiModelProperty(value = "滚动图片，多张提交key为productIamge-N(修改时上传增量，例如：第1张：productIamge-1、第2张：productIamge-2...)")
	private String productImages;
	
	
	@ApiModelProperty(value = "详情图片描述(以json字符串的格式传输，格式：[\"xxxxxx\",\"zzzzzzz\",...]，其顺序要和其详情图片index一致，没有文字描述空字符串处理)")
	private String imageContents;
	@ApiModelProperty(value = "详情图片，多张提交key为productDetailImage-'index'-N(例如：productDetailImage-1-1、productDetailImage-1-2,productDetailImage-2-1、productDetailImage-2-2,....)")
	private String productDetailImages;
	
	
	@ApiModelProperty(value = "修改时回显的特征图片url(新增时传空,格式：url1,url2,url3....)")
	private String backFeatureImageUrls;
	
	@ApiModelProperty(value = "修改时回显的滚动图片url(新增时传空,格式：url1,url2,url3....)")
	private String backProductImageUrls;
	
	@ApiModelProperty(value = "修改时回显的详情图片url(新增时传空,json格式：{\"productDetailImage-1\":[\"xxxx-1-1,xxxx-1-2\",\"zzzz-2-1,zzzz-2-2\"],\"productDetailImage-2\":[],...})")
	private String backProductDetailImageUrls;
	
	public String getBackFeatureImageUrls() {
		return backFeatureImageUrls;
	}

	public void setBackFeatureImageUrls(String backFeatureImageUrls) {
		this.backFeatureImageUrls = backFeatureImageUrls;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
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

	public String getProductImages() {
		return productImages;
	}

	public void setProductImage(String productImages) {
		this.productImages = productImages;
	}

	public String getProductDetailImages() {
		return productDetailImages;
	}

	public void setProductDetailImages(String productDetailImages) {
		this.productDetailImages = productDetailImages;
	}

	public String getBackProductImageUrls() {
		return backProductImageUrls;
	}

	public void setBackProductImageUrls(String backProductImageUrls) {
		this.backProductImageUrls = backProductImageUrls;
	}

	public String getBackProductDetailImageUrls() {
		return backProductDetailImageUrls;
	}

	public void setBackProductDetailImageUrls(String backProductDetailImageUrls) {
		this.backProductDetailImageUrls = backProductDetailImageUrls;
	}

	public String getImageContents() {
		return imageContents;
	}

	public void setImageContents(String imageContents) {
		this.imageContents = imageContents;
	}

	public void setProductImages(String productImages) {
		this.productImages = productImages;
	}

}
