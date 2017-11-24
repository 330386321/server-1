package com.lawu.eshop.product.srv.service;

import com.lawu.eshop.product.srv.exception.DataNotExistException;

/**
 * 抢购活动关注服务接口
 * 
 * @author jiangxinjun
 * @createDate 2017年11月24日
 * @updateDate 2017年11月24日
 */
public interface SeckillActivityAttentionService {
    
    /**
     * 根据活动商品id添加关注记录以及增加商品关注人数
     * 
     * @param activityProductId 活动商品id
     * @param memberId 用户id
     * @author jiangxinjun
     * @createDate 2017年11月24日
     * @updateDate 2017年11月24日
     */
    void attention(Long activityProductId, Long memberId) throws DataNotExistException;
}
