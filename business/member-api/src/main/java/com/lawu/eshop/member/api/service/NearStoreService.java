package com.lawu.eshop.member.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.NearStoreDTO;
import com.lawu.eshop.user.query.NearStoreParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
@FeignClient(value = "user-srv")
public interface NearStoreService {

    /**
     * 查询附近门店
     *
     * @param nearStoreParam
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "nearStore/listNearStore")
    Result<Page<NearStoreDTO>> listNearStore(@ModelAttribute NearStoreParam nearStoreParam);
}
