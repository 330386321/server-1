package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.merchant.api.service.MerchantInfoService;
import com.lawu.eshop.user.dto.MerchantInfoDTO;
import com.lawu.eshop.user.dto.param.MerchantProfileParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @ApiOperation(value = "设置网站链接", notes = "设置网站链接", httpMethod = "PUT")
   // @Authorization
    @RequestMapping(value = "updateMerchantSizeLink", method = RequestMethod.PUT)
    public Result updateMerchantSizeLink(@RequestBody  @ApiParam MerchantProfileParam merchantProfileParam,
                                       @RequestParam @ApiParam(required = true, value = "主键") Long id){
      int result =   merchantProfileService.updateMerchantSizeLink(merchantProfileParam,id);
      if(result == 1){
          return successResponse();
      }else {
          return failResponse(ResultCode.BAD_REQUEST,"设置失败");
      }
    }


    @ApiOperation(value ="查询商家信息", notes = "查询商家主页基本信息",httpMethod = "GET")
    //@Authorization
    @RequestMapping(value ="findMerchantProfileInfo", method = RequestMethod.GET)
    public Result<MerchantInfoDTO> findMerchantProfileInfo(@RequestParam @ApiParam(required = true, value = "商家主键") Long merchantProfileId){
        MerchantInfoDTO merchantProfileDTO = merchantProfileService.findMerchantProfileInfo(merchantProfileId);
        if(merchantProfileDTO == null){
            return  successResponse();
        }else{
            return successResponse(merchantProfileDTO);

        }
    }


}
