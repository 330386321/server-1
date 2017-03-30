package com.lawu.eshop.product.srv.bo;

/**
 * 产品类别BO
 *
 * @author Yangqh
 * @date 2017/3/22
 */
public class ProductInfoBO {

    private Long id;
    private Long merchantId;
    private String name;
    private String content;
    private Integer totalSales;//销量
    private String featureImage;
    private String imagesHeadUrl;
    private String imageDetailUrl;
    private String spec;
    private String priceMax;
    private String priceMin;
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
	
	public String getImagesHeadUrl() {
		return imagesHeadUrl;
	}
	public void setImagesHeadUrl(String imagesHeadUrl) {
		this.imagesHeadUrl = imagesHeadUrl;
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
	public void setPriceMax(String priceMax) {
		this.priceMax = priceMax;
	}
	public String getPriceMin() {
		return priceMin;
	}
	public void setPriceMin(String priceMin) {
		this.priceMin = priceMin;
	}
	public String getImageDetailUrl() {
		return imageDetailUrl;
	}
	public void setImageDetailUrl(String imageDetailUrl) {
		this.imageDetailUrl = imageDetailUrl;
	}
    
}
