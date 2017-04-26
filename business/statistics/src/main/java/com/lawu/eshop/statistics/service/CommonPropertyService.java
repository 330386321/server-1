package com.lawu.eshop.statistics.service;

import java.math.BigDecimal;
import java.util.Map;

public interface CommonPropertyService {

	/**
	 * 广告提成比例
	 * @return
	 */
	Map<String,BigDecimal> getAdCommissionPropertys();

	/**
	 * 销售和营业额提成比例
	 * @return
	 */
	Map<String, BigDecimal> getSaleCommissionPropertys();
}
