package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.BusinessDepositStatusEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.PropertyInfoDirectionEnum;
import com.lawu.eshop.property.constants.TransactionTitleEnum;
import com.lawu.eshop.property.dto.BusinessDepositInitDTO;
import com.lawu.eshop.property.param.BusinessDepositSaveDataParam;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.domain.BusinessDepositDO;
import com.lawu.eshop.property.srv.domain.BusinessDepositDOExample;
import com.lawu.eshop.property.srv.mapper.BusinessDepositDOMapper;
import com.lawu.eshop.property.srv.service.BusinessDepositService;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
import com.lawu.eshop.utils.StringUtil;

@Service
public class BusinessDepositServiceImpl implements BusinessDepositService {

	@Autowired
	private BusinessDepositDOMapper businessDepositDOMapper;
	@Autowired
	private TransactionDetailService transactionDetailService;

	@Override
	@Transactional
	public BusinessDepositInitDTO save(BusinessDepositSaveDataParam param) {
		BusinessDepositDO bddo = new BusinessDepositDO();
		bddo.setBusinessId(Long.valueOf(param.getBusinessId()));
		bddo.setUserNum(param.getUserNum());
		bddo.setBusinessAccount(param.getBusinessAccount());
		bddo.setBusinessName(param.getBusinessName());
		bddo.setDepositNumber(StringUtil.getRandomNum(""));
		bddo.setAmount(new BigDecimal(param.getDeposit()));
		bddo.setStatus(BusinessDepositStatusEnum.PAYING.val);
		bddo.setProvinceId(Long.valueOf(param.getProvinceId()));
		bddo.setCityId(Long.valueOf(param.getCityId()));
		bddo.setAreaId(Long.valueOf(param.getAreaId()));
		bddo.setGmtCreate(new Date());
		businessDepositDOMapper.insertSelective(bddo);

		BusinessDepositInitDTO dto = new BusinessDepositInitDTO();
		dto.setId(bddo.getId());
		dto.setDeposit(param.getDeposit());
		return dto;
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public Result doHandleDepositNotify(NotifyCallBackParam param) {
		Result result = new Result();
		BusinessDepositDO deposit = businessDepositDOMapper.selectByPrimaryKey(Long.valueOf(param.getBizIds()));
		if (deposit == null) {
			result.setRet(ResultCode.FAIL);
			result.setMsg("保证金初始记录为空");
			return result;
		}
		BigDecimal dbMoney = deposit.getAmount();
		BigDecimal backMoney = new BigDecimal(param.getTotalFee());
		if (backMoney.compareTo(dbMoney) != 0) {
			result.setRet(ResultCode.FAIL);
			result.setMsg("保证金回调金额与保证金表保存的金额不一致(回调金额：" + backMoney + ",表中金额:" + dbMoney + ")");
			return result;
		}

		// 新增交易明细
		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		tdsParam.setTitle(TransactionTitleEnum.DEPOSIT.val);
		tdsParam.setTransactionNum(param.getOutTradeNo());
		tdsParam.setUserNum(param.getUserNum());
		tdsParam.setTransactionAccount(param.getBuyerLogonId());
		tdsParam.setTransactionType(MerchantTransactionTypeEnum.DEPOSIT.getValue());
		tdsParam.setTransactionAccountType(param.getTransactionPayTypeEnum().val);
		tdsParam.setAmount(new BigDecimal(param.getTotalFee()));
		tdsParam.setBizId(Long.valueOf(param.getBizIds()));
		tdsParam.setThirdTransactionNum(param.getTradeNo());
		tdsParam.setDirection(PropertyInfoDirectionEnum.IN.val);
		transactionDetailService.save(tdsParam);
		
		//更新保证金记录状态
		BusinessDepositDO depositDO = new BusinessDepositDO();
		depositDO.setThirdNumber(param.getTradeNo());
		depositDO.setThirdAccount(param.getBuyerLogonId());
		depositDO.setPaymentMethod(param.getTransactionPayTypeEnum().val);
		depositDO.setStatus(BusinessDepositStatusEnum.VERIFY.val);
		depositDO.setGmtPay(new Date());
		BusinessDepositDOExample example = new BusinessDepositDOExample();
		example.createCriteria().andIdEqualTo(Long.valueOf(param.getBizIds()));
		businessDepositDOMapper.updateByExample(depositDO, example);
		
		//TODO 发送MQ消息修改门店状态
		
		
		result.setRet(ResultCode.SUCCESS);
		return result;
	}

}
