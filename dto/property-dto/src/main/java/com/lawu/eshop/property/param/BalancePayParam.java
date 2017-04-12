package com.lawu.eshop.property.param;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 余额支付参数对象
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月11日 下午5:35:25
 *
 */
public class BalancePayParam {

	// 金额
	@NotBlank(message = "amount不能为空")
	@Pattern(regexp = "^\\d{0,8}\\.{0,1}(\\d{1,2})?$", message = "amount格式错误或小数位不超过2位")
	@ApiParam (name="amount",required = true, value = "金额")
	private String amount;

	// 交易标题
	@NotBlank(message = "title不能为空")
	@ApiParam (name="title",required = true, value = "标题")
	private String title;

	// 业务表ID(支持多个,用英文逗号分割)
	@NotBlank(message = "bizIds不能为空")
	@ApiParam (name="bizIds",required = true, value = "业务表ID(支持多个,用英文逗号分割)")
	private String bizIds;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBizIds() {
		return bizIds;
	}

	public void setBizIds(String bizIds) {
		this.bizIds = bizIds;
	}

}