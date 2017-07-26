package com.lawu.eshop.merchant.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.MerchantAuditService;
import com.lawu.eshop.user.dto.MerchantStoreAuditDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/7/24.
 */
@Service
public class MockMerchantAuditService extends BaseController implements MerchantAuditService {
    @Override
    public Result<MerchantStoreAuditDTO> getRecentMerchantAuditRecord(@RequestParam("merchantId") Long merchantId) {
        return successGet();
    }
}
