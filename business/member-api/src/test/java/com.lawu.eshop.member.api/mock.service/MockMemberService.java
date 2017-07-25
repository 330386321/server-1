package com.lawu.eshop.member.api.mock.service;/**
 * Created by ${Yangqh} on 2017/7/24.
 */

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.user.dto.*;
import com.lawu.eshop.user.param.MemberQuery;
import com.lawu.eshop.user.param.RegisterRealParam;
import com.lawu.eshop.user.param.UserParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class MockMemberService extends BaseController implements MemberService {
    @Override
    public Result<LoginUserDTO> find(@PathVariable("account") String account, @RequestParam("pwd") String pwd) {
        return null;
    }

    @Override
    public Result<UserDTO> findMemberInfo(@PathVariable("memberId") Long memberId) {
        return null;
    }

    @Override
    public Result updateMemberInfo(@ModelAttribute UserParam memberParam, @PathVariable("id") Long id) {
        return null;
    }

    @Override
    public Result updateLoginPwd(@PathVariable("id") Long id, @RequestParam("originalPwd") String originalPwd, @RequestParam("newPwd") String newPwd) {
        return null;
    }

    @Override
    public Result resetLoginPwd(@PathVariable("mobile") String mobile, @RequestParam("newPwd") String newPwd) {
        return null;
    }

    @Override
    public Result<Page<EfriendDTO>> findMemberListByUser(@RequestParam("userId") Long id, @RequestBody MemberQuery query, @RequestParam("inviterType") Byte inviterType) {
        return null;
    }

    @Override
    public Result register(@ModelAttribute RegisterRealParam registerRealParam) {
        return null;
    }

    @Override
    public Result<MemberDTO> getMemberByAccount(@PathVariable("account") String account) {
        return null;
    }

    @Override
    public Result<UserHeadImgDTO> saveHeadImage(@PathVariable("memberId") Long memberId, @RequestParam("headimg") String headimg) {
        return null;
    }

    @Override
    public CashUserInfoDTO findCashUserInfo(@PathVariable("id") Long id) {
        return null;
    }

    @Override
    public Result setGtAndRongYunInfo(@PathVariable("id") Long id, @RequestParam("cid") String cid) {
        return null;
    }

    @Override
    public Result<MemberInfoForShoppingOrderDTO> getMemberInfoForShoppingOrder(@PathVariable("id") Long id) {
        return null;
    }

    @Override
    public Result<MemberDTO> findMemberInfoById(@PathVariable("memberId") Long memberId) {
        return null;
    }

    @Override
    public Result<UserRedPacketDTO> isRegister(@RequestParam("moblie") String moblie) {
        return null;
    }

    @Override
    public Result<RongYunDTO> getRongYunInfoByNum(@PathVariable("num") String num) {
        return null;
    }

    @Override
    public Result<Boolean> isExistsMobile(@RequestParam("mobile") String mobile) {
        return null;
    }

    @Override
    public Result delUserGtPush(@RequestParam("memberId") Long memberId) {
        return null;
    }

    @Override
    public Result<List<MemberDTO>> getMemberByIds(@RequestParam("memberIds") List<Long> memberIds) {
        return null;
    }

    @Override
    public Result<MemberMineInfoDTO> findMemberMineInfo(@PathVariable("memberId") Long memberId) {
        return null;
    }

    @Override
    public Result<AdQueryMemberInfoDTO> adQueryMemberInfo(@PathVariable("memberId") Long memberId) {
        return null;
    }
}