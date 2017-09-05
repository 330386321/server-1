package com.lawu.eshop.order.srv.bo;

import java.util.List;

import com.lawu.eshop.order.srv.utils.express.kdniao.constants.CodeEnum;

/**
 * 单号识别BO
 * 
 * @author jiangxinjun
 * @date 2017年9月5日
 */
public class ExpressRecognitionDetailBO {
	
	/**
	 * 电商用户ID
	 */
	private String eBusinessId;
	
	/**
	 * 物流单号
	 */
	private String logisticCode;
	
	/**
	 * 成功与否
	 */
	private Boolean success;
	
	/**
	 * 失败原因
	 */
	private CodeEnum code;
	
	/**
	 * 可能得快递公司列表
	 * 一家或多家快递公司
	 * 排名靠前的命中率更高
	 */
	private List<ShipperBO> shippers;
	
	public List<ShipperBO> getShippers() {
		return shippers;
	}

	public String geteBusinessId() {
		return eBusinessId;
	}

	public void seteBusinessId(String eBusinessId) {
		this.eBusinessId = eBusinessId;
	}

	public String getLogisticCode() {
		return logisticCode;
	}

	public void setLogisticCode(String logisticCode) {
		this.logisticCode = logisticCode;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	public CodeEnum getCode() {
		return code;
	}

	public void setCode(CodeEnum code) {
		this.code = code;
	}

	public void setShippers(List<ShipperBO> shippers) {
		this.shippers = shippers;
	}
}
