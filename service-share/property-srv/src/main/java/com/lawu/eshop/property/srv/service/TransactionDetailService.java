package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.param.TransactionDetailQueryParam;
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
	 * 根据用户编号、交易类型、查询参数分页查询交易明细
	 * 
	 * @param userNo 用户编号
	 * @param transactionType 交易类型
	 * @param transactionDetailQueryParam 查询参数
	 * @return 
	 */
	Page<TransactionDetailBO> findPageByUserNum(String userNo, Byte transactionType, TransactionDetailQueryParam transactionDetailQueryParam);
	
	/**
	 * 根据用户编号和交易类型查询交易的总条数
	 * 
	 * @param userNo
	 * @param transactionType
	 * @return
	 */
	Integer findCountByUserNum(String userNo, Byte transactionType);

	/**
	 * 保存交易记录表
	 * @param param
	 * @return
	 */
	int save(TransactionDetailSaveDataParam param);
}
