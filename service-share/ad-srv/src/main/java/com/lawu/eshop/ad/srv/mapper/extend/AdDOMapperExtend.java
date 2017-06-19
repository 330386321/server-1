package com.lawu.eshop.ad.srv.mapper.extend;

import java.util.List;

import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.ad.srv.domain.extend.AdDOView;

public interface AdDOMapperExtend {
	/**
	 * 浏览次数加一
	 * @param id
	 * @return
	 */
    int updateHitsByPrimaryKey(Long id);
    
    /**
     * 积分榜，人气榜
     * @param adDO
     * @return
     */
    List<AdDO> selectAdAll(AdDOView adDOView);
    
    /**
     * 精选广告
     * @return
     */
    List<AdDO> selectChoiceness();
}