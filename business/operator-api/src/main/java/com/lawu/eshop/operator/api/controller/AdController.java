package com.lawu.eshop.operator.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.param.AdFindParam;
import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.operator.api.service.AdService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * 描述：广告管理
 * @author zhangrc
 * @date 2017/04/5
 */
@Api(tags = "ad")
@RestController
@RequestMapping(value = "ad/")
public class AdController extends BaseController {

    @Autowired
    private AdService adService;
    
 

    @ApiOperation(value = "广告列表", notes = "广告列表,[]（张荣成）", httpMethod = "POST")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectListByMerchant", method = RequestMethod.POST)
    public Result<Page<AdDTO>> selectListByMerchant(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                                 @ModelAttribute @ApiParam( value = "查询信息") AdFindParam adPlatParam) {
    	Result<Page<AdDTO>>  pageDTOS=adService.selectListByPlatForm(adPlatParam);
    	return pageDTOS;
    }
    

    @ApiOperation(value = "广告操作下架", notes = "广告操作下架,[5001]（张荣成）", httpMethod = "PUT")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "updateStatus/{id}", method = RequestMethod.PUT)
    public Result updateStatus(@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
    	Result rs= adService.updateStatus(id);
    	return rs;
    }
    
    
    
    @ApiOperation(value = "广告操作删除", notes = "广告操作删除,[]（张荣成）", httpMethod = "PUT")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "remove/{id}", method = RequestMethod.PUT)
    public Result remove(@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
    	Result rs= adService.remove(id);
    	return rs;
    }
    
    @ApiOperation(value = "广告审核", notes = "广告审核,[]（张荣成）", httpMethod = "PUT")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "auditVideo/{id}", method = RequestMethod.PUT)
    public Result auditVideo(@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
    	Result rs= adService.auditVideo(id);
    	return rs;
    }


}
