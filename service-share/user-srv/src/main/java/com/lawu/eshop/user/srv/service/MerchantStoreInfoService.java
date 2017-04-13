package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.user.param.MerchantStoreParam;
import com.lawu.eshop.user.srv.bo.*;

import java.util.List;

/**
 * 门店信息接口
 * Created by zhangyong on 2017/3/24.
 */
public interface MerchantStoreInfoService {

    /**
     * 根据商户id查询门店信息
     *
     * @param id
     * @return
     */
    MerchantStoreInfoBO selectMerchantStore(Long id);

    /**
     * 新增门店信息
     *
     * @param merchantId
     * @param merchantStoreParam
     */
    void saveMerchantStoreInfo(Long merchantId, MerchantStoreParam merchantStoreParam);

    /**
     * 查询门店扩展信息
     *
     * @param example（营业执照号码/身份证号）
     * @param type（1：营业执照号码        2:身份证号）
     * @return
     */
    MerchantStoreProfileBO selectStoreInfoByExample(String example, Integer type);

    /**
     * 修改门店信息
     *
     * @param merchantId         门店id
     * @param merchantStoreParam 门店信息
     */
    void updateMerchantStoreInfo(Long merchantId, MerchantStoreParam merchantStoreParam, Long merchantStoreId);

    MerchantStoreInfoBO selectMerchantStoreByMId(Long merchantId);
    
    /**
     * 根据商家id列表批量查询商家是否支持七天退货
     * 
     * @param merchantIds
     * @return
     */
    List<MerchantStoreNoReasonReturnBO> selectNoReasonReturnByMerchantIds(List<Long> merchantIds);

    void saveMerchantStoreAuditInfo(Long merchantId, MerchantStoreParam merchantStoreParam, Long merchantStoreId);

    /**
     * 根据门店ID查询门店详细信息
     *
     * @param id
     * @return
     */
    StoreDetailBO getStoreDetailById(Long id);

    /**
     * 用户、商家提现时根据商家ID获取账号、名称、省市区信息冗余到提现表中
     * @param id
     * @return
     * @author Yangqh
     */
	CashUserInfoBO findCashUserInfo(Long id);

    MerchantStoreInfoBO findStoreNameAndImgByMerchantId(Long merchantId);

    MerchantStoreAuditBO findStoreAuditInfo(Long merchantId);

    /**
     * 增加门店买单笔数
     * @param merchantId
     */
    void addMerchantStoreBuyNums(Long merchantId);
}
