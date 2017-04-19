package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.property.param.TransactionDetailQueryForMemberParam;
import com.lawu.eshop.property.param.TransactionDetailQueryForMerchantParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.bo.TransactionDetailBO;

/**
 * 交易明细服务接口
 *
 * @author Sunny
 * @date 2017/3/29
 */
public interface TransactionDetailService {

	/**
	 * 根据用户编号、查询参数分页查询交易明细  - 商家
	 * 
	 * @param userNum 用户编号
	 * @param param 查询参数
	 * @return 
	 */
	Page<TransactionDetailBO> findPageByUserNumForMerchant(String userNum, TransactionDetailQueryForMerchantParam param);
	
	/**
	 * 根据用户编号、查询参数分页查询交易明细 - 用户
	 * 
	 * @param userNum 用户编号
	 * @param param 查询参数
	 * @return 
	 */
	Page<TransactionDetailBO> findPageByUserNumForMember(String userNum, TransactionDetailQueryForMemberParam param);
	
	/**
	 * 保存交易记录表
	 * @param param
	 * @return
	 */
	int save(TransactionDetailSaveDataParam param);
}
