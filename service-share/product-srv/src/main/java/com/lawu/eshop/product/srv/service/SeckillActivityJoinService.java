package com.lawu.eshop.product.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.param.SeckillActivityJoinParam;
import com.lawu.eshop.product.srv.bo.SeckillActivityJoinBO;

/**
 * 参加抢购活动服务接口
 * 
 * @author zhangrc
 * @date 2017/11/23
 *
 */
public interface SeckillActivityJoinService {
	
	/**
	 * 活动专场列表
	 * @param param
	 * @return
	 */
	Page<SeckillActivityJoinBO> queryPage(SeckillActivityJoinParam param);

}
