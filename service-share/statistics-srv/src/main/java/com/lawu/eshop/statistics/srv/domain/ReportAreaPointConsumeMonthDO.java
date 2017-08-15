package com.lawu.eshop.statistics.srv.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ReportAreaPointConsumeMonthDO implements Serializable {
    /**
     *
     * 主键
     * report_area_point_consume_month.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * 会员充值积分
     * report_area_point_consume_month.member_recharge_point
     *
     * @mbg.generated
     */
    private BigDecimal memberRechargePoint;

    /**
     *
     * 商家充值积分
     * report_area_point_consume_month.merchant_recharge_point
     *
     * @mbg.generated
     */
    private BigDecimal merchantRechargePoint;

    /**
     *
     * 总充值积分
     * report_area_point_consume_month.total_recharge_point
     *
     * @mbg.generated
     */
    private BigDecimal totalRechargePoint;

    /**
     *
     * 会员积分消费
     * report_area_point_consume_month.member_point
     *
     * @mbg.generated
     */
    private BigDecimal memberPoint;

    /**
     *
     * 商家积分消费
     * report_area_point_consume_month.merchant_point
     *
     * @mbg.generated
     */
    private BigDecimal merchantPoint;

    /**
     *
     * 总积分消费
     * report_area_point_consume_month.total_point
     *
     * @mbg.generated
     */
    private BigDecimal totalPoint;

    /**
     *
     * 省编号
     * report_area_point_consume_month.province_id
     *
     * @mbg.generated
     */
    private Integer provinceId;

    /**
     *
     * 市编号
     * report_area_point_consume_month.city_id
     *
     * @mbg.generated
     */
    private Integer cityId;

    /**
     *
     * 区编号
     * report_area_point_consume_month.area_id
     *
     * @mbg.generated
     */
    private Integer areaId;

    /**
     *
     * 统计数据所属日期
     * report_area_point_consume_month.gmt_report
     *
     * @mbg.generated
     */
    private Date gmtReport;

    /**
     *
     * 统计时间
     * report_area_point_consume_month.gmt_create
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table report_area_point_consume_month
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_area_point_consume_month.id
     *
     * @return the value of report_area_point_consume_month.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_area_point_consume_month.id
     *
     * @param id the value for report_area_point_consume_month.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_area_point_consume_month.member_recharge_point
     *
     * @return the value of report_area_point_consume_month.member_recharge_point
     *
     * @mbg.generated
     */
    public BigDecimal getMemberRechargePoint() {
        return memberRechargePoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_area_point_consume_month.member_recharge_point
     *
     * @param memberRechargePoint the value for report_area_point_consume_month.member_recharge_point
     *
     * @mbg.generated
     */
    public void setMemberRechargePoint(BigDecimal memberRechargePoint) {
        this.memberRechargePoint = memberRechargePoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_area_point_consume_month.merchant_recharge_point
     *
     * @return the value of report_area_point_consume_month.merchant_recharge_point
     *
     * @mbg.generated
     */
    public BigDecimal getMerchantRechargePoint() {
        return merchantRechargePoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_area_point_consume_month.merchant_recharge_point
     *
     * @param merchantRechargePoint the value for report_area_point_consume_month.merchant_recharge_point
     *
     * @mbg.generated
     */
    public void setMerchantRechargePoint(BigDecimal merchantRechargePoint) {
        this.merchantRechargePoint = merchantRechargePoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_area_point_consume_month.total_recharge_point
     *
     * @return the value of report_area_point_consume_month.total_recharge_point
     *
     * @mbg.generated
     */
    public BigDecimal getTotalRechargePoint() {
        return totalRechargePoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_area_point_consume_month.total_recharge_point
     *
     * @param totalRechargePoint the value for report_area_point_consume_month.total_recharge_point
     *
     * @mbg.generated
     */
    public void setTotalRechargePoint(BigDecimal totalRechargePoint) {
        this.totalRechargePoint = totalRechargePoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_area_point_consume_month.member_point
     *
     * @return the value of report_area_point_consume_month.member_point
     *
     * @mbg.generated
     */
    public BigDecimal getMemberPoint() {
        return memberPoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_area_point_consume_month.member_point
     *
     * @param memberPoint the value for report_area_point_consume_month.member_point
     *
     * @mbg.generated
     */
    public void setMemberPoint(BigDecimal memberPoint) {
        this.memberPoint = memberPoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_area_point_consume_month.merchant_point
     *
     * @return the value of report_area_point_consume_month.merchant_point
     *
     * @mbg.generated
     */
    public BigDecimal getMerchantPoint() {
        return merchantPoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_area_point_consume_month.merchant_point
     *
     * @param merchantPoint the value for report_area_point_consume_month.merchant_point
     *
     * @mbg.generated
     */
    public void setMerchantPoint(BigDecimal merchantPoint) {
        this.merchantPoint = merchantPoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_area_point_consume_month.total_point
     *
     * @return the value of report_area_point_consume_month.total_point
     *
     * @mbg.generated
     */
    public BigDecimal getTotalPoint() {
        return totalPoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_area_point_consume_month.total_point
     *
     * @param totalPoint the value for report_area_point_consume_month.total_point
     *
     * @mbg.generated
     */
    public void setTotalPoint(BigDecimal totalPoint) {
        this.totalPoint = totalPoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_area_point_consume_month.province_id
     *
     * @return the value of report_area_point_consume_month.province_id
     *
     * @mbg.generated
     */
    public Integer getProvinceId() {
        return provinceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_area_point_consume_month.province_id
     *
     * @param provinceId the value for report_area_point_consume_month.province_id
     *
     * @mbg.generated
     */
    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_area_point_consume_month.city_id
     *
     * @return the value of report_area_point_consume_month.city_id
     *
     * @mbg.generated
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_area_point_consume_month.city_id
     *
     * @param cityId the value for report_area_point_consume_month.city_id
     *
     * @mbg.generated
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_area_point_consume_month.area_id
     *
     * @return the value of report_area_point_consume_month.area_id
     *
     * @mbg.generated
     */
    public Integer getAreaId() {
        return areaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_area_point_consume_month.area_id
     *
     * @param areaId the value for report_area_point_consume_month.area_id
     *
     * @mbg.generated
     */
    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_area_point_consume_month.gmt_report
     *
     * @return the value of report_area_point_consume_month.gmt_report
     *
     * @mbg.generated
     */
    public Date getGmtReport() {
        return gmtReport;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_area_point_consume_month.gmt_report
     *
     * @param gmtReport the value for report_area_point_consume_month.gmt_report
     *
     * @mbg.generated
     */
    public void setGmtReport(Date gmtReport) {
        this.gmtReport = gmtReport;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column report_area_point_consume_month.gmt_create
     *
     * @return the value of report_area_point_consume_month.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column report_area_point_consume_month.gmt_create
     *
     * @param gmtCreate the value for report_area_point_consume_month.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}