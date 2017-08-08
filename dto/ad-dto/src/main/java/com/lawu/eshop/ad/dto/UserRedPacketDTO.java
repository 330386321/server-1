/**
 * 
 */
package com.lawu.eshop.ad.dto;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author lihj
 * @date 2017年8月4日
 */
public class UserRedPacketDTO {

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "红包类型[0普通红包,1拼手气红包]")
	private Byte type;

	@ApiModelProperty(value = "红包类型中文")
	private String typeStr;

	@ApiModelProperty(value = "红包总数")
	private Integer totalCount;
	@ApiModelProperty(value = "总金额")
	private BigDecimal totalMoney;

	@ApiModelProperty(value = "红包状态 1有效 2领取完 3过期")
	private Byte status;
	@ApiModelProperty(value = "红包创建时间")
	private Date gmtCreate;
	@ApiModelProperty(value = "红包创建时间格式化后")
	private String gmtCreateStr;

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
	 * @return the typeStr
	 */
	public String getTypeStr() {
		return typeStr;
	}

	/**
	 * @param typeStr
	 *            the typeStr to set
	 */
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
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

	/**
	 * @return the gmtCreateStr
	 */
	public String getGmtCreateStr() {
		return gmtCreateStr;
	}

	/**
	 * @param gmtCreateStr
	 *            the gmtCreateStr to set
	 */
	public void setGmtCreateStr(String gmtCreateStr) {
		this.gmtCreateStr = gmtCreateStr;
	}

}
