package com.lawu.eshop.property.param;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月12日 下午8:42:22
 *
 */
public class RechargeSaveDataParam extends RechargeSaveParam{

	@NotBlank(message = "userNum不能为空")
	private String userNum;
	
	private String rechargeScale;
	
	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public String getRechargeScale() {
		return rechargeScale;
	}

	public void setRechargeScale(String rechargeScale) {
		this.rechargeScale = rechargeScale;
	}

	

}