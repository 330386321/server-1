package com.lawu.eshop.order.param;

import com.lawu.eshop.order.param.foreign.ShoppingOrderRequestRefundForeignParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 购物订单退货参数 api暴露给app参数
 * 
 * @author Sunny
 * @date 2017/4/11
 */
@ApiModel
public class ShoppingOrderRequestRefundParam extends ShoppingOrderRequestRefundForeignParam {

	/**
	 * 凭证图片
	 */
	@ApiModelProperty(required = false, value = "凭证图片")
	private String voucherPicture;

	public String getVoucherPicture() {
		return voucherPicture;
	}

	public void setVoucherPicture(String voucherPicture) {
		this.voucherPicture = voucherPicture;
	}

}
