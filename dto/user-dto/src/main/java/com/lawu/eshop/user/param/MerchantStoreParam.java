package com.lawu.eshop.user.param;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * Created by zhangyong on 2017/3/27.
 */
public class MerchantStoreParam {


    /**
     * 店铺名称
     */
    private  String name;

    /**
     * 省市区
     */
    private String regionPath;

    /**
     * 店铺地址信息
     */
    private String address;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 主营业务
     */
    private String industryPath;

    /**
     * 店铺介绍
     */
    private String intro;

    /**
     * 负责人名字
     */
    private String principalName;

    /**
     * 负责人手机
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
     * 个人经营者身份证号码
     */
    private String operatorCardId;

    /**
     * 个人经营者姓名
     */
    private String operatorName;

    /**
     * 门店照
     */
    private String storeUrl;
    /**
     * 门店环境照
     */
    private String environmentUrl ;
    /**
     * 身份证照
     */
    private String idcardUrl;
    /**
     * 营业执照照片
     */
    private String licenseUrl;
    /**
     * 门店logo
     */
    private String logoUrl;
    /**
     * 其他照片
     */
    private String otherUrl;


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

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public String getEnvironmentUrl() {
        return environmentUrl;
    }

    public void setEnvironmentUrl(String environmentUrl) {
        this.environmentUrl = environmentUrl;
    }

    public String getIdcardUrl() {
        return idcardUrl;
    }

    public void setIdcardUrl(String idcardUrl) {
        this.idcardUrl = idcardUrl;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getOtherUrl() {
        return otherUrl;
    }

    public void setOtherUrl(String otherUrl) {
        this.otherUrl = otherUrl;
    }
}
