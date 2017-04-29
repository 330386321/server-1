package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.operator.api.service.MerchantAuditService;
import com.lawu.eshop.user.dto.MerchantStoreAuditDTO;
import com.lawu.eshop.user.param.ListStoreAuditParam;
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
@RequestMapping(value = "merchantAudit/")
public class MerchantAuditController extends BaseController {

    @Autowired
    private MerchantAuditService merchantAuditService;

    /**
     * 门店审核列表
     *
     * @param auditParam
     * @return
     */
    @ApiOperation(value = "门店列表", notes = "查询门店列表。（梅述全）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @PageBody
    @RequestMapping(value = "listStoreAudit", method = RequestMethod.POST)
    public Result<Page<MerchantStoreAuditDTO>> listStoreAudit(@RequestBody @ApiParam ListStoreAuditParam auditParam) {
        return merchantAuditService.listStoreAudit(auditParam);
    }

    /**
     * 门店审核详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "门店审核详情", notes = "查询门店审核详情。[1002]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getMerchantStoreAudit/{id}", method = RequestMethod.GET)
    public Result<MerchantStoreAuditDTO> getMerchantStoreAudit(@PathVariable @ApiParam(required = true, value = "门店审核ID") Long id) {
        return merchantAuditService.getMerchantStoreAuditById(id);
    }

    /**
     * 门店审核接口
     *
     * @param storeAuditId
     * @param auditParam
     * @return
     */
    @ApiOperation(value = "门店审核信息", notes = "根据门店审核记录ID更新对应信息  [1000]（章勇）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "updateMerchantAudit", method = RequestMethod.POST)
    public Result updateMerchantAudit(@RequestParam @ApiParam(required = true, value = "门店审核ID") Long storeAuditId,
                                      @ModelAttribute @ApiParam MerchantAuditParam auditParam) {
        Integer auditorId = 0;
        auditParam.setAuditorId(auditorId);
        return merchantAuditService.updateMerchantAudit(storeAuditId, auditParam);
    }
}
