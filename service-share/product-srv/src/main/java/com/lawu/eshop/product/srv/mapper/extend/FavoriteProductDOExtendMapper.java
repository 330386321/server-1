package com.lawu.eshop.product.srv.mapper.extend;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.lawu.eshop.product.srv.domain.extend.FavoriteProductExtendDO;

/**
 * 
 * 我收藏的商品扩展接口
 * @author zhangrc
 * @date 2017/03/30
 *
 */
public interface FavoriteProductDOExtendMapper {
	
	/**
	 * 我邀请的商家
	 * @param merchantInviter
	 * @return
	 */
	List<FavoriteProductExtendDO> selectMyFavoriteProductByRowbounds(FavoriteProductExtendDO favoriteProductExtendDO,RowBounds rowBounds);
	
   
}