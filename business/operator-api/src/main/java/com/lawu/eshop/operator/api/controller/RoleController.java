package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.operator.api.service.RoleService;
import com.lawu.eshop.operator.dto.RoleDTO;
import com.lawu.eshop.operator.param.RoleInfoParam;
import com.lawu.eshop.operator.param.RoleParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyong
 * @date 2017/4/21.
 */
@Api(tags = "role")
@RestController
@RequestMapping(value = "role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "查询角色信息记录", notes = "查询角色信息记录 [1004，1000]（章勇）", httpMethod = "GET")
    @RequiresPermissions("role:find")
    @RequestMapping(value = "findroleList", method = RequestMethod.GET)
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    public Result<Page<RoleDTO>> findroleList(@ModelAttribute RoleParam param) {
        if (param == null) {
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Result<Page<RoleDTO>> pageResult = roleService.findroleList(param);
        return pageResult;
    }

    @ApiOperation(value = "添加角色信息", notes = "添加角色信息 [1004,1000]（章勇）", httpMethod = "POST")
    @RequiresPermissions("role:add")
    @RequestMapping(value = "addRole", method = RequestMethod.POST)
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    public Result addRole(@ModelAttribute RoleInfoParam param) {
        if (param == null || StringUtils.isEmpty(param.getRoleName()) || StringUtils.isEmpty(param.getRoleKey())) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Result result = roleService.addRole(param);
        return result;
    }

    @ApiOperation(value = "编辑角色信息", notes = "编辑角色信息 [1004,1000]（章勇）", httpMethod = "PUT")
    @RequiresPermissions("role:edit")
    @RequestMapping(value = "editRole/{id}", method = RequestMethod.PUT)
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    public Result updateRole(@PathVariable(value = "id") @ApiParam(value = "角色ID", required = true) Integer id, @ModelAttribute RoleInfoParam param) {
        if (param == null ) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Result result = roleService.updateRole(id, param);
        return result;
    }

    @ApiOperation(value = "删除角色信息", notes = "删除角色信息 [1004，2102,1000]（章勇）", httpMethod = "DELETE")
    @RequiresPermissions("role:del")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "delRole/{id}", method = RequestMethod.DELETE)
    public Result delRole(@PathVariable(value = "id") Integer id) {
        if (id == null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Result result = roleService.delRole(id);
        return result;
    }


    /**
     * 权限关联
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    @ApiOperation(value = "权限关联", notes = "权限关联 [1004，2103,1000]（章勇）", httpMethod = "POST")
    @RequiresPermissions("role:add_permission")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "addRolePermission", method = RequestMethod.POST)
    Result addRolePermission(@RequestParam(value = "roleId") @ApiParam(value = "角色ID", required = true) Integer roleId,
                             @RequestParam(value = "permissionId") @ApiParam(value = "权限ID", required = true) Integer permissionId) {
        Result result = roleService.addRolePermission(roleId, permissionId);
        return result;
    }
}
