package com.lawu.eshop.ad.srv.mapper.extend;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.lawu.eshop.ad.srv.domain.extend.FavoriteAdDOView;

/**
 * 广告收藏接口扩展
 * @author zhangrc
 * @date 2017/4/8
 *
 */
public interface FavoriteAdDOMapperExtend {
	
	/**
	 * 我收藏的广告
	 * @param memberId
	 * @param rowBounds
	 * @return
	 */
	List<FavoriteAdDOView> selectMyFavoriteAdByRowbounds(Long  memberId, RowBounds rowBounds);
    
}