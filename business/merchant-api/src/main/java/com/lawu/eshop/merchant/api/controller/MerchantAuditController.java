package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.merchant.api.service.MerchantAuditService;
import com.lawu.eshop.user.dto.MerchantStoreAuditDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyong
 * @date 2017/7/17.
 */
@Api(tags = "merchantAudit")
@RestController
@RequestMapping(value = "merchantAudit/")
public class MerchantAuditController extends BaseController{

    @Autowired
    private MerchantAuditService merchantAuditService;

    @ApiOperation(value = "获取最新一条审核信息", notes = "获取最新一条审核信息。[2009] （章勇）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getRecentMerchantAuditRecord", method = RequestMethod.GET)
    public Result<MerchantStoreAuditDTO> getRecentMerchantAuditRecord(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token){
        Long userId = UserUtil.getCurrentUserId(getRequest());

        return merchantAuditService.getRecentMerchantAuditRecord(userId);
    }
}
