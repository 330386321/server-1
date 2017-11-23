package com.lawu.eshop.product.srv.domain;

import java.io.Serializable;
import java.util.Date;

public class SeckillActivityAttentionDO implements Serializable {
    /**
     *
     * 
     * seckill_activity_attention.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * 抢购活动id
     * seckill_activity_attention.activity_id
     *
     * @mbg.generated
     */
    private Long activityId;

    /**
     *
     * 商品id
     * seckill_activity_attention.product_id
     *
     * @mbg.generated
     */
    private Long productId;

    /**
     *
     * 用户id
     * seckill_activity_attention.member_id
     *
     * @mbg.generated
     */
    private Long memberId;

    /**
     *
     * 是否提醒(0-未提醒|1-已提醒)
     * seckill_activity_attention.is_remind
     *
     * @mbg.generated
     */
    private Boolean isRemind;

    /**
     *
     * 修改时间
     * seckill_activity_attention.gmt_modified
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     *
     * 创建时间
     * seckill_activity_attention.gmt_create
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table seckill_activity_attention
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column seckill_activity_attention.id
     *
     * @return the value of seckill_activity_attention.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column seckill_activity_attention.id
     *
     * @param id the value for seckill_activity_attention.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column seckill_activity_attention.activity_id
     *
     * @return the value of seckill_activity_attention.activity_id
     *
     * @mbg.generated
     */
    public Long getActivityId() {
        return activityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column seckill_activity_attention.activity_id
     *
     * @param activityId the value for seckill_activity_attention.activity_id
     *
     * @mbg.generated
     */
    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column seckill_activity_attention.product_id
     *
     * @return the value of seckill_activity_attention.product_id
     *
     * @mbg.generated
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column seckill_activity_attention.product_id
     *
     * @param productId the value for seckill_activity_attention.product_id
     *
     * @mbg.generated
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column seckill_activity_attention.member_id
     *
     * @return the value of seckill_activity_attention.member_id
     *
     * @mbg.generated
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column seckill_activity_attention.member_id
     *
     * @param memberId the value for seckill_activity_attention.member_id
     *
     * @mbg.generated
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column seckill_activity_attention.is_remind
     *
     * @return the value of seckill_activity_attention.is_remind
     *
     * @mbg.generated
     */
    public Boolean getIsRemind() {
        return isRemind;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column seckill_activity_attention.is_remind
     *
     * @param isRemind the value for seckill_activity_attention.is_remind
     *
     * @mbg.generated
     */
    public void setIsRemind(Boolean isRemind) {
        this.isRemind = isRemind;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column seckill_activity_attention.gmt_modified
     *
     * @return the value of seckill_activity_attention.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column seckill_activity_attention.gmt_modified
     *
     * @param gmtModified the value for seckill_activity_attention.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column seckill_activity_attention.gmt_create
     *
     * @return the value of seckill_activity_attention.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column seckill_activity_attention.gmt_create
     *
     * @param gmtCreate the value for seckill_activity_attention.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}