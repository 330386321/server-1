package com.lawu.eshop.property.srv.service;

import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.param.TotalSalesQueryParam;
import com.lawu.eshop.property.param.TransactionDetailQueryForBackageParam;
import com.lawu.eshop.property.param.TransactionDetailQueryForMemberParam;
import com.lawu.eshop.property.param.TransactionDetailQueryForMerchantParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.param.UserIncomeExpenditureQueryParam;
import com.lawu.eshop.property.srv.bo.TotalSalesBO;
import com.lawu.eshop.property.srv.bo.TotalSalesGroupByAreaBO;
import com.lawu.eshop.property.srv.bo.TransactionDetailBO;
import com.lawu.eshop.property.srv.bo.UserIncomeExpenditureBO;

/**
 * 交易明细服务接口
 *
 * @author Sunny
 * @date 2017/3/29
 */
public interface TransactionDetailService {

	/**
	 * 根据用户编号、查询参数分页查询交易明细  - 商家
	 * 
	 * @param userNum 用户编号
	 * @param param 查询参数
	 * @return 
	 */
	Page<TransactionDetailBO> findPageByUserNumForMerchant(String userNum, TransactionDetailQueryForMerchantParam param);
	
	/**
	 * 根据用户编号、查询参数分页查询交易明细 - 用户
	 * 
	 * @param userNum 用户编号
	 * @param param 查询参数
	 * @return 
	 */
	Page<TransactionDetailBO> findPageByUserNumForMember(String userNum, TransactionDetailQueryForMemberParam param);
	
	/**
	 * 保存交易记录表
	 * @param param
	 * @return
	 */
	int save(TransactionDetailSaveDataParam param);

	/**
	 * 根据用户编号、第三方交易号校验用户是否存在交易记录，存在表示已支付
	 * @param param
	 * @return
	 * @author yangqh
	 * @date 2017年5月9日 下午4:25:02
	 */
	boolean verifyOrderIsPaySuccess(NotifyCallBackParam param);

	/**
	 * 查询运营后台充值记录
	 *
	 * @param param
	 * @return
	 */
	Page<TransactionDetailBO> getBackageRechargePageList(TransactionDetailQueryForBackageParam param);
	
	/**
	 * 查询平台销售金额
	 *
	 * @param param
	 * @return
	 */
	List<TotalSalesBO> selectTotalSales(TotalSalesQueryParam param);
	
	
	/**
	 * 查询平台销售金额groupby area
	 *
	 * @param param
	 * @return
	 */
	List<TotalSalesGroupByAreaBO> selectTotalSalesGroupByArea(TotalSalesQueryParam param);
	
	/**
	 * 查询用户收入和支出金额
	 *
	 * @param param
	 * @return
	 */
	List<UserIncomeExpenditureBO> selectUserIncomeExpenditure(UserIncomeExpenditureQueryParam param);

}
