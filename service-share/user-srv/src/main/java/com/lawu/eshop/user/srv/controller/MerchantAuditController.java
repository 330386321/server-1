package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.param.MerchantAuditParam;
import com.lawu.eshop.user.srv.service.MerchantAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyong
 * @date 2017/3/31.
 */
@RestController
@RequestMapping(value = "audit/")
public class MerchantAuditController extends BaseController {

    @Autowired
    private MerchantAuditService merchantAuditService;

    /**
     * 门店审核
     * 根据审核记录id更新审核记录
     * @param storeAuditId
     * @param auditParam
     * @return
     */
    @RequestMapping(value = "updateMerchantAudit/{storeAuditId}", method = RequestMethod.PUT)
    public Result updateMerchantAudit(@PathVariable("storeAuditId")Long storeAuditId, @RequestBody MerchantAuditParam auditParam) {
        merchantAuditService.updateMerchantAudit(storeAuditId,auditParam);
        return successCreated();
    }
}
