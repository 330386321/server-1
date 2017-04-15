package com.lawu.eshop.ad.srv.domain;

import java.io.Serializable;
import java.util.Date;

public class MemberAdRecordDO implements Serializable {
    /**
     *
     * 主键
     * member_ad_record.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * 会员
     * member_ad_record.member_id
     *
     * @mbg.generated
     */
    private Long memberId;

    /**
     *
     * 广告
     * member_ad_record.ad_id
     *
     * @mbg.generated
     */
    private Long adId;

    /**
     *
     * 点击日期:年月日
     * member_ad_record.click_date
     *
     * @mbg.generated
     */
    private Date clickDate;

    /**
     *
     * 创建时间
     * member_ad_record.gmt_create
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table member_ad_record
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member_ad_record.id
     *
     * @return the value of member_ad_record.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member_ad_record.id
     *
     * @param id the value for member_ad_record.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member_ad_record.member_id
     *
     * @return the value of member_ad_record.member_id
     *
     * @mbg.generated
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member_ad_record.member_id
     *
     * @param memberId the value for member_ad_record.member_id
     *
     * @mbg.generated
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member_ad_record.ad_id
     *
     * @return the value of member_ad_record.ad_id
     *
     * @mbg.generated
     */
    public Long getAdId() {
        return adId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member_ad_record.ad_id
     *
     * @param adId the value for member_ad_record.ad_id
     *
     * @mbg.generated
     */
    public void setAdId(Long adId) {
        this.adId = adId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member_ad_record.click_date
     *
     * @return the value of member_ad_record.click_date
     *
     * @mbg.generated
     */
    public Date getClickDate() {
        return clickDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member_ad_record.click_date
     *
     * @param clickDate the value for member_ad_record.click_date
     *
     * @mbg.generated
     */
    public void setClickDate(Date clickDate) {
        this.clickDate = clickDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member_ad_record.gmt_create
     *
     * @return the value of member_ad_record.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member_ad_record.gmt_create
     *
     * @param gmtCreate the value for member_ad_record.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}