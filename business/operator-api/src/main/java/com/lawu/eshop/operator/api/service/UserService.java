package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.dto.UserDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhangyong
 * @date 2017/4/19.
 */
@FeignClient(value = "operator-srv")
public interface UserService {
    @RequestMapping(value = "user/withPwd/{account}",method = RequestMethod.POST)
    public Result<UserDTO> find(@PathVariable("account") String account, @RequestParam(value = "pwd") String pwd);

    @RequestMapping(value = "user/findByAccount/{account}",method = RequestMethod.POST)
    Result<UserDTO> find(String account);
}
