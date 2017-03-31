package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.user.constants.UserSexEnum;
import com.lawu.eshop.user.dto.EfriendDTO;
import com.lawu.eshop.user.dto.LoginUserDTO;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.param.RegisterRealParam;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.query.MemberQuery;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.converter.LoginUserConverter;
import com.lawu.eshop.user.srv.converter.MemberConverter;
import com.lawu.eshop.user.srv.service.MemberService;
import com.lawu.eshop.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result<LoginUserDTO> find(@PathVariable("account") String account, @RequestParam String pwd) {
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
            memberBO.setUserSex(UserSexEnum.getEnum(memberBO.getSex()));
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
     * @param id          ID
     * @param originalPwd 原始密码
     * @param newPwd      新密码
     * @param type        业务类型(1--忘记密码，2--修改密码)
     */
    @RequestMapping(value = "updateLoginPwd/{id}", method = RequestMethod.PUT)
    public Result updateLoginPwd(@PathVariable Long id, @RequestParam String originalPwd, @RequestParam String newPwd, @RequestParam Integer type) {
        MemberBO memberBO = memberService.getMemberById(id);
        if (type == 2 && !MD5.MD5Encode(originalPwd).equals(memberBO.getPwd())) {
            return successGet(ResultCode.VERIFY_PWD_FAIL);
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
    public Result<Page<EfriendDTO>> findMemberListByUser(@RequestParam Long userId, @RequestBody MemberQuery memberQuery) {
        Page<MemberBO> pageMemberBOS = memberService.findMemberListByUser(userId, memberQuery);
        Page<EfriendDTO> page = MemberConverter.convertPageDOTS(pageMemberBOS);
        return successGet(page);
    }

    /**
     * 会员注册
     *
     * @param registerRealParam 会员注册信息
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Result register(@RequestBody RegisterRealParam registerRealParam) {
        memberService.register(registerRealParam);
        return successCreated();
    }

    /**
     * 修改头像
     *
     * @param mermberId
     * @param headimg
     * @return
     */
    @RequestMapping(value = "saveHeadImage/{mermberId}", method = RequestMethod.PUT)
    public Result saveHeadImage(@PathVariable("mermberId") Long mermberId, @RequestParam String headimg) {
        memberService.updateMemberHeadImg(headimg, mermberId);
        return successCreated();
    }

}
