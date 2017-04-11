package com.lawu.eshop.property.srv.bo;

import com.lawu.eshop.compensating.transaction.Notification;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月11日 下午7:57:08
 *
 */
public class BalancePayNotification extends Notification {
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
