package com.lawu.eshop.mall.dto.foreign;

import java.io.Serializable;
import java.math.BigDecimal;

import com.lawu.eshop.mall.constants.ShoppingOrderItemRefundStatusEnum;
import com.lawu.eshop.mall.constants.ShoppingRefundTypeEnum;

import io.swagger.annotations.ApiModelProperty;

public class ShoppingOrderItemRefundDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商家ID
	 */
	@ApiModelProperty(value = "商家ID", required = true)
	private Long merchantId;

	/**
	 * 商家名称
	 */
	@ApiModelProperty(value = "商家名称", required = true)
	private String merchantName;

	/**
	 * 退款类型
	 */
	@ApiModelProperty(value = "退款类型|REFUND 退款|RETURN_REFUND 退货退款", required = true)
	private ShoppingRefundTypeEnum type;

	/**
	 * 退款金额
	 */
	@ApiModelProperty(value = "退款金额", required = true)
	private BigDecimal amount;

	/**
	 * 商品名称
	 */
	@ApiModelProperty(value = "商品名称", required = true)
	private String productName;

	/**
	 * 商品型号名称
	 */
	@ApiModelProperty(value = "商品型号名称", required = true)
	private String productModelName;

	/**
	 * 商品特征图片
	 */
	@ApiModelProperty(value = "商品特征图片", required = true)
	private String productFeatureImage;

	/**
	 * 数量
	 */
	@ApiModelProperty(value = "数量", required = true)
	private Integer quantity;

	/**
	 * 退款状态
	 */
	@ApiModelProperty(value = "退款状态|TO_BE_CONFIRMED 待商家确认|FILL_RETURN_ADDRESS 填写退货地址|TO_BE_RETURNED 待退货|TO_BE_REFUNDED 待退款|REFUND_SUCCESSFULLY 退款成功|REFUND_FAILED 退款失败|PLATFORM_INTERVENTION 平台介入")
	private ShoppingOrderItemRefundStatusEnum refundStatus;

	/**
	 * 购物退款详情id
	 */
	@ApiModelProperty(value = "购物退款详情id", required = true)
	private Long shoppingRefundDetailId;
	
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public ShoppingRefundTypeEnum getType() {
		return type;
	}

	public void setType(ShoppingRefundTypeEnum type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductModelName() {
		return productModelName;
	}

	public void setProductModelName(String productModelName) {
		this.productModelName = productModelName;
	}

	public String getProductFeatureImage() {
		return productFeatureImage;
	}

	public void setProductFeatureImage(String productFeatureImage) {
		this.productFeatureImage = productFeatureImage;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public ShoppingOrderItemRefundStatusEnum getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(ShoppingOrderItemRefundStatusEnum refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Long getShoppingRefundDetailId() {
		return shoppingRefundDetailId;
	}

	public void setShoppingRefundDetailId(Long shoppingRefundDetailId) {
		this.shoppingRefundDetailId = shoppingRefundDetailId;
	}
	
}