package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.operator.api.service.PropertyService;
import com.lawu.eshop.user.dto.PropertyDTO;
import com.lawu.eshop.user.param.ListPropertyParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author meishueuan
 * @date 2017/4/10.
 */
@Api(tags = "property")
@RestController
@RequestMapping(value = "property/")
public class PropertyController extends BaseController {

    @Autowired
    private PropertyService propertyService;

    @ApiOperation(value = "新增系统参数", notes = "新增系统参数。（梅述全）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "saveProperty", method = RequestMethod.POST)
    public Result saveProperty(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                               @RequestParam @ApiParam(name = "name", required = true, value = "键") String name,
                               @RequestParam @ApiParam(name = "value", required = true, value = "值") String value,
                               @RequestParam @ApiParam(name = "remark", required = true, value = "备注") String remark) {
        return propertyService.saveProperty(name, value, remark);
    }

    @ApiOperation(value = "删除系统参数", notes = "根据ID删除系统参数。[1002]（梅述全）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @Authorization
    @RequestMapping(value = "deleteProperty/{id}", method = RequestMethod.DELETE)
    public Result deleteProperty(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                 @PathVariable @ApiParam(name = "id", required = true, value = "ID") Long id) {
        return propertyService.deletePropertyById(id);
    }

    @ApiOperation(value = "修改系统参数", notes = "根据ID修改系统参数。[1002]（梅述全）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "updateProperty/{id}", method = RequestMethod.PUT)
    public Result updateProperty(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                 @PathVariable @ApiParam(name = "id", required = true, value = "ID") Long id,
                                 @RequestParam @ApiParam(name = "name", required = true, value = "键") String name,
                                 @RequestParam @ApiParam(name = "value", required = true, value = "值") String value,
                                 @RequestParam @ApiParam(name = "remark", required = true, value = "备注") String remark) {
        return propertyService.updatePropertyById(id, name, value, remark);
    }

    @ApiOperation(value = "根据ID查询系统参数", notes = "根据ID查询系统参数。[1002]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getProperty/{id}", method = RequestMethod.GET)
    public Result<PropertyDTO> getProperty(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                           @PathVariable @ApiParam(name = "id", required = true, value = "ID") Long id) {
        return propertyService.getPropertyById(id);
    }

    @ApiOperation(value = "系统参数列表", notes = "系统参数列表。（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "listProperty", method = RequestMethod.GET)
    public Result<Page<PropertyDTO>> listSearchWord(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                    @ModelAttribute @ApiParam ListPropertyParam listPropertyParam) {
        return propertyService.listProperty(listPropertyParam);
    }
}
