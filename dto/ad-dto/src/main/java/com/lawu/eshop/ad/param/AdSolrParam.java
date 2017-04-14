package com.lawu.eshop.ad.param;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangrc
 * @date 2017/4/13.
 */
public class AdSolrParam extends AbstractPageParam {


    @ApiModelProperty(value = "广告标题")
    private String tilte;

	public String getTilte() {
		return tilte;
	}

	public void setTilte(String tilte) {
		this.tilte = tilte;
	}

    
}
