package com.lawu.eshop.ad.srv.service;

import java.util.List;

import com.lawu.eshop.ad.srv.bo.MemberAdRecodeCommissionBO;

public interface MemberAdRecordService {
	
	/**
	 * 判断用户是否点击过广告
	 * @param memberId
	 * @return
	 */
	boolean isClickToDay(Long memberId,Long adId);

	/**
	 * 查询未计算提成的用户点击广告记录
	 * @return
	 * @author yangqh
	 */
	List<MemberAdRecodeCommissionBO> getNoneCommissionAds();

	/**
	 * 修改用户点击广告记录表状态为已计算提成
	 * @param id
	 * @return
	 * @author yangqh
	 */
	int updateMemberAdRecardStatus(Long id);
 
}
