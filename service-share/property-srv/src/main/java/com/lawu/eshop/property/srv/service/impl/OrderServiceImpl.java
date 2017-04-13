package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.TransactionTitleEnum;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.domain.extend.PropertyInfoDOEiditView;
import com.lawu.eshop.property.srv.mapper.extend.PropertyInfoDOMapperExtend;
import com.lawu.eshop.property.srv.service.OrderService;
import com.lawu.eshop.property.srv.service.TransactionDetailService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TransactionDetailService transactionDetailService;
	@Autowired
	private PropertyInfoDOMapperExtend propertyInfoDOMapperExtend;

	@Override
	public int doHandleOrderPayNotify(NotifyCallBackParam param) {
		// 新增会员交易记录
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

		// 更新订单状态
		// TODO 发送MQ消息更新订单状态

		return ResultCode.SUCCESS;
	}

	@Override
	@Transactional
	public int doHandlePayOrderNotify(NotifyCallBackParam param) {

		// 新增会员交易记录，加商家交易明细，加商家财产余额，<更新订单状态>

		// 新增会员交易明细
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

		// 新增商家交易明细
		TransactionDetailSaveDataParam tdsParam1 = new TransactionDetailSaveDataParam();
		tdsParam1.setTitle(TransactionTitleEnum.PAY.val);
		tdsParam1.setTransactionNum(param.getOutTradeNo());
		tdsParam1.setUserNum(param.getSideUserNum());
		tdsParam1.setTransactionAccount(param.getBuyerLogonId());
		tdsParam1.setTransactionType(MerchantTransactionTypeEnum.PAY.getValue());
		tdsParam1.setTransactionAccountType(param.getTransactionPayTypeEnum().val);
		tdsParam1.setAmount(new BigDecimal(param.getTotalFee()));
		tdsParam1.setBizId(Long.valueOf(param.getBizIds()));
		tdsParam1.setThirdTransactionNum(param.getTradeNo());
		transactionDetailService.save(tdsParam);

		// 加商家财产余额
		PropertyInfoDOEiditView infoDoView = new PropertyInfoDOEiditView();
		infoDoView.setUserNum(param.getSideUserNum());
		infoDoView.setBalance(new BigDecimal(param.getTotalFee()));
		infoDoView.setGmtModified(new Date());
		propertyInfoDOMapperExtend.updatePropertyInfoAddBalance(infoDoView);

		// 更新订单状态
		// TODO 发送MQ消息更新买单状态
		

		return ResultCode.SUCCESS;
	}

}
