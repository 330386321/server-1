package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.user.dto.MerchantInfoDTO;
import com.lawu.eshop.user.dto.param.MerchantProfileParam;
import com.lawu.eshop.user.srv.bo.MerchantInfoBO;
import com.lawu.eshop.user.srv.bo.MerchantProfileBO;
import com.lawu.eshop.user.srv.bo.MerchantStoreProfileBO;
import com.lawu.eshop.user.srv.converter.MerchantInfoConverter;
import com.lawu.eshop.user.srv.service.MerchantProfileService;
import com.lawu.eshop.user.srv.service.MerchantService;
import com.lawu.eshop.user.srv.service.MerchantStoreProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商家扩展信息
 * Created by zhangyong on 2017/3/23.
 */
@RestController
@RequestMapping(value = "merchantInfo/")
public class MerchantInfoController {

    @Autowired
    private MerchantProfileService merchantProfileService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantStoreProfileService merchantStoreProfileService;

    /**
     * 设置网站链接
     * @param merchantProfileParam
     */
    @RequestMapping(value = "updateMerchantSizeLink", method = RequestMethod.PUT)
    public int updateMerchantSizeLink(@RequestBody MerchantProfileParam merchantProfileParam, @RequestParam Long id){
       int result =  merchantProfileService.updateMerchantSizeLink(merchantProfileParam,id);

       return result;
    }

    /**
     * 查询商家主页基本信息
     * @param merchantProfileId
     * @return
     */
    @RequestMapping(value = "findMerchantProfileInfo/{merchantProfileId}", method = RequestMethod.GET)
    public MerchantInfoDTO findMerchantProfileInfo(@PathVariable("merchantProfileId") Long merchantProfileId){
        //商家扩展信息
        MerchantProfileBO merchantProfileBO = merchantProfileService.findMerchantProfileInfo(merchantProfileId);
        // 商家基本信息
        MerchantInfoBO merchantInfoBO = merchantService.findMerchantInfo(merchantProfileId);

        MerchantInfoDTO merchantInfoDTO = MerchantInfoConverter.coverConverDTO(merchantProfileBO,merchantInfoBO);
        //门店信息
        MerchantStoreProfileBO merchantStoreBO = merchantStoreProfileService.findMerchantStoreInfo(merchantProfileId);
        if(merchantStoreBO != null){
            merchantInfoDTO.setAccount(merchantStoreBO.getPrincipalMobile());
            merchantInfoDTO.setPrincipalName(merchantStoreBO.getPrincipalName());
        }

        return merchantInfoDTO;
    }
}
