package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.param.MerchantAuditParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zhangyong
 * @date 2017/3/31.
 */
@FeignClient(value = "user-srv")
public interface MerchantAuditService {

    @RequestMapping(method = RequestMethod.PUT, value = "audit/updateMerchantAudit/{storeAuditId}")
    Result updateMerchantAudit(@PathVariable("storeAuditId") Long storeAuditId, @ModelAttribute  MerchantAuditParam auditParam);
}
