package com.lawu.eshop.ad.srv.service;

import com.lawu.eshop.ad.constants.GoodsTypeEnum;
import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.constants.TypeEnum;
import com.lawu.eshop.ad.param.AdPlatformFindParam;
import com.lawu.eshop.ad.param.AdPlatformParam;
import com.lawu.eshop.ad.srv.bo.AdPlatformBO;
import com.lawu.eshop.framework.core.page.Page;

import java.util.List;

/**
 * 平台广告管理
 *
 * @author zhangrc
 * @date 2017/4/5
 */
public interface AdPlatformService {

    /**
     * 添加广告
     *
     * @param adPlatformParam
     * @param url
     * @return
     */
    Integer saveAdPlatform(AdPlatformParam adPlatformParam, String url);

    /**
     * 删除广告
     *
     * @param id
     * @return
     */
    Integer removeAdPlatform(Long id);

    /**
     * 根据不同的位置查询不同的广告
     *
     * @param positionEnum
     * @return
     */
    List<AdPlatformBO> selectByPosition(PositionEnum positionEnum);


    /**
     * 运营平台查询广告
     *
     * @param param
     * @return
     */
    Page<AdPlatformBO> selectList(AdPlatformFindParam param);

    /**
     * 发布广告
     *
     * @param id
     * @return
     */
    Integer issueAd(Long id);

    /**
     * 设置广告位
     *
     * @param id
     * @param positionEnum
     * @return
     */
    Integer setPosition(Long id, PositionEnum positionEnum);

    /**
     * @param id
     * @param adPlatformParam
     * @param url
     * @return
     */
    Integer update(Long id, AdPlatformParam adPlatformParam, String url);

    /**
     * 单个查询
     *
     * @param id
     * @return
     */
    AdPlatformBO select(Long id);

    /**
     * 广告下架
     *
     * @param id
     */
    void unShelve(Long id);


    /**
     * 根据类型位置查询广告
     *
     * @param typeEnum
     * @param positionEnum
     * @return
     */
    List<AdPlatformBO> getAdPlatformByTypePosition(TypeEnum typeEnum, PositionEnum positionEnum);

    /**
     * 根据精品类型查询精品
     *
     * @param goodsTypeEnum
     * @return
     */
    List<AdPlatformBO> getAdPlatformWithGoods(GoodsTypeEnum goodsTypeEnum);

}
