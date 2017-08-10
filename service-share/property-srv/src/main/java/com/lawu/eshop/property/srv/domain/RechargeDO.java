package com.lawu.eshop.property.srv.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RechargeDO implements Serializable {
    /**
     *
     * 主键
     * recharge.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * 用户编号
     * recharge.user_num
     *
     * @mbg.generated
     */
    private String userNum;

    /**
     *
     * 充值金额/积分
     * recharge.recharge_money
     *
     * @mbg.generated
     */
    private BigDecimal rechargeMoney;

    /**
     *
     * 当前充值比例
     * recharge.current_scale
     *
     * @mbg.generated
     */
    private String currentScale;

    /**
     *
     * 充值所得金额/积分
     * recharge.money
     *
     * @mbg.generated
     */
    private BigDecimal money;

    /**
     *
     * 充值类型：1-余额,2-积分
     * recharge.recharge_type
     *
     * @mbg.generated
     */
    private Byte rechargeType;

    /**
     *
     * 充值方式：2-支付宝,3微信
     * recharge.channel
     *
     * @mbg.generated
     */
    private Byte channel;

    /**
     *
     * 1-待支付,2-成功,3-失败
     * recharge.status
     *
     * @mbg.generated
     */
    private Byte status;

    /**
     *
     * 充值单号
     * recharge.recharge_number
     *
     * @mbg.generated
     */
    private String rechargeNumber;

    /**
     *
     * 第三方支付的订单号
     * recharge.third_number
     *
     * @mbg.generated
     */
    private String thirdNumber;

    /**
     *
     * 省ID
     * recharge.province_id
     *
     * @mbg.generated
     */
    private Integer provinceId;

    /**
     *
     * 市ID
     * recharge.city_id
     *
     * @mbg.generated
     */
    private Integer cityId;

    /**
     *
     * 区ID
     * recharge.area_id
     *
     * @mbg.generated
     */
    private Integer areaId;

    /**
     *
     * 创建日期
     * recharge.gmt_create
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     *
     * 修改日期
     * recharge.gmt_modified
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     *
     * 备注
     * recharge.note
     *
     * @mbg.generated
     */
    private String note;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table recharge
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recharge.id
     *
     * @return the value of recharge.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recharge.id
     *
     * @param id the value for recharge.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recharge.user_num
     *
     * @return the value of recharge.user_num
     *
     * @mbg.generated
     */
    public String getUserNum() {
        return userNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recharge.user_num
     *
     * @param userNum the value for recharge.user_num
     *
     * @mbg.generated
     */
    public void setUserNum(String userNum) {
        this.userNum = userNum == null ? null : userNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recharge.recharge_money
     *
     * @return the value of recharge.recharge_money
     *
     * @mbg.generated
     */
    public BigDecimal getRechargeMoney() {
        return rechargeMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recharge.recharge_money
     *
     * @param rechargeMoney the value for recharge.recharge_money
     *
     * @mbg.generated
     */
    public void setRechargeMoney(BigDecimal rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recharge.current_scale
     *
     * @return the value of recharge.current_scale
     *
     * @mbg.generated
     */
    public String getCurrentScale() {
        return currentScale;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recharge.current_scale
     *
     * @param currentScale the value for recharge.current_scale
     *
     * @mbg.generated
     */
    public void setCurrentScale(String currentScale) {
        this.currentScale = currentScale == null ? null : currentScale.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recharge.money
     *
     * @return the value of recharge.money
     *
     * @mbg.generated
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recharge.money
     *
     * @param money the value for recharge.money
     *
     * @mbg.generated
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recharge.recharge_type
     *
     * @return the value of recharge.recharge_type
     *
     * @mbg.generated
     */
    public Byte getRechargeType() {
        return rechargeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recharge.recharge_type
     *
     * @param rechargeType the value for recharge.recharge_type
     *
     * @mbg.generated
     */
    public void setRechargeType(Byte rechargeType) {
        this.rechargeType = rechargeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recharge.channel
     *
     * @return the value of recharge.channel
     *
     * @mbg.generated
     */
    public Byte getChannel() {
        return channel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recharge.channel
     *
     * @param channel the value for recharge.channel
     *
     * @mbg.generated
     */
    public void setChannel(Byte channel) {
        this.channel = channel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recharge.status
     *
     * @return the value of recharge.status
     *
     * @mbg.generated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recharge.status
     *
     * @param status the value for recharge.status
     *
     * @mbg.generated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recharge.recharge_number
     *
     * @return the value of recharge.recharge_number
     *
     * @mbg.generated
     */
    public String getRechargeNumber() {
        return rechargeNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recharge.recharge_number
     *
     * @param rechargeNumber the value for recharge.recharge_number
     *
     * @mbg.generated
     */
    public void setRechargeNumber(String rechargeNumber) {
        this.rechargeNumber = rechargeNumber == null ? null : rechargeNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recharge.third_number
     *
     * @return the value of recharge.third_number
     *
     * @mbg.generated
     */
    public String getThirdNumber() {
        return thirdNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recharge.third_number
     *
     * @param thirdNumber the value for recharge.third_number
     *
     * @mbg.generated
     */
    public void setThirdNumber(String thirdNumber) {
        this.thirdNumber = thirdNumber == null ? null : thirdNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recharge.province_id
     *
     * @return the value of recharge.province_id
     *
     * @mbg.generated
     */
    public Integer getProvinceId() {
        return provinceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recharge.province_id
     *
     * @param provinceId the value for recharge.province_id
     *
     * @mbg.generated
     */
    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recharge.city_id
     *
     * @return the value of recharge.city_id
     *
     * @mbg.generated
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recharge.city_id
     *
     * @param cityId the value for recharge.city_id
     *
     * @mbg.generated
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recharge.area_id
     *
     * @return the value of recharge.area_id
     *
     * @mbg.generated
     */
    public Integer getAreaId() {
        return areaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recharge.area_id
     *
     * @param areaId the value for recharge.area_id
     *
     * @mbg.generated
     */
    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recharge.gmt_create
     *
     * @return the value of recharge.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recharge.gmt_create
     *
     * @param gmtCreate the value for recharge.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recharge.gmt_modified
     *
     * @return the value of recharge.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recharge.gmt_modified
     *
     * @param gmtModified the value for recharge.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column recharge.note
     *
     * @return the value of recharge.note
     *
     * @mbg.generated
     */
    public String getNote() {
        return note;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column recharge.note
     *
     * @param note the value for recharge.note
     *
     * @mbg.generated
     */
    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}