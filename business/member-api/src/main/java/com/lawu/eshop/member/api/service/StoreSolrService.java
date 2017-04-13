package com.lawu.eshop.member.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.NearStoreDTO;
import com.lawu.eshop.user.param.StoreSolrParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author meishuquan
 * @date 2017/4/13.
 */
@FeignClient(value = "user-srv")
public interface StoreSolrService {

    /**
     * 会员APP查询门店列表
     *
     * @param storeSolrParam
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "storeSolr/listStore")
    Result<Page<NearStoreDTO>> listStore(@ModelAttribute StoreSolrParam storeSolrParam);
}