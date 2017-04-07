package com.lawu.eshop.property.param;

import java.io.Serializable;

/**
 * 
 * <p>
 * Description: pc调用支付宝前请求参数
 * </p>
 * @author Yangqh
 * @date 2017年4月7日 下午2:15:10
 *
 */
public class PcAlipayDataParam extends PcAlipayParam implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户编号
	 */
	private String userNum;

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	

}