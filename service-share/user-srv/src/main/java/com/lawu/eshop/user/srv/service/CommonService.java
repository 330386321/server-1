package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.user.srv.bo.InviterBO;

/**
 * 公共服务接口
 *
 * @author meishuquan
 * @date 2017/3/23
 */
public interface CommonService {

    /**
     * 根据账号查询邀请人信息
     *
     * @param account 邀请人账号
     * @return
     */
    InviterBO getInviterByAccount(String account);
}
