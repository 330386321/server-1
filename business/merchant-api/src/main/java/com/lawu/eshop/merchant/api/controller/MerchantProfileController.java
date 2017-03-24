package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.merchant.api.service.MerchantProfileService;
import com.lawu.eshop.user.dto.MerchantProfileDTO;
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
@Api(tags = "merchantprofile")
@RestController
@RequestMapping(value = "/")
public class MerchantProfileController extends BaseController {

    @Autowired
    private MerchantProfileService merchantProfileService;

    @ApiOperation(value = "设置网站链接", notes = "设置网站链接", httpMethod = "POST")
   // @Authorization
    @RequestMapping(value = "updateMerchantSizeLink", method = RequestMethod.POST)
    public Result updateMerchantSizeLink(@RequestBody  @ApiParam MerchantProfileParam merchantProfileParam,
                                       @RequestParam @ApiParam(required = true, value = "主键") Long id){
      int result =   merchantProfileService.updateMerchantSizeLink(merchantProfileParam,id);
      if(result == 1){
          return successResponse();
      }else {
          return failResponse(ResultCode.BAD_REQUEST,"设置失败");
      }
    }


    @ApiOperation(value ="查询商家信息", notes = "查询商家主页基本信息",httpMethod = "POST")
    //@Authorization
    @RequestMapping(value ="findMerchantProfileInfo", method = RequestMethod.POST)
    public Result<MerchantProfileDTO> findMerchantProfileInfo(@RequestParam @ApiParam(required = true, value = "商家主键") Long merchantProfileId){
        MerchantProfileDTO merchantProfileDTO = merchantProfileService.findMerchantProfileInfo(merchantProfileId);
        if(merchantProfileDTO == null){
            return  successResponse();
        }else{
            return successResponse(merchantProfileDTO);

        }
    }


}
