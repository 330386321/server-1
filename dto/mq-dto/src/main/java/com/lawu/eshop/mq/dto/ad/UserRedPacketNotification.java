/**
 * 
 */
package com.lawu.eshop.mq.dto.ad;

import java.math.BigDecimal;

import com.lawu.eshop.compensating.transaction.Notification;

/**
 * @author lihj
 * @date 2017年8月7日
 */
public class UserRedPacketNotification extends Notification {

	/**
	 * 
	 */
	private static final long serialVersionUID = -878359637513530169L;

	private Long id;
	private String userNum;

	private BigDecimal money;

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
	 * @return the money
	 */
	public BigDecimal getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(BigDecimal money) {
		this.money = money;
	}

}
