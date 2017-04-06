package com.lawu.eshop.user.dto;

import com.lawu.eshop.user.constants.UserSexEnum;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
public class FansMerchantDTO {

    @ApiModelProperty(value = "会员ID")
    private Long memberId;

    @ApiModelProperty(value = "会员编号")
    private String num;

    @ApiModelProperty(value = "会员账号")
    private String account;

    @ApiModelProperty(value = "性别")
    private UserSexEnum userSexEnum;

    @ApiModelProperty(value = "区域路劲")
    private String regionPath;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "消费次数")
    private Integer consumeCount;

    @ApiModelProperty(value = "消费金额")
    private Double consumeAmount;

    @ApiModelProperty(value = "最近消费时间")
    private Date lastConsumeTime;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public UserSexEnum getUserSexEnum() {
        return userSexEnum;
    }

    public void setUserSexEnum(UserSexEnum userSexEnum) {
        this.userSexEnum = userSexEnum;
    }

    public String getRegionPath() {
        return regionPath;
    }

    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Integer getConsumeCount() {
        return consumeCount;
    }

    public void setConsumeCount(Integer consumeCount) {
        this.consumeCount = consumeCount;
    }

    public Double getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(Double consumeAmount) {
        this.consumeAmount = consumeAmount;
    }

    public Date getLastConsumeTime() {
        return lastConsumeTime;
    }

    public void setLastConsumeTime(Date lastConsumeTime) {
        this.lastConsumeTime = lastConsumeTime;
    }
}
