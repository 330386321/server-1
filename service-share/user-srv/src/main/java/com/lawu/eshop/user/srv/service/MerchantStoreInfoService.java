package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.user.srv.bo.MerchantStoreInfoBO;

/**
 * 门店信息接口
 * Created by zhangyong on 2017/3/24.
 */
public interface MerchantStoreInfoService {

    MerchantStoreInfoBO selectMerchantStore(Long id);
}
