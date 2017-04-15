package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.BusinessDepositInitDTO;
import com.lawu.eshop.property.param.BusinessDepositQueryDataParam;
import com.lawu.eshop.property.param.BusinessDepositSaveDataParam;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.srv.bo.BusinessDepositQueryBO;

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

	/**
	 * 运营平台列表查询
	 * @param param
	 * @return
	 */
	Page<BusinessDepositQueryBO> selectDepositList(BusinessDepositQueryDataParam param);

	

}
