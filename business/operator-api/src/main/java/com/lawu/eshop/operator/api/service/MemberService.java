package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.MessagePushDTO;
import com.lawu.eshop.user.dto.UserDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/24.
 */
@FeignClient(value = "user-srv")
public interface MemberService {

    /**
     * 查询所有用户userNum，cid
     * @return
     */
    @RequestMapping(value = "member/findMessagePushList", method = RequestMethod.GET)
    Result<List<MessagePushDTO>> findMessagePushList(@RequestParam(value = "area",required = false) String area);

    @RequestMapping(value = "member/findMessagePushByMobile", method = RequestMethod.GET)
    MessagePushDTO findMessagePushByMobile(@RequestParam("moblie") String moblie);

    /**
     * 根据账号查询会员信息
     *
     * @param account 会员账号
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "member/getMember/{account}")
    Result<MemberDTO> getMemberByAccount(@PathVariable("account") String account);

    /**
     * 根据编号查询会员信息
     *
     * @param num
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "member/getMemberByNum")
    Result<UserDTO> getMemberByNum(@RequestParam("num") String num);

    @RequestMapping(method = RequestMethod.GET, value = "member/getMemberAccountById")
    String getMemberAccountById(@RequestParam("memberId") Long memberId);
}
