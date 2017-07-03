package com.lawu.eshop.user.srv.mapper.extend;

import com.lawu.eshop.user.srv.domain.extend.MerchantDOView;

/**
 * @author zhangyong
 * @date 2017/6/5.
 */
public interface MerchantDOMapperExtend {

    int delMerchantGtPush(Long merchantId);

    MerchantDOView getMerchantViewById(Long id);
}
