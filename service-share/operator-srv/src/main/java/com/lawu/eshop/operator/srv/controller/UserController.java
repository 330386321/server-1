package com.lawu.eshop.operator.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.operator.dto.UserDTO;
import com.lawu.eshop.operator.dto.UserRoleDTO;
import com.lawu.eshop.operator.srv.bo.RoleBO;
import com.lawu.eshop.operator.srv.bo.UserBO;
import com.lawu.eshop.operator.srv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhangyong
 * @date 2017/4/19.
 */
@RestController
@RequestMapping(value = "user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    /**
     * 根据账号密码查询用户信息
     * @param account
     * @param pwd
     * @return
     */
    @RequestMapping(value = "withPwd/{account}",method = RequestMethod.POST)
    public Result<UserDTO> find(@PathVariable("account") String account, @RequestParam(value = "pwd") String pwd) {
        UserBO userBO = userService.find(account, pwd);
        if (userBO == null) {
            return successGet(ResultCode.MEMBER_WRONG_PWD);
        }
        //查询user对应的roleKey
        List<RoleBO> roleBOList = userService.findUserRoleByUserId(userBO.getId());
        List<UserRoleDTO> userRoleDTOS = new ArrayList<>();
        Set<String> rolesKeys = new HashSet<>();
        if(roleBOList.isEmpty()){
            roleBOList = new ArrayList<>();
        }else{
            //查询role对应的permission_key
            for(RoleBO roleBO : roleBOList){
                rolesKeys.add(roleBO.getRoleKey());
                UserRoleDTO userRoleDTO = new UserRoleDTO();
                userRoleDTO.setRoleKey(roleBO.getRoleKey());
                List<String> permissionKeyList = userService.findRolePermission(roleBO.getId());
                userRoleDTO.setPermissionsKey(permissionKeyList);
                userRoleDTOS.add(userRoleDTO);
            }
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setAccount(userBO.getAccount());
        userDTO.setPassword(userBO.getPassword());
        userDTO.setRoles(userRoleDTOS);
        userDTO.setRolesKey(rolesKeys);
        return successGet(userDTO);
    }

    @RequestMapping(value = "findByAccount/{account}",method = RequestMethod.POST)
    public Result<UserDTO> find(@PathVariable("account") String account) {
        UserBO userBO = userService.find(account);
        if (userBO == null) {
            return successGet(ResultCode.MEMBER_WRONG_PWD);
        }
        //查询user对应的roleKey
        List<RoleBO> roleBOList = userService.findUserRoleByUserId(userBO.getId());
        List<UserRoleDTO> userRoleDTOS = new ArrayList<>();
        Set<String> rolesKeys = new HashSet<>();
        if(roleBOList.isEmpty()){
            roleBOList = new ArrayList<>();
        }else{
            //查询role对应的permission_key
            for(RoleBO roleBO : roleBOList){
                rolesKeys.add(roleBO.getRoleKey());
                UserRoleDTO userRoleDTO = new UserRoleDTO();
                userRoleDTO.setRoleKey(roleBO.getRoleKey());
                List<String> permissionKeyList = userService.findRolePermission(roleBO.getId());
                userRoleDTO.setPermissionsKey(permissionKeyList);
                userRoleDTOS.add(userRoleDTO);
            }
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setAccount(userBO.getAccount());
        userDTO.setPassword(userBO.getPassword());
        userDTO.setRoles(userRoleDTOS);
        userDTO.setRolesKey(rolesKeys);
        return successGet(userDTO);
    }

}
