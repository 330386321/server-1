package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.user.dto.MerchantProfileDTO;
import com.lawu.eshop.user.dto.param.MerchantProfileParam;
import com.lawu.eshop.user.srv.bo.MerchantBO;
import com.lawu.eshop.user.srv.bo.MerchantProfileBO;
import com.lawu.eshop.user.srv.converter.MerchantProfileConverter;
import com.lawu.eshop.user.srv.service.MerchantProfileService;
import com.lawu.eshop.user.srv.service.MerchantService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商家扩展信息
 * Created by zhangyong on 2017/3/23.
 */
@RestController
@RequestMapping(value = "merchantprofile/")
public class MerchantProfileController {

    @Autowired
    private MerchantProfileService merchantProfileService;

    @Autowired
    private MerchantService merchantService;

    /**
     * 设置网站链接
     * @param merchantProfileParam
     */
    @RequestMapping(value = "updateMerchantSizeLink", method = RequestMethod.GET)
    public void updateMerchantSizeLink(@ModelAttribute MerchantProfileParam merchantProfileParam){
        merchantProfileService.updateMerchantSizeLink(merchantProfileParam);
    }

    /**
     * 查询商家主页基本信息
     * @param merchantProfileId
     * @return
     */
    @RequestMapping(value = "findMerchantProfileInfo", method = RequestMethod.POST)
    public MerchantProfileDTO findMerchantProfileInfo(@RequestParam Long merchantProfileId){
        //商家扩展信息
        MerchantProfileBO merchantProfileBO = merchantProfileService.findMerchantProfileInfo(merchantProfileId);
        // 商家基本信息

        MerchantBO merchantBO = merchantService.findMerchantInfo(merchantProfileId);

        return MerchantProfileConverter.coverConverDTO(merchantProfileBO);
    }
}
