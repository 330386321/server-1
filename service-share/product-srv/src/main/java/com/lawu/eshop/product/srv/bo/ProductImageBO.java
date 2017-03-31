package com.lawu.eshop.product.srv.bo;

/**
 * 产品图片BO
 *
 * @author Sunny
 * @date 2017/3/31
 */
public class ProductImageBO {
	
	/**
	 * 产品ID
	 */
	private Long id;
	
	/**
	 * 特征图片
	 */
	private String featureImage;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFeatureImage() {
		return featureImage;
	}

	public void setFeatureImage(String featureImage) {
		this.featureImage = featureImage;
	}

}
