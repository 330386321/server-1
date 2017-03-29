package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.user.dto.MerchantInfoDTO;
import com.lawu.eshop.user.param.MerchantProfileParam;
import com.lawu.eshop.user.srv.bo.MerchantInfoBO;
import com.lawu.eshop.user.srv.bo.MerchantProfileBO;
import com.lawu.eshop.user.srv.bo.MerchantStoreInfoBO;
import com.lawu.eshop.user.srv.bo.MerchantStoreProfileBO;
import com.lawu.eshop.user.srv.converter.MerchantInfoConverter;
import com.lawu.eshop.user.srv.service.MerchantProfileService;
import com.lawu.eshop.user.srv.service.MerchantService;
import com.lawu.eshop.user.srv.service.MerchantStoreInfoService;
import com.lawu.eshop.user.srv.service.MerchantStoreProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商家扩展信息
 * Created by zhangyong on 2017/3/23.
 */
@RestController
@RequestMapping(value = "merchantInfo/")
public class MerchantInfoController extends BaseController{

    @Autowired
    private MerchantProfileService merchantProfileService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantStoreInfoService merchantStoreInfoService;

    /**
     * 设置网站链接
     * @param merchantProfileParam
     */
    @RequestMapping(value = "updateMerchantSizeLink/{id}", method = RequestMethod.PUT)
    public Result updateMerchantSizeLink(@RequestBody MerchantProfileParam merchantProfileParam, @PathVariable("id") Long id){
       int result =  merchantProfileService.updateMerchantSizeLink(merchantProfileParam,id);
        if(result ==1){
           return successCreated();
        }else{
            return successCreated(ResultCode.USER_WRONG_ID);

        }
    }

    /**
     * 查询商家主页基本信息
     * @param merchantProfileId
     * @return
     */
    @RequestMapping(value = "findMerchantProfileInfo/{merchantProfileId}", method = RequestMethod.GET)
    public Result<MerchantInfoDTO> findMerchantProfileInfo(@PathVariable("merchantProfileId") Long merchantProfileId){
        //商家扩展信息
        MerchantProfileBO merchantProfileBO = merchantProfileService.findMerchantProfileInfo(merchantProfileId);
        // 商家基本信息
        MerchantInfoBO merchantInfoBO = merchantService.findMerchantInfo(merchantProfileId);

        MerchantInfoDTO merchantInfoDTO = MerchantInfoConverter.coverConverDTO(merchantProfileBO,merchantInfoBO);
        if(merchantInfoDTO == null){
            return successGet();
        }else{
            //门店信息
            MerchantStoreInfoBO merchantStoreBO = merchantStoreInfoService.selectMerchantStoreByMId(merchantProfileId);
            if(merchantStoreBO != null){
                merchantInfoDTO.setAccount(merchantStoreBO.getPrincipalMobile());
                merchantInfoDTO.setPrincipalName(merchantStoreBO.getPrincipalName());
            }
            return successGet(merchantInfoDTO);
        }

    }
}
