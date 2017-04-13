package com.lawu.eshop.user.dto;

import com.lawu.eshop.user.constants.MerchantAuditStatusEnum;
import io.swagger.annotations.ApiParam;

/**
 * 商家扩展信息表
 * Created by zhangyong on 2017/3/23.
 */
public class MerchantInfoDTO {


    /**
     * 邀请会员数
     */
    @ApiParam(name = "inviteMemberCount",value = "邀请会员数")
    private Integer inviteMemberCount;

    /**
     * 邀请商家数
     */
    @ApiParam(name = "inviteMerchantCount",value = "邀请商家数")
    private Integer inviteMerchantCount;

    /**
     * 头像
     */
    @ApiParam(name = "headimg",value = "头像")
    private String headimg;

    /**
     * 账号
     */
    @ApiParam(name = "account",value = "账号")
    private String account;

    /**
     * 负责人名字
     */
    @ApiParam(name = "principalName ",value = "负责人名字")
    private String principalName;

    @ApiParam(name = "merchantStoreId ",value = "门店ID")
    private Long merchantStoreId;

    @ApiParam(name = "merchantStoreId ",value = "门店审核状态")
    private MerchantAuditStatusEnum auditStatusEnum;

    @ApiParam(name = "merchantStoreId ",value = "门店类型:NORMAL_MERCHANT:普通商铺,ENTITY_MERCHANT:实体商铺")
    private MerchantStoreTypeEnum storeTypeEnum;

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
}
