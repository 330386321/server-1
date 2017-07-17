package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.ad.param.CommissionJobParam;
import com.lawu.eshop.property.param.PropertyInfoDataParam;

/**
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月24日 下午4:34:16
 *
 */
public interface CommissionService {

	/**
	 * 定时任务计算上线提成(点广告、买单、商品订单)
	 * A->B->C->D（用户）A、B、C也可能是商家
	 *	
	 * @param param
	 * @return
	 */
	int calculation(CommissionJobParam param);

}
