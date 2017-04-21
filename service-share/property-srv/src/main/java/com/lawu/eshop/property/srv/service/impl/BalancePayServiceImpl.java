package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.PropertyInfoDirectionEnum;
import com.lawu.eshop.property.constants.PropertyType;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.param.BalancePayDataParam;
import com.lawu.eshop.property.param.PointDetailSaveDataParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.domain.extend.PropertyInfoDOEiditView;
import com.lawu.eshop.property.srv.mapper.extend.PropertyInfoDOMapperExtend;
import com.lawu.eshop.property.srv.service.BalancePayService;
import com.lawu.eshop.property.srv.service.PointDetailService;
import com.lawu.eshop.property.srv.service.PropertyInfoService;
import com.lawu.eshop.property.srv.service.PropertyService;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.utils.StringUtil;

@Service
public class BalancePayServiceImpl implements BalancePayService {
	
	@Autowired
	private PropertyInfoService propertyInfoService;
	@Autowired
	private TransactionDetailService transactionDetailService;
	@Autowired
	private PropertyInfoDOMapperExtend propertyInfoDOMapperExtend;

	@Autowired
	@Qualifier("payOrderTransactionMainServiceImpl")
    private TransactionMainService<Reply> payOrderTransactionMainServiceImpl;
	
	@Autowired
	@Qualifier("shoppingOrderPaymentTransactionMainServiceImpl")
    private TransactionMainService<Reply> shoppingOrderPaymentTransactionMainServiceImpl;
	
	@Autowired
	private PropertyService propertyService;
	@Autowired
	private PointDetailService pointDetailService;

	
	@Override
	@Transactional
	public int balancePayProductOrder(BalancePayDataParam param) {
		int retCode = propertyInfoService.validateBalance(param.getUserNum(), param.getAccount());
		if (retCode != ResultCode.SUCCESS) {
			return retCode;
		}
		
		//新增会员交易明细
		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		tdsParam.setTitle(param.getTitle());
		tdsParam.setTransactionNum(StringUtil.getRandomNum(""));
		tdsParam.setUserNum(param.getUserNum());
		tdsParam.setTransactionType(param.getMemberTransactionTypeEnum().getValue());
		tdsParam.setTransactionAccount(param.getAccount());
		tdsParam.setTransactionAccountType(TransactionPayTypeEnum.BALANCE.val);
		tdsParam.setAmount(new BigDecimal(param.getTotalAmount()));
		tdsParam.setBizId(Long.valueOf(param.getBizIds()));
		tdsParam.setDirection(PropertyInfoDirectionEnum.OUT.val);
		transactionDetailService.save(tdsParam);
		
		//减会员财产余额
		PropertyInfoDOEiditView infoDoView = new PropertyInfoDOEiditView();
		infoDoView.setUserNum(param.getUserNum());
		infoDoView.setBalance(new BigDecimal(param.getTotalAmount()));
		infoDoView.setGmtModified(new Date());
		propertyInfoDOMapperExtend.updatePropertyInfoMinusBalance(infoDoView);
		
		//发异步消息更新购物订单状态
		String []bizIds = param.getBizIds().split(",");
		for(int i = 0 ; i < bizIds.length ; i++){
			shoppingOrderPaymentTransactionMainServiceImpl.sendNotice(Long.valueOf(bizIds[i]));
		}
		
		return ResultCode.SUCCESS;
	}
	
	@Override
	@Transactional
	public int balancePay(BalancePayDataParam param) {
		
		int retCode = propertyInfoService.validateBalance(param.getUserNum(), param.getTotalAmount());
		if (retCode != ResultCode.SUCCESS) {
			return retCode;
		}
		
		String transactionNum = StringUtil.getRandomNum("");
		//新增会员交易明细
		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		tdsParam.setTitle(param.getTitle());
		tdsParam.setTransactionNum(transactionNum);
		tdsParam.setUserNum(param.getUserNum());
		tdsParam.setTransactionType(param.getMemberTransactionTypeEnum().getValue());
		tdsParam.setTransactionAccount(param.getAccount());
		tdsParam.setTransactionAccountType(TransactionPayTypeEnum.BALANCE.val);
		tdsParam.setAmount(new BigDecimal(param.getTotalAmount()));
		tdsParam.setBizId(Long.valueOf(param.getBizIds()));
		tdsParam.setDirection(PropertyInfoDirectionEnum.OUT.val);
		transactionDetailService.save(tdsParam);
		//减会员财产余额
		PropertyInfoDOEiditView infoDoView = new PropertyInfoDOEiditView();
		infoDoView.setUserNum(param.getUserNum());
		infoDoView.setBalance(new BigDecimal(param.getTotalAmount()));
		infoDoView.setGmtModified(new Date());
		propertyInfoDOMapperExtend.updatePropertyInfoMinusBalance(infoDoView);
		
		//新增商家交易明细
		TransactionDetailSaveDataParam tdsParam1 = new TransactionDetailSaveDataParam();
		tdsParam1.setTitle(param.getTitle());
		tdsParam1.setTransactionNum(transactionNum);
		tdsParam1.setUserNum(param.getSideUserNum());
		tdsParam1.setTransactionType(param.getMerchantTransactionTypeEnum().getValue());
		tdsParam1.setTransactionAccount(param.getAccount());
		tdsParam1.setTransactionAccountType(TransactionPayTypeEnum.BALANCE.val);
		tdsParam1.setAmount(new BigDecimal(param.getTotalAmount()));
		tdsParam1.setBizId(Long.valueOf(param.getBizIds()));
		tdsParam1.setDirection(PropertyInfoDirectionEnum.IN.val);
		transactionDetailService.save(tdsParam1);
		//加商家财产余额
		PropertyInfoDOEiditView infoDoView1 = new PropertyInfoDOEiditView();
		infoDoView1.setUserNum(param.getSideUserNum());
		infoDoView1.setBalance(new BigDecimal(param.getTotalAmount()));
		infoDoView1.setGmtModified(new Date());
		propertyInfoDOMapperExtend.updatePropertyInfoAddBalance(infoDoView1);
		
		//发异步消息更新买单状态
		String []bizIds = param.getBizIds().split(",");
		for(int i = 0 ; i < bizIds.length ; i++){
			payOrderTransactionMainServiceImpl.sendNotice(Long.valueOf(bizIds[i]));
		}
		
		
		return ResultCode.SUCCESS;
	}
	
	@Override
	@Transactional
	public int balancePayPoint(BalancePayDataParam param) {
		//校验余额
		//根据系统参数获取充值比例
		//减财产余额、加财产积分
		//新增余额充值积分交易明细
		//新增积分交易明细
		int retCode = propertyInfoService.validateBalance(param.getUserNum(), param.getAccount());
		if (retCode != ResultCode.SUCCESS) {
			return retCode;
		}
		
		//获取余额冲积分比例，设置充值积分
		String property_key = PropertyType.MEMBER_BALANCE_PAY_POINT_SCALE;
		Byte transactionType = param.getMemberTransactionTypeEnum().getValue();
		if(param.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)){
			property_key = PropertyType.MERCHANT_BALANCE_PAY_POINT_SCALE;
			transactionType = param.getMerchantTransactionTypeEnum().getValue();
		}
		String payScale = propertyService.getValue(property_key);
		if("".equals(payScale)){
			
		}
		double dPayMoney = new Double(param.getTotalAmount()).doubleValue();
		double dPayScale = new Double(payScale).doubleValue();
		double point = dPayMoney * dPayScale;
		
		//减财产余额
		PropertyInfoDOEiditView infoDoView1 = new PropertyInfoDOEiditView();
		infoDoView1.setUserNum(param.getUserNum());
		infoDoView1.setBalance(new BigDecimal(param.getTotalAmount()));
		infoDoView1.setGmtModified(new Date());
		propertyInfoDOMapperExtend.updatePropertyInfoMinusBalance(infoDoView1);
		//加财产积分
		infoDoView1.setPoint(new BigDecimal(point));
		propertyInfoDOMapperExtend.updatePropertyInfoAddPoint(infoDoView1);
		
		String num = StringUtil.getRandomNum("");
		
		//新增交易明细
		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		tdsParam.setTitle(param.getTitle());
		tdsParam.setTransactionNum(num);
		tdsParam.setUserNum(param.getUserNum());
		tdsParam.setTransactionType(transactionType);
		tdsParam.setTransactionAccount(param.getAccount());
		tdsParam.setTransactionAccountType(TransactionPayTypeEnum.BALANCE.val);
		tdsParam.setAmount(new BigDecimal(param.getTotalAmount()));
		tdsParam.setBizId(Long.valueOf(param.getBizIds()));
		tdsParam.setDirection(PropertyInfoDirectionEnum.OUT.val);
		transactionDetailService.save(tdsParam);
		
		//新增积分明细
		PointDetailSaveDataParam pdsParam = new PointDetailSaveDataParam();
		pdsParam.setTitle(param.getTitle());
		pdsParam.setPointNum(num);
		pdsParam.setUserNum(param.getUserNum());
		pdsParam.setPointType(transactionType);
		pdsParam.setPoint(new BigDecimal(point));
		pdsParam.setDirection(PropertyInfoDirectionEnum.IN.val);
		pdsParam.setRemark("");
		pointDetailService.save(pdsParam);
		
		return ResultCode.SUCCESS;
	}


}
