package com.lawu.eshop.statistics.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.ad.dto.MemberAdRecodeCommissionDTO;
import com.lawu.eshop.framework.web.Result;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月25日 上午10:47:56
 *
 */
@FeignClient(value= "ad-srv")
public interface AdSrvService {

	/**
	 * 获取未计算提成的会员点击广告记录
	 * @return
	 * @author yangqh
	 */
	@RequestMapping(method = RequestMethod.GET, value = "commission/getNoneCommissionAds")
	List<MemberAdRecodeCommissionDTO> getNoneCommissionAds();

	/**
	 * 修改为已计算提成
	 * @param id member_ad_record主键
	 * @return
	 * @author yangqh
	 */
	@RequestMapping(method = RequestMethod.POST, value = "commission/updateMemberAdRecardStatus")
	int updateMemberAdRecardStatus(@RequestParam("id") Long id);
	
	/**
	 * 查询所有广告集合
	 * @return
	 */
	@RequestMapping(value = "ad/getAllAd", method = RequestMethod.GET)
    public Result<List<Long>> getAllAd();
	
	/**
	 * 修改广告浏览次数
	 * @param id
	 * @param count
	 * @return
	 */
	@RequestMapping(value = "ad/updateViewCount/{id}", method = RequestMethod.PUT)
    public Result<List<Long>> updateViewCount(@PathVariable("id")  Long  id,@RequestParam("count") Integer count);

}
