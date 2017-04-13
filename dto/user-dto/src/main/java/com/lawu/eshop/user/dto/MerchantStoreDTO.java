package com.lawu.eshop.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiParam;

import java.math.BigDecimal;
import java.util.Date;

public class MerchantStoreDTO {


    @ApiParam(name = "merchantStoreId", value = "门店id")
    private Long merchantStoreId;
    /**
     * 店铺名称
     * merchant_store.name
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    @ApiParam(name = "name", value = "店铺名称")
    private String name;

    /**
     * 省市区
     * merchant_store.region_path
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    @ApiParam(name = "regionPath", value = "省市区")
    private String regionPath;

    /**
     * 店铺地址信息
     * merchant_store.address
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    @ApiParam(name = "address", value = "店铺地址信息")
    private String address;

    /**
     * 经度
     * merchant_store.longitude
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    @ApiParam(name = "longitude", value = "经度")
    private BigDecimal longitude;

    /**
     * 纬度
     * merchant_store.latitude
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    @ApiParam(name = "latitude", value = "纬度")
    private BigDecimal latitude;

    /**
     * 主营业务
     * merchant_store.industry_path
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    @ApiParam(name = "industryPath", value = "主营业务")
    private String industryPath;

    /**
     * 店铺介绍
     * merchant_store.intro
     *
     * @mbg.generated 2017-03-24 10:29:55
     */
    @ApiParam(name = "intro", value = "店铺介绍")
    private String intro;

    /**
     * 负责人
     */
    @ApiParam(name = "principalName", value = "负责人")
    private String principalName;

    /**
     * 负责人电话
     */
    @ApiParam(name = "principalMobile", value = "负责人电话")
    private String principalMobile;

    /**
     * 注册公司名称
     */
    @ApiParam(name = "companyName", value = "注册公司名称")
    private String companyName;

    /**
     * 营业执照号码
     */
    @ApiParam(name = "regNumber", value = "营业执照号码")
    private String regNumber;

    /**
     * 经营住所
     */
    @ApiParam(name = "companyAddress", value = "经营住所")
    private String companyAddress;

    /**
     * 营业执照有效期
     */
    @ApiParam(name = "licenseIndate", value = "营业执照有效期")
    private Date licenseIndate;

    /**
     * 经营类型
     */
    @ApiParam(name = "manageType", value = "经营类型")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private MerchantStoreTypeEnum manageType;

    /**
     * 证件类型
     */
    @ApiParam(name = "certifType", value = "证件类型")
    private CertifTypeEnum certifType;

    /**
     * 个人经营者身份证号码
     */
    @ApiParam(name = "operatorCardId", value = "个人经营者身份证号码")
    private String operatorCardId;

    /**
     * 个人经营者姓名
     */
    @ApiParam(name = "operatorName", value = "个人经营者姓名")
    private String operatorName;

    /**
     * 图片类型
     */
    @ApiParam(name = "type", value = "图片类型")
    private MerchantStoreImageEnum type;

    /**
     * 图片路径
     */
    @ApiParam(name = "path", value = "图片路径")
    private String path;

    /**
     * 门店状态
     */
    @ApiParam(name = "merchantStatus", value = "门店状态:MERCHANT_STATUS_UNCHECK:未审核,MERCHANT_STATUS_CHECKED:审核通过,MERCHANT_STATUS_CHECK_FAILED:审核不通过,MERCHANT_STATUS_NOT_MONEY:未提交保证金,MERCHANT_STATUS_GIVE_MONEY_CHECK:已提交保证金待财务核实,MERCHANT_STATUS_GIVE_MONEY_CHECK_FAILED:财务审核不通过")
    private MerchantStatusEnum merchantStatus;

    /**
     * 门店审核状态
     */
    @ApiParam(name = "auditSuccess", value = "门店审核状态 false未审核，true 已审核/审核")
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

    public MerchantStoreTypeEnum getManageType() {
        return manageType;
    }

    public void setManageType(MerchantStoreTypeEnum manageType) {
        this.manageType = manageType;
    }

    public CertifTypeEnum getCertifType() {
        return certifType;
    }

    public void setCertifType(CertifTypeEnum certifType) {
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

    public MerchantStoreImageEnum getType() {
        return type;
    }

    public void setType(MerchantStoreImageEnum type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MerchantStatusEnum getMerchantStatus() {
        return merchantStatus;
    }

    public void setMerchantStatus(MerchantStatusEnum merchantStatus) {
        this.merchantStatus = merchantStatus;
    }

    public boolean isAuditSuccess() {
        return auditSuccess;
    }

    public void setAuditSuccess(boolean auditSuccess) {
        this.auditSuccess = auditSuccess;
    }
}