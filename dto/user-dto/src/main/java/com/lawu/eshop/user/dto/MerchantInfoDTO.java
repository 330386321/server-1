package com.lawu.eshop.user.dto;

import com.lawu.eshop.user.constants.MerchantAuditStatusEnum;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商家扩展信息表
 * Created by zhangyong on 2017/3/23.
 */
public class MerchantInfoDTO {


    /**
     * 邀请会员数
     */
    @ApiModelProperty(name = "inviteMemberCount",value = "邀请会员数")
    private Integer inviteMemberCount;

    /**
     * 邀请商家数
     */
    @ApiModelProperty(name = "inviteMerchantCount",value = "邀请商家数")
    private Integer inviteMerchantCount;

    /**
     * 头像
     */
    @ApiModelProperty(name = "headimg",value = "头像")
    private String headimg;

    /**
     * 账号
     */
    @ApiModelProperty(name = "account",value = "账号")
    private String account;

    /**
     * 负责人名字
     */
    @ApiModelProperty(name = "principalName ",value = "负责人名字")
    private String principalName;

    @ApiModelProperty(name = "merchantStoreId ",value = "门店ID")
    private Long merchantStoreId;

    @ApiModelProperty(name = "merchantStoreId ",value = "门店审核状态")
    private MerchantAuditStatusEnum auditStatusEnum;

    @ApiModelProperty(name = "storeTypeEnum ",value = "门店类型:NORMAL_MERCHANT:普通商铺,ENTITY_MERCHANT:实体商铺")
    private MerchantStoreTypeEnum storeTypeEnum;

    @ApiModelProperty(name = "certifTypeEnum ",value = "门店类型:CERTIF_TYPE_IDCARD:身份证,CERTIF_TYPE_LICENSE:营业执照")
    private CertifTypeEnum certifTypeEnum;

    @ApiModelProperty(name = "gtCid ",value = "个推CID")
    private String gtCid;

    @ApiModelProperty(name = "ryToken ",value = "融云token")
    private String ryToken;

    public Integer getInviteMemberCount() {
        return inviteMemberCount;
    }

    public void setInviteMemberCount(Integer inviteMemberCount) {
        this.inviteMemberCount = inviteMemberCount;
    }

    public Integer getInviteMerchantCount() {
        return inviteMerchantCount;
    }

    public void setInviteMerchantCount(Integer inviteMerchantCount) {
        this.inviteMerchantCount = inviteMerchantCount;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public Long getMerchantStoreId() {
        return merchantStoreId;
    }

    public void setMerchantStoreId(Long merchantStoreId) {
        this.merchantStoreId = merchantStoreId;
    }

    public MerchantAuditStatusEnum getAuditStatusEnum() {
        return auditStatusEnum;
    }

    public void setAuditStatusEnum(MerchantAuditStatusEnum auditStatusEnum) {
        this.auditStatusEnum = auditStatusEnum;
    }

    public MerchantStoreTypeEnum getStoreTypeEnum() {
        return storeTypeEnum;
    }

    public void setStoreTypeEnum(MerchantStoreTypeEnum storeTypeEnum) {
        this.storeTypeEnum = storeTypeEnum;
    }

    public CertifTypeEnum getCertifTypeEnum() {
        return certifTypeEnum;
    }

    public void setCertifTypeEnum(CertifTypeEnum certifTypeEnum) {
        this.certifTypeEnum = certifTypeEnum;
    }

    public String getGtCid() {
        return gtCid;
    }

    public void setGtCid(String gtCid) {
        this.gtCid = gtCid;
    }

    public String getRyToken() {
        return ryToken;
    }

    public void setRyToken(String ryToken) {
        this.ryToken = ryToken;
    }
}
