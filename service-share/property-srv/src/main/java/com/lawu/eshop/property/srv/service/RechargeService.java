package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.RechargeSaveDTO;
import com.lawu.eshop.property.dto.ThirdPayCallBackQueryPayOrderDTO;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.param.RechargeSaveDataParam;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月12日 下午8:50:30
 *
 */
public interface RechargeService {

	/**
	 * 用户商家第三方充值余额积分保存充值记录
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	RechargeSaveDTO save(RechargeSaveDataParam param);

	/**
	 * 用户商家充值余额积分回调
	 * @param param
	 * @param result
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Result doHandleRechargeNotify(NotifyCallBackParam param);

	/**
	 * 调用第三方支付时获取需要支付的金额
	 * @param rechargeId
	 * @return
	 */
	ThirdPayCallBackQueryPayOrderDTO getRechargeMoney(String rechargeId);



}
