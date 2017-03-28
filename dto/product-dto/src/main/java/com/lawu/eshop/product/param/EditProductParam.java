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

	@ApiModelProperty(value = "商品型号信息", required = true)
	private String spec;
	
	@ApiModelProperty(value = "特征图片url(新增时为空)", required = false)
	private String featureImageStr;
	
	@ApiModelProperty(value = "商品图片(新增时为空)", required = false)
	private String iamgeStr;
	
	public String getFeatureImageStr() {
		return featureImageStr;
	}

	public void setFeatureImageStr(String featureImageStr) {
		this.featureImageStr = featureImageStr;
	}

	public String getIamgeStr() {
		return iamgeStr;
	}

	public void setIamgeStr(String iamgeStr) {
		this.iamgeStr = iamgeStr;
	}

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

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

}
