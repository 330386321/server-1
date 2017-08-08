/**
 * 
 */
package com.lawu.eshop.ad.param;

import com.lawu.eshop.framework.core.page.AbstractPageParam;

import io.swagger.annotations.ApiParam;

/**
 * 查询用户红包param
 * 
 * @author lihj
 * @date 2017年8月4日
 */
public class UserRedPacketSelectParam extends AbstractPageParam {

	@ApiParam(name = "userNum", required = true, value = "用户num")
	private String userNum;

	/**
	 * @return the userNum
	 */
	public String getUserNum() {
		return userNum;
	}

	/**
	 * @param userNum
	 *            the userNum to set
	 */
	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

}
