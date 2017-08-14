package com.lawu.eshop.property.srv.mapper.extend;

import java.util.List;

import com.lawu.eshop.property.srv.domain.extend.TotalSalesDO;
import com.lawu.eshop.property.srv.domain.extend.TotalSalesGroupByAreaDO;
import com.lawu.eshop.property.srv.domain.extend.TotalSalesQueryExample;
import com.lawu.eshop.property.srv.domain.extend.UserIncomeExpenditureDO;
import com.lawu.eshop.property.srv.domain.extend.UserIncomeExpenditureExample;

public interface TransactionDetailExtendDOMapper {
	
	/**
	 * 根据时间获取获取买单和订单的收入
	 * 
	 * @param param
	 * @return
	 * @author Sunny
	 * @date 2017年7月3日
	 */
	List<TotalSalesDO> selectTotalSales(TotalSalesQueryExample example);
	
	/**
	 * 查询用户支出和收入的金额
	 * 
	 * @param param
	 * @return
	 * @author Sunny
	 * @date 2017年7月3日
	 */
	List<UserIncomeExpenditureDO> selectUserIncomeExpenditure(UserIncomeExpenditureExample example);
	
	
	/**
	 * 根据时间获取获取买单和订单的收入group by area
	 * 
	 * @param param
	 * @return
	 * @author Sunny
	 * @date 2017年7月3日
	 */
	List<TotalSalesGroupByAreaDO> selectTotalSalesGroupByArea(TotalSalesQueryExample example);
	
}