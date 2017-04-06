package com.lawu.eshop.member.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.EfriendDTO;
import com.lawu.eshop.user.dto.LoginUserDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.param.RegisterRealParam;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.query.MemberQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author Leach
 * @date 2017/3/13
 */
@FeignClient(value = "user-srv")
public interface MemberService {

    /**
     * 查询会员信息
     *
     * @param account 登录账号
     * @param pwd     密码
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "member/withPwd/{account}")
    Result<LoginUserDTO> find(@PathVariable("account") String account, @RequestParam("pwd") String pwd);

    /**
     * 会员资料查询
     *
     * @param memberId 会员id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "member/findMemberInfo/{memberId}")
    Result<UserDTO> findMemberInfo(@PathVariable("memberId") Long memberId);

    /**
     * 会员资料修改
     *
     * @param memberParam 会员信息
     */
    @RequestMapping(method = RequestMethod.PUT, value = "member/updateMemberInfo/{id}")
    Result updateMemberInfo(@ModelAttribute UserParam memberParam, @PathVariable("id") Long id);

    /**
     * 修改密码
     *
     * @param id          主键
     * @param originalPwd 原始密码
     * @param newPwd      新密码
     * @param type        业务类型
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "member/updateLoginPwd/{id}")
    Result updateLoginPwd(@PathVariable("id") Long id, @RequestParam("originalPwd") String originalPwd, @RequestParam("newPwd") String newPwd, @RequestParam("type") Integer type);

    /**
     * 查询我的E友
     *
     * @return
     * @author zhangrc
     * @date 2017/03/23
     */
    @RequestMapping(method = RequestMethod.POST, value = "member/findMemberListByUser")
    Result<Page<EfriendDTO>> findMemberListByUser(@RequestParam("userId") Long id, @RequestBody MemberQuery query);

    /**
     * 会员注册
     *
     * @param registerRealParam 会员注册信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "member/register")
    Result register(@ModelAttribute RegisterRealParam registerRealParam);

    /**
     * 根据账号查询会员信息
     *
     * @param account 会员账号
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "member/getMember/{account}")
    Result getMemberByAccount(@PathVariable("account") String account);

    /**
     * 修改头像
     *
     * @param mermberId
     * @param headimg
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "member/saveHeadImage/{memberId}")
    Result saveHeadImage(@PathVariable("memberId") Long memberId, @RequestParam("headimg") String headimg);

}
