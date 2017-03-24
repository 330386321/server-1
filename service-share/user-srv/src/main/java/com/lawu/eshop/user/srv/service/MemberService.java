package com.lawu.eshop.user.srv.service;

import java.util.List;

import com.lawu.eshop.user.dto.param.MemberParam;
import com.lawu.eshop.user.param.RegisterParam;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.query.MemberQuery;
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

    int updateMemberInfo(UserParam memberParam, Long id);

    /**
     * 修改密码
     *
     * @param id  主键
     * @param pwd 密码
     */
    void updatePwd(Long id, String pwd);

    /**
     * 根据账号查询会员信息
     * @param account   会员账号
     * @return
     */
    MemberBO getMemberByAccount(String account);

    /**
     * @author zhangrc
     * @date 2017/03/23
     * 查询我的E友
     * @return
     */
    List<MemberBO> findMemberListByUser(MemberQuery memberQuery);

    /**
     * 会员注册
     * @param registerParam 会员注册信息
     */
    void register(RegisterParam registerParam);
    

}
