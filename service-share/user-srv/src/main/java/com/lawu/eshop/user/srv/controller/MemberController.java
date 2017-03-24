package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.param.RegisterParam;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.converter.MemberConverter;
import com.lawu.eshop.user.srv.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Leach
 * @date 2017/3/13
 */
@RestController
@RequestMapping(value = "member/")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public UserDTO login(@RequestParam String account, @RequestParam String pwd) {
        MemberBO memberBO = memberService.find(account, pwd);
        return MemberConverter.convertDTO(memberBO);
    }

    /**
     * 会员个人资料查询
     *
     * @param memberId 会员id
     * @return
     */
    @RequestMapping(value = "findMemberInfo/{memberId}", method = RequestMethod.GET)
    public UserDTO findMemberInfo(@PathVariable("memberId") Long memberId) {
        MemberBO memberBO = memberService.findMemberInfoById(memberId);
        return MemberConverter.convertDTO(memberBO);
    }

    /**
     * 会员个人资料修改
     *
     * @param memberParam 会员信息
     * @return
     */
    @RequestMapping(value = "updateMemberInfo/{id}", method = RequestMethod.PUT)
    public int updateMemberInfo(@RequestBody UserParam memberParam, @PathVariable("id") Long id) {
          int result =  memberService.updateMemberInfo(memberParam,id);

          return result;

    }

    /**
     * 会员修改密码
     *
     * @param id  主键
     * @param pwd 密码
     */
    @RequestMapping(value = "updatePwd", method = RequestMethod.POST)
    public void updatePwd(@RequestParam Long id, @RequestParam String pwd) {
        memberService.updatePwd(id, pwd);
    }

    /**
     * 根据账号查询会员信息
     *
     * @param account 会员账号
     * @return
     */
    @RequestMapping(value = "getMemberByAccount", method = RequestMethod.GET)
    public UserDTO getMemberByAccount(@RequestParam String account) {
        MemberBO memberBO = memberService.getMemberByAccount(account);
        return MemberConverter.convertDTO(memberBO);
    }

    /**
     * 我的E友
     *@author zhangrc
     *@date 2017/03/23
     *@param inviterId 登录用户
     *@return
     */
    @RequestMapping(value = "findMemberListByUserId", method = RequestMethod.GET)
    public List<MemberDTO> findMemberListByUser(@RequestParam Long inviterId) {
        List<MemberBO> memberBOS = memberService.findMemberListByUser(inviterId);
        return MemberConverter.convertListDOTS(memberBOS);
    }

    /**
     * 会员注册
     * @param registerParam 会员注册信息
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public void register(@ModelAttribute RegisterParam registerParam){
         memberService.register(registerParam);
    }

}
