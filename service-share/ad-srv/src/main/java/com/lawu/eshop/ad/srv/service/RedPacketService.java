package com.lawu.eshop.ad.srv.service;

import java.math.BigDecimal;

import com.lawu.eshop.ad.param.RedPacketParam;

/**
 * 红包接口
 * @author zhangrc
 * @date 2017/4/8
 *
 */
public interface RedPacketService {
	
	/**
	 * 創建紅包
	 * @param param
	 * @return
	 */
	Integer save(RedPacketParam param,Long merchantId,String num);
	
	/**
	 * 根据商家查询发的红包
	 * @param merchantId
	 * @return
	 */
	Integer selectCount(Long merchantId);
	
	/**
	 * 领取红包
	 * @param memberId
	 * @return
	 */
	BigDecimal getRedPacket(Long id,Long memberId);

}
