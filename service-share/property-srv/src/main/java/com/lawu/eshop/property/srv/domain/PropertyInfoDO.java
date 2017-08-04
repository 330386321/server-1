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
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * 用户编号
     * property_info.user_num
     *
     * @mbg.generated
     */
    private String userNum;

    /**
     *
     * 余额
     * property_info.balance
     *
     * @mbg.generated
     */
    private BigDecimal balance;

    /**
     *
     * 积分
     * property_info.point
     *
     * @mbg.generated
     */
    private BigDecimal point;

    /**
     *
     * 爱心账户
     * property_info.love_account
     *
     * @mbg.generated
     */
    private BigDecimal loveAccount;

    /**
     *
     * 冻结资金
     * property_info.freeze_money
     *
     * @mbg.generated
     */
    private BigDecimal freezeMoney;

    /**
     *
     * 支付密码
     * property_info.pay_password
     *
     * @mbg.generated
     */
    private String payPassword;

    /**
     *
     * 是否冻结(0-否1-是)
     * property_info.freeze
     *
     * @mbg.generated
     */
    private Byte freeze;

    /**
     *
     * 修改时间
     * property_info.gmt_modified
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     *
     * 创建时间
     * property_info.gmt_create
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table property_info
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property_info.id
     *
     * @return the value of property_info.id
     *
     * @mbg.generated
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
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property_info.user_num
     *
     * @return the value of property_info.user_num
     *
     * @mbg.generated
     */
    public String getUserNum() {
        return userNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column property_info.user_num
     *
     * @param userNum the value for property_info.user_num
     *
     * @mbg.generated
     */
    public void setUserNum(String userNum) {
        this.userNum = userNum == null ? null : userNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property_info.balance
     *
     * @return the value of property_info.balance
     *
     * @mbg.generated
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
     * @mbg.generated
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
     * @mbg.generated
     */
    public BigDecimal getPoint() {
        return point;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column property_info.point
     *
     * @param point the value for property_info.point
     *
     * @mbg.generated
     */
    public void setPoint(BigDecimal point) {
        this.point = point;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property_info.love_account
     *
     * @return the value of property_info.love_account
     *
     * @mbg.generated
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
     * @mbg.generated
     */
    public void setLoveAccount(BigDecimal loveAccount) {
        this.loveAccount = loveAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property_info.freeze_money
     *
     * @return the value of property_info.freeze_money
     *
     * @mbg.generated
     */
    public BigDecimal getFreezeMoney() {
        return freezeMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column property_info.freeze_money
     *
     * @param freezeMoney the value for property_info.freeze_money
     *
     * @mbg.generated
     */
    public void setFreezeMoney(BigDecimal freezeMoney) {
        this.freezeMoney = freezeMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property_info.pay_password
     *
     * @return the value of property_info.pay_password
     *
     * @mbg.generated
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
     * @mbg.generated
     */
    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword == null ? null : payPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property_info.freeze
     *
     * @return the value of property_info.freeze
     *
     * @mbg.generated
     */
    public Byte getFreeze() {
        return freeze;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column property_info.freeze
     *
     * @param freeze the value for property_info.freeze
     *
     * @mbg.generated
     */
    public void setFreeze(Byte freeze) {
        this.freeze = freeze;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property_info.gmt_modified
     *
     * @return the value of property_info.gmt_modified
     *
     * @mbg.generated
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
     * @mbg.generated
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
     * @mbg.generated
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
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}