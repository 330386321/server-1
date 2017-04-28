package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.MerchantStoreAuditDTO;
import com.lawu.eshop.user.param.ListStoreAuditParam;
import com.lawu.eshop.user.param.MerchantAuditParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyong
 * @date 2017/3/31.
 */
@FeignClient(value = "user-srv")
public interface MerchantAuditService {

    @RequestMapping(method = RequestMethod.PUT, value = "audit/updateMerchantAudit/{storeAuditId}")
    Result updateMerchantAudit(@PathVariable("storeAuditId") Long storeAuditId, @ModelAttribute MerchantAuditParam auditParam);

    /**
     * 查询门店列表
     *
     * @param auditParam
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "audit/listAllStoreAudit")
    Result<Page<MerchantStoreAuditDTO>> listStoreAudit(@RequestBody ListStoreAuditParam auditParam);

    /**
     * 查询门店审核详情
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "audit/getMerchantStoreAudit/{id}")
    Result<MerchantStoreAuditDTO> getMerchantStoreAuditById(@PathVariable("id") Long id);
}
