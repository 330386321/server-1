package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.message.MessageProducerService;
import com.lawu.eshop.property.constants.BusinessDepositOperEnum;
import com.lawu.eshop.property.constants.BusinessDepositStatusEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.PropertyInfoDirectionEnum;
import com.lawu.eshop.property.constants.PropertyType;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.constants.TransactionTitleEnum;
import com.lawu.eshop.property.dto.BusinessDepositInitDTO;
import com.lawu.eshop.property.param.*;
import com.lawu.eshop.property.srv.bo.BusinessDepositDetailBO;
import com.lawu.eshop.property.srv.bo.BusinessDepositQueryBO;
import com.lawu.eshop.property.srv.domain.BankAccountDO;
import com.lawu.eshop.property.srv.domain.BusinessDepositDO;
import com.lawu.eshop.property.srv.domain.BusinessDepositDOExample;
import com.lawu.eshop.property.srv.domain.BusinessDepositDOExample.Criteria;
import com.lawu.eshop.property.srv.mapper.BankAccountDOMapper;
import com.lawu.eshop.property.srv.mapper.BusinessDepositDOMapper;
import com.lawu.eshop.property.srv.service.BusinessDepositService;
import com.lawu.eshop.property.srv.service.PropertyService;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
import com.lawu.eshop.user.dto.MerchantStatusEnum;
import com.lawu.eshop.user.param.HandleDepostMessage;
import com.lawu.eshop.utils.DateUtil;
import com.lawu.eshop.utils.StringUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BusinessDepositServiceImpl implements BusinessDepositService {

	@Autowired
	private BusinessDepositDOMapper businessDepositDOMapper;
	@Autowired
	private TransactionDetailService transactionDetailService;
	@Autowired
	private BankAccountDOMapper bankAccountDOMapper;
	@Autowired
	private MessageProducerService messageProducerService;
	@Autowired
	private PropertyService propertyService;

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

		// 更新保证金记录状态
		BusinessDepositDO depositDO = new BusinessDepositDO();
		depositDO.setThirdNumber(param.getTradeNo());
		depositDO.setThirdAccount(param.getBuyerLogonId());
		depositDO.setPaymentMethod(param.getTransactionPayTypeEnum().val);
		depositDO.setStatus(BusinessDepositStatusEnum.VERIFY.val);
		depositDO.setGmtPay(new Date());
		BusinessDepositDOExample example = new BusinessDepositDOExample();
		example.createCriteria().andIdEqualTo(Long.valueOf(param.getBizIds()));
		businessDepositDOMapper.updateByExample(depositDO, example);

		// 回调成功后，发送MQ消息修改门店状态为：已缴保证金待核实
		HandleDepostMessage message = new HandleDepostMessage();
		message.setUserNum(param.getUserNum());
		message.setStatusEnum(MerchantStatusEnum.MERCHANT_STATUS_GIVE_MONEY_CHECK);
		messageProducerService.sendMessage(MqConstant.TOPIC_PROPERTY_SRV, MqConstant.TAG_HANDLE_DEPOSIT, message);

		result.setRet(ResultCode.SUCCESS);
		return result;
	}

	@Override
	public Page<BusinessDepositQueryBO> selectDepositList(BusinessDepositQueryDataParam param) {
		BusinessDepositDOExample example = new BusinessDepositDOExample();
		Criteria criteria1 = example.createCriteria();
		criteria1.andGmtCreateBetween(param.getBeginDate(), param.getEndDate())
				.andStatusEqualTo(param.getBusinessDepositStatusEnum().val);
		if (param.getTransactionPayTypeEnum() != null) {
			criteria1.andPaymentMethodEqualTo(param.getTransactionPayTypeEnum().val);
		}
		if (param.getRegionPath() != null && !"".equals(param.getRegionPath())) {
			if (param.getRegionPath().split("/").length == 1) {
				criteria1.andProvinceIdEqualTo(Long.valueOf(param.getRegionPath().split("/")[0]));
			} else if (param.getRegionPath().split("/").length == 2) {
				criteria1.andCityIdEqualTo(Long.valueOf(param.getRegionPath().split("/")[1]));
			} else if (param.getRegionPath().split("/").length == 3) {
				criteria1.andAreaIdEqualTo(Long.valueOf(param.getRegionPath().split("/")[2]));
			}
		}

		if (param.getContent() != null && !"".equals(param.getContent().trim())) {
			Criteria criteria2 = example.createCriteria();
			criteria2.andDepositNumberEqualTo(param.getContent());

			Criteria criteria3 = example.createCriteria();
			criteria3.andThirdNumberEqualTo(param.getContent());

			example.or(criteria2);
			example.or(criteria3);
		}

		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
		Page<BusinessDepositQueryBO> page = new Page<BusinessDepositQueryBO>();
		page.setTotalCount(businessDepositDOMapper.countByExample(example));
		page.setCurrentPage(param.getCurrentPage());
		List<BusinessDepositDO> bddos = businessDepositDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		List<BusinessDepositQueryBO> newList = new ArrayList<BusinessDepositQueryBO>();
		for (BusinessDepositDO bddo : bddos) {
			BusinessDepositQueryBO ddqbo = new BusinessDepositQueryBO();
			ddqbo.setId(bddo.getId());
			ddqbo.setGmtPay(
					bddo.getGmtPay() == null ? "" : DateUtil.getDateFormat(bddo.getGmtPay(), "yyyy-MM-dd HH:mm:ss"));
			ddqbo.setThirdNumber(bddo.getThirdNumber() == null ? "" : bddo.getThirdNumber());
			ddqbo.setBusinessAccount(bddo.getBusinessAccount() == null ? "" : bddo.getBusinessAccount());
			ddqbo.setDepositNumber(bddo.getDepositNumber() == null ? "" : bddo.getDepositNumber());
			ddqbo.setBusinessName(bddo.getBusinessName() == null ? "" : bddo.getBusinessName());
			ddqbo.setAmount(bddo.getAmount() == null ? new BigDecimal("0") : bddo.getAmount());
			if (TransactionPayTypeEnum.ALIPAY.val.equals(bddo.getPaymentMethod())) {
				ddqbo.setPayMethod("支付宝");
			} else if (TransactionPayTypeEnum.WX.val.equals(bddo.getPaymentMethod())) {
				ddqbo.setPayMethod("微信");
			}

			if (BusinessDepositStatusEnum.VERIFY.val.equals(bddo.getStatus())) {
				ddqbo.setStatus("待核实");
			} else if (BusinessDepositStatusEnum.VERIFYD.val.equals(bddo.getStatus())) {
				ddqbo.setStatus("已核实");
			} else if (BusinessDepositStatusEnum.APPLY_REFUND.val.equals(bddo.getStatus())) {
				ddqbo.setStatus("申请退款");
			} else if (BusinessDepositStatusEnum.ACCPET_REFUND.val.equals(bddo.getStatus())) {
				ddqbo.setStatus("退款已受理");
			} else if (BusinessDepositStatusEnum.REFUND_SUCCESS.val.equals(bddo.getStatus())) {
				ddqbo.setStatus("退款成功");
			} else if (BusinessDepositStatusEnum.REFUND_FAILURE.val.equals(bddo.getStatus())) {
				ddqbo.setStatus("退款失败");
			}

			BankAccountDO bankAccountDO = bankAccountDOMapper.selectByPrimaryKey(bddo.getBusinessBankAccountId());
			ddqbo.setBusinessBankAccount(bankAccountDO.getAccountName() == null ? "" : bankAccountDO.getAccountName());
			ddqbo.setBankNo(bankAccountDO.getAccountNumber() == null ? "" : bankAccountDO.getAccountNumber());
			ddqbo.setBankName(bankAccountDO.getNote() == null ? ""
					: bankAccountDO.getNote().substring(0, bankAccountDO.getNote().indexOf("(")));
			ddqbo.setBankBranchName(bankAccountDO.getSubBranchName() == null ? "" : bankAccountDO.getSubBranchName());

			ddqbo.setAuditUserName(bddo.getOperUserName() == null ? "" : bddo.getOperUserName());
			ddqbo.setRemark(bddo.getRemark() == null ? "" : bddo.getRemark());
			newList.add(ddqbo);
		}
		page.setRecords(newList);
		return page;
	}

	@Override
	@Transactional
	public int updateBusinessDeposit(BusinessDepositOperDataParam param) {
		BusinessDepositDO bddo = new BusinessDepositDO();
		bddo.setStatus(param.getBusinessDepositOperEnum().val);
		bddo.setOperUserId(param.getOperUserId());
		bddo.setOperUserName(param.getOperUserName());
		bddo.setRemark(param.getFailReason() == null ? "" : param.getFailReason());
		bddo.setGmtModified(new Date());
		BusinessDepositDOExample example = new BusinessDepositDOExample();
		example.createCriteria().andIdEqualTo(Long.valueOf(param.getId()));
		businessDepositDOMapper.updateByExampleSelective(bddo, example);

		HandleDepostMessage message = new HandleDepostMessage();
		message.setUserNum(param.getUserNum());
		if (BusinessDepositOperEnum.VERIFYD.val.equals(param.getBusinessDepositOperEnum().val)) {
			// 核实操作成功后，发送MQ消息修改门店状态为：待审核
			message.setStatusEnum(MerchantStatusEnum.MERCHANT_STATUS_UNCHECK);
			messageProducerService.sendMessage(MqConstant.TOPIC_PROPERTY_SRV, MqConstant.TAG_HANDLE_DEPOSIT, message);

		} else if (BusinessDepositOperEnum.REFUND_SUCCESS.val.equals(param.getBusinessDepositOperEnum().val)) {
			// 退款成功操作后，发送MQ消息修改门店状态为：注销
			message.setStatusEnum(MerchantStatusEnum.MERCHANT_STATUS_CANCEL);
			messageProducerService.sendMessage(MqConstant.TOPIC_PROPERTY_SRV, MqConstant.TAG_HANDLE_DEPOSIT, message);
		}
		return ResultCode.SUCCESS;
	}

	@Override
	public int refundDeposit(BusinessRefundDepositParam param) {

		BusinessDepositDO deposit = businessDepositDOMapper.selectByPrimaryKey(Long.valueOf(param.getId()));
		int diffDays = DateUtil.daysOfTwo(deposit.getGmtModified(), new Date());
		String sysDays = propertyService.getValue(PropertyType.DEPOSIT_REFUND_DIFF_DAYS);
		if ("".equals(sysDays)) {
			sysDays = PropertyType.DEPOSIT_REFUND_DIFF_DAYS_DEFAULT;
		}
		if (diffDays <= Integer.valueOf(sysDays).intValue()) {
			return ResultCode.DEPOSIT_IN_SYSTEM_DAYS;
		}

		BusinessDepositDO bddo = new BusinessDepositDO();
		bddo.setStatus(BusinessDepositStatusEnum.APPLY_REFUND.val);
		bddo.setBusinessBankAccountId(Long.valueOf(param.getBusinessBankAccountId()));
		bddo.setGmtRefund(new Date());
		BusinessDepositDOExample example = new BusinessDepositDOExample();
		example.createCriteria().andIdEqualTo(Long.valueOf(param.getId()));
		businessDepositDOMapper.updateByExampleSelective(bddo, example);
		return ResultCode.SUCCESS;
	}

	@Override
	public BusinessDepositDetailBO selectDeposit(String businessId) {
		BusinessDepositDOExample example = new BusinessDepositDOExample();
		example.createCriteria().andBusinessIdEqualTo(Long.valueOf(businessId));
		List<BusinessDepositDO> list = businessDepositDOMapper.selectByExample(example);
		if (list == null || list.isEmpty()) {
			return null;
		}
		BusinessDepositDetailBO bo = new BusinessDepositDetailBO();
		bo.setId(list.get(0).getId());
		bo.setAmount(list.get(0).getAmount());
		bo.setBusinessDepositStatusEnum(BusinessDepositStatusEnum.getEnum(list.get(0).getStatus()));

		BankAccountDO bankAccountDO = bankAccountDOMapper.selectByPrimaryKey(list.get(0).getBusinessBankAccountId());
		bo.setBankName(bankAccountDO.getNote() == null ? ""
				: bankAccountDO.getNote().substring(0, bankAccountDO.getNote().indexOf("(")));
		String accountName = bankAccountDO.getAccountName();
		if (accountName.length() == 2) {
			accountName = "*" + accountName.substring(1);
		} else {
			accountName = accountName.substring(0, 1) + "*" + accountName.substring(accountName.length() - 1);
		}
		bo.setAccountName(accountName);
		bo.setCardNo(bankAccountDO.getNote() == null ? ""
				: bankAccountDO.getNote().substring(bankAccountDO.getNote().indexOf("(") + 1,
						bankAccountDO.getNote().indexOf(")")));
		return bo;
	}

}
