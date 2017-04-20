package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.CashUserInfoDTO;
import com.lawu.eshop.user.dto.EfriendDTO;
import com.lawu.eshop.user.dto.LoginUserDTO;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.MemberInfoForShoppingOrderDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.dto.UserHeadImgDTO;
import com.lawu.eshop.user.param.MemberQuery;
import com.lawu.eshop.user.param.RegisterRealParam;
import com.lawu.eshop.user.param.UserParam;

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
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "member/updateLoginPwd/{id}")
    Result updateLoginPwd(@PathVariable("id") Long id, @RequestParam("originalPwd") String originalPwd, @RequestParam("newPwd") String newPwd);

    /**
     * 重置密码
     *
     * @param mobile 账号
     * @param newPwd 新密码
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "member/resetLoginPwd/{mobile}")
    Result resetLoginPwd(@PathVariable("mobile") String mobile, @RequestParam("newPwd") String newPwd);

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
    Result<MemberDTO> getMemberByAccount(@PathVariable("account") String account);

    /**
     * 修改头像
     *
     * @param memberId
     * @param headimg
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "member/saveHeadImage/{memberId}")
    Result<UserHeadImgDTO> saveHeadImage(@PathVariable("memberId") Long memberId, @RequestParam("headimg") String headimg);

    /**
     * 用户、商家提现时根据商家ID获取账号、名称、省市区信息冗余到提现表中
     *
     * @param id
     * @return
     * @author Yangqh
     */
    @RequestMapping(method = RequestMethod.GET, value = "member/findCashUserInfo/{id}")
    CashUserInfoDTO findCashUserInfo(@PathVariable("id") Long id);

    /**
     * 增加推送、融云 CID，token
     * @param id
     * @param cid
     * @param ryToken
     * @return
     */
    @RequestMapping(value = "member/setGtAndRongYunInfo/{id}",method = RequestMethod.PUT)
    Result setGtAndRongYunInfo(@PathVariable("id") Long id,@RequestParam("cid") String cid);

	/**
	 * 创建商品订单需要添加用户的一些信息
	 * 
	 * @param id
	 *            用户id
	 * @return
	 */
	@RequestMapping(value = "member/getMemberInfoForShoppingOrder/{id}", method = RequestMethod.GET)
	Result<MemberInfoForShoppingOrderDTO> getMemberInfoForShoppingOrder(@PathVariable("id") Long id);
	
	/**
	 * 查询用户信息
	 * @param memberId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "member/findMember/{memberId}")
    Result<MemberDTO> findMemberInfoById(@PathVariable("memberId") Long memberId);
}
