package com.lawu.eshop.member.api.service;

import com.lawu.eshop.member.api.service.hystrix.MemberServiceHystrix;
import com.lawu.eshop.user.dto.UserDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Leach
 * @date 2017/3/13
 */
@FeignClient(value= "member-srv", fallback = MemberServiceHystrix.class)
public interface MemberService {

    /**
     * 查询会员信息
     *
     * @param account 登录账号
     * @param pwd 密码
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "member/find")
    UserDTO find(@RequestParam("account") String account, @RequestParam("pwd") String pwd);

}
