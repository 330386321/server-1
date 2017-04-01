package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.merchant.api.service.InviterService;
import com.lawu.eshop.user.dto.InviterDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meishuquan
 * @date 2017/3/30
 */
@Api(tags = "inviter")
@RestController
@RequestMapping(value = "inviter/")
public class InviterController extends BaseController {

    @Autowired
    private InviterService inviterService;

    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "查询邀请人", notes = "根据账号查询邀请人信息。[1002](梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getInviter/{account}", method = RequestMethod.GET)
    public Result<InviterDTO> getInviterByAccount(@PathVariable @ApiParam(required = true, value = "邀请人账号") String account) {
        return inviterService.getInviterByAccount(account);
    }

}
