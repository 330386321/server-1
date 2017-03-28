package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.query.FavoriteMerchantParam;
import com.lawu.eshop.user.srv.bo.FavoriteMerchantBO;

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
	
	/**
	 * 我收藏的商家
	 * @param memberId
	 * @return
	 */
	Page<FavoriteMerchantBO> getMyFavoriteMerchant(Long memberId,FavoriteMerchantParam pageQuery);

}
