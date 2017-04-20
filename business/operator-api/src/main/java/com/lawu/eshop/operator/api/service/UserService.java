package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.dto.UserDTO;
import com.lawu.eshop.operator.dto.UserListDTO;
import com.lawu.eshop.operator.param.UserPageParam;
import com.lawu.eshop.operator.param.UserParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyong
 * @date 2017/4/19.
 */
@FeignClient(value = "operator-srv")
public interface UserService {

    @RequestMapping(value = "user/withPwd/{account}",method = RequestMethod.POST)
    public Result<UserDTO> find(@PathVariable("account") String account, @RequestParam(value = "pwd") String pwd);

    @RequestMapping(value = "user/findByAccount/{account}",method = RequestMethod.POST)
    Result<UserDTO> find(@PathVariable(value = "account") String account);

    /**
     * 新增用户
     * @param account
     * @param name
     * @param password
     * @return
     */
    @RequestMapping(value = "user/addUser/",method = RequestMethod.POST)
    Result addUser(@RequestParam(value = "account") String account,@RequestParam(value = "name") String name,@RequestParam(value = "password") String password);

    /**
     * 编辑用户
     * @param userParam
     * @return
     */
    @RequestMapping(value = "user/editUser", method = RequestMethod.PUT)
    Result editUser(@ModelAttribute UserParam userParam);

    @RequestMapping(value = "user/findUserList", method = RequestMethod.POST)
    Result<Page<UserListDTO>> findUserList(@ModelAttribute UserPageParam pageParam);

    /**
     * 分配角色
     * @param userId
     * @param roleId
     * @return
     */
    @RequestMapping(value = "user/assignRoles", method = RequestMethod.POST)
    Result assignRoles(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "roleId") Integer roleId);

    @RequestMapping(value = "user/delUser/{id}", method = RequestMethod.DELETE)
    Result delUser(@PathVariable(value = "id") Integer id);

    @RequestMapping(value = "user/userDisabled/{id}", method = RequestMethod.PUT)
    Result userDisabled(@PathVariable(value = "id") Integer id);
}
