package com.lawu.eshop.cache.srv.service;

import java.util.Set;

/**
 * 存储广告浏览记录
 * @author zhangrc
 * @date 2014/4/27
 *
 */
public interface AdViewService {
	
	void setAdView(String adId, String memberId);
	
	
	Set<String> getAdviews(String adId);
	

}
