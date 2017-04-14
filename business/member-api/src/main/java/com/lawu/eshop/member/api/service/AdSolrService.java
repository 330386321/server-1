package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.ad.dto.AdSolrDTO;
import com.lawu.eshop.ad.param.AdSolrParam;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;

/**
 * @author zhangrc
 * @date 2017/4/12.
 */
@FeignClient(value = "ad-srv")
public interface AdSolrService {


    /**
     * 会员APP广告搜索
     *
     * @param productSolrParam
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "adSolr/queryAdByTitle")
    Result<Page<AdSolrDTO>> queryAdByTitle(@ModelAttribute AdSolrParam adSolrParam);
}
