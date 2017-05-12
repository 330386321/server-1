package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.PropertyInfoDirectionEnum;
import com.lawu.eshop.property.constants.ThirdPartyBizFlagEnum;
import com.lawu.eshop.property.constants.ThirdPayStatusEnum;
import com.lawu.eshop.property.constants.TransactionTitleEnum;
import com.lawu.eshop.property.dto.RechargeSaveDTO;
import com.lawu.eshop.property.dto.ThirdPayCallBackQueryPayOrderDTO;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.param.PointDetailSaveDataParam;
import com.lawu.eshop.property.param.RechargeSaveDataParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.domain.RechargeDO;
import com.lawu.eshop.property.srv.domain.RechargeDOExample;
import com.lawu.eshop.property.srv.domain.extend.PropertyInfoDOEiditView;
import com.lawu.eshop.property.srv.mapper.RechargeDOMapper;
import com.lawu.eshop.property.srv.mapper.extend.PropertyInfoDOMapperExtend;
import com.lawu.eshop.property.srv.service.PointDetailService;
import com.lawu.eshop.property.srv.service.RechargeService;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.utils.StringUtil;

@Service
public class RechargeServiceImpl implements RechargeService {

	private static Logger logger = LoggerFactory.getLogger(RechargeServiceImpl.class);
			
	@Autowired
	private RechargeDOMapper rechargeDOMapper;
	@Autowired
	private PropertyInfoDOMapperExtend propertyInfoDOMapperExtend;
	@Autowired
	private TransactionDetailService transactionDetailService;
	@Autowired
	private PointDetailService pointDetailService;

	@Override
	@Transactional
	public RechargeSaveDTO save(RechargeSaveDataParam param) {
		RechargeDO recharge = new RechargeDO();
		recharge.setUserNum(param.getUserNum());
		recharge.setRechargeMoney(new BigDecimal(param.getRechargeMoney()));
		recharge.setCurrentScale(param.getRechargeScale());

		double dCurrentScale = new Double(param.getRechargeScale()).doubleValue();
		double dRechargeMoney = new Double(param.getRechargeMoney()).doubleValue();
		double money = dRechargeMoney * dCurrentScale;
		recharge.setMoney(new BigDecimal(money));

		recharge.setRechargeType(param.getPayTypeEnum().val);
		recharge.setChannel(param.getTransactionPayTypeEnum().val);
		recharge.setStatus(ThirdPayStatusEnum.PAYING.val);
		recharge.setRechargeNumber(StringUtil.getRandomNum(""));
		recharge.setGmtCreate(new Date());
		rechargeDOMapper.insertSelective(recharge);

		if (recharge.getId() == null) {
			return null;
		}
		RechargeSaveDTO dto = new RechargeSaveDTO();
		dto.setId(recharge.getId());
		return dto;
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public Result doHandleRechargeNotify(NotifyCallBackParam param) {
		Result result = new Result();

		RechargeDO recharge = rechargeDOMapper.selectByPrimaryKey(Long.valueOf(param.getBizIds()));
		if (recharge == null) {
			result.setRet(ResultCode.FAIL);
			result.setMsg("充值记录为空");
			return result;
		}else{
			if(ThirdPayStatusEnum.SUCCESS.val.equals(recharge.getStatus())){
				result.setRet(ResultCode.SUCCESS);
				logger.info("重复回调(判断幂等)");
				return result;
			}
		}
		BigDecimal dbMoney = recharge.getRechargeMoney();
		BigDecimal backMoney = new BigDecimal(param.getTotalFee());
		if (backMoney.compareTo(dbMoney) != 0) {
			result.setRet(ResultCode.FAIL);
			result.setMsg("充值回调金额与充值表保存的金额不一致(回调金额：" + backMoney + ",表中金额:" + dbMoney + ")");
			return result;
		}

		// 充余额：加财产余额，新增充余额交易明细,修改充值表状态
		// 充积分：加财产积分，新增积分明细，新增充积分交易明细,修改充值表状态

		int bizFlagInt = Integer.valueOf(param.getBizFlag()).intValue();
		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		
		if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BALANCE.val.equals(StringUtil.intToByte(bizFlagInt))
				|| ThirdPartyBizFlagEnum.MEMBER_PAY_BALANCE.val.equals(StringUtil.intToByte(bizFlagInt))) {
			
			tdsParam.setTitle(TransactionTitleEnum.RECHARGE.val);
			
			//加余额
			PropertyInfoDOEiditView infoDoView = new PropertyInfoDOEiditView();
			infoDoView.setUserNum(param.getUserNum());
			infoDoView.setBalance(new BigDecimal(param.getTotalFee()));
			infoDoView.setGmtModified(new Date());
			propertyInfoDOMapperExtend.updatePropertyInfoAddBalance(infoDoView);
			
		} else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_POINT.val.equals(StringUtil.intToByte(bizFlagInt))
				|| ThirdPartyBizFlagEnum.MEMBER_PAY_POINT.val.equals(StringUtil.intToByte(bizFlagInt))) {

			tdsParam.setTitle(TransactionTitleEnum.INTEGRAL_RECHARGE.val);
			
			//新增积分明细
			PointDetailSaveDataParam pdsParam = new PointDetailSaveDataParam();
			pdsParam.setTitle(TransactionTitleEnum.INTEGRAL_RECHARGE.val);
			pdsParam.setPointNum(param.getOutTradeNo());
			pdsParam.setUserNum(param.getUserNum());
			if(param.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
				pdsParam.setPointType(MemberTransactionTypeEnum.RECHARGE.getValue());
			}else if(param.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)){
				pdsParam.setPointType(MerchantTransactionTypeEnum.RECHARGE.getValue());
			}
			pdsParam.setPoint(recharge.getMoney());
			pdsParam.setDirection(PropertyInfoDirectionEnum.IN.val);
			pdsParam.setRemark("");
			pointDetailService.save(pdsParam);
			
			//加积分
			PropertyInfoDOEiditView infoDoView = new PropertyInfoDOEiditView();
			infoDoView.setUserNum(param.getUserNum());
			infoDoView.setPoint(recharge.getMoney());
			infoDoView.setGmtModified(new Date());
			propertyInfoDOMapperExtend.updatePropertyInfoAddPoint(infoDoView);
		}
		
		//新增交易明细
		tdsParam.setTransactionNum(param.getOutTradeNo());
		tdsParam.setUserNum(param.getUserNum());
		tdsParam.setTransactionAccount(param.getBuyerLogonId());
		if(param.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
			tdsParam.setTransactionType(MemberTransactionTypeEnum.RECHARGE.getValue());
		}else if(param.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)){
			tdsParam.setTransactionType(MerchantTransactionTypeEnum.RECHARGE.getValue());
		}
		tdsParam.setTransactionAccountType(param.getTransactionPayTypeEnum().val);
		tdsParam.setAmount(new BigDecimal(param.getTotalFee()));
		tdsParam.setBizId(param.getBizIds());
		tdsParam.setThirdTransactionNum(param.getTradeNo());
		tdsParam.setDirection(PropertyInfoDirectionEnum.IN.val);
		transactionDetailService.save(tdsParam);
		
		//更新充值表状态
		RechargeDO rechargeDO = new RechargeDO();
		rechargeDO.setStatus(ThirdPayStatusEnum.SUCCESS.val);
		rechargeDO.setThirdNumber(param.getTradeNo());
		rechargeDO.setGmtModified(new Date());
		RechargeDOExample example = new RechargeDOExample();
		example.createCriteria().andIdEqualTo(Long.valueOf(param.getBizIds()));
		rechargeDOMapper.updateByExampleSelective(rechargeDO, example);
		
		result.setRet(ResultCode.SUCCESS);
		
		return result;
	}

	@Override
	public ThirdPayCallBackQueryPayOrderDTO getRechargeMoney(String rechargeId) {
		RechargeDO recharge = rechargeDOMapper.selectByPrimaryKey(Long.valueOf(rechargeId));
		if(recharge == null){
			return null;
		}
		ThirdPayCallBackQueryPayOrderDTO rechargeDTO = new ThirdPayCallBackQueryPayOrderDTO();
		rechargeDTO.setActualMoney(recharge.getRechargeMoney().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		rechargeDTO.setOrderNum(recharge.getRechargeNumber());
		return rechargeDTO;
	}

}
