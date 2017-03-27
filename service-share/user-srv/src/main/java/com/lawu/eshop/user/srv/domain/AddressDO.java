package com.lawu.eshop.user.srv.domain;

import java.io.Serializable;
import java.util.Date;

public class AddressDO implements Serializable {
    /**
     *
     * 主键
     * address.id
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    private Long id;

    /**
     *
     * 用户ID
     * address.user_id
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    private Long userId;

    /**
     *
     * 默认，0否，1是
     * address.is_default
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    private Boolean isDefault;

    /**
     *
     * 收件人
     * address.name
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    private String name;

    /**
     *
     * 手机
     * address.mobile
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    private String mobile;

    /**
     *
     * 省市区
     * address.region_path
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    private String regionPath;

    /**
     *
     * 地址，除省市县外
     * address.addr
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    private String addr;

    /**
     *
     * 邮编
     * address.postcode
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    private String postcode;

    /**
     *
     * 备注
     * address.remark
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    private String remark;

    /**
     *
     * 状态
     * address.status
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    private Byte status;

    /**
     *
     * 修改时间
     * address.gmt_modified
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    private Date gmtModified;

    /**
     *
     * 创建时间
     * address.gmt_create
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table address
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column address.id
     *
     * @return the value of address.id
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column address.id
     *
     * @param id the value for address.id
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column address.user_id
     *
     * @return the value of address.user_id
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column address.user_id
     *
     * @param userId the value for address.user_id
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column address.is_default
     *
     * @return the value of address.is_default
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column address.is_default
     *
     * @param isDefault the value for address.is_default
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column address.name
     *
     * @return the value of address.name
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column address.name
     *
     * @param name the value for address.name
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column address.mobile
     *
     * @return the value of address.mobile
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column address.mobile
     *
     * @param mobile the value for address.mobile
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column address.region_path
     *
     * @return the value of address.region_path
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public String getRegionPath() {
        return regionPath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column address.region_path
     *
     * @param regionPath the value for address.region_path
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath == null ? null : regionPath.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column address.addr
     *
     * @return the value of address.addr
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public String getAddr() {
        return addr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column address.addr
     *
     * @param addr the value for address.addr
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column address.postcode
     *
     * @return the value of address.postcode
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column address.postcode
     *
     * @param postcode the value for address.postcode
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column address.remark
     *
     * @return the value of address.remark
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column address.remark
     *
     * @param remark the value for address.remark
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column address.status
     *
     * @return the value of address.status
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column address.status
     *
     * @param status the value for address.status
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column address.gmt_modified
     *
     * @return the value of address.gmt_modified
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column address.gmt_modified
     *
     * @param gmtModified the value for address.gmt_modified
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column address.gmt_create
     *
     * @return the value of address.gmt_create
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column address.gmt_create
     *
     * @param gmtCreate the value for address.gmt_create
     *
     * @mbg.generated 2017-03-27 10:54:42
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}