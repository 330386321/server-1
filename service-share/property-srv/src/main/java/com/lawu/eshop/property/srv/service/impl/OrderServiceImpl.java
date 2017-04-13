package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.TransactionTitleEnum;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.service.OrderService;
import com.lawu.eshop.property.srv.service.TransactionDetailService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TransactionDetailService transactionDetailService;
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result doHandleOrderPayNotify(NotifyCallBackParam param) {
		//新增会员交易记录
		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		tdsParam.setTitle(TransactionTitleEnum.ORDER_PAY.val);
		tdsParam.setTransactionNum(param.getOutTradeNo());
		tdsParam.setUserNum(param.getUserNum());
		tdsParam.setTransactionAccount(param.getBuyerLogonId());
		tdsParam.setTransactionType(MemberTransactionTypeEnum.PAY_ORDERS.getValue());
		tdsParam.setTransactionAccountType(param.getTransactionPayTypeEnum().val);
		tdsParam.setAmount(new BigDecimal(param.getTotalFee()));
		tdsParam.setBizId(Long.valueOf(param.getBizIds().split(",")[0]));
		tdsParam.setThirdTransactionNum(param.getTradeNo());
		transactionDetailService.save(tdsParam);
		
		//更新订单状态
		//TODO 发送MQ消息更新订单状态
		
		
		
		return null;
	}

	@Override
	public Result doHandlePayOrderNotify(NotifyCallBackParam param) {
		//新增会员交易记录，加商家财产余额，<更新订单状态>
		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		tdsParam.setTitle(TransactionTitleEnum.PAY.val);
		tdsParam.setTransactionNum(param.getOutTradeNo());
		tdsParam.setUserNum(param.getUserNum());
		tdsParam.setTransactionAccount(param.getBuyerLogonId());
		tdsParam.setTransactionType(MemberTransactionTypeEnum.PAY.getValue());
		tdsParam.setTransactionAccountType(param.getTransactionPayTypeEnum().val);
		tdsParam.setAmount(new BigDecimal(param.getTotalFee()));
		tdsParam.setBizId(Long.valueOf(param.getBizIds()));
		tdsParam.setThirdTransactionNum(param.getTradeNo());
		transactionDetailService.save(tdsParam);
		
		
		
		return null;
	}

}
