package com.lawu.eshop.statistics.srv.service;

/**
 * @author zhangyong
 * @date 2017/6/29.
 */
public interface UserActiveService {
    Integer collectionMemberActiveDaily();

    Integer collectionMerchantActiveDaily();

    Integer collectionMemberActiveMonth();

    Integer collectionMerchantActiveMonth();
}
