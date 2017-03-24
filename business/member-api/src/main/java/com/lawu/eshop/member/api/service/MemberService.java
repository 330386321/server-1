package com.lawu.eshop.member.api.service;

import java.util.List;

import com.lawu.eshop.framework.web.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.member.api.service.hystrix.MemberServiceHystrix;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.dto.param.MemberParam;
import com.lawu.eshop.user.param.UserParam;

/**
 * @author Leach
 * @date 2017/3/13
 */
@FeignClient(value = "user-srv", fallback = MemberServiceHystrix.class)
public interface MemberService {

    /**
     * 查询会员信息
     *
     * @param account 登录账号
     * @param pwd     密码
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "member/find")
    UserDTO find(@RequestParam("account") String account, @RequestParam("pwd") String pwd);

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
    Result updateMemberInfo(@RequestBody UserParam memberParam,@PathVariable("id")  Long id);

    /**
     * 修改密码
     *
     * @param id  主键
     * @param pwd 密码
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "member/updatePwd")
    void updatePwd(@RequestParam("id") Long id, @RequestParam("pwd") String pwd);

    /**
     * 查询我的E友
     * @author zhangrc
     * @date 2017/03/23
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "member/findMemberListByUser")
    List<MemberDTO> findMemberListByUser(@ModelAttribute MemberParam memberParam );


    /**
     * 根据邀请人账号查询邀请人信息
     *
     * @param account 邀请人账号
     */
    @RequestMapping(method = RequestMethod.GET, value = "user/common/getInviterByAccount")
    InviterDTO getInviterByAccount(@RequestParam("account") String account);

}
