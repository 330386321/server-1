package com.lawu.eshop.user.srv.service;

import java.util.List;

import com.lawu.eshop.user.param.MerchantStoreParam;
import com.lawu.eshop.user.srv.bo.MerchantStoreBO;

/**
 * 店面信息获取
 *
 * @author zhangrc
 * @date 2017/4/10
 */
public interface MerchantStoreService {
    /**
     * 根据商家id查询门店信息
     *
     * @param merchantId
     * @return
     */
    MerchantStoreBO selectMerchantStore(Long merchantId);

    /**
     * 加入7天退货保障
     *
     * @param id
     */
    void updateNoReasonReturn(Long id);

    /**
     * 根据门店ID查询门店信息
     *
     * @param id
     * @return
     */
    MerchantStoreBO getMerchantStoreById(Long id);
    
    /**
     * 查询所有的门店
     * @param param
     * @return
     */
    List<MerchantStoreBO> selectAllMerchantStore(MerchantStoreParam param);

}
