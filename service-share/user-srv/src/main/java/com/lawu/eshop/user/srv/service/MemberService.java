package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.user.srv.bo.MessagePushBO;
import org.springframework.web.bind.annotation.PathVariable;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.param.MemberQuery;
import com.lawu.eshop.user.param.RegisterRealParam;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.srv.bo.CashUserInfoBO;
import com.lawu.eshop.user.srv.bo.MemberBO;

import java.util.List;

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
     * 修改登录密码
     *
     * @param id          主键
     * @param newPwd      新密码
     * @return
     */
    void updateLoginPwd(Long id,  String newPwd);

    /**
     * 根据账号查询会员信息
     *
     * @param account 会员账号
     * @return
     */
    MemberBO getMemberByAccount(String account);

    /**
     * @author zhangrc
     * @date 2017/03/23
     * 查询我的E友
     * @return
     */
    Page<MemberBO> findMemberListByUser(Long inviterId,MemberQuery memberQuery,byte inviterType);

    /**
     * 会员注册
     *
     * @param registerRealParam 会员注册信息
     */
    void register(RegisterRealParam registerRealParam);

    /**
     * 根据会员ID查询会员信息
     *
     * @param id 会员ID
     */
    MemberBO getMemberById(Long id );

    /**
     * 修改头像
     * @param headimg
     * @param mermberId
     */
    void updateMemberHeadImg(String headimg, Long mermberId);

    /**
     * 用户、商家提现时根据商家ID获取账号、名称、省市区信息冗余到提现表中
     * @param id
     * @return
     * @author Yangqh
     */
    CashUserInfoBO findCashUserInfo(@PathVariable("id") Long id);
    
    /**
     * 根据区域查询用户数量
     * @param regionPath
     * @return
     */
    Integer findMemberCount(String regionPath);

    Integer setGtAndRongYunInfo(Long id, String cid);

    MemberBO findMemberByNum(String userNum);

    List<MessagePushBO> findMessagePushList(String area);
}
