package com.lawu.eshop.user.srv.domain;

import java.io.Serializable;
import java.util.Date;

public class MerchantStoreProfileDO implements Serializable {
    /**
     *
     * 主键
     * merchant_store_profile.id
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    private Long id;

    /**
     *
     * 商家
     * merchant_store_profile.merchant_id
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    private Long merchantId;

    /**
     *
     * 注册公司名称
     * merchant_store_profile.company_name
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    private String companyName;

    /**
     *
     * 营业执照号码
     * merchant_store_profile.reg_number
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    private String regNumber;

    /**
     *
     * 经营住所
     * merchant_store_profile.company_address
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    private String companyAddress;

    /**
     *
     * 营业执照有效期
     * merchant_store_profile.license_indate
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    private Date licenseIndate;

    /**
     *
     * 经营类型
     * merchant_store_profile.manage_type
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    private Byte manageType;

    /**
     *
     * 证件类型
     * merchant_store_profile.certif_type
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    private Byte certifType;

    /**
     *
     * 个人经营者身份证号码
     * merchant_store_profile.operator_card_id
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    private String operatorCardId;

    /**
     *
     * 个人经营者姓名
     * merchant_store_profile.operator_name
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    private String operatorName;

    /**
     *
     * 修改时间
     * merchant_store_profile.gmt_modified
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    private Date gmtModified;

    /**
     *
     * 创建时间
     * merchant_store_profile.gmt_create
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table merchant_store_profile
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_store_profile.id
     *
     * @return the value of merchant_store_profile.id
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_store_profile.id
     *
     * @param id the value for merchant_store_profile.id
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_store_profile.merchant_id
     *
     * @return the value of merchant_store_profile.merchant_id
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public Long getMerchantId() {
        return merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_store_profile.merchant_id
     *
     * @param merchantId the value for merchant_store_profile.merchant_id
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_store_profile.company_name
     *
     * @return the value of merchant_store_profile.company_name
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_store_profile.company_name
     *
     * @param companyName the value for merchant_store_profile.company_name
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_store_profile.reg_number
     *
     * @return the value of merchant_store_profile.reg_number
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public String getRegNumber() {
        return regNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_store_profile.reg_number
     *
     * @param regNumber the value for merchant_store_profile.reg_number
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber == null ? null : regNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_store_profile.company_address
     *
     * @return the value of merchant_store_profile.company_address
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public String getCompanyAddress() {
        return companyAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_store_profile.company_address
     *
     * @param companyAddress the value for merchant_store_profile.company_address
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress == null ? null : companyAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_store_profile.license_indate
     *
     * @return the value of merchant_store_profile.license_indate
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public Date getLicenseIndate() {
        return licenseIndate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_store_profile.license_indate
     *
     * @param licenseIndate the value for merchant_store_profile.license_indate
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public void setLicenseIndate(Date licenseIndate) {
        this.licenseIndate = licenseIndate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_store_profile.manage_type
     *
     * @return the value of merchant_store_profile.manage_type
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public Byte getManageType() {
        return manageType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_store_profile.manage_type
     *
     * @param manageType the value for merchant_store_profile.manage_type
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public void setManageType(Byte manageType) {
        this.manageType = manageType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_store_profile.certif_type
     *
     * @return the value of merchant_store_profile.certif_type
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public Byte getCertifType() {
        return certifType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_store_profile.certif_type
     *
     * @param certifType the value for merchant_store_profile.certif_type
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public void setCertifType(Byte certifType) {
        this.certifType = certifType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_store_profile.operator_card_id
     *
     * @return the value of merchant_store_profile.operator_card_id
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public String getOperatorCardId() {
        return operatorCardId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_store_profile.operator_card_id
     *
     * @param operatorCardId the value for merchant_store_profile.operator_card_id
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public void setOperatorCardId(String operatorCardId) {
        this.operatorCardId = operatorCardId == null ? null : operatorCardId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_store_profile.operator_name
     *
     * @return the value of merchant_store_profile.operator_name
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_store_profile.operator_name
     *
     * @param operatorName the value for merchant_store_profile.operator_name
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_store_profile.gmt_modified
     *
     * @return the value of merchant_store_profile.gmt_modified
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_store_profile.gmt_modified
     *
     * @param gmtModified the value for merchant_store_profile.gmt_modified
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_store_profile.gmt_create
     *
     * @return the value of merchant_store_profile.gmt_create
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_store_profile.gmt_create
     *
     * @param gmtCreate the value for merchant_store_profile.gmt_create
     *
     * @mbg.generated 2017-03-24 17:37:11
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}