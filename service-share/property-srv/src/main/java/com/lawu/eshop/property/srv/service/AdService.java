package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.property.param.NotifyCallBackParam;

/**
 *
 */
public interface AdService {

	/**
	 * 商家发E咻&红包时回调
	 * @param param
	 * @return
	 */
	int doHandleMerchantAdNotify(NotifyCallBackParam param);
}
