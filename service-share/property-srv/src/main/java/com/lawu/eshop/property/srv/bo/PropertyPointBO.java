package com.lawu.eshop.property.srv.bo;

import java.io.Serializable;

/**
 * @author Sunny
 * @date 2017/3/31
 */
public class PropertyPointBO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
    * 积分
    */
   private Integer point;

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}
   
}
