package com.lawu.eshop.jobs.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.dto.VisitUserInfoDTO;
import com.lawu.eshop.user.param.ListMerchantStoreParam;
import com.lawu.eshop.user.param.StoreStatisticsParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/25.
 */
@FeignClient(value = "user-srv")
public interface MerchantStoreService {

    /**
     * 查询所有审核通过的实体店铺
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "merchantStore/listMerchantStore")
    Result<List<MerchantStoreDTO>> listMerchantStore(@ModelAttribute ListMerchantStoreParam listMerchantStoreParam);

    /**
     * 更新门店统计信息
     *
     * @param id
     * @param param
     */
    @RequestMapping(method = RequestMethod.PUT, value = "merchantStore/updateStoreStatistics/{id}")
    void updateStoreStatisticsById(@PathVariable("id") Long id, @ModelAttribute StoreStatisticsParam param);

    /**
     * 商家信息查询
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "merchantStore/selectMerchantStoreByMId", method = RequestMethod.GET)
	public Result<MerchantStoreDTO> selectMerchantStoreByMId(@RequestParam("merchantId") Long merchantId);

    @RequestMapping(method = RequestMethod.GET, value = "merchantStore/findAccountAndRegionPathByNum")
    VisitUserInfoDTO findAccountAndRegionPathByNum(@RequestParam("merchantNum") String merchantNum);
}
