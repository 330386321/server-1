package com.lawu.eshop.user.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author meishuquan
 * @date 2017/3/23
 */
public class MerchantSNSDTO {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "编号")
    private String num;

    @ApiModelProperty(value = "头像")
    private String headimg;

    @ApiModelProperty(value = "负责人")
    private String principalName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	
   
}
