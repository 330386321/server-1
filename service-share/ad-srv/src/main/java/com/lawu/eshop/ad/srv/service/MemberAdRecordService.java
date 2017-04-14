package com.lawu.eshop.ad.srv.service;

public interface MemberAdRecordService {
	
	/**
	 * 判断用户是否点击过广告
	 * @param memberId
	 * @return
	 */
	boolean isClickToDay(Long memberId,Long adId);
 
}
