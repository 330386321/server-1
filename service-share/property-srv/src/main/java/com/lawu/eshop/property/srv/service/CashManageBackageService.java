package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.property.param.CashBackageQueryDataParam;
import com.lawu.eshop.property.param.CashBackageQueryDetailParam;
import com.lawu.eshop.property.param.CashBackageQuerySumParam;
import com.lawu.eshop.property.srv.bo.WithdrawCashBackageQueryBO;
import com.lawu.eshop.property.srv.bo.WithdrawCashBackageQuerySumBO;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月5日 下午2:48:51
 *
 */
public interface CashManageBackageService {

	/**
	 * 运营后台提现管理列表
	 * @param param
	 * @return
	 */
	Page<WithdrawCashBackageQueryBO> findCashInfo(CashBackageQueryDataParam param);

	/**
	 * 运营平台提现管理统计成功总笔数和成功总金额
	 * @param param
	 * @return
	 */
	WithdrawCashBackageQuerySumBO getTotalNum(CashBackageQuerySumParam param) throws Exception;

	/**
	 * 运营平台提现详情
	 * @param param
	 * @return
	 */
	Page<WithdrawCashBackageQueryBO> findCashInfoDetail(CashBackageQueryDetailParam param);
	
	

}
