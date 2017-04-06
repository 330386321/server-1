package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.property.param.CashDataParam;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月5日 下午2:48:51
 *
 */
public interface CashManageFrontService {

	/**
	 * 提现操作，校验规则：
	 * 
	 * 1、校验提现金额小数最大2位
	 * 2、校验提现比例系统参数未配置
	 * 3、校验自然月提现次数大于1次时，提现金额必须大于5元
	 * 4、校验用户对应财产记录是否为空、记录数是否正确（仅一条）
	 * 5、校验银行卡（是否存在、是否是该用户的）
	 * 6、校验用户余额、支付密码
	 * 
	 * @param cashMoney
	 */
	int save(CashDataParam cash);
	
	

}
