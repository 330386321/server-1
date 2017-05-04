package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.user.dto.*;
import com.lawu.eshop.user.param.MemberQuery;
import com.lawu.eshop.user.param.RegisterRealParam;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.srv.bo.CashUserInfoBO;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.bo.MessagePushBO;
import com.lawu.eshop.user.srv.converter.LoginUserConverter;
import com.lawu.eshop.user.srv.converter.MemberConverter;
import com.lawu.eshop.user.srv.service.MemberService;
import com.lawu.eshop.utils.BeanUtil;
import com.lawu.eshop.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
     */
    @RequestMapping(value = "updateLoginPwd/{id}", method = RequestMethod.PUT)
    public Result updateLoginPwd(@PathVariable Long id, @RequestParam String originalPwd, @RequestParam String newPwd) {
        MemberBO memberBO = memberService.getMemberById(id);
        if (memberBO == null) {
            return successCreated(ResultCode.RESOURCE_NOT_FOUND);
        }
        if (!MD5.MD5Encode(originalPwd).equals(memberBO.getPwd())) {
            return successGet(ResultCode.VERIFY_PWD_FAIL);
        }
        memberService.updateLoginPwd(id, newPwd);
        return successCreated();
    }

    /**
     * 重置登录密码
     *
     * @param mobile 账号
     * @param newPwd 新密码
     */
    @RequestMapping(value = "resetLoginPwd/{mobile}", method = RequestMethod.PUT)
    public Result resetLoginPwd(@PathVariable String mobile, @RequestParam String newPwd) {
        MemberBO memberBO = memberService.getMemberByAccount(mobile);
        if (memberBO == null) {
            return successCreated(ResultCode.NOT_FOUND_DATA);
        }
        memberService.updateLoginPwd(memberBO.getId(), newPwd);
        return successCreated();
    }

    /**
     * 根据账号查询会员信息
     *
     * @param account 会员账号
     * @return
     */
    @RequestMapping(value = "getMember/{account}", method = RequestMethod.GET)
    public Result<MemberDTO> getMemberByAccount(@PathVariable String account) {
        MemberBO memberBO = memberService.getMemberByAccount(account);
        if (memberBO == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successGet(MemberConverter.convertMDTO(memberBO));
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
    public Result<Page<EfriendDTO>> findMemberListByUser(@RequestParam Long userId, @RequestBody MemberQuery memberQuery, @RequestParam byte inviterType) {
        Page<MemberBO> pageMemberBOS = memberService.findMemberListByUser(userId, memberQuery, inviterType);
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
     * @param memberId
     * @param headimg
     * @return
     */
    @RequestMapping(value = "saveHeadImage/{memberId}", method = RequestMethod.POST)
    public Result<UserHeadImgDTO> saveHeadImage(@PathVariable("memberId") Long memberId, @RequestParam String headimg) {
        memberService.updateMemberHeadImg(headimg, memberId);
        UserHeadImgDTO userHeadImgDTO = new UserHeadImgDTO();
        userHeadImgDTO.setHeadImg(headimg);
        return successCreated(userHeadImgDTO);
    }

    /**
     * 用户、商家提现时根据用户ID获取账号、名称、省市区信息冗余到提现表中
     *
     * @param id
     * @return
     * @throws Exception
     * @author Yangqh
     */
    @RequestMapping(value = "findCashUserInfo/{id}", method = RequestMethod.GET)
    public CashUserInfoDTO findCashUserInfo(@PathVariable("id") Long id) throws Exception {
        CashUserInfoBO cashUserInfoBO = memberService.findCashUserInfo(id);
        if (cashUserInfoBO == null) {
            return null;
        }
        CashUserInfoDTO dto = new CashUserInfoDTO();
        BeanUtil.copyProperties(cashUserInfoBO, dto);
        return dto;
    }

    /**
     * 根据地区查询人数
     *
     * @param areas
     * @return
     */
    @RequestMapping(value = "findMemberCount", method = RequestMethod.GET)
    public Integer findMemberCount(@RequestParam("areas") String areas) {
        Integer count = memberService.findMemberCount(areas);
        return count;
    }

    /**
     * 增加推送、即时通讯token
     *
     * @param id
     * @param cid
     * @return
     */
    @RequestMapping(value = "setGtAndRongYunInfo/{id}", method = RequestMethod.PUT)
    public Result setGtAndRongYunInfo(@PathVariable("id") Long id, @RequestParam("cid") String cid) {
        Integer row = memberService.setGtAndRongYunInfo(id, cid);
        if (row == null || row <= 0) {
            successCreated(ResultCode.SAVE_FAIL);
        }
        return successCreated(ResultCode.SUCCESS);
    }

    /**
     * 创建商品订单需要添加用户的一些信息
     *
     * @param id 用户id
     * @return
     */
    @RequestMapping(value = "getMemberInfoForShoppingOrder/{id}", method = RequestMethod.GET)
    public Result<MemberInfoForShoppingOrderDTO> getMemberInfoForShoppingOrder(@PathVariable("id") Long id) {
        MemberBO memberBO = memberService.getMemberById(id);
        if (memberBO == null || memberBO.getId() == null || memberBO.getId() <= 0) {
            successCreated(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successCreated(MemberConverter.convert(memberBO));
    }


    @RequestMapping(value = "findMember/{memberId}", method = RequestMethod.GET)
    public Result<MemberDTO> findMember(@PathVariable("memberId") Long memberId) {
        MemberBO memberBO = memberService.findMemberInfoById(memberId);
        if (memberBO == null) {
            return successGet();
        } else {
            return successGet(MemberConverter.convertMDTO(memberBO));
        }
    }

    /**
     * 查询所有用户userNum，cid
     *
     * @return
     */
    @RequestMapping(value = "findMessagePushList", method = RequestMethod.GET)
    public Result<List<MessagePushDTO>> findMessagePushList(@RequestParam(value = "area", required = false) String area) {
        List<MessagePushBO> list = memberService.findMessagePushList(area);
        if (list.isEmpty()) {
            return successGet(new ArrayList<MessagePushDTO>());
        }
        List<MessagePushDTO> pushDTOS = new ArrayList<>();
        for (MessagePushBO messagePushBO : list) {
            MessagePushDTO messagePushDTO = new MessagePushDTO();
            messagePushDTO.setUserNum(messagePushBO.getUserNum());
            messagePushDTO.setGtCid(messagePushBO.getGtCid());
            pushDTOS.add(messagePushDTO);
        }
        return successGet(pushDTOS);
    }

    /**
     * 根据手机号查询userNum
     *
     * @param moblie
     * @return
     */
    @RequestMapping(value = "findMessagePushByMobile", method = RequestMethod.GET)
    MessagePushDTO findMessagePushByMobile(@RequestParam("moblie") String moblie) {
        MessagePushBO messagePushBO = memberService.findMessagePushByMobile(moblie);
        if (messagePushBO == null) {
            return null;
        }
        MessagePushDTO messagePushDTO = new MessagePushDTO();
        messagePushDTO.setUserNum(messagePushBO.getUserNum());
        return messagePushDTO;
    }

    /**
     * 判断用户是否注册
     *
     * @param moblie
     * @return
     */
    @RequestMapping(value = "isRegister", method = RequestMethod.GET)
    Result<Boolean> isRegister(@RequestParam("moblie") String moblie) {
        Boolean flag = memberService.isRegister(moblie);
        return successCreated(flag);
    }

    /**
     * 根据会员编号查询会员信息
     *
     * @param num
     * @return
     */
    @RequestMapping(value = "getMemberByNum", method = RequestMethod.GET)
    Result<UserDTO> getMemberByNum(@RequestParam String num) {
        MemberBO memberBO = memberService.getMemberByNum(num);
        if (memberBO == null) {
            return successGet(ResultCode.NOT_FOUND_DATA);
        }
        return successGet(MemberConverter.convertDTO(memberBO));
    }

}
