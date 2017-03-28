package com.lawu.eshop.product.param;

/**
 * 
 * <p>
 * Description: api调用server传参，包含图片url
 * </p>
 * @author Yangqh
 * @date 2017年3月27日 下午8:21:43
 *
 */
public class EditDataProductParam extends EditProductParam {
	
	private String featureImage;
	private String imageUrl;
	public String getFeatureImage() {
		return featureImage;
	}
	public void setFeatureImage(String featureImage) {
		this.featureImage = featureImage;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
}
