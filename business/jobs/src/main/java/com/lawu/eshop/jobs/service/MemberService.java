package com.lawu.eshop.jobs.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.dto.VisitUserInfoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhangyong
 * @date 2017/7/3.
 */
@FeignClient(value = "user-srv")
public interface MemberService {

    @RequestMapping(value = "member/getTotalCount",method = RequestMethod.GET)
    Integer getTotalCount();

    @RequestMapping(value = "member/findUserAccountAndRegionPathByNum", method = RequestMethod.GET)
    VisitUserInfoDTO findUserAccountAndRegionPathByNum(@RequestParam("userNum") String userNum);
    
    @RequestMapping(value = "findMemberInfo/{memberId}", method = RequestMethod.GET)
    Result<UserDTO> findMemberInfo(@PathVariable("memberId") Long memberId);
}
