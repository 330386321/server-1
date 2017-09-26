package com.lawu.eshop.merchant.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.mall.dto.RegionDTO;
import com.lawu.eshop.mall.dto.RegionPathDTO;
import com.lawu.eshop.mall.dto.RegionProvinceDTO;
import com.lawu.eshop.merchant.api.service.RegionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * @author zhangyong
 * @date 2017/4/10.
 */
@Api(tags = "region")
@RestController
@RequestMapping("region/")
public class RegionController extends BaseController {

    @Autowired
    private RegionService regionService;

    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "城市列表", notes = "城市列表 [1004,1000] 章勇", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "region/getRegionList", method = RequestMethod.GET)
    public Result<List<RegionDTO>> getRegionList() {
        Result<List<RegionDTO>> listResult = regionService.getRegionList();
        return listResult;
    }
    
    @SuppressWarnings("unchecked")
	@ApiOperation(value = "地区列表", notes = "所有地区列表 [1004,1000](蒋鑫俊)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "group", method = RequestMethod.GET)
    public Result<List<RegionProvinceDTO>> group() {
        Result<List<RegionProvinceDTO>> listResult = regionService.group();
        return successGet(listResult);
    }
    
    @SuppressWarnings("unchecked")
	@ApiOperation(value = "地区列表", notes = "所有地区列表 [1004,1000](蒋鑫俊)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Result<List<RegionPathDTO>> list() {
        Result<List<RegionPathDTO>> listResult = regionService.list();
        return successGet(listResult);
    }

    @Audit(date = "2017-09-19", reviewer = "杨清华")
    @ApiOperation(value = "pc地区控件数据来源", notes = "pc地区控件数据来源-杨清华", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "region/getRegionSelectorData", method = RequestMethod.GET)
    public Result<String> getRegionSelectorData() {
        return regionService.getRegionSelectorData();
    }
}
