package com.lawu.eshop.statistics.srv.mapper.extend;

import java.util.Date;

/**
 * @author zhangyong
 * @date 2017/6/29.
 */
public interface UserActiveDOMapperExtend {
    Integer collectionMemberActiveDaily(Date time);

    Integer collectionMerchantActiveDaily(Date time);
}
