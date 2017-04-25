package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.operator.api.service.PermissonService;
import com.lawu.eshop.operator.dto.PermissionListDTO;
import com.lawu.eshop.operator.dto.PermissionDTO;
import com.lawu.eshop.operator.param.PermissionParam;
import com.lawu.eshop.operator.param.PerssionParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import om.lawu.eshop.shiro.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/20.
 */
@Api(tags = "permisson")
@RestController
@RequestMapping(value = "permisson")
public class PermissonController extends BaseController{
    @Autowired
    private PermissonService permissonService;

    @ApiOperation(value = "查询权限信息", notes = "查询权限信息 （章勇）", httpMethod = "GET")
    @RequestMapping(value = "getPermssion",method = RequestMethod.GET)
    @RequiresAuthentication
    @ApiResponse(code = HttpCode.SC_OK,message = "success")
    public Result<List<PermissionDTO>> getPermssion(){

        String account = UserUtil.getCurrentUserAccount();
        if(StringUtils.isEmpty(account)){
            return successGet(ResultCode.USER_NOT_LOGIN);
        }
        Result<List<PermissionDTO>> perssions = permissonService.findPermissionByAccount(account);

        return perssions;
    }

    @ApiOperation(value = "新增权限", notes = "新增权限 [1004，1005]（章勇）", httpMethod = "POST")
    @RequiresPermissions("permssion:add")
    @RequestMapping(value = "addPerssion", method = RequestMethod.POST)
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    public Result addPerssion(@ModelAttribute PerssionParam perssionParam) {
        if(perssionParam == null){
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Result result = permissonService.addPerssion(perssionParam);
        return result;
    }

    @ApiOperation(value = "查询权限记录列表", notes = "查询权限记录列表 [1004，1000]（章勇）", httpMethod = "GET")
    @RequiresPermissions("permssion:find")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "findPerminnionList", method = RequestMethod.GET)
    public Result<Page<PermissionListDTO>> findPerminnionList(@ModelAttribute PermissionParam param){
        if(param == null){
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Result<Page<PermissionListDTO>> pageResult = permissonService.findPerminnionList(param);
        return pageResult;
    }
}
