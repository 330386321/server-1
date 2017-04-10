package com.lawu.eshop.mall.srv.service;

import com.lawu.eshop.mall.param.MerchantFavoredParam;
import com.lawu.eshop.mall.srv.bo.MerchantFavoredBO;

/**
 * @author zhangyong
 * @date 2017/4/10.
 */
public interface MerchantFavoredService {
    Integer saveMerchantFavoredInfo(Long merchantId, MerchantFavoredParam param);

    MerchantFavoredBO findFavoredByMerchantId(Long merchantId);

    void delMerchantFavoredInfoById(Long merchantId);

    MerchantFavoredBO findFavoredById(Long id);

    Integer updateMerchantFavoredInfo(Long merchantId, MerchantFavoredParam param);
}
