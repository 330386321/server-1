package com.lawu.eshop.merchant.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.merchant.api.service.MerchantInfoService;
import com.lawu.eshop.merchant.api.service.PropertyInfoService;
import com.lawu.eshop.property.dto.PropertyLoveAccountDTO;
import com.lawu.eshop.user.dto.MerchantInfoDTO;
import com.lawu.eshop.user.dto.MerchantInfoFromPublishAdDTO;
import com.lawu.eshop.user.dto.param.MerchantSizeLinkDTO;
import com.lawu.eshop.user.param.MerchantProfileParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * 商家扩展信息
 * Created by Administrator on 2017/3/23.
 */
@Api(tags = "merchantInfo")
@RestController
@RequestMapping(value = "merchantInfo/")
public class MerchantInfoController extends BaseController {

    @Autowired
    private MerchantInfoService merchantProfileService;
    @Autowired
    private PropertyInfoService propertyInfoService;
    
    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "设置网站链接", notes = "设置网站链接，成功返回merchantInfo。[2100] （章勇）", httpMethod = "PUT")
    @Authorization
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "updateMerchantSizeLink", method = RequestMethod.PUT)
    public Result updateMerchantSizeLink(@ModelAttribute @ApiParam(name = "merchantProfileParam", value = "商家附件") MerchantProfileParam merchantProfileParam,
                                         @RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        Long id = UserUtil.getCurrentUserId(getRequest());
        Result result = merchantProfileService.updateMerchantSizeLink(merchantProfileParam, id);
        return result;
    }


    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @ApiOperation(value = "查询商家主页基本信息", notes = "查询商家主页基本信息，成功返回merchantInfo。（章勇）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getCurrentMerchantInfo", method = RequestMethod.GET)
    public Result<MerchantInfoDTO> getCurrentMerchantInfo(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        Long id = UserUtil.getCurrentUserId(getRequest());
        String userNum = UserUtil.getCurrentUserNum(getRequest());
        Result<MerchantInfoDTO> result = merchantProfileService.getCurrentMerchantInfo(id);
        PropertyLoveAccountDTO propertyLoveAccountDTO=propertyInfoService.selectLoveAccount(userNum).getModel();
        result.getModel().setLoveAccount(propertyLoveAccountDTO.getLoveAccount());
        return result;
    }

    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "查询门店网站链接信息", notes = "查询门店网站链接信息 [1004]（章勇）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getMerchantSizeLink", method = RequestMethod.GET)
    public Result<MerchantSizeLinkDTO> getMerchantSizeLink(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        Long merchantId = UserUtil.getCurrentUserId(getRequest());
        Result<MerchantSizeLinkDTO> result = merchantProfileService.getMerchantSizeLink(merchantId);
        return result;
    }

    @Audit(date = "2017-08-03", reviewer = "孙林青")
    @ApiOperation(value = "创建广告时查询的商家信息", notes = "创建广告时查询的商家信息 []（洪钦明）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getMerchantInfoFromPublishAd", method = RequestMethod.GET)
    public Result<MerchantInfoFromPublishAdDTO> getMerchantInfoFromPublishAd(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        Long merchantId = UserUtil.getCurrentUserId(getRequest());
        Result<MerchantInfoFromPublishAdDTO> result =  merchantProfileService.getMerchantInfoFromPublishAd(merchantId);
        return result;
    }
}
