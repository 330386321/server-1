package com.lawu.eshop.mq.dto.product;

import com.lawu.eshop.compensating.transaction.Notification;

public class DelProductCommentNotification extends Notification {

    private Long productModelId;

	public Long getProductModelId() {
		return productModelId;
	}

	public void setProductModelId(Long productModelId) {
		this.productModelId = productModelId;
	}

    
}
