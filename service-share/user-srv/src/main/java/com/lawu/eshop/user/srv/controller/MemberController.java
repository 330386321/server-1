package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import java.util.List;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.AddressDTO;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.dto.param.MemberParam;
import com.lawu.eshop.user.param.RegisterParam;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.query.MemberQuery;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.converter.AddressConverter;
import com.lawu.eshop.user.srv.converter.MemberConverter;
import com.lawu.eshop.user.srv.service.MemberService;
import com.lawu.eshop.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Leach
 * @date 2017/3/13
 */
@RestController
@RequestMapping(value = "member/")
public class MemberController extends BaseController {

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
    public Result<UserDTO> findMemberInfo(@PathVariable("memberId") Long memberId) {
        MemberBO memberBO = memberService.findMemberInfoById(memberId);
        if(memberBO == null){
            return successGet();
        }else{
            return successGet(MemberConverter.convertDTO(memberBO));
        }
    }

    /**
     * 会员个人资料修改
     *
     * @param memberParam 会员信息
     * @return
     */
    @RequestMapping(value = "updateMemberInfo/{id}", method = RequestMethod.PUT)
    public Result updateMemberInfo(@RequestBody UserParam memberParam, @PathVariable("id") Long id) {
          int result =  memberService.updateMemberInfo(memberParam,id);
          if(result == 1){
            return  successCreated();
          }else{
              return successCreated(ResultCode.USER_WRONG_ID);
          }
    }

    /**
     * 修改登录密码
     *
     * @param id          主键
     * @param originalPwd 原始密码
     * @param newPwd      新密码
     */
    @RequestMapping(value = "updateLoginPwd", method = RequestMethod.PUT)
    public Result updateLoginPwd(@RequestParam Long id, @RequestParam String originalPwd, @RequestParam String newPwd) {
        MemberBO memberBO = memberService.getMemberById(id);
        if (!MD5.MD5Encode(originalPwd).equals(memberBO.getPwd())) {
            return failVerify();
        }
        memberService.updateLoginPwd(id, originalPwd, newPwd);
        return successCreated();
    }

    /**
     * 根据账号查询会员信息
     *
     * @param account 会员账号
     * @return
     */
    @RequestMapping(value = "getMemberByAccount", method = RequestMethod.GET)
    public Result getMemberByAccount(@RequestParam String account) {
        MemberBO memberBO = memberService.getMemberByAccount(account);
        MemberDTO memberDTO = MemberConverter.convertMDTO(memberBO);
        return successGet(memberDTO);
    }

    /**
     *我的E友
     *@author zhangrc
     *@date 2017/03/23
     *@param inviterId 登录用户
     *@return
     */
    @RequestMapping(value = "findMemberListByUser", method = RequestMethod.GET)
    public Result<List<MemberDTO>> findMemberListByUser(@ModelAttribute MemberQuery memberQuery) {
        List<MemberBO> memberBOS = memberService.findMemberListByUser(memberQuery);
        return (Result<List<MemberDTO>>) MemberConverter.convertListDOTS(memberBOS);
    }

    /**
     * 会员注册
     *
     * @param registerParam 会员注册信息
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Result register(@RequestBody RegisterParam registerParam) {
        memberService.register(registerParam);
        return successCreated();
    }

}
