package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.user.srv.bo.MemberBO;

/**
 * 会员服务接口
 *
 * @author Leach
 * @date 2017/3/13
 */
public interface MemberService {

    /**
     * 查询会员信息
     *
     * @param account 登录账号
     * @param pwd 密码
     * @return
     */
    MemberBO find(String account, String pwd);
}
