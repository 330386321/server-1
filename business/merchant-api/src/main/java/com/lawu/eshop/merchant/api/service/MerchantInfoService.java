package com.lawu.eshop.merchant.api.service;


import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.MerchantInfoDTO;
import com.lawu.eshop.user.dto.param.MerchantSizeLinkDTO;
import com.lawu.eshop.user.param.MerchantProfileParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    @RequestMapping(method = RequestMethod.GET, value ="merchantInfo/getCurrentMerchantInfo/{merchantId}")
    Result<MerchantInfoDTO> getCurrentMerchantInfo(@PathVariable("merchantId") Long merchantId);

    @RequestMapping(value = "merchantInfo/getMerchantSizeLink/{merchantId}",method = RequestMethod.GET)
    public Result<MerchantSizeLinkDTO> getMerchantSizeLink(@PathVariable("merchantId") Long merchantId);

}
