package com.lawu.eshop.product.param;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawu.eshop.common.constants.MemberGradeEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 抢购活动更新参数
 * 
 * @author jiangxinjun
 * @createDate 2017年11月27日
 * @updateDate 2017年11月27日
 */
@ApiModel
public class SeckillActivityUpdateParam{
    
    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称", required = true)
    private String name;

    /**
     * 开始时间(yyyy-MM-dd HH:mm)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "开始时间(yyyy-MM-dd HH:mm)", required = true)
    private Date startDate;

    /**
     * 会员等级
     */
    @ApiModelProperty(value = "会员等级", required = true)
    private MemberGradeEnum memberLevel;
    
    /**
     * 活动定价
     */
    @ApiModelProperty(value = "活动定价", required = true)
    private BigDecimal sellingPrice;
    
    /**
     * 宣传图片
     */
    @ApiModelProperty(value = "宣传图片", required = true)
    private String picture;

    /**
     * 结束时间(yyyy-MM-dd HH:mm)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "结束时间(yyyy-MM-dd HH:mm)", required = true)
    private Date endDate;
    
    
    /**
     * 商家可提交审核的商品数
     */
    @ApiModelProperty(value = "商家可提交审核的商品数", required = true)
    private Integer productValidCount;


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Date getStartDate() {
        return startDate;
    }


    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public MemberGradeEnum getMemberLevel() {
        return memberLevel;
    }


    public void setMemberLevel(MemberGradeEnum memberLevel) {
        this.memberLevel = memberLevel;
    }


    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }


    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }


    public String getPicture() {
        return picture;
    }


    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getEndDate() {
        return endDate;
    }


    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public Integer getProductValidCount() {
        return productValidCount;
    }


    public void setProductValidCount(Integer productValidCount) {
        this.productValidCount = productValidCount;
    }
    
}