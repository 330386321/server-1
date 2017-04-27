package com.lawu.eshop.cache.srv.service;

import java.util.List;

/**
 * 存储广告浏览记录
 * @author zhangrc
 * @date 2014/4/27
 *
 */
public interface AdViewService {
	
	void setAdView(String adId, String memberId);
	
	
	List<String> getAdviews(String adId);
	

}
