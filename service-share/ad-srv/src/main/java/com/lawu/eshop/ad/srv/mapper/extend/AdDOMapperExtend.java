package com.lawu.eshop.ad.srv.mapper.extend;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.ad.srv.domain.AdDOExample;
import com.lawu.eshop.ad.srv.domain.extend.AdDOView;
import com.lawu.eshop.ad.srv.domain.extend.ReportAdView;

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
    
    /**
     * 商家端批量删除
     * @param adIds
     */
    void batchDeleteAd(List<Long> adIds);

    /**
     * 统计广告
     * @return
     */
	List<ReportAdView> selectReportAdEarningsByRowbounds(RowBounds rowBounds);
	
	/**
	 * 收益总记录数
	 * @return
	 */
	Integer selectReportAdEarningscount();
}