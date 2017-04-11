package com.lawu.eshop.user.srv.bo;

import java.math.BigDecimal;
import java.util.Date;

import com.lawu.eshop.user.dto.MerchantStatusEnum;

/**
 * 商家门店信息
 * Created by Administrator on 2017/3/24.
 */
public class MerchantStoreInfoBO {

    private Long merchantStoreId;
    /**
     *
     * 店铺名称
     * merchant_store.name
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    private String name;

    /**
     *
     * 省市区
     * merchant_store.region_path
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    private String regionPath;

    /**
     *
     * 店铺地址信息
     * merchant_store.address
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    private String address;

    /**
     *
     * 经度
     * merchant_store.longitude
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    private BigDecimal longitude;

    /**
     *
     * 纬度
     * merchant_store.latitude
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    private BigDecimal latitude;

    /**
     *
     * 主营业务
     * merchant_store.industry_path
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    private String industryPath;

    /**
     *
     * 店铺介绍
     * merchant_store.intro
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    private String intro;

    /**
     * 负责人
     *
     */
    private String principalName;

    /**
     * 负责人电话
     */
    private String principalMobile;

    /**
     * 注册公司名称
     */
    private String companyName;

    /**
     * 营业执照号码
     */
    private String regNumber;

    /**
     * 经营住所
     */
    private String companyAddress;

    /**
     * 营业执照有效期
     */
    private Date licenseIndate;

    /**
     * 经营类型
     */
    private Byte manageType;

    /**
     * 证件类型
     */
    private Byte certifType;

    /**
     *
     * 个人经营者身份证号码
     */
    private String operatorCardId;

    /**
     * 个人经营者姓名
     */
    private String operatorName;

    /**
     * 图片类型
     */
    private Byte type;

    /**
     * 图片路径
     */
    private String path;
    
    /**
    *
    * 是否支持无理由退货,0否 1是
    * merchant_store.is_no_reason_return
    *
    * @mbg.generated 2017-03-30 10:55:26
    */
    private Boolean isNoReasonReturn;

    /**
     * 门店状态
     */
    private MerchantStatusEnum statusEnum;

    /**
     * 门店审核状态
     */
    private boolean auditSuccess = false;

    public Long getMerchantStoreId() {
        return merchantStoreId;
    }

    public void setMerchantStoreId(Long merchantStoreId) {
        this.merchantStoreId = merchantStoreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegionPath() {
        return regionPath;
    }

    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getIndustryPath() {
        return industryPath;
    }

    public void setIndustryPath(String industryPath) {
        this.industryPath = industryPath;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getPrincipalMobile() {
        return principalMobile;
    }

    public void setPrincipalMobile(String principalMobile) {
        this.principalMobile = principalMobile;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public Date getLicenseIndate() {
        return licenseIndate;
    }

    public void setLicenseIndate(Date licenseIndate) {
        this.licenseIndate = licenseIndate;
    }

    public Byte getManageType() {
        return manageType;
    }

    public void setManageType(Byte manageType) {
        this.manageType = manageType;
    }

    public Byte getCertifType() {
        return certifType;
    }

    public void setCertifType(Byte certifType) {
        this.certifType = certifType;
    }

    public String getOperatorCardId() {
        return operatorCardId;
    }

    public void setOperatorCardId(String operatorCardId) {
        this.operatorCardId = operatorCardId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

	public Boolean getIsNoReasonReturn() {
		return isNoReasonReturn;
	}

	public void setIsNoReasonReturn(Boolean isNoReasonReturn) {
		this.isNoReasonReturn = isNoReasonReturn;
	}

    public MerchantStatusEnum getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(MerchantStatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }

    public boolean isAuditSuccess() {
        return auditSuccess;
    }

    public void setAuditSuccess(boolean auditSuccess) {
        this.auditSuccess = auditSuccess;
    }
}
