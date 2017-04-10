package com.lawu.eshop.ad.srv.service;

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
	Integer save(RedPacketParam param,Long merchantId);

}
