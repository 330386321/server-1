package com.lawu.eshop.external.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.MemberDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 */
@FeignClient(value = "user-srv", path = "member/")
public interface MemberService {

    /**
     * 查询用户信息
     *
     * @param memberId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "findMember/{memberId}")
    Result<MemberDTO> findMemberInfoById(@PathVariable("memberId") Long memberId);

}
