package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.mall.dto.IndustryTypeDTO;
import com.lawu.eshop.member.api.service.IndustryTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
@Api(tags = "industryType")
@RestController
@RequestMapping(value = "industryType/")
public class IndustryTypeController extends BaseController {

    @Autowired
    private IndustryTypeService industryTypeService;

    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "查询行业", notes = "查询所有行业。 [1100] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "listIndustryType", method = RequestMethod.GET)
    public Result<List<IndustryTypeDTO>> listIndustryType() {
        return industryTypeService.listIndustryType();
    }

    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @ApiOperation(value = "查询父行业下的行业", notes = "根据父行业ID查询行业。 [1100] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "listIndustryType/{parentId}", method = RequestMethod.GET)
    public Result<List<IndustryTypeDTO>> listIndustryTypeByParentId(@PathVariable @ApiParam(required = true, value = "父行业ID") Short parentId) {
        return industryTypeService.listIndustryTypeByParentId(parentId);
    }
}
