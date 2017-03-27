package com.lawu.eshop.product.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 商品列表查询，封装的DTO
 *
 * @author Leach
 * @date 2017/3/22
 */
public class ProductQueryDTO {

    private Long id;
	
    private String category;
	
    private String categoryName;
	
    private String name;
	
    private Integer status;
	
    private String gmtCreate;
	
    private String featureImage;
	
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
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
}
