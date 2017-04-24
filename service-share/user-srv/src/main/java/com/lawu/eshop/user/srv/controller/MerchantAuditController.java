package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.user.constants.MerchantAuditStatusEnum;
import com.lawu.eshop.user.dto.MerchantAuditInfoDTO;
import com.lawu.eshop.user.param.MerchantAuditParam;
import com.lawu.eshop.user.srv.bo.MerchantStoreAuditBO;
import com.lawu.eshop.user.srv.bo.MerchantStoreInfoBO;
import com.lawu.eshop.user.srv.service.MerchantAuditService;
import com.lawu.eshop.user.srv.service.MerchantStoreInfoService;
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
    @Autowired
    private MerchantStoreInfoService merchantStoreInfoService;

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
     * 查询门店审失败或待审核信息
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "getMerchantAuditInfo/{merchantId}", method = RequestMethod.GET)
    public Result<MerchantAuditInfoDTO> getMerchantAuditInfo(@PathVariable(value = "merchantId") Long merchantId){

        //查询门店信息
        MerchantStoreInfoBO merchantStoreBO = merchantStoreInfoService.selectMerchantStoreByMId(merchantId);
        if(merchantStoreBO == null){
            return successGet(ResultCode.MERCHANT_STORE_NO_EXIST);
        }
        MerchantStoreAuditBO storeAuditBO = merchantAuditService.getMerchantAuditInfo(merchantId);
        MerchantAuditInfoDTO auditInfoDTO = new MerchantAuditInfoDTO();
        auditInfoDTO.setRemark(storeAuditBO.getRemark());
        auditInfoDTO.setGmtCreate(storeAuditBO.getGmtCreate());
        auditInfoDTO.setMerchantAuditStatusEnum(MerchantAuditStatusEnum.getEnum(storeAuditBO.getStatus()));
        auditInfoDTO.setStoreStatus(merchantStoreBO.getStatusEnum());
        return successGet(auditInfoDTO);
    }
}
