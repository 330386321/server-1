package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.api.service.RoleService;
import com.lawu.eshop.operator.dto.RoleDTO;
import com.lawu.eshop.operator.param.RoleParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyong
 * @date 2017/4/21.
 */
@Api(tags = "role")
@RestController
@RequestMapping(value = "role")
public class RoleController extends BaseController{

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "查询角色信息记录", notes = "查询角色信息记录 [1004，1000]（章勇）", httpMethod = "GET")
    @RequiresPermissions("role:find")
    @RequestMapping(value = "findroleList", method = RequestMethod.GET)
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    public Result<Page<RoleDTO>> findroleList(@ModelAttribute RoleParam param){

        return successGet();
    }
}
