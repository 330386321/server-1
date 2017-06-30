package com.lawu.eshop.statistics.srv.mapper.extend;

import com.lawu.eshop.statistics.srv.domain.extend.UserActiveDOView;

import java.util.Date;

/**
 * @author zhangyong
 * @date 2017/6/29.
 */
public interface UserActiveDOMapperExtend {
    Integer collectionMemberActiveDaily(Date time);

    Integer collectionMerchantActiveDaily(Date time);

    Integer collectionMemberActiveMonth(Date nowDate);

    Integer collectionMerchantActiveMonth(Date nowDate);

    UserActiveDOView collectionMemberActiveAreaDaily(Date time);

    UserActiveDOView collectionMerchantActiveAreaDaily(Date time);
}
