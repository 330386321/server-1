package com.lawu.eshop.product.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.param.SeckillActivityJoinParam;
import com.lawu.eshop.product.param.SeckillActivityManageParam;
import com.lawu.eshop.product.srv.bo.SeckillActivityDetailBO;
import com.lawu.eshop.product.srv.bo.SeckillActivityJoinBO;
import com.lawu.eshop.product.srv.bo.SeckillActivityManageBO;
import com.lawu.eshop.product.srv.bo.SeckillActivityManageDetailBO;

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
	
	
	/**
	 * 活动管理列表
	 * @param param
	 * @return
	 */
	Page<SeckillActivityManageBO> queryManagePage(SeckillActivityManageParam param);
	
	
	/**
	 * 活动详情
	 * @param id
	 * @return
	 */
	SeckillActivityDetailBO querySeckillActivityDetail(Long id , Long merchantId);
	
	
	/**
	 * 活动管理详情
	 * @param id
	 * @return
	 */
	SeckillActivityManageDetailBO querySeckillActivityManageDetail(Long id , Long merchantId);

}
