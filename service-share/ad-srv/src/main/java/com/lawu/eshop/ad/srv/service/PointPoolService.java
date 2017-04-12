package com.lawu.eshop.ad.srv.service;

import java.util.List;

/**
 * 积分池
 * @author zhangrc
 * @date 2017/4/11
 *
 */
public interface PointPoolService {
	
	/**
	 * 根据广告id查询前三名用户
	 * @param adId
	 * @return
	 */
	List<Long> selectMemberList(Long adId);
	
	
	/**
	 * 根据用户查询是否已经抢到赞
	 * @param memberId
	 * @return
	 */
	Boolean selectStatusByMember(Long adId,Long memberId);

}
