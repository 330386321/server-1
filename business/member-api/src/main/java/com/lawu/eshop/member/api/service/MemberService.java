package com.lawu.eshop.member.api.service;

import java.util.List;

import com.lawu.eshop.member.api.service.hystrix.MemberServiceHystrix;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.dto.param.UserParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.member.api.service.hystrix.MemberServiceHystrix;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.UserDTO;

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
    @RequestMapping(method = RequestMethod.GET, value = "member/findMemberInfo")
    UserDTO findMemberInfo(@RequestParam("memberId") Long memberId);

    /**
     * 会员资料修改
     * @param memberParam 会员信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "member/updateMemberInfo")
    void updateMemberInfo(@ModelAttribute UserParam memberParam);

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
    @RequestMapping(method = RequestMethod.GET,value = "member/findMemberListByUserId")
    List<MemberDTO> findMemberListByUserId(@RequestParam("inviterId") Long inviterId );

}
