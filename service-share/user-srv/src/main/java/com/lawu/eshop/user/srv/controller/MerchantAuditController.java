package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.user.constants.MerchantAuditStatusEnum;
import com.lawu.eshop.user.dto.MerchantAuditInfoDTO;
import com.lawu.eshop.user.param.MerchantAuditParam;
import com.lawu.eshop.user.srv.bo.MerchantStoreAuditBO;
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

    /**
     * 查询门店审核成功和失败审核信息
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "getMerchantAuditInfo/{merchantId}", method = RequestMethod.GET)
    public Result<MerchantAuditInfoDTO> getMerchantAuditInfo(@PathVariable(value = "merchantId") Long merchantId){
        MerchantStoreAuditBO storeAuditBO = merchantAuditService.getMerchantAuditInfo(merchantId);
        if(storeAuditBO == null){
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        MerchantAuditInfoDTO auditInfoDTO = new MerchantAuditInfoDTO();
        auditInfoDTO.setRemark(storeAuditBO.getRemark());
        auditInfoDTO.setMerchantAuditStatusEnum(MerchantAuditStatusEnum.getEnum(storeAuditBO.getStatus()));
        return successGet(auditInfoDTO);
    }
}
