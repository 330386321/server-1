package com.lawu.eshop.user.srv.domain;

import java.io.Serializable;
import java.util.Date;

public class MerchantDO implements Serializable {
    /**
     *
     * 主键
     * merchant.id
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    private Long id;

    /**
     *
     * 商家编号
     * merchant.num
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    private String num;

    /**
     *
     * 账号
     * merchant.account
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    private String account;

    /**
     *
     * 密码
     * merchant.pwd
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    private String pwd;

    /**
     *
     * 手机号码
     * merchant.mobile
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    private String mobile;

    /**
     *
     * 头像
     * merchant.headimg
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    private String headimg;

    /**
     *
     * 状态 (0--无效，1--有效)
     * merchant.status
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    private Byte status;

    /**
     *
     * 邀请者ID
     * merchant.inviter_id
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    private Long inviterId;

    /**
     *
     * 邀请者类型 (1--会员，2--商户)
     * merchant.inviter_type
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    private Byte inviterType;

    /**
     *
     * 等级
     * merchant.level
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    private Integer level;

    /**
     *
     * 资金账户ID
     * merchant.property_id
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    private Long propertyId;

    /**
     *
     * 修改时间
     * merchant.gmt_modified
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    private Date gmtModified;

    /**
     *
     * 创建时间
     * merchant.gmt_create
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table merchant
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant.id
     *
     * @return the value of merchant.id
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant.id
     *
     * @param id the value for merchant.id
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant.num
     *
     * @return the value of merchant.num
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public String getNum() {
        return num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant.num
     *
     * @param num the value for merchant.num
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant.account
     *
     * @return the value of merchant.account
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant.account
     *
     * @param account the value for merchant.account
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant.pwd
     *
     * @return the value of merchant.pwd
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant.pwd
     *
     * @param pwd the value for merchant.pwd
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant.mobile
     *
     * @return the value of merchant.mobile
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant.mobile
     *
     * @param mobile the value for merchant.mobile
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant.headimg
     *
     * @return the value of merchant.headimg
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public String getHeadimg() {
        return headimg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant.headimg
     *
     * @param headimg the value for merchant.headimg
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public void setHeadimg(String headimg) {
        this.headimg = headimg == null ? null : headimg.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant.status
     *
     * @return the value of merchant.status
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant.status
     *
     * @param status the value for merchant.status
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant.inviter_id
     *
     * @return the value of merchant.inviter_id
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public Long getInviterId() {
        return inviterId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant.inviter_id
     *
     * @param inviterId the value for merchant.inviter_id
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant.inviter_type
     *
     * @return the value of merchant.inviter_type
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public Byte getInviterType() {
        return inviterType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant.inviter_type
     *
     * @param inviterType the value for merchant.inviter_type
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public void setInviterType(Byte inviterType) {
        this.inviterType = inviterType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant.level
     *
     * @return the value of merchant.level
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant.level
     *
     * @param level the value for merchant.level
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant.property_id
     *
     * @return the value of merchant.property_id
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public Long getPropertyId() {
        return propertyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant.property_id
     *
     * @param propertyId the value for merchant.property_id
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant.gmt_modified
     *
     * @return the value of merchant.gmt_modified
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant.gmt_modified
     *
     * @param gmtModified the value for merchant.gmt_modified
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant.gmt_create
     *
     * @return the value of merchant.gmt_create
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant.gmt_create
     *
     * @param gmtCreate the value for merchant.gmt_create
     *
     * @mbg.generated 2017-03-28 16:56:26
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}