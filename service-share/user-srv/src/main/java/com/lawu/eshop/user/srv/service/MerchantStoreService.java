package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.user.param.ListMerchantStoreParam;
import com.lawu.eshop.user.param.MerchantStoreParam;
import com.lawu.eshop.user.param.StoreStatisticsParam;
import com.lawu.eshop.user.srv.bo.*;

import java.util.List;

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


    /**
     * 查询所有审核通过的实体店铺
     *
     * @return
     */
    List<MerchantStoreBO> listMerchantStore(ListMerchantStoreParam listMerchantStoreParam);

    /**
     * 更新门店统计数据
     *
     * @param id
     * @param param
     */
    void updateStoreStatisticsById(Long id, StoreStatisticsParam param);

    /**
     * 更新门店索引
     *
     * @param id
     */
    void updateStoreIndex(Long id);

    /**
     * 重建门店索引
     */
    void rebuildStoreIndex();

    /**
     * 删除无效的门店索引
     */
    void delInvalidStoreIndex();
    
    /**
     * 查询商家相关信息
     * @param merchantIds
     * @return
     */
    List<MerchantAdInfoBO> getAdMerchantStoreByIds(List<Long> merchantIds);

    MerchantStoreStatusBO merchantStoreIsExist(Long id);

    MerchantInfoBO findAccountAndRegionPathByNum(String merchantNum);

    /**
     * 新店推荐
     *
     * @param regionPath
     * @return
     * @author meishuquan
     */
    List<NewMerchantStoreBO> listNewMerchant(String regionPath);

    /**
     * 优选美食-人气最高
     *
     * @param industryId
     * @param regionPath
     * @return
     * @author meishuquan
     */
    List<RecommendFoodBO> listRecommendFoodConsume(Integer industryId, String regionPath);

    /**
     * 优选美食-评价最高
     *
     * @param industryId
     * @param regionPath
     * @return
     * @author meishuquan
     */
    List<RecommendFoodBO> listRecommendFoodComment(Integer industryId, String regionPath);
}
