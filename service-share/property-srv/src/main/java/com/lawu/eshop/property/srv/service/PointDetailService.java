package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.property.param.PointDetailQueryParam;
import com.lawu.eshop.property.param.PointDetailSaveDataParam;
import com.lawu.eshop.property.param.TransactionDetailQueryForBackageParam;
import com.lawu.eshop.property.srv.bo.PointDetailBO;

/**
 * 积分明细服务接口
 *
 * @author Sunny
 * @date 2017/3/30
 */
public interface PointDetailService {

	/**
	 * 根据用户编号、查询参数分页查询积分明细
	 * 
	 * @param userNo 用户编号
	 * @param transactionDetailQueryParam 查询参数
	 * @return 
	 */
	Page<PointDetailBO> findPageByUserNum(String userNo, PointDetailQueryParam transactionDetailQueryParam);
	
	/**
	 * 保存积分明细记录
	 * @param param
	 * @return
	 */
	int save(PointDetailSaveDataParam param);

	/**
	 * 查询运营后台充值积分记录
	 *
	 * @param param
	 * @return
	 */
	Page<PointDetailBO> getBackagePointPageList(TransactionDetailQueryForBackageParam param);

}
