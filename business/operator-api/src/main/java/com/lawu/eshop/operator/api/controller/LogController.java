package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.operator.api.service.LogService;
import com.lawu.eshop.operator.dto.LogDTO;
import com.lawu.eshop.operator.param.ListLogParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author meishuquan
 * @date 2017/5/3.
 */
@Api(tags = "log")
@RestController
@RequestMapping(value = "log/")
public class LogController extends BaseController {

    @Autowired
    private LogService logService;

    @ApiOperation(value = "日志列表", notes = "查询日志列表。（梅述全）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequiresPermissions("log:list")
    @PageBody
    @RequestMapping(value = "listLog", method = RequestMethod.POST)
    public Result<Page<LogDTO>> listAd(@RequestBody @ApiParam ListLogParam listLogParam) {
        return logService.listLog(listLogParam);
    }

    @ApiOperation(value = "日志详情", notes = "查询日志详情。[1002]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getLog/{id}", method = RequestMethod.GET)
    public Result<LogDTO> getLog(@PathVariable @ApiParam(value = "ID") Long id) {
        return logService.getLogById(id);
    }

}
