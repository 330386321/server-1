package com.lawu.eshop.property.srv.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BusinessDepositDO implements Serializable {
    /**
     *
     * 
     * business_deposit.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * 商家ID
     * business_deposit.business_id
     *
     * @mbg.generated
     */
    private Long businessId;

    /**
     *
     * 商家编号
     * business_deposit.user_num
     *
     * @mbg.generated
     */
    private String userNum;

    /**
     *
     * 商家账号
     * business_deposit.business_account
     *
     * @mbg.generated
     */
    private String businessAccount;

    /**
     *
     * 负责人姓名
     * business_deposit.business_name
     *
     * @mbg.generated
     */
    private String businessName;

    /**
     *
     * 商户订单号
     * business_deposit.deposit_number
     *
     * @mbg.generated
     */
    private String depositNumber;

    /**
     *
     * 第三方订单号
     * business_deposit.third_number
     *
     * @mbg.generated
     */
    private String thirdNumber;

    /**
     *
     * 第三方平台支付账户
     * business_deposit.third_account
     *
     * @mbg.generated
     */
    private String thirdAccount;

    /**
     *
     * 充值方式：2-支付宝,3微信
     * business_deposit.payment_method
     *
     * @mbg.generated
     */
    private Byte paymentMethod;

    /**
     *
     * 金额
     * business_deposit.amount
     *
     * @mbg.generated
     */
    private BigDecimal amount;

    /**
     *
     * 状态(0待支付 1未核实 2已核实 3申请退款 4受理退款 5退款成功 6退款失败)
     * business_deposit.status
     *
     * @mbg.generated
     */
    private Byte status;

    /**
     *
     * 提现银行卡关联ID
     * business_deposit.business_bank_account_id
     *
     * @mbg.generated
     */
    private Long businessBankAccountId;

    /**
     *
     * 注册区域省ID(冗余)
     * business_deposit.province_id
     *
     * @mbg.generated
     */
    private Long provinceId;

    /**
     *
     * 注册区域城市ID(冗余)
     * business_deposit.city_id
     *
     * @mbg.generated
     */
    private Long cityId;

    /**
     *
     * 注册区域区ID(冗余)
     * business_deposit.area_id
     *
     * @mbg.generated
     */
    private Long areaId;

    /**
     *
     * 初始化时间
     * business_deposit.gmt_create
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     *
     * 付款回调时间
     * business_deposit.gmt_pay
     *
     * @mbg.generated
     */
    private Date gmtPay;

    /**
     *
     * 核实时间
     * business_deposit.gmt_verify
     *
     * @mbg.generated
     */
    private Date gmtVerify;

    /**
     *
     * 退款时间
     * business_deposit.gmt_refund
     *
     * @mbg.generated
     */
    private Date gmtRefund;

    /**
     *
     * 受理时间
     * business_deposit.gmt_accpet
     *
     * @mbg.generated
     */
    private Date gmtAccpet;

    /**
     *
     * 修改时间
     * business_deposit.gmt_modified
     *
     * @mbg.generated
     */
    private Date gmtModified;

    /**
     *
     * 处理人ID
     * business_deposit.oper_user_id
     *
     * @mbg.generated
     */
    private Long operUserId;

    /**
     *
     * 处理人姓名
     * business_deposit.oper_user_name
     *
     * @mbg.generated
     */
    private String operUserName;

    /**
     *
     * 备注
     * business_deposit.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table business_deposit
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.id
     *
     * @return the value of business_deposit.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.id
     *
     * @param id the value for business_deposit.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.business_id
     *
     * @return the value of business_deposit.business_id
     *
     * @mbg.generated
     */
    public Long getBusinessId() {
        return businessId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.business_id
     *
     * @param businessId the value for business_deposit.business_id
     *
     * @mbg.generated
     */
    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.user_num
     *
     * @return the value of business_deposit.user_num
     *
     * @mbg.generated
     */
    public String getUserNum() {
        return userNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.user_num
     *
     * @param userNum the value for business_deposit.user_num
     *
     * @mbg.generated
     */
    public void setUserNum(String userNum) {
        this.userNum = userNum == null ? null : userNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.business_account
     *
     * @return the value of business_deposit.business_account
     *
     * @mbg.generated
     */
    public String getBusinessAccount() {
        return businessAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.business_account
     *
     * @param businessAccount the value for business_deposit.business_account
     *
     * @mbg.generated
     */
    public void setBusinessAccount(String businessAccount) {
        this.businessAccount = businessAccount == null ? null : businessAccount.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.business_name
     *
     * @return the value of business_deposit.business_name
     *
     * @mbg.generated
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.business_name
     *
     * @param businessName the value for business_deposit.business_name
     *
     * @mbg.generated
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName == null ? null : businessName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.deposit_number
     *
     * @return the value of business_deposit.deposit_number
     *
     * @mbg.generated
     */
    public String getDepositNumber() {
        return depositNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.deposit_number
     *
     * @param depositNumber the value for business_deposit.deposit_number
     *
     * @mbg.generated
     */
    public void setDepositNumber(String depositNumber) {
        this.depositNumber = depositNumber == null ? null : depositNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.third_number
     *
     * @return the value of business_deposit.third_number
     *
     * @mbg.generated
     */
    public String getThirdNumber() {
        return thirdNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.third_number
     *
     * @param thirdNumber the value for business_deposit.third_number
     *
     * @mbg.generated
     */
    public void setThirdNumber(String thirdNumber) {
        this.thirdNumber = thirdNumber == null ? null : thirdNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.third_account
     *
     * @return the value of business_deposit.third_account
     *
     * @mbg.generated
     */
    public String getThirdAccount() {
        return thirdAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.third_account
     *
     * @param thirdAccount the value for business_deposit.third_account
     *
     * @mbg.generated
     */
    public void setThirdAccount(String thirdAccount) {
        this.thirdAccount = thirdAccount == null ? null : thirdAccount.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.payment_method
     *
     * @return the value of business_deposit.payment_method
     *
     * @mbg.generated
     */
    public Byte getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.payment_method
     *
     * @param paymentMethod the value for business_deposit.payment_method
     *
     * @mbg.generated
     */
    public void setPaymentMethod(Byte paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.amount
     *
     * @return the value of business_deposit.amount
     *
     * @mbg.generated
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.amount
     *
     * @param amount the value for business_deposit.amount
     *
     * @mbg.generated
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.status
     *
     * @return the value of business_deposit.status
     *
     * @mbg.generated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.status
     *
     * @param status the value for business_deposit.status
     *
     * @mbg.generated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.business_bank_account_id
     *
     * @return the value of business_deposit.business_bank_account_id
     *
     * @mbg.generated
     */
    public Long getBusinessBankAccountId() {
        return businessBankAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.business_bank_account_id
     *
     * @param businessBankAccountId the value for business_deposit.business_bank_account_id
     *
     * @mbg.generated
     */
    public void setBusinessBankAccountId(Long businessBankAccountId) {
        this.businessBankAccountId = businessBankAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.province_id
     *
     * @return the value of business_deposit.province_id
     *
     * @mbg.generated
     */
    public Long getProvinceId() {
        return provinceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.province_id
     *
     * @param provinceId the value for business_deposit.province_id
     *
     * @mbg.generated
     */
    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.city_id
     *
     * @return the value of business_deposit.city_id
     *
     * @mbg.generated
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.city_id
     *
     * @param cityId the value for business_deposit.city_id
     *
     * @mbg.generated
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.area_id
     *
     * @return the value of business_deposit.area_id
     *
     * @mbg.generated
     */
    public Long getAreaId() {
        return areaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.area_id
     *
     * @param areaId the value for business_deposit.area_id
     *
     * @mbg.generated
     */
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.gmt_create
     *
     * @return the value of business_deposit.gmt_create
     *
     * @mbg.generated
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.gmt_create
     *
     * @param gmtCreate the value for business_deposit.gmt_create
     *
     * @mbg.generated
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.gmt_pay
     *
     * @return the value of business_deposit.gmt_pay
     *
     * @mbg.generated
     */
    public Date getGmtPay() {
        return gmtPay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.gmt_pay
     *
     * @param gmtPay the value for business_deposit.gmt_pay
     *
     * @mbg.generated
     */
    public void setGmtPay(Date gmtPay) {
        this.gmtPay = gmtPay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.gmt_verify
     *
     * @return the value of business_deposit.gmt_verify
     *
     * @mbg.generated
     */
    public Date getGmtVerify() {
        return gmtVerify;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.gmt_verify
     *
     * @param gmtVerify the value for business_deposit.gmt_verify
     *
     * @mbg.generated
     */
    public void setGmtVerify(Date gmtVerify) {
        this.gmtVerify = gmtVerify;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.gmt_refund
     *
     * @return the value of business_deposit.gmt_refund
     *
     * @mbg.generated
     */
    public Date getGmtRefund() {
        return gmtRefund;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.gmt_refund
     *
     * @param gmtRefund the value for business_deposit.gmt_refund
     *
     * @mbg.generated
     */
    public void setGmtRefund(Date gmtRefund) {
        this.gmtRefund = gmtRefund;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.gmt_accpet
     *
     * @return the value of business_deposit.gmt_accpet
     *
     * @mbg.generated
     */
    public Date getGmtAccpet() {
        return gmtAccpet;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.gmt_accpet
     *
     * @param gmtAccpet the value for business_deposit.gmt_accpet
     *
     * @mbg.generated
     */
    public void setGmtAccpet(Date gmtAccpet) {
        this.gmtAccpet = gmtAccpet;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.gmt_modified
     *
     * @return the value of business_deposit.gmt_modified
     *
     * @mbg.generated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.gmt_modified
     *
     * @param gmtModified the value for business_deposit.gmt_modified
     *
     * @mbg.generated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.oper_user_id
     *
     * @return the value of business_deposit.oper_user_id
     *
     * @mbg.generated
     */
    public Long getOperUserId() {
        return operUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.oper_user_id
     *
     * @param operUserId the value for business_deposit.oper_user_id
     *
     * @mbg.generated
     */
    public void setOperUserId(Long operUserId) {
        this.operUserId = operUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.oper_user_name
     *
     * @return the value of business_deposit.oper_user_name
     *
     * @mbg.generated
     */
    public String getOperUserName() {
        return operUserName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.oper_user_name
     *
     * @param operUserName the value for business_deposit.oper_user_name
     *
     * @mbg.generated
     */
    public void setOperUserName(String operUserName) {
        this.operUserName = operUserName == null ? null : operUserName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column business_deposit.remark
     *
     * @return the value of business_deposit.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column business_deposit.remark
     *
     * @param remark the value for business_deposit.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}