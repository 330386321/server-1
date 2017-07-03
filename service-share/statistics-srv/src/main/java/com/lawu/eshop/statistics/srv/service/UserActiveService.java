package com.lawu.eshop.statistics.srv.service;

import com.lawu.eshop.statistics.srv.bo.UserActiveBO;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/6/29.
 */
public interface UserActiveService {
    Integer collectionMemberActiveDaily();

    Integer collectionMerchantActiveDaily();

    Integer collectionMemberActiveMonth();

    Integer collectionMerchantActiveMonth();

    List<UserActiveBO> collectionMemberActiveAreaDaily();

    List<UserActiveBO> collectionMerchantActiveAreaDaily();

    List<UserActiveBO> collectionMemberActiveAreaMonth();

    List<UserActiveBO> collectionMerchantActiveAreaMonth();
}
