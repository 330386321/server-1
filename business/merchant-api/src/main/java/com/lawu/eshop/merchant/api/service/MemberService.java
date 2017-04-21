package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.EfriendDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.param.MemberQuery;

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
    Result<Page<EfriendDTO>> findMemberListByUser(@RequestParam("userId") Long id, @RequestBody MemberQuery query,@RequestParam("inviterType") byte inviterType);
}
