package com.lawu.eshop.member.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.dto.MerchantStoreNoReasonReturnDTO;
import com.lawu.eshop.user.dto.StoreDetailDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.dto.MerchantStoreNoReasonReturnDTO;
import com.lawu.eshop.user.dto.StoreDetailDTO;
import java.util.List;

/**
 * 
 * @author Sunny
 * @date 2017/3/30
 */
@FeignClient(value = "user-srv")
public interface MerchantStoreService {

    /**
     * 根据商家ID获取商家门店的名称
     * 
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "merchantStore/getNameBymerchantId/{merchantId}", method = RequestMethod.GET)
    public Result<String> getNameByMerchantId(@PathVariable("merchantId") Long merchantId);

    /**
     * 根据商家ID查询门店是否支持七天无理由退货
     * @param merchantId
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.GET, value = "merchantStore/findIsNoReasonReturnById")
    Result findIsNoReasonReturnById(@RequestParam("merchantId") Long merchantId);
    
    /**
     * 根据商家ID列表批量查询该商家是否支持七天无理由退货
     *
     * @param merchantIdList
     */
    @RequestMapping(value = "merchantStore/selectNoReasonReturnByMerchantIds", method = RequestMethod.GET)
    Result<List<MerchantStoreNoReasonReturnDTO>> selectNoReasonReturnByMerchantIds(@RequestParam("merchantIdList") List<Long> merchantIdList);
    
    /**
     * 根据门店ID查询门店信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "merchantStore/storeDetail/{id}")
    Result<StoreDetailDTO> getStoreDetailById(@PathVariable("id") Long id);

    /**
     * 根据商家查询门店信息
     * @param merchantId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "merchantStore/selectMerchantStoreByMerchantId")
    Result<MerchantStoreDTO> selectMerchantStoreByMerchantId(@PathVariable("merchantId") Long merchantId);

    /**
     * 根据商家查询门店信息
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "merchantStore/selectMerchantStoreByMId", method = RequestMethod.GET)
    public Result<MerchantStoreDTO> selectMerchantStoreByMId(@RequestParam("merchantId") Long merchantId);

    /**
     * 根据商家ID查询门店信息
     * @param merchantId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "merchantStore/findStoreNameAndImgByMerchantId/{merchantId}")
    MerchantStoreDTO findStoreNameAndImgByMerchantId(@PathVariable("merchantId") Long merchantId);
}
