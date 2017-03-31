package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.user.param.MerchantAuditParam;

/**
 * @author zhangyong
 * @date 2017/3/31.
 */
public interface MerchantAuditService {
    /**
     * 根据审核列表修改审核信息
     *
     * @param storeAuditId
     */
    void updateMerchantAudit(Long storeAuditId, MerchantAuditParam auditParam);
}
