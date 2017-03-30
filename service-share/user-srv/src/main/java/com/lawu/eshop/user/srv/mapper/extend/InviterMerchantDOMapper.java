package com.lawu.eshop.user.srv.mapper.extend;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.lawu.eshop.user.srv.domain.extend.InviterMerchantDO;

/**
 * 
 * 我推荐的商家扩展接口
 * @author zhangrc
 * @date 2017/03/30
 *
 */
public interface InviterMerchantDOMapper {
	
	/**
	 * 我邀请的商家
	 * @param merchantInviter
	 * @return
	 */
	List<InviterMerchantDO> selectInviterMerchantByRowbounds(InviterMerchantDO inviterMerchantDO,RowBounds rowBounds);
	
   
}