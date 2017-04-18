package com.lawu.eshop.user.srv.service;

import java.util.List;

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

    /**
     * 根据被邀请人查询出该人所有level邀请人编号
     * @param invitedUserNum
     * @param level
     * @return
     */
	List<String> selectHigherLevelInviters(String invitedUserNum, int level);
}
