package com.lawu.eshop.user.param;

import io.swagger.annotations.ApiParam;

public class MerchantStorePlatParam {
	
	/**
     * 店铺名称
     */
    @ApiParam(name = "name", value = "店铺名称")
    private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    

}
