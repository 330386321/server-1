package com.lawu.eshop.property.param;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * <p>
 * Description: 商家申请退保证金
 * </p>
 * @author Yangqh
 * @date 2017年4月15日 下午4:40:13
 *
 */
public class BusinessRefundDepositDataParam extends BusinessRefundDepositParam{

	@NotBlank(message="userName不能为空")
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	

}
