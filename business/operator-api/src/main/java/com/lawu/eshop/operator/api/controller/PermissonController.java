package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.operator.api.service.PermissonService;
import com.lawu.eshop.operator.dto.PermissionDTO;
import com.lawu.eshop.operator.dto.PermissionListDTO;
import com.lawu.eshop.operator.param.PermissionParam;
import com.lawu.eshop.operator.param.PerssionParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/20.
 */
@Api(tags = "permission")
@RestController
@RequestMapping(value = "permission/")
public class PermissonController extends BaseController {
    @Autowired
    private PermissonService permissonService;

    @ApiOperation(value = "查询权限信息", notes = "查询权限信息 （章勇）", httpMethod = "GET")
    @RequestMapping(value = "getPermssion", method = RequestMethod.GET)
    // @RequiresAuthentication
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    public Result<List<PermissionDTO>> getPermssion(@RequestParam("account") String account) {

       // String account = UserUtil.getCurrentUserAccount();
        if (StringUtils.isEmpty(account)) {
            return successGet(ResultCode.USER_NOT_LOGIN);
        }
        Result<List<PermissionDTO>> perssions = permissonService.findPermissionByAccount(account);

        return perssions;
    }

    @ApiOperation(value = "新增权限", notes = "新增权限 [1004，1005]（章勇）", httpMethod = "POST")
    // @RequiresPermissions("permssion:add")
    @RequestMapping(value = "addPermssion", method = RequestMethod.POST)
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    public Result addPermssion(@ModelAttribute PerssionParam perssionParam) {
        if (perssionParam == null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Result result = permissonService.addPerssion(perssionParam);
        return result;
    }

    @ApiOperation(value = "查询权限记录列表", notes = "查询权限记录列表 [1004，1000]（章勇）", httpMethod = "GET")
    // @RequiresPermissions("permssion:find")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @PageBody
    @RequestMapping(value = "findPermissionList", method = RequestMethod.GET)
    public Result<Page<PermissionListDTO>> findPermissionList(@ModelAttribute PermissionParam param) {
        if (param == null) {
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Result<Page<PermissionListDTO>> pageResult = permissonService.findPermissionList(param);
        return pageResult;
    }

    @ApiOperation(value = "查询权限记录列表", notes = "查询权限记录列表 [1004，1000]（章勇）", httpMethod = "GET")
    // @RequiresPermissions("permssion:find")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "findAllPermissionList", method = RequestMethod.GET)
    public Result<List<PermissionListDTO>> findAllPerminnionList() {
        List<PermissionListDTO> listDTOS = permissonService.findAllPermissionList();
        return successGet(listDTOS);
    }


    @ApiOperation(value = "根据roleId查询权限记录ID", notes = "根据roleId查询权限记录ID [1000]（章勇）", httpMethod = "GET")
    // @RequiresPermissions("permssion:find")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "findPermissionListByRoleId/{roleId}", method = RequestMethod.GET)
    public Result<List<PermissionListDTO>> findPermissionListByRoleId(@PathVariable(value = "roleId") Integer roleId) {
        List<PermissionListDTO> listDTOS = permissonService.findPermissionListByRoleId(roleId);
        return successGet(listDTOS);
    }

    @ApiOperation(value = "删除权限记录", notes = "删除权限记录 [1004，1000]（章勇）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "delPermission", method = RequestMethod.POST)
    public Result delPermission(@RequestParam(value = "permissionIds") String permissionIds) {
        if (StringUtils.isEmpty(permissionIds)) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        return permissonService.delPermission(permissionIds);
    }

    @ApiOperation(value = "根据ID查询权限信息", notes = "根据ID查询权限信息 [1004，1000]（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "findPermissionInfoById/{id}", method = RequestMethod.GET)
    public Result<PermissionListDTO> findPermissionInfoById(@PathVariable(value = "id") Integer id) {
        if (id == null) {
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        PermissionListDTO permissionListDTO = permissonService.findPermissionInfoById(id);
        return successGet(permissionListDTO);
    }

    @ApiOperation(value = "编辑权限信息", notes = "编辑权限信息 [1004，1000]（章勇）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "editPermission/{id}", method = RequestMethod.PUT)
    public Result editPermission(@PathVariable(value = "id") Integer id, @ModelAttribute PerssionParam perssionParam) {
        if (id == null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }

        return permissonService.editPermission(id, perssionParam);
    }

}
