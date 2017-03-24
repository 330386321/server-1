package com.lawu.eshop.user.srv.service;

/**
 * 我的E友 商家数量查询
 * @author zhangrc
 *
 */
public interface MemberProfileService {
	
	/**
	 * 根据会员id 查询当前的E友总数量数量
	 * @param id
	 * @return
	 */
	Integer getMemberCount(Long id);
	
	/**
	 * 根据会员id 查询当前推荐商家的总数量
	 * @param id
	 * @return
	 */
	Integer getMerchantCount(Long id);

}
