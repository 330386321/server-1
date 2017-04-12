package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.param.BalancePayDataParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.bo.PropertyBalanceBO;
import com.lawu.eshop.property.srv.domain.extend.PropertyInfoDOEiditView;
import com.lawu.eshop.property.srv.mapper.extend.PropertyInfoDOMapperExtend;
import com.lawu.eshop.property.srv.service.BalancePayService;
import com.lawu.eshop.property.srv.service.PropertyInfoService;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
import com.lawu.eshop.utils.StringUtil;

@Service
public class BalancePayServiceImpl implements BalancePayService {
	
	@Autowired
	private PropertyInfoService propertyInfoService;
	@Autowired
	private TransactionDetailService transactionDetailService;
	@Autowired
	private PropertyInfoDOMapperExtend propertyInfoDOMapperExtend;
	@SuppressWarnings("rawtypes")
	@Autowired
    private TransactionMainService transactionMainService;

	@Override
	@Transactional
	public int balancePay(BalancePayDataParam param) {
		
		PropertyBalanceBO balanceBO = propertyInfoService.getPropertyBalanceByUserNum(param.getUserNum());
		BigDecimal dbBalance = balanceBO.getBalance();
		double dBalacne = dbBalance.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		double dOrderMoney = new Double(param.getAmount()).doubleValue();
		if(dBalacne < dOrderMoney){
			return ResultCode.PROPERTY_INFO_BALANCE_LESS;
		}
		
		//新增交易明细
		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		tdsParam.setTitle(param.getTitle());
		tdsParam.setTransactionNum(StringUtil.getRandomNum(""));
		tdsParam.setUserNum(param.getUserNum());
		tdsParam.setTransactionType(param.getMemberTransactionTypeEnum().getValue());
		tdsParam.setTransactionAccount(param.getAccount());
		tdsParam.setTransactionAccountType(TransactionPayTypeEnum.BALANCE.val);
		tdsParam.setAmount(new BigDecimal(param.getAmount()));
		tdsParam.setBizId(Long.valueOf(param.getBizIds().split(",")[0]));
		transactionDetailService.save(tdsParam);
		
		//减财产余额
		PropertyInfoDOEiditView infoDoView = new PropertyInfoDOEiditView();
		infoDoView.setUserNum(param.getUserNum());
		infoDoView.setBalance(new BigDecimal(param.getAmount()));
		infoDoView.setGmtModified(new Date());
		propertyInfoDOMapperExtend.updatePropertyInfoMinusBalance(infoDoView);
		
		//发异步消息更新状态
		String []bizIds = param.getBizIds().split(",");
		for(int i = 0 ; i < bizIds.length ; i++){
			transactionMainService.sendNotice(Long.valueOf(bizIds[i]));
		}
		
		return ResultCode.SUCCESS;
	}

}
