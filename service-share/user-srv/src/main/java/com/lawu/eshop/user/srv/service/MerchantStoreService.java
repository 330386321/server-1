package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.user.srv.bo.MerchantStoreBO;/**
 * 店面信息获取
 * @author zhangrc
 * @date 2017/4/10
 *
 */
public interface MerchantStoreService {
	/**
     * 根据商家id查询门店信息
     * @param merchantId
     * @return
     */
	MerchantStoreBO selectMerchantStore(Long merchantId);

}
