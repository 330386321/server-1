package com.lawu.eshop.merchant.api.service;


import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.MerchantInfoDTO;
import com.lawu.eshop.user.param.MerchantProfileParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user-srv")
public interface MerchantInfoService {

    /**
     * 设置网站链接
     * @param merchantProfileParam
     */
    @RequestMapping(method = RequestMethod.PUT, value = "merchantInfo/updateMerchantSizeLink/{id}")
    Result updateMerchantSizeLink(@ModelAttribute MerchantProfileParam merchantProfileParam, @PathVariable("id") Long id);

    /**
     * 查询商家主页基本信息
     * @param merchantProfileId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value ="merchantInfo/findMerchantProfileInfo/{merchantProfileId}")
    Result<MerchantInfoDTO> findMerchantProfileInfo(@PathVariable("merchantProfileId") Long merchantProfileId);

}
