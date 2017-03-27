package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.user.srv.domain.FavoriteMerchantDO;

/**
 * 商家收藏
 * @author zhangrc
 * @date 2017/3/27
 *
 */
public interface FavoriteMerchantService {
	
	/**
	 * 商家收藏
	 * @param favoriteMerchant
	 * @return
	 */
	Integer save(Long memberId ,Long merchantId);

}
