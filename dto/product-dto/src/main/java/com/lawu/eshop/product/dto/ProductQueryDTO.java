package com.lawu.eshop.product.dto;

import com.lawu.eshop.product.constant.ProductStatusEnum;

import io.swagger.annotations.ApiModelProperty;

/**
 * 商品列表查询，封装的DTO
 *
 * @author Leach
 * @date 2017/3/22
 */
public class ProductQueryDTO {

	@ApiModelProperty(value = "商品ID", required = true)
    private Long id;
	
	@ApiModelProperty(value = "分类名称(PC)", required = true)
    private String category;
	
	@ApiModelProperty(value = "商品名称", required = true)
    private String name;
	
	@ApiModelProperty(value = "商品状态", required = true)
    private ProductStatusEnum status;
    
	@ApiModelProperty(value = "库存", required = true)
    private String totalInventory;
    
	@ApiModelProperty(value = "销量", required = true)
    private String totalSalesVolume;
    
	@ApiModelProperty(value = "收藏", required = true)
    private String totalFavorite;
	
	@ApiModelProperty(value = "创建时间", required = true)
    private String gmtCreate;
	
	@ApiModelProperty(value = "封面图片", required = true)
    private String featureImage;
	
	@ApiModelProperty(value = "商品型号(PC){id:型号ID,name:型号名称,originalPrice:原价,price:现价,inventory:库存}", required = true)
    private String spec;
	
	@ApiModelProperty(value = "最低价格", required = true)
	private String minPrice;
	
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
	public ProductStatusEnum getStatus() {
		return status;
	}
	public void setStatus(ProductStatusEnum status) {
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
	public String getTotalInventory() {
		return totalInventory;
	}
	public void setTotalInventory(String totalInventory) {
		this.totalInventory = totalInventory;
	}
	public String getTotalSalesVolume() {
		return totalSalesVolume;
	}
	public void setTotalSalesVolume(String totalSalesVolume) {
		this.totalSalesVolume = totalSalesVolume;
	}
	public String getTotalFavorite() {
		return totalFavorite;
	}
	public void setTotalFavorite(String totalFavorite) {
		this.totalFavorite = totalFavorite;
	}
	public String getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}
	
}
