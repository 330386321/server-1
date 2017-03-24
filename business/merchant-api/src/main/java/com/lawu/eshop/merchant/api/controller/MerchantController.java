package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.merchant.api.service.MerchantService;
import com.lawu.eshop.user.dto.InviterDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meishuquan
 * @date 2017/3/22
 */
@Api(tags = "merchant")
@RestController
@RequestMapping(value = "merchant/")
public class MerchantController extends BaseController {

    @Autowired
    private MerchantService merchantService;

    @ApiOperation(value = "修改密码", notes = "商户修改密码", httpMethod = "POST")
    @Authorization
    @RequestMapping(value = "updatePwd", method = RequestMethod.POST)
    public Result updatePwd(@RequestParam @ApiParam(required = true, value = "主键") Long id,
                            @RequestParam @ApiParam(required = true, value = "密码") String pwd) {
        merchantService.updatePwd(id, pwd);
        return successCreated();
    }

    @ApiOperation(value = "查询邀请人", notes = "根据账号查询邀请人信息", httpMethod = "GET")
    @RequestMapping(value = "getInviterByAccount", method = RequestMethod.GET)
    public Result<InviterDTO> getInviterByAccount(@RequestParam @ApiParam(required = true, value = "邀请人账号") String account) {
        InviterDTO inviterDTO = merchantService.getInviterByAccount(account);
        if (inviterDTO == null) {
            return successGet();
        }
        if (inviterDTO.getInviterId() < 1) {
            return failServerError( "查询邀请人信息调用异常");
        }
        return successGet(inviterDTO);
    }

}
