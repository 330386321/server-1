package com.lawu.eshop.statistics.srv.service;

import com.lawu.eshop.statistics.param.UserRegAreaParam;

/**
 * @author meishuquan
 * @date 2017/6/29.
 */
public interface UserRegService {

    /**
     * 按日统计用户注册量
     *
     * @param memberCount
     * @param merchantCount
     */
    void saveUserRegDaily(Integer memberCount, Integer merchantCount);

    /**
     * 按月统计用户注册量
     *
     * @param memberCount
     * @param merchantCount
     */
    void saveUserRegMonth(Integer memberCount, Integer merchantCount);

    /**
     * 按市级更新用户注册量
     *
     * @param param
     */
    void updateUserRegArea(UserRegAreaParam param);

}
