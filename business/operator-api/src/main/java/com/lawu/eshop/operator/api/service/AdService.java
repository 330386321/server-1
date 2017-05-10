package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AuditEnum;
import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.param.AdFindParam;
import com.lawu.eshop.ad.param.ListAdParam;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "ad-srv")
public interface AdService {
	
	/**
	 * 查询广告
	 * @param adPlatParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "ad/selectListByPlatForm")
    Result<Page<AdDTO>> selectListByPlatForm(@RequestBody AdFindParam adPlatParam);
	
	/**
	 * 操作广告下架
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT,value = "ad/updateStatus/{id}")
    public Result updateStatus(@PathVariable("id") Long id);
	
	/**
	 * 操作广告删除
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT,value = "ad/remove/{id}")
    public Result remove(@PathVariable("id") Long id);
	
	/**
	 * 审核视频
	 * @param auditEnum
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT,value = "ad/auditVideo/{id}")
    public Result auditVideo(@PathVariable("id") Long id,@RequestBody AuditEnum auditEnum);

	/**
	 * 查询广告列表
	 *
	 * @param listAdParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "ad/listAllAd")
	Result<Page<AdDTO>> listAd(@RequestBody ListAdParam listAdParam );

	/**
	 * 根据ID查询广告详情
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET,value = "ad/getAd/{id}")
	Result<AdDTO> getAdById(@PathVariable("id") Long id);

	/**
	 * 操作广告(下架、删除)
	 *
	 * @param id
	 * @param adStatusEnum
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT,value = "ad/operatorUpdateAdStatus/{id}")
	Result operatorUpdateAdStatus(@PathVariable("id") Long id, @RequestParam("adStatusEnum") AdStatusEnum adStatusEnum);

	/**
	 * 查询上架中的平面视频广告
	 *
	 * @param listAdParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "ad/listFlatVideoAd")
	Result<List<AdDTO>> listFlatVideoAd(@ModelAttribute ListAdParam listAdParam);

	/**
	 * 更新平面视频广告索引
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "ad/updateAdIndex/{id}")
	Result updateAdIndex(@PathVariable("id") Long id);

}
