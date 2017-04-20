package com.lawu.eshop.property.dto;

import java.io.Serializable;

import com.lawu.eshop.property.constants.CashStatusEnum;

public class WithdrawCashStatusDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * 主键
     */
    private Long id;
	
    /**
     * 提现状态
     */
    private CashStatusEnum status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CashStatusEnum getStatus() {
		return status;
	}

	public void setStatus(CashStatusEnum status) {
		this.status = status;
	}
    
}