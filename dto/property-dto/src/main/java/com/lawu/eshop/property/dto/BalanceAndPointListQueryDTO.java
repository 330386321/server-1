package com.lawu.eshop.property.dto;

import java.math.BigDecimal;

import com.lawu.eshop.property.constants.BusinessDepositStatusEnum;

import io.swagger.annotations.ApiModelProperty;

public class BalanceAndPointListQueryDTO {

	@ApiModelProperty(value = "支付时间")
	private String gmtPay;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "单号")
	private String num;

	@ApiModelProperty(value = "第三方订单号")
	private String thirdNumber;

	@ApiModelProperty(value = "负责人姓名")
	private String BusinessName;
	
	@ApiModelProperty(value = "商家编号")
	private String userNum;

	@ApiModelProperty(value = "金额")
	private BigDecimal amount;

	@ApiModelProperty(value = "支付方式")
	private String payMethod;

	@ApiModelProperty(value = "状态枚举")
	private BusinessDepositStatusEnum businessDepositStatusEnum;
	
	@ApiModelProperty(value = "状态")
	private String status;
	
	

}
