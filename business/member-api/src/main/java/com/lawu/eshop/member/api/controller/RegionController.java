package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.mall.dto.RegionDTO;
import com.lawu.eshop.member.api.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/10.
 */
@Api(tags = "region")
@RestController
@RequestMapping("/")
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
}
