package com.lawu.eshop.mall.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangyong
 * @date 2017/4/7.
 */
public class CommentGradeDTO {

    @ApiModelProperty(value = "综合评分")
    private Double avgGrade;

    @ApiModelProperty(value = "好评率")
    private Double goodGrad;

    public Double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(Double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public Double getGoodGrad() {
        return goodGrad;
    }

    public void setGoodGrad(Double goodGrad) {
        this.goodGrad = goodGrad;
    }
}
