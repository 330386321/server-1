package com.lawu.eshop.member.api.service;

import com.lawu.eshop.user.dto.LoginUserDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.param.RegisterParam;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.query.MemberQuery;

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
    @RequestMapping(method = RequestMethod.GET, value = "withPwd/{account}")
    Result<LoginUserDTO> find(@PathVariable("account") String account, @RequestParam("pwd") String pwd);

    /**
     * 会员资料查询
     * @param memberId 会员id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "member/findMemberInfo/{memberId}")
    Result<UserDTO> findMemberInfo(@PathVariable("memberId") Long memberId);

    /**
     * 会员资料修改
     * @param memberParam 会员信息
     */
    @RequestMapping(method = RequestMethod.PUT, value = "member/updateMemberInfo/{id}")
    Result updateMemberInfo(@ModelAttribute UserParam memberParam,@PathVariable("id")  Long id);

    /**
     * 修改密码
     *
     * @param id          主键
     * @param originalPwd 原始密码
     * @param newPwd      新密码
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "member/updateLoginPwd/{id}")
    Result updateLoginPwd(@PathVariable("id") Long id, @RequestParam("originalPwd") String originalPwd, @RequestParam("newPwd") String newPwd);

    /**
     * 查询我的E友
     * @author zhangrc
     * @date 2017/03/23
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "member/findMemberListByUser")
    Result<Page<MemberDTO>> findMemberListByUser(@RequestBody MemberQuery query );


    /**
     * 根据邀请人账号查询邀请人信息
     *
     * @param account 邀请人账号
     */
    @RequestMapping(method = RequestMethod.GET, value = "user/common/getInviterByAccount/{account}")
    Result getInviterByAccount(@PathVariable("account") String account);

    /**
     * 会员注册
     *
     * @param registerParam 会员注册信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "member/register")
    Result register(@ModelAttribute RegisterParam registerParam);

    /**
     * 根据账号查询会员信息
     * @param account   会员账号
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "member/getMember/{account}")
    Result getMemberByAccount(@PathVariable("account") String account);

}
