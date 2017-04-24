package com.lawu.eshop.statistics.service;

public interface ClickAdCommissionService {

	/**
	 * 1、查询用户广告点击记录中未计算提成的数据
	 * 2、查询用户上线邀请，计算提成
	 * A->B->C->D（用户）A、B、C也可能是商家
	 *	D点价值1元钱一次的广告（商家发布时广告单价为1元）：
	 *
	 *	D获得：50%*1*0.997，进余额
	 *	C提成：0.5*16%*0.997，进余额
	 *	B提成：0.5*3%*0.997，进余额
	 *	A提成：0.5*1%，进积分
     *
	 */
	void executeAutoClickAdCommission();

}
