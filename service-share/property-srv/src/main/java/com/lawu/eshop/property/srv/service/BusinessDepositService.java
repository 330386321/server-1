package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.BusinessDepositInitDTO;
import com.lawu.eshop.property.param.BusinessDepositSaveDataParam;
import com.lawu.eshop.property.param.NotifyCallBackParam;

public interface BusinessDepositService {

	/**
	 * 初始化商家保证金记录
	 * @param param
	 * @return
	 */
	BusinessDepositInitDTO save(BusinessDepositSaveDataParam param);

	/**
	 * 处理第三方支付回调
	 * 校验金额，新增商家交易明细，更新保证金表
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Result doHandleDepositNotify(NotifyCallBackParam param);

	

}
