package com.lawu.eshop.mall.srv.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MerchantFavoredDO implements Serializable {
    /**
     *
     * 主键
     * merchant_favored.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * 商家ID
     * merchant_favored.merchant_id
     *
     * @mbg.generated
     */
    private Long merchantId;

    /**
     *
     * 1:每满、2:满减、3:全单折扣
     * merchant_favored.type
     *
     * @mbg.generated
     */
    private Byte type;

    /**
     *
     * 满额
     * merchant_favored.reach_amount
     *
     * @mbg.generated
     */
    private BigDecimal reachAmount;

    /**
     *
     * 优惠金额
     * merchant_favored.favored_amount
     *
     * @mbg.generated
     */
    private BigDecimal favoredAmount;

    /**
     *
     * 折扣率
     * merchant_favored.discount_rate
     *
     * @mbg.generated
     */
    private BigDecimal discountRate;

    /**
     *
     * 每周有效时间段
     * merchant_favored.valid_week_time
     *
     * @mbg.generated
     */
    private String validWeekTime;

    /**
     *
     * 每日有效开始时间
     * merchant_favored.valid_day_begin_time
     *
     * @mbg.generated
     */
    private String validDayBeginTime;

    /**
     *
     * 每日有效结束时间
     * merchant_favored.valid_day_end_time
     *
     * @mbg.generated
     */
    private String validDayEndTime;

    /**
     *
     * 总有效期：开始时间
     * merchant_favored.entire_begin_time
     *
     * @mbg.generated
     */
    private Date entireBeginTime;

    /**
     *
     * 总有效期：结束时间
     * merchant_favored.entire_end_time
     *
     * @mbg.generated
     */
    private Date entireEndTime;

    /**
     *
     * 状态（1：有效0：无效）
     * merchant_favored.status
     *
     * @mbg.generated
     */
    private Byte status;

    /**
     *
     * 修改时间
     * merchant_favored.gmt_modified
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     *
     * 创建时间
     * merchant_favored.gmt_create
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table merchant_favored
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_favored.id
     *
     * @return the value of merchant_favored.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_favored.id
     *
     * @param id the value for merchant_favored.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_favored.merchant_id
     *
     * @return the value of merchant_favored.merchant_id
     *
     * @mbg.generated
     */
    public Long getMerchantId() {
        return merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_favored.merchant_id
     *
     * @param merchantId the value for merchant_favored.merchant_id
     *
     * @mbg.generated
     */
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_favored.type
     *
     * @return the value of merchant_favored.type
     *
     * @mbg.generated
     */
    public Byte getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_favored.type
     *
     * @param type the value for merchant_favored.type
     *
     * @mbg.generated
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_favored.reach_amount
     *
     * @return the value of merchant_favored.reach_amount
     *
     * @mbg.generated
     */
    public BigDecimal getReachAmount() {
        return reachAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_favored.reach_amount
     *
     * @param reachAmount the value for merchant_favored.reach_amount
     *
     * @mbg.generated
     */
    public void setReachAmount(BigDecimal reachAmount) {
        this.reachAmount = reachAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_favored.favored_amount
     *
     * @return the value of merchant_favored.favored_amount
     *
     * @mbg.generated
     */
    public BigDecimal getFavoredAmount() {
        return favoredAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_favored.favored_amount
     *
     * @param favoredAmount the value for merchant_favored.favored_amount
     *
     * @mbg.generated
     */
    public void setFavoredAmount(BigDecimal favoredAmount) {
        this.favoredAmount = favoredAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_favored.discount_rate
     *
     * @return the value of merchant_favored.discount_rate
     *
     * @mbg.generated
     */
    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_favored.discount_rate
     *
     * @param discountRate the value for merchant_favored.discount_rate
     *
     * @mbg.generated
     */
    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_favored.valid_week_time
     *
     * @return the value of merchant_favored.valid_week_time
     *
     * @mbg.generated
     */
    public String getValidWeekTime() {
        return validWeekTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_favored.valid_week_time
     *
     * @param validWeekTime the value for merchant_favored.valid_week_time
     *
     * @mbg.generated
     */
    public void setValidWeekTime(String validWeekTime) {
        this.validWeekTime = validWeekTime == null ? null : validWeekTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_favored.valid_day_begin_time
     *
     * @return the value of merchant_favored.valid_day_begin_time
     *
     * @mbg.generated
     */
    public String getValidDayBeginTime() {
        return validDayBeginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_favored.valid_day_begin_time
     *
     * @param validDayBeginTime the value for merchant_favored.valid_day_begin_time
     *
     * @mbg.generated
     */
    public void setValidDayBeginTime(String validDayBeginTime) {
        this.validDayBeginTime = validDayBeginTime == null ? null : validDayBeginTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_favored.valid_day_end_time
     *
     * @return the value of merchant_favored.valid_day_end_time
     *
     * @mbg.generated
     */
    public String getValidDayEndTime() {
        return validDayEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_favored.valid_day_end_time
     *
     * @param validDayEndTime the value for merchant_favored.valid_day_end_time
     *
     * @mbg.generated
     */
    public void setValidDayEndTime(String validDayEndTime) {
        this.validDayEndTime = validDayEndTime == null ? null : validDayEndTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_favored.entire_begin_time
     *
     * @return the value of merchant_favored.entire_begin_time
     *
     * @mbg.generated
     */
    public Date getEntireBeginTime() {
        return entireBeginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_favored.entire_begin_time
     *
     * @param entireBeginTime the value for merchant_favored.entire_begin_time
     *
     * @mbg.generated
     */
    public void setEntireBeginTime(Date entireBeginTime) {
        this.entireBeginTime = entireBeginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_favored.entire_end_time
     *
     * @return the value of merchant_favored.entire_end_time
     *
     * @mbg.generated
     */
    public Date getEntireEndTime() {
        return entireEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_favored.entire_end_time
     *
     * @param entireEndTime the value for merchant_favored.entire_end_time
     *
     * @mbg.generated
     */
    public void setEntireEndTime(Date entireEndTime) {
        this.entireEndTime = entireEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_favored.status
     *
     * @return the value of merchant_favored.status
     *
     * @mbg.generated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_favored.status
     *
     * @param status the value for merchant_favored.status
     *
     * @mbg.generated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_favored.gmt_modified
     *
     * @return the value of merchant_favored.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_favored.gmt_modified
     *
     * @param gmtModified the value for merchant_favored.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_favored.gmt_create
     *
     * @return the value of merchant_favored.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_favored.gmt_create
     *
     * @param gmtCreate the value for merchant_favored.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}