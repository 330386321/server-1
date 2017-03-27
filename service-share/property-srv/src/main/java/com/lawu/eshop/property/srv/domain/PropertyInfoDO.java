package com.lawu.eshop.property.srv.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PropertyInfoDO implements Serializable {
    /**
     *
     * 主键
     * property_info.id
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    private Long id;

    /**
     *
     * 用户编号
     * property_info.user_no
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    private String userNo;

    /**
     *
     * 余额
     * property_info.balance
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    private BigDecimal balance;

    /**
     *
     * 积分
     * property_info.point
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    private Integer point;

    /**
     *
     * 爱心账户
     * property_info.love_account
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    private BigDecimal loveAccount;

    /**
     *
     * 支付密码
     * property_info.pay_password
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    private String payPassword;

    /**
     *
     * 修改时间
     * property_info.gmt_modified
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    private Date gmtModified;

    /**
     *
     * 创建时间
     * property_info.gmt_create
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table property_info
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property_info.id
     *
     * @return the value of property_info.id
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column property_info.id
     *
     * @param id the value for property_info.id
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property_info.user_no
     *
     * @return the value of property_info.user_no
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column property_info.user_no
     *
     * @param userNo the value for property_info.user_no
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property_info.balance
     *
     * @return the value of property_info.balance
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column property_info.balance
     *
     * @param balance the value for property_info.balance
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property_info.point
     *
     * @return the value of property_info.point
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    public Integer getPoint() {
        return point;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column property_info.point
     *
     * @param point the value for property_info.point
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    public void setPoint(Integer point) {
        this.point = point;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property_info.love_account
     *
     * @return the value of property_info.love_account
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    public BigDecimal getLoveAccount() {
        return loveAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column property_info.love_account
     *
     * @param loveAccount the value for property_info.love_account
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    public void setLoveAccount(BigDecimal loveAccount) {
        this.loveAccount = loveAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property_info.pay_password
     *
     * @return the value of property_info.pay_password
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    public String getPayPassword() {
        return payPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column property_info.pay_password
     *
     * @param payPassword the value for property_info.pay_password
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword == null ? null : payPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property_info.gmt_modified
     *
     * @return the value of property_info.gmt_modified
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column property_info.gmt_modified
     *
     * @param gmtModified the value for property_info.gmt_modified
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property_info.gmt_create
     *
     * @return the value of property_info.gmt_create
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column property_info.gmt_create
     *
     * @param gmtCreate the value for property_info.gmt_create
     *
     * @mbg.generated 2017-03-27 11:15:34
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}