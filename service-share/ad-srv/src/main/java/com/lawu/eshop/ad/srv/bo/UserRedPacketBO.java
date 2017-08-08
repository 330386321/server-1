/**
 * 
 */
package com.lawu.eshop.ad.srv.bo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lihj
 * @date 2017年8月4日
 */
public class UserRedPacketBO {
	/**
	 *
	 * 
	 * user_red_packet.id
	 *
	 * @mbg.generated
	 */
	private Long id;

	/**
	 *
	 * 用户num user_red_packet.user_num
	 *
	 * @mbg.generated
	 */
	private String userNum;

	/**
	 *
	 * 用户账号 user_red_packet.user_account
	 *
	 * @mbg.generated
	 */
	private String userAccount;

	/**
	 *
	 * 红包类型[0普通红包,1拼手气红包] user_red_packet.type
	 *
	 * @mbg.generated
	 */
	private Byte type;

	/**
	 *
	 * 红包总数 user_red_packet.total_count
	 *
	 * @mbg.generated
	 */
	private Integer totalCount;

	/**
	 *
	 * 总金额 user_red_packet.total_money
	 *
	 * @mbg.generated
	 */
	private BigDecimal totalMoney;

	/**
	 *
	 * 红包状态 1有效 2领取完 3过期 user_red_packet.status
	 *
	 * @mbg.generated
	 */
	private Byte status;

	/**
	 *
	 * 创建时间 user_red_packet.gmt_create
	 *
	 * @mbg.generated
	 */
	private Date gmtCreate;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the userNum
	 */
	public String getUserNum() {
		return userNum;
	}

	/**
	 * @param userNum
	 *            the userNum to set
	 */
	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	/**
	 * @return the userAccount
	 */
	public String getUserAccount() {
		return userAccount;
	}

	/**
	 * @param userAccount
	 *            the userAccount to set
	 */
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	/**
	 * @return the type
	 */
	public Byte getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Byte type) {
		this.type = type;
	}

	/**
	 * @return the totalCount
	 */
	public Integer getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the totalMoney
	 */
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	/**
	 * @param totalMoney
	 *            the totalMoney to set
	 */
	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	/**
	 * @return the status
	 */
	public Byte getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	/**
	 * @return the gmtCreate
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}

	/**
	 * @param gmtCreate
	 *            the gmtCreate to set
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

}
