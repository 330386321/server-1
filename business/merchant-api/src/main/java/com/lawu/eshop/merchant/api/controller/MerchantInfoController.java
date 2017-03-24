package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.merchant.api.service.MerchantInfoService;
import com.lawu.eshop.user.dto.MerchantInfoDTO;
import com.lawu.eshop.user.param.MerchantProfileParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商家扩展信息
 * Created by Administrator on 2017/3/23.
 */
@Api(tags = "merchantInfo")
@RestController
@RequestMapping(value = "/")
public class MerchantInfoController extends BaseController {

    @Autowired
    private MerchantInfoService merchantProfileService;

    @ApiOperation(value = "设置网站链接", notes = "设置网站链接，成功返回merchantInfo。[2100] （章勇）", httpMethod = "PUT")
   // @Authorization
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "updateMerchantSizeLink", method = RequestMethod.PUT)
    public Result updateMerchantSizeLink(@ModelAttribute  @ApiParam MerchantProfileParam merchantProfileParam,
                                       @RequestParam @ApiParam(required = true, value = "商家ID") Long id){
        Result result =   merchantProfileService.updateMerchantSizeLink(merchantProfileParam,id);
        return result;
    }


    @ApiOperation(value ="查询商家信息", notes = "查询商家主页基本信息，成功返回merchantInfo。（章勇）",httpMethod = "GET")
    //@Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value ="findMerchantProfileInfo/{merchantProfileId}", method = RequestMethod.GET)
    public Result<MerchantInfoDTO> findMerchantProfileInfo(@PathVariable("merchantProfileId") @ApiParam(required = true, value = "商家主键") Long merchantProfileId){
        Result<MerchantInfoDTO> result = merchantProfileService.findMerchantProfileInfo(merchantProfileId);
        return result;
    }


}
