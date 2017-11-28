package com.lawu.eshop.product.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 抢购活动商品不通过参数
 * 
 * @author jiangxinjun
 * @createDate 2017年11月27日
 * @updateDate 2017年11月27日
 */
@ApiModel
public class SeckillActivityProductNotPassedParam {
    
    /**
     * 反馈原因
     */
    @ApiModelProperty(value = "反馈原因", required = false)
    private String reasons;

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }
    
}
