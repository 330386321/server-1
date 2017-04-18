package com.lawu.eshop.ad.srv.service;

import com.lawu.eshop.ad.param.FavoriteAdParam;
import com.lawu.eshop.ad.srv.bo.FavoriteAdDOViewBO;
import com.lawu.eshop.framework.core.page.Page;

/**
 * 广告收藏接口
 * @author zhangrc
 * @date 2017/4/8
 *
 */
public interface FavoriteAdService {
	
	/**
	 * 广告收藏
	 * @param adId
	 * @param memberId
	 * @return
	 */
	Integer save(Long adId,Long memberId);
	
	/**
	 * 取消收藏
	 * @param id
	 */
	void remove(Long adId ,Long memberId);
	
	/**
	 * 我收藏的广告列表
	 * @param memberId
	 * @return
	 */
	Page<FavoriteAdDOViewBO> selectMyFavoriteAd(FavoriteAdParam param,Long memberId);

}
