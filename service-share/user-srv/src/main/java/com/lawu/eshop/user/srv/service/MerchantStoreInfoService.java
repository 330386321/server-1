package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.user.dto.CertifTypeEnum;
import com.lawu.eshop.user.dto.MerchantStoreImageEnum;
import com.lawu.eshop.user.dto.MerchantStoreTypeEnum;
import com.lawu.eshop.user.param.MerchantStoreParam;
import com.lawu.eshop.user.srv.bo.MerchantStoreInfoBO;
import com.lawu.eshop.user.srv.bo.MerchantStoreProfileBO;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 门店信息接口
 * Created by zhangyong on 2017/3/24.
 */
public interface MerchantStoreInfoService {

    /**
     * 根据商户id查询门店信息
     * @param id
     * @return
     */
    MerchantStoreInfoBO selectMerchantStore(Long id);

    /**
     * 新增门店信息
     * @param merchantId
     * @param merchantStoreParam
     */
    void saveMerchantStoreInfo(Long merchantId, MerchantStoreParam merchantStoreParam,MerchantStoreTypeEnum storeTypeEnum,CertifTypeEnum certifTypeEnum);

    /**
     * 查询门店扩展信息
     * @param example（营业执照号码/身份证号）
     * @param type（1：营业执照号码 2:身份证号）
     * @return
     */
    MerchantStoreProfileBO selectStoreInfoByExample(String example,Integer type);

    /**
     * 修改门店信息
     * @param merchantId 门店id
     * @param merchantStoreParam 门店信息
     */
    void updateMerchantStoreInfo(Long merchantId, MerchantStoreParam merchantStoreParam,Long merchantStoreId,MerchantStoreTypeEnum storeTypeEnum,CertifTypeEnum certifTypeEnum);

    MerchantStoreInfoBO selectMerchantStoreByMId(Long merchantId);

    void saveMerchantStoreAuditInfo(Long merchantId, MerchantStoreParam merchantStoreParam, Long merchantStoreId, MerchantStoreTypeEnum storeType, CertifTypeEnum certifType);
    
}
