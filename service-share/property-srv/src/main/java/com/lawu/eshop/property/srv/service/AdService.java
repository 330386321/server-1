package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.property.param.PropertyInfoDataParam;

/**
 * 
 * <p>
 * Description:会员点击广告
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月24日 下午4:34:16
 *
 */
public interface AdService {

	/**
	 * 会员点击广告
	 * A->B->C->D（用户）A、B、C也可能是商家
	 *	D点价值1元钱一次的广告（商家发布时广告单价为1元）：
	 *	D获得：50%*1*0.997，进余额 ---------------------当前计算会员
	 *
	 *	C提成：0.5*16%*0.997，进余额
	 *	B提成：0.5*3%*0.997，进余额
	 *	A提成：0.5*1%，进积分
     *
	 * @param param
	 * @return
	 */
	int clickAd(PropertyInfoDataParam param);

}
