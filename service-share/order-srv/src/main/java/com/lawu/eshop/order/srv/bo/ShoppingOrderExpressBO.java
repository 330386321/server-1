package com.lawu.eshop.order.srv.bo;

import java.io.Serializable;
public class ShoppingOrderExpressBO implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
    /**
     * 运单编号
     */
    private String waybillNum;

    /**
     * 快递公司编码
     */
    private String expressCompanyCode;

    /**
     * 快递公司名称
     */
    private String expressCompanyName;

	public String getWaybillNum() {
		return waybillNum;
	}

	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}

	public String getExpressCompanyCode() {
		return expressCompanyCode;
	}

	public void setExpressCompanyCode(String expressCompanyCode) {
		this.expressCompanyCode = expressCompanyCode;
	}

	public String getExpressCompanyName() {
		return expressCompanyName;
	}

	public void setExpressCompanyName(String expressCompanyName) {
		this.expressCompanyName = expressCompanyName;
	}
    
}