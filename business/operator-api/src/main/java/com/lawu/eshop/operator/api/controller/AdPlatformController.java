package com.lawu.eshop.operator.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.dto.AdPlatformDTO;
import com.lawu.eshop.ad.param.AdPlatformParam;
import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.FileDirConstant;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.operator.api.service.AdPlatformService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import util.UploadFileUtil;

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
    @ApiOperation(value = "广告信息查询", notes = "广告信息查询[]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectByPosition", method = RequestMethod.POST)
    public Result<List<AdPlatformDTO>> selectByPosition(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@ModelAttribute @ApiParam(required = true, value = "位置") PositionEnum positionEnum) {
        Result<List<AdPlatformDTO>> adPlatformDTOS = adPlatformService.selectByPosition(positionEnum);
        return adPlatformDTOS;
    }

    /**
     * 删除广告
     * @param id
     * @return
     */
    @Authorization
    @ApiOperation(value = "删除广告", notes = "删除广告[1002]（张荣成）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "removeAdPlatform/{id}", method = RequestMethod.DELETE)
    public Result remove(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        Result rs = adPlatformService.removeAdPlatform(id);
        return rs;
    }

    /**
     * 添加广告
     * @param adPlatform
     * @return
     */
    @Authorization
    @ApiOperation(value = "添加广告", notes = "添加广告[1011|1015|1010]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "saveAdPlatform", method = RequestMethod.POST)
    public Result addAddress(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@ModelAttribute @ApiParam(required = true, value = "广告信息") AdPlatformParam adPlatform) {
    	 HttpServletRequest request = getRequest();
    	 String mediaUrl = "";
         Map<String, String> retMap = UploadFileUtil.uploadOneImage(request, FileDirConstant.DIR_AD_IMAGE);
         if(!"".equals(retMap.get("imgUrl"))){
        	 mediaUrl = retMap.get("imgUrl").toString();
         }
        Result rs = adPlatformService.saveAdPlatform(adPlatform,mediaUrl);
        return rs;
    }


}
