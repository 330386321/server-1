package com.lawu.eshop.product.dto;

/**
 * 用户端商品详情封装DTO
 *
 * @author Leach
 * @date 2017/3/22
 */
public class ProductInfoDTO {

    private Long id;
    private Long merchantId;
    private String name;
	private String content;
    private Integer totalSales;//销量
    private String featureImage;
    private String imagesUrl;
    private String spec;
    private String priceMax;
    private String priceMin;
    
    private String isSupportEleven;//是否支持七天无理由退货
    private String evaluateJson;//商品评价json字符串
    
	public String getIsSupportEleven() {
		return isSupportEleven;
	}
	public void setIsSupportEleven(String isSupportEleven) {
		this.isSupportEleven = isSupportEleven;
	}
	public String getEvaluateJson() {
		return evaluateJson;
	}
	public void setEvaluateJson(String evaluateJson) {
		this.evaluateJson = evaluateJson;
	}
	public Integer getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(Integer totalSales) {
		this.totalSales = totalSales;
	}
	public String getPriceMax() {
		return priceMax;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public void setPriceMax(String priceMax) {
		this.priceMax = priceMax;
	}
	public String getPriceMin() {
		return priceMin;
	}
	public void setPriceMin(String priceMin) {
		this.priceMin = priceMin;
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
