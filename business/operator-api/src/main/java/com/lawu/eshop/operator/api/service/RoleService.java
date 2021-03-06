package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.dto.RoleDTO;
import com.lawu.eshop.operator.param.RoleInfoParam;
import com.lawu.eshop.operator.param.RoleParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/21.
 */
@FeignClient(value = "operator-srv")
public interface RoleService {

    @RequestMapping(value = "role/findroleList",method = RequestMethod.POST)
    Result<Page<RoleDTO>> findroleList(@ModelAttribute RoleParam param);

    @RequestMapping(value = "role/addRole",method = RequestMethod.POST)
    Result addRole(@ModelAttribute RoleInfoParam param);

    @RequestMapping(value = "role/updateRole/{id}", method = RequestMethod.PUT)
    Result updateRole(@PathVariable(value = "id") Integer id, @ModelAttribute RoleInfoParam param);

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "role/delRole/{id}", method = RequestMethod.PUT)
     Result delRole(@PathVariable(value = "id") Integer id);

    /**
     * 权限关联
     * @param roleId
     * @param permissionId
     * @return
     */
    @RequestMapping(value = "role/addRolePermission", method = RequestMethod.POST)
    Result addRolePermission(@RequestParam(value = "roleId") Integer roleId, @RequestParam(value = "permissionIds") String permissionIds);

    @RequestMapping(value = "role/findroleListAll",method = RequestMethod.GET)
    Result<List<RoleDTO>> findroleListAll();

    @RequestMapping(value = "role/findRoleByUserId/{userId}",method = RequestMethod.GET)
    List<RoleDTO> findRoleByUserId(@PathVariable(value = "userId") Integer userId);

    @RequestMapping(value = "role/findRoleById/{id}",method = RequestMethod.GET)
    RoleDTO findRoleById(@PathVariable(value = "id") Integer id);
}
