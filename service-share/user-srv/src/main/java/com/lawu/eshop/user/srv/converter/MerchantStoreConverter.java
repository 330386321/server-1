package com.lawu.eshop.user.srv.converter;

import com.lawu.eshop.user.srv.bo.MerchantStoreProfileBO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreProfileDO;

/**
 * 商家门店信息转换
 * Created by Administrator on 2017/3/24.
 */
public class MerchantStoreConverter {
    /**
     * BO转换
     *
     * @param merchantStoreProfileDO
     * @return
     */
    public static MerchantStoreProfileBO convertBO(MerchantStoreProfileDO merchantStoreProfileDO) {
        if (merchantStoreProfileDO == null) {
            return null;
        }

        MerchantStoreProfileBO merchantStoreProfileBO = new MerchantStoreProfileBO();
        merchantStoreProfileBO.setMerchantId(merchantStoreProfileDO.getMerchantId());
//        merchantStoreProfileBO.setPrincipalMobile(merchantStoreProfileDO.getPrincipalMobile());
//        merchantStoreProfileBO.setPrincipalName(merchantStoreProfileDO.getPrincipalName());

        return merchantStoreProfileBO;

    }
}
