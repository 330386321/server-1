package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.user.dto.UserDTO;
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
     * @param pwd     密码
     * @return
     */
    MemberBO find(String account, String pwd);

    /**
     * 查询会员个人资料
     *
     * @param id 会员id
     * @return
     */
    MemberBO findMemberInfoById(Long id);

    void updateMemberInfo(UserDTO memberParam);

    /**
     * 修改密码
     *
     * @param id  主键
     * @param pwd 密码
     */
    void updatePwd(Long id, String pwd);
}
