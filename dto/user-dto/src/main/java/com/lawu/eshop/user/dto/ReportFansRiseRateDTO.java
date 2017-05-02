package com.lawu.eshop.user.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年5月2日 下午2:43:39
 *
 */
public class ReportFansRiseRateDTO {

    @ApiModelProperty(value = "报表x轴")
    private List<String> x;

    @ApiModelProperty(value = "报表y轴")
    private List<String> y;

	public List<String> getX() {
		return x;
	}

	public void setX(List<String> x) {
		this.x = x;
	}

	public List<String> getY() {
		return y;
	}

	public void setY(List<String> y) {
		this.y = y;
	}

   
}
