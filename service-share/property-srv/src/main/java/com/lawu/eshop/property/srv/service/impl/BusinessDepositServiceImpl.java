package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.message.MessageProducerService;
import com.lawu.eshop.property.constants.BusinessDepositStatusEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.PropertyInfoDirectionEnum;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.constants.TransactionTitleEnum;
import com.lawu.eshop.property.dto.BusinessDepositInitDTO;
import com.lawu.eshop.property.param.BusinessDepositQueryDataParam;
import com.lawu.eshop.property.param.BusinessDepositSaveDataParam;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.bo.BusinessDepositQueryBO;
import com.lawu.eshop.property.srv.domain.BankAccountDO;
import com.lawu.eshop.property.srv.domain.BusinessDepositDO;
import com.lawu.eshop.property.srv.domain.BusinessDepositDOExample;
import com.lawu.eshop.property.srv.domain.BusinessDepositDOExample.Criteria;
import com.lawu.eshop.property.srv.mapper.BankAccountDOMapper;
import com.lawu.eshop.property.srv.mapper.BusinessDepositDOMapper;
import com.lawu.eshop.property.srv.service.BusinessDepositService;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
import com.lawu.eshop.utils.DateUtil;
import com.lawu.eshop.utils.StringUtil;

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
		
		// 发送MQ消息修改门店状态
		messageProducerService.sendMessage(MqConstant.TOPIC_PROPERTY_SRV,MqConstant.TAG_HANDLE_DEPOSIT,param.getUserNum());
		
		
		result.setRet(ResultCode.SUCCESS);
		return result;
	}

	@Override
	public Page<BusinessDepositQueryBO> selectDepositList(BusinessDepositQueryDataParam param) {
		BusinessDepositDOExample example = new BusinessDepositDOExample();
		Criteria criteria1 = example.createCriteria();
		criteria1.andGmtCreateBetween(param.getBeginDate(), param.getEndDate())
				.andStatusEqualTo(param.getBusinessDepositStatusEnum().val);
		if(param.getTransactionPayTypeEnum() != null){
			criteria1.andPaymentMethodEqualTo(param.getTransactionPayTypeEnum().val);
		}
		if (param.getRegionPath() != null && !"".equals(param.getRegionPath())) {
			if (param.getRegionPath().split("/").length == 1) {
				criteria1.andProvinceIdEqualTo(Long.valueOf(param.getRegionPath().split("/")[0]));
			} else if (param.getRegionPath().split("/").length == 2) {
				criteria1.andProvinceIdEqualTo(Long.valueOf(param.getRegionPath().split("/")[1]));
			} else if (param.getRegionPath().split("/").length == 3) {
				criteria1.andProvinceIdEqualTo(Long.valueOf(param.getRegionPath().split("/")[2]));
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
		for(BusinessDepositDO bddo : bddos){
			BusinessDepositQueryBO ddqbo = new BusinessDepositQueryBO();
			ddqbo.setId(bddo.getId());
			ddqbo.setGmtPay(DateUtil.getDateFormat(bddo.getGmtPay(), "yyyy-MM-dd HH:mm:ss"));
			ddqbo.setThirdNumber(bddo.getThirdNumber());
			ddqbo.setBusinessAccount(bddo.getBusinessAccount());
			ddqbo.setDepositNumber(bddo.getDepositNumber());
			ddqbo.setBusinessName(bddo.getBusinessName());
			ddqbo.setAmount(bddo.getAmount());
			if(TransactionPayTypeEnum.ALIPAY.val.equals(bddo.getPaymentMethod())){
				ddqbo.setPayMethod("支付宝");
			} else if(TransactionPayTypeEnum.WX.val.equals(bddo.getPaymentMethod())){
				ddqbo.setPayMethod("微信");
			}
			
			if(BusinessDepositStatusEnum.VERIFY.val.equals(bddo.getStatus())){
				ddqbo.setStatus("待核实");
			} else if(BusinessDepositStatusEnum.VERIFYD.val.equals(bddo.getStatus())){
				ddqbo.setStatus("已核实");
			} else if(BusinessDepositStatusEnum.APPLY_REFUND.val.equals(bddo.getStatus())){
				ddqbo.setStatus("申请退款");
			} else if(BusinessDepositStatusEnum.ACCPET_REFUND.val.equals(bddo.getStatus())){
				ddqbo.setStatus("退款已受理");
			} else if(BusinessDepositStatusEnum.REFUND_SUCCESS.val.equals(bddo.getStatus())){
				ddqbo.setStatus("退款成功");
			} else if(BusinessDepositStatusEnum.REFUND_FAILURE.val.equals(bddo.getStatus())){
				ddqbo.setStatus("退款失败");
			}
			
			BankAccountDO bankAccountDO = bankAccountDOMapper.selectByPrimaryKey(bddo.getBusinessBankAccountId());
			ddqbo.setBusinessBankAccount(bankAccountDO.getAccountName());
			ddqbo.setBankNo(bankAccountDO.getAccountNumber());
			ddqbo.setBankName(bankAccountDO.getNote().substring(0, bankAccountDO.getNote().indexOf("(")));
			ddqbo.setBankBranchName(bankAccountDO.getSubBranchName());
			
			ddqbo.setAuditUserName(bddo.getOperUserName());
			ddqbo.setRemark(bddo.getRemark());
			newList.add(ddqbo);
		}
		page.setRecords(newList);
		return page;
	}

}
