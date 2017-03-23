package com.lawu.eshop.product.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 产品DTO
 *
 * @author Leach
 * @date 2017/3/22
 */
public class ProductDTO {

	@ApiModelProperty(value = "主键", required = true)
    private Long id;
	
	@ApiModelProperty(value = "分类", required = true)
    private String category;
	
	@ApiModelProperty(value = "商品名称", required = true)
    private String name;
	
	@ApiModelProperty(value = "状态", required = true)
    private Integer status;
	
	@ApiModelProperty(value = "创建时间", required = true)
    private String gmtCreate;
	
	@ApiModelProperty(value = "封面图片", required = true)
    private String featureImage;
	
	@ApiModelProperty(value = "商品图片", required = true)
    private String imagesUrl;
	
	@ApiModelProperty(value = "商品型号", required = true)
    private String spec;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
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
    
}
