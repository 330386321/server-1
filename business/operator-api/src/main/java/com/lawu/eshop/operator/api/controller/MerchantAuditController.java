package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.api.service.MerchantAuditService;
import com.lawu.eshop.user.param.MerchantAuditParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyong
 * @date 2017/3/31.
 */
@Api(tags = "merchantAudit")
@RestController
@RequestMapping(value = "/")
public class MerchantAuditController extends BaseController {

    @Autowired
    private MerchantAuditService merchantAuditService;

    /**
     * 门店审核接口
     * @param storeAuditId
     * @param auditParam
     * @return
     */
    @ApiOperation(value = "门店审核信息", notes = "根据门店审核记录ID更新对应信息  [1000]（章勇）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "updateMerchantAudit/{storeAuditId}", method = RequestMethod.PUT)
    public Result updateMerchantAudit(@PathVariable("storeAuditId") @ApiParam(required = true, value = "门店审核ID") Long storeAuditId,
                                      @ModelAttribute @ApiParam MerchantAuditParam auditParam) {
        return merchantAuditService.updateMerchantAudit(storeAuditId, auditParam);
    }
}
