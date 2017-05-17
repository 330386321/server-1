package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.mall.dto.IndustryTypeDTO;
import com.lawu.eshop.merchant.api.service.IndustryTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/5/16.
 */
@Api(tags = "industryType")
@RestController
@RequestMapping(value = "industryType/")
public class IndustryTypeController {

    @Autowired
    private IndustryTypeService industryTypeService;

    @Audit(date = "2017-05-16", reviewer = "孙林青")
    @ApiOperation(value = "查询行业(嵌套分组)", notes = "查询所有行业(嵌套分组)。 [1100] (章勇)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getIndustryListWithGroup", method = RequestMethod.GET)
    public Result<List<IndustryTypeDTO>> getIndustryListWithGroup() {
        return industryTypeService.listIndustryType();
    }

    @Audit(date = "2017-05-16", reviewer = "孙林青")
    @ApiOperation(value = "查询父行业下的所有行业", notes = "查询父行业下的所有行业。 [1100] (章勇)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listIndustryType/{parentId}", method = RequestMethod.GET)
    public Result<List<IndustryTypeDTO>> listIndustryType(@PathVariable @ApiParam(required = true, value = "父行业ID") Short parentId) {
        return industryTypeService.listIndustryTypeByParentId(parentId);
    }

    @Audit(date = "2017-05-17", reviewer = "孙林青")
    @ApiOperation(value = "查询所有行业（不区分级别）", notes = "查询所有行业（不区分级别）。 [1100] (章勇)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getAllIndustryList", method = RequestMethod.GET)
    public  Result<List<IndustryTypeDTO>> getAllIndustryList() {
        return industryTypeService.getAllIndustryList();
    }
}
