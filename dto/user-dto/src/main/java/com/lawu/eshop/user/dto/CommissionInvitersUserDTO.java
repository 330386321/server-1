package com.lawu.eshop.user.dto;

/**
 * 
 * <p>
 * Description: 计算提成时，查询上3级推荐用户的用户编号和等级
 * </p>
 * @author Yangqh
 * @date 2017年4月26日 下午5:28:24
 *
 */
public class CommissionInvitersUserDTO {

	private Integer level;//用户等级
	private String userNum;//用户编号
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getUserNum() {
		return userNum;
	}
	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
	
}
