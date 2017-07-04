package com.lawu.eshop.property.srv.mapper.extend;

import java.util.List;

import com.lawu.eshop.property.srv.domain.extend.TotalSalesDO;
import com.lawu.eshop.property.srv.domain.extend.TotalSalesQueryExample;

public interface TransactionDetailExtendDOMapper {
	
	/**
	 * 根据时间获取获取买单和订单的收入
	 * 
	 * @param param
	 * @return
	 * @author Sunny
	 * @date 2017年7月3日
	 */
	List<TotalSalesDO> selectTotalSales(TotalSalesQueryExample param);
	
}