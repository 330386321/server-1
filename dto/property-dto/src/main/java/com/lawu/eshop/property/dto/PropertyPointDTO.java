package com.lawu.eshop.property.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Sunny
 * @date 2017/3/31
 */
public class PropertyPointDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
    * 积分
    */
	@ApiModelProperty(name = "point", value= "积分", required = true)
   private Integer point;

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}
   
}
