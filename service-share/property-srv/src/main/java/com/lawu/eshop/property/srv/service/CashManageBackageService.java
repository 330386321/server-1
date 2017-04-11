package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.property.param.CashBackageQueryDataParam;
import com.lawu.eshop.property.srv.bo.WithdrawCashBackageQueryBO;

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
	
	

}
