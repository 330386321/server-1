package com.lawu.eshop.merchant.api.service;


import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.CertifTypeEnum;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.dto.MerchantStoreTypeEnum;
import com.lawu.eshop.user.param.MerchantStoreParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhangyong
 * @date 2017/3/22
 */
@FeignClient(value = "user-srv")
public interface MerchantStoreService {



    /**
     * 根据商家id查询门店信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "merchantStore/findMerchantStoreInfo/{id}")
    Result<MerchantStoreDTO> selectMerchantStore(@PathVariable("id") Long id);

    /**
     * 新增门店信息
     * @param merchantId 商家id
     * @param merchantStoreParam 门店信息
     * @param storeType 经营类型
     * @param certifType 证件类型
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "merchantStore/saveMerchantStoreInfo/{merchantId}")
    Result saveMerchantStoreInfo(@PathVariable("merchantId") Long merchantId, @ModelAttribute MerchantStoreParam merchantStoreParam, @RequestParam("storeType") MerchantStoreTypeEnum storeType, @RequestParam("certifType")CertifTypeEnum certifType);

    /**
     * 修改门店信息
     * @param merchantId 商家id
     * @param merchantStoreParam 门店信息
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "merchantStore/updateMerchantStoreInfo/{merchantStoreId}")
    Result updateMerchantStoreInfo(@PathVariable("merchantStoreId") Long merchantStoreId,@RequestParam("merchantId") Long merchantId, @ModelAttribute MerchantStoreParam merchantStoreParam,@RequestParam("storeType") MerchantStoreTypeEnum storeType, @RequestParam("certifType")CertifTypeEnum certifType);

}
