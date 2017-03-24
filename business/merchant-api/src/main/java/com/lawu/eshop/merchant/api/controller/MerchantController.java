package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.MerchantService;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.param.RegisterParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "修改登录密码", notes = "根据商户ID修改登录密码。(梅述全)", httpMethod = "PUT")
    @Authorization
    @RequestMapping(value = "updateLoginPwd/{id}", method = RequestMethod.PUT)
    public Result updateLoginPwd(@PathVariable @ApiParam(required = true, value = "id") Long id,
                                 @RequestParam @ApiParam(required = true, value = "原始密码") String originalPwd,
                                 @RequestParam @ApiParam(required = true, value = "新密码") String newPwd) {
        return merchantService.updateLoginPwd(id, originalPwd, newPwd);
    }

    @ApiOperation(value = "查询邀请人", notes = "根据账号查询邀请人信息。(梅述全)", httpMethod = "GET")
    @RequestMapping(value = "getInviterByAccount/{account}", method = RequestMethod.GET)
    public Result<InviterDTO> getInviterByAccount(@PathVariable @ApiParam(required = true, value = "邀请人账号") String account) {
        return merchantService.getInviterByAccount(account);
    }

    @ApiOperation(value = "注册", notes = "商户注册。(梅述全)", httpMethod = "POST")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Result getInviterByAccount(@RequestBody @ApiParam(required = true, value = "注册信息") RegisterParam registerParam) {
        return  merchantService.register(registerParam);
    }

}
