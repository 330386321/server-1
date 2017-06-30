package com.lawu.eshop.statistics.srv.service;

import com.lawu.eshop.statistics.srv.bo.UserActiveBO;

/**
 * @author zhangyong
 * @date 2017/6/29.
 */
public interface UserActiveService {
    Integer collectionMemberActiveDaily();

    Integer collectionMerchantActiveDaily();

    Integer collectionMemberActiveMonth();

    Integer collectionMerchantActiveMonth();

    UserActiveBO collectionMemberActiveAreaDaily();

    UserActiveBO collectionMerchantActiveAreaDaily();
}
