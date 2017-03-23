package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
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
public class MerchantProfileController {

    @Autowired
    private MerchantProfileService merchantProfileService;

    @ApiOperation(value = "设置网站链接", notes = "设置网站链接", httpMethod = "POST")
   // @Authorization
    @RequestMapping(value = "updateMerchantSizeLink", method = RequestMethod.POST)
    public void updateMerchantSizeLink(@ModelAttribute @ApiParam MerchantProfileParam merchantProfileParam){
        merchantProfileService.updateMerchantSizeLink(merchantProfileParam);
    }


    @ApiOperation(value ="查询商家信息", notes = "查询商家主页基本信息",httpMethod = "POST")
    //@Authorization
    @RequestMapping(value ="findMerchantProfileInfo", method = RequestMethod.POST)
    public MerchantProfileDTO findMerchantProfileInfo(@RequestParam @ApiParam(required = true, value = "商家主键") Long merchantProfileId){
        MerchantProfileDTO merchantProfileDTO = merchantProfileService.findMerchantProfileInfo(merchantProfileId);

        return merchantProfileDTO;
    }


}
