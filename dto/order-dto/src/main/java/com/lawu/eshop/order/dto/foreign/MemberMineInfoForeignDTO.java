package com.lawu.eshop.order.dto.foreign;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author Sunny
 * @date 2017年4月21日
 */
@ApiModel
public class MemberMineInfoForeignDTO extends ShoppingOrderNumberOfOrderStatusDTO implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	/**
	 * 昵称
	 */
    @ApiModelProperty(value = "昵称", required = true)
    private String nickname;
	
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", required = true)
    private String headimg;
    
    /**
     * 等级
     */
    @ApiModelProperty(value = "等级", required = true)
    private Integer level;
    
	/**
	 * E友总数
	 */
	@ApiModelProperty(value = "E友总数", required = true)
	private Integer inviteeMemberCount;
	
	/**
	 * 邀请的商家总数
	 */
	@ApiModelProperty(value = "邀请的商家总数", required = true)
	private Integer inviteeMechantCount;
	
	/**
	 * 爱心账户
	 */
	@ApiModelProperty(value= "爱心账户", required = true)
	private BigDecimal loveAccount;
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getInviteeMemberCount() {
		return inviteeMemberCount;
	}

	public void setInviteeMemberCount(Integer inviteeMemberCount) {
		this.inviteeMemberCount = inviteeMemberCount;
	}

	public Integer getInviteeMechantCount() {
		return inviteeMechantCount;
	}

	public void setInviteeMechantCount(Integer inviteeMechantCount) {
		this.inviteeMechantCount = inviteeMechantCount;
	}

	public BigDecimal getLoveAccount() {
		return loveAccount;
	}

	public void setLoveAccount(BigDecimal loveAccount) {
		this.loveAccount = loveAccount;
	}
	
}