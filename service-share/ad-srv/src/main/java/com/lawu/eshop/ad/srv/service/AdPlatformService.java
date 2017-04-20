package com.lawu.eshop.ad.srv.service;

import java.util.List;

import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.param.AdPlatformFindParam;
import com.lawu.eshop.ad.param.AdPlatformParam;
import com.lawu.eshop.ad.srv.bo.AdPlatformBO;

/**
 * 平台广告管理
 * @author zhangrc
 * @date 2017/4/5
 *
 */
public interface AdPlatformService {
	
	/**
	 * 添加广告
	 * @param adPlatformParam
	 * @param url
	 * @return
	 */
	Integer saveAdPlatform(AdPlatformParam adPlatformParam,String url);
	
	/**
	 * 删除广告
	 * @param id
	 * @return
	 */ 
	Integer removeAdPlatform(Long id);
	
	/**
	 * 根据不同的位置查询不同的广告
	 * @param positionEnum
	 * @return
	 */
	List<AdPlatformBO> selectByPosition(PositionEnum positionEnum);
	
	
	/**
	 * 运营平台查询广告
	 * @param param
	 * @return
	 */
	List<AdPlatformBO> selectList(AdPlatformFindParam param);
	
	/**
	 * 发布广告
	 * @param id
	 * @return
	 */ 
	Integer issueAd(Long id);
	
	/**
	 * 设置广告位
	 * @param id
	 * @param positionEnum
	 * @return
	 */ 
	Integer setPosition(Long id,PositionEnum positionEnum);
	
	/**
	 * 
	 * @param id
	 * @param adPlatformParam
	 * @param url
	 * @return
	 */
	Integer update(Long id,AdPlatformParam adPlatformParam,String url);
	
	/**
	 * 单个查询
	 * @param id
	 * @return
	 */
	AdPlatformBO select(Long id);

}
