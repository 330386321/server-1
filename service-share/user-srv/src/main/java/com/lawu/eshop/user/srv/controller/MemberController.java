package com.lawu.eshop.user.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.user.dto.LoginUserDTO;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.param.RegisterParam;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.query.MemberQuery;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.converter.LoginUserConverter;
import com.lawu.eshop.user.srv.converter.MemberConverter;
import com.lawu.eshop.user.srv.service.MemberService;
import com.lawu.eshop.utils.MD5;

/**
 * @author Leach
 * @date 2017/3/13
 */
@RestController
@RequestMapping(value = "member/")
public class MemberController extends BaseController {

    @Autowired
    private MemberService memberService;

    /**
     * 根据用户名密码查找会员
     *
     * @param account
     * @param pwd
     * @return
     */
    @RequestMapping(value = "withPwd/{account}", method = RequestMethod.GET)
    public Result<LoginUserDTO> find(@PathVariable String account, @RequestParam String pwd) {
        MemberBO memberBO = memberService.find(account, pwd);
        if (memberBO == null) {
            return successGet(ResultCode.MEMBER_WRONG_PWD);
        }
        return successGet(LoginUserConverter.convert(memberBO));
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
        if (memberBO == null) {
            return successGet();
        } else {
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
        int result = memberService.updateMemberInfo(memberParam, id);
        if (result == 1) {
            return successCreated();
        } else {
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
    @RequestMapping(value = "updateLoginPwd/{id}", method = RequestMethod.PUT)
    public Result updateLoginPwd(@PathVariable Long id, @RequestParam String originalPwd, @RequestParam String newPwd) {
        MemberBO memberBO = memberService.getMemberById(id);
        if (!MD5.MD5Encode(originalPwd).equals(memberBO.getPwd())) {
            return successGet(ResultCode.VERIFY_FAIL);
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
    @RequestMapping(value = "getMember/{account}", method = RequestMethod.GET)
    public Result getMemberByAccount(@PathVariable String account) {
        MemberBO memberBO = memberService.getMemberByAccount(account);
        MemberDTO memberDTO = MemberConverter.convertMDTO(memberBO);
        if (memberDTO == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successGet(memberDTO);
    }

    /**
     * 我的E友
     *
     * @param memberQuery 登录用户
     * @return
     * @author zhangrc
     * @date 2017/03/23
     */
    @RequestMapping(value = "findMemberListByUser", method = RequestMethod.POST)
    public Result<Page<MemberDTO>> findMemberListByUser(@RequestParam Long userId, @RequestBody MemberQuery memberQuery) {
        Page<MemberBO> pageMemberBOS = memberService.findMemberListByUser(userId,memberQuery);
        Page<MemberDTO> page = MemberConverter.convertPageDOTS(pageMemberBOS);
        return successGet(page);
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
