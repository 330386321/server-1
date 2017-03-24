package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.user.srv.bo.MerchantStoreProfileBO;

/**
 *
 * Created by Administrator on 2017/3/23.
 */
public interface MerchantStoreProfileService {

    MerchantStoreProfileBO findMerchantStoreInfo(Long merchantProfileId);

}
