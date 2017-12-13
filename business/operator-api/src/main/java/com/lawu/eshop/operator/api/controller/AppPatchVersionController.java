package com.lawu.eshop.operator.api.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.mall.constants.AppPatchVersionStatusEnum;
import com.lawu.eshop.mall.dto.AppPatchVersionOperatorDTO;
import com.lawu.eshop.mall.param.AppPatchVersionParam;
import com.lawu.eshop.mall.query.OperatorAppPatchVersionQuery;
import com.lawu.eshop.operator.api.service.AppPatchVersionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author meishuquan
 * @date 2017/12/12.
 */
@Api(tags = "appPatchVersion")
@RestController
@RequestMapping(value = "appPatchVersion/")
public class AppPatchVersionController extends BaseController {

    @Autowired
    private AppPatchVersionService appPatchVersionService;

    @ApiOperation(value = "新增APP补丁版本", notes = "新增APP补丁版本。（梅述全）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("patch_version:add")
    @RequestMapping(value = "saveAppPatchVersion", method = RequestMethod.POST)
    public Result saveAppPatchVersion(@ModelAttribute AppPatchVersionParam param) {
        return appPatchVersionService.saveAppPatchVersion(param);
    }

    @ApiOperation(value = "启用APP补丁版本", notes = "启用APP补丁版本。（梅述全）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("patch_version:enable")
    @RequestMapping(value = "updateAppPatchVersionStatus/{id}", method = RequestMethod.PUT)
    public Result updateAppPatchVersionStatus(@PathVariable @ApiParam(name = "id", required = true, value = "ID") Long id) {
        return appPatchVersionService.updateAppPatchVersionStatus(id, AppPatchVersionStatusEnum.ENABLE);
    }

    @ApiOperation(value = "APP补丁版本列表", notes = "APP补丁版本列表。（梅述全）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("patch_version:list")
    @PageBody
    @RequestMapping(value = "listOperatorAppPatchVersion", method = RequestMethod.POST)
    public Result<Page<AppPatchVersionOperatorDTO>> listOperatorAppPatchVersion(@RequestBody @ApiParam OperatorAppPatchVersionQuery query) {
        return appPatchVersionService.listOperatorAppPatchVersion(query);
    }

}
