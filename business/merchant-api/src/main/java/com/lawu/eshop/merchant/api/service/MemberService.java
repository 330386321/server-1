package com.lawu.eshop.merchant.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.EfriendDTO;
import com.lawu.eshop.user.dto.RongYunDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.dto.VisitUserInfoDTO;
import com.lawu.eshop.user.param.MemberQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyong
 * @date 2017/4/17.
 */
@FeignClient(value = "user-srv")
public interface MemberService {
    /**
     * 会员资料查询
     *
     * @param memberId 会员id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "member/findMemberInfo/{memberId}")
    Result<UserDTO> findMemberInfo(@PathVariable("memberId") Long memberId);

    /**
     * 查询我的E友
     *
     * @return
     * @author zhangrc
     * @date 2017/03/23
     */
    @RequestMapping(method = RequestMethod.POST, value = "member/findMemberListByUser")
    Result<Page<EfriendDTO>> findMemberListByUser(@RequestParam("userId") Long id, @RequestBody MemberQuery query, @RequestParam("inviterType") byte inviterType);

    /**
     * 根据会员编号查询会员信息
     *
     * @param num
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "member/getMemberByNum")
    Result<UserDTO> getMemberByNum(@RequestParam("num") String num);

    /**
     * 根据会员编号查询融云需要的信息
     *
     * @param num
     * @return
     */
    @RequestMapping(value = "member/getRongYunInfo/{num}", method = RequestMethod.GET)
    Result<RongYunDTO> getRongYunInfoByNum(@PathVariable("num") String num);

    /**
     * 根据用户编号获取用户账号和区域路径
     * @param userNum
     * @return
     */
    @RequestMapping(value = "findUserAccountAndRegionPathByNum", method = RequestMethod.GET)
    VisitUserInfoDTO findUserAccountAndRegionPathByNum(@PathVariable("userNum") String userNum);
}
