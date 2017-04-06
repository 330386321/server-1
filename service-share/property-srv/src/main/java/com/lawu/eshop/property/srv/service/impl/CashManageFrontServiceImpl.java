package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.CashChannelEnum;
import com.lawu.eshop.property.constants.CashStatusEnum;
import com.lawu.eshop.property.constants.PropertyType;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.constants.TransactionTitle;
import com.lawu.eshop.property.param.CashDataParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.domain.BankAccountDO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDOExample;
import com.lawu.eshop.property.srv.domain.WithdrawCashDO;
import com.lawu.eshop.property.srv.domain.WithdrawCashDOExample;
import com.lawu.eshop.property.srv.domain.extend.PropertyInfoDOView;
import com.lawu.eshop.property.srv.mapper.BankAccountDOMapper;
import com.lawu.eshop.property.srv.mapper.PropertyInfoDOMapper;
import com.lawu.eshop.property.srv.mapper.WithdrawCashDOMapper;
import com.lawu.eshop.property.srv.mapper.extend.PropertyInfoDOMapperExtend;
import com.lawu.eshop.property.srv.service.CashManageFrontService;
import com.lawu.eshop.property.srv.service.PropertyService;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
import com.lawu.eshop.utils.DateUtil;

@Service
public class CashManageFrontServiceImpl implements CashManageFrontService {

	@Autowired
	private WithdrawCashDOMapper withdrawCashDOMapper;
	@Autowired
	private PropertyInfoDOMapper propertyInfoDOMapper;
	@Autowired
	private PropertyService propertyService;
	@Autowired
	private PropertyInfoDOMapperExtend propertyInfoDOMapperExtend;
	@Autowired
	private BankAccountDOMapper bankAccountDOMapper;
	@Autowired
	private TransactionDetailService transactionDetailService;

	@Override
	@Transactional
	public int save(CashDataParam cash) {

		String currentScale = propertyService.getValue(PropertyType.CASH_SCALE);
		if ("".equals(currentScale)) {
			return ResultCode.PROPERTY_CASH_SCALE_NULL;
		}

		String cashMoney = cash.getCashMoney();
		WithdrawCashDOExample example = new WithdrawCashDOExample();
		example.createCriteria().andUserNumEqualTo(cash.getUserNum())
				.andGmtCreateGreaterThanOrEqualTo(DateUtil.getFirstDayOfMonth());
		int count = withdrawCashDOMapper.countByExample(example);
		double dCashMoney = new Double(cashMoney).doubleValue();
		if (count > 1 && dCashMoney <= 5) {
			return ResultCode.CASH_MORE_NUM_MAX_MONEY_ERROR;
		}

		BankAccountDO bankAccountDo = bankAccountDOMapper.selectByPrimaryKey(cash.getBusinessBankAccountId());
		if (bankAccountDo == null) {
			return ResultCode.PROPERTY_CASH_BANK_NOT_EXIST;
		} else if (!bankAccountDo.getUserNum().trim().equals(cash.getUserNum())) {
			return ResultCode.PROPERTY_CASH_BANK_NOT_MATCH;
		}

		PropertyInfoDOExample infoExample = new PropertyInfoDOExample();
		infoExample.createCriteria().andUserNumEqualTo(cash.getUserNum());
		List<PropertyInfoDO> infoList = propertyInfoDOMapper.selectByExample(infoExample);
		if (infoList == null || infoList.isEmpty() || infoList.size() < 1) {
			return ResultCode.PROPERTY_INFO_NULL;
		} else if (infoList.size() > 1) {
			return ResultCode.PROPERTY_INFO_OUT_INDEX;
		} else {
			PropertyInfoDO info = infoList.get(0);
			BigDecimal balance = info.getBalance();
			double dBalacne = balance.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			if (dBalacne < dCashMoney) {
				return ResultCode.PROPERTY_INFO_BALANCE_LESS;
			}
			// TODO 校验支付密码

		}

		double dCurrentScale = new Double(currentScale).doubleValue();
		double money = dCashMoney * dCurrentScale;

		WithdrawCashDO withdrawCashDO = new WithdrawCashDO();
		withdrawCashDO.setCashMoney(new BigDecimal(cashMoney));
		withdrawCashDO.setCurrentScale(currentScale);
		withdrawCashDO.setMoney(new BigDecimal(money));
		withdrawCashDO.setUserNum(cash.getUserNum());
		withdrawCashDO.setUserType(cash.getUserType());
		withdrawCashDO.setChannel(CashChannelEnum.ARTIFICIAL.val);
		withdrawCashDO.setStatus(CashStatusEnum.APPLY.val);
		withdrawCashDO.setBusinessBankAccountId(cash.getBusinessBankAccountId());
		withdrawCashDO.setCashNumber(cash.getCashNumber());
		withdrawCashDO.setGmtCreate(new Date());
		withdrawCashDOMapper.insertSelective(withdrawCashDO);

		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		tdsParam.setTitle(TransactionTitle.CASH);
		tdsParam.setTransactionNum(cash.getCashNumber());
		tdsParam.setUserNum(cash.getUserNum());
		tdsParam.setTransactionType(cash.getTransactionType());
		tdsParam.setTransactionAccount(cash.getAccount());
		tdsParam.setTransactionAccountType(TransactionPayTypeEnum.BALANCE.val);
		tdsParam.setAmount(new BigDecimal(cashMoney));
		tdsParam.setBizId(withdrawCashDO.getId());
		transactionDetailService.save(tdsParam);

		PropertyInfoDOView infoDoView = new PropertyInfoDOView();
		infoDoView.setId(infoList.get(0).getId());
		infoDoView.setBalance(new BigDecimal(cashMoney));
		infoDoView.setGmtModified(new Date());
		propertyInfoDOMapperExtend.updateByPrimaryKeySelective(infoDoView);

		return ResultCode.SUCCESS;
	}

}
