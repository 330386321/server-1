package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.dto.MerchantStorePlatDTO;
import com.lawu.eshop.user.param.ListMerchantStoreParam;
import com.lawu.eshop.user.param.MerchantStorePlatParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "user-srv")
public interface MerchantStoreService {
	
	 @RequestMapping(value = "merchantStore/selectAllMerchantStore",method = RequestMethod.POST)
	 Result<List<MerchantStorePlatDTO>> selectAllMerchantStore(@ModelAttribute MerchantStorePlatParam param);
	 
	 /**
      * 根据商家id查询门店信息
      *
      * @param id
      * @return
      */
    @RequestMapping(method = RequestMethod.GET, value = "merchantStore/findMerchantStoreInfo/{id}")
    Result<MerchantStoreDTO> selectMerchantStore(@PathVariable("id") Long id);

	/**
	 * 查询所有审核通过的实体店铺
	 *
	 * @param listMerchantStoreParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "merchantStore/listMerchantStore")
	Result<List<MerchantStoreDTO>> listMerchantStore(@ModelAttribute ListMerchantStoreParam listMerchantStoreParam);

	/**
	 * 更新门店索引
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "merchantStore/updateStoreIndex/{id}")
	Result updateStoreIndex(@PathVariable("id") Long id);

	/**
	 * 重建门店索引
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "merchantStore/rebuildStoreIndex")
	Result rebuildStoreIndex();

}
