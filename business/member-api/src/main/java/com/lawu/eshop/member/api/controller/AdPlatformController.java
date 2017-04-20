package com.lawu.eshop.member.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.dto.AdPlatformDTO;
import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.member.api.service.AdPlatformService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * 描述：广告管理
 * @author zhangrc
 * @date 2017/04/5
 */
@Api(tags = "adPlatform")
@RestController
@RequestMapping(value = "adPlatform/")
public class AdPlatformController extends BaseController {

    @Autowired
    private AdPlatformService adPlatformService;

    /**
     * 根据位置查询广告
     * @param positionEnum
     * @return
     */
    @Authorization
    @ApiOperation(value = "广告信息查询", notes = "广告信息查询[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectByPosition", method = RequestMethod.GET)
    public Result<List<AdPlatformDTO>> selectByPosition(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@ModelAttribute @ApiParam(required = true, value = "POSITON_RECOMMEND 人气推荐 POSITON_SHOP_TOP 要购物顶部广告 POSITON_SHOP_CHOOSE"
			+ "要购物今日推荐  POSITON_SHOP_GOODS 要购物精品 POSITON_AD_TOP 看广告顶部广告") PositionEnum positionEnum) {
        Result<List<AdPlatformDTO>> adPlatformDTOS = adPlatformService.selectByPosition(positionEnum);
        return adPlatformDTOS;
    }



}
