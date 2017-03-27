package com.lawu.eshop.product.srv.bo;

/**
 * 产品类别BO
 *
 * @author Yangqh
 * @date 2017/3/22
 */
public class ProductInfoBO {

    private Long id;
    private String name;
    private String content;
    private Integer totalSales;//销量
    private String featureImage;
    private String imagesUrl;
    private String spec;
    private String priceMax;
    private String priceMin;
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
    
}
