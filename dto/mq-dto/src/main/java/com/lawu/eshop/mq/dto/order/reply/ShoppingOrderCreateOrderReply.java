package com.lawu.eshop.mq.dto.order.reply;

import com.lawu.eshop.compensating.transaction.Reply;

/**
 * 创建订单事务回复消息
 * 
 * @author Sunny
 * @date 2017年6月6日
 */
public class ShoppingOrderCreateOrderReply extends Reply {

	private static final long serialVersionUID = 1L;
	
	private Integer resultCode;

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}
	
}
