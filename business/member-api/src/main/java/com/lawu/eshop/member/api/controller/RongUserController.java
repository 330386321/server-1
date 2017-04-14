package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.member.api.service.RongUserService;
import com.lawu.eshop.user.dto.RongYunOnlineDTO;
import com.lawu.eshop.user.dto.RongYunRefreshDTO;
import com.lawu.eshop.user.dto.RongYunTokenDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyong
 * @date 2017/4/14.
 */
@Api(tags = "rongUser")
@RestController
@RequestMapping(value = "rongUser")
public class RongUserController extends BaseController{

    @Autowired
    private RongUserService rongUserService;

    @ApiOperation(value = "获取融云token", notes = "获取融云token。[1005] (章勇)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getRongToken",method = RequestMethod.GET)
    public Result<RongYunTokenDTO> getRongToken(@RequestParam("name") @ApiParam(value = "用户名称",required = true) String name,
                                                @RequestParam("portraitUri") @ApiParam(value = "用户头像",required = true) String portraitUri,
                                                @RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) throws Exception{
        String userNum = UserUtil.getCurrentUserNum(getRequest());
        if("".equals(name) || "".equals(portraitUri)){
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Result<RongYunTokenDTO> rongYunDTO = rongUserService.getRongToken(userNum,name,portraitUri);
        return rongYunDTO;
    }

    @ApiOperation(value = "检查在线状态", notes = "检查在线状态。[1000] (章勇)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "checkOnline", method = RequestMethod.GET)
    public Result<RongYunOnlineDTO> checkOnline(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) throws Exception {
        String userNum = UserUtil.getCurrentUserNum(getRequest());

        Result<RongYunOnlineDTO> onlineDTO = rongUserService.checkOnline(userNum);
        return onlineDTO;
    }

    @ApiOperation(value = "获取融云token", notes = "获取融云token。[1004] (章勇)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "refreshUserInfo", method = RequestMethod.GET)
    public Result<RongYunRefreshDTO> refreshUserInfo(@RequestParam("name") @ApiParam(value = "用户名称", required = true) String name,
                                                     @RequestParam("portraitUri") @ApiParam(value = "用户头像", required = true) String portraitUri,
                                                     @RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) throws Exception {
        String userNum = UserUtil.getCurrentUserNum(getRequest());
        if ("".equals(name) || "".equals(portraitUri)) {
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Result<RongYunRefreshDTO> refreshDTO = rongUserService.refreshUserInfo(userNum, name, portraitUri);
        return refreshDTO;
    }

}
