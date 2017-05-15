package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.pay.handle.AlipayBusinessHandle;
import com.lawu.eshop.pay.handle.WxpayBusinessHandle;
import com.lawu.eshop.pay.sdk.weixin.sdk.common.JsonResult;
import com.lawu.eshop.property.constants.*;
import com.lawu.eshop.property.param.*;
import com.lawu.eshop.property.srv.PropertySrvConfig;
import com.lawu.eshop.property.srv.domain.FreezeDO;
import com.lawu.eshop.property.srv.domain.FreezeDOExample;
import com.lawu.eshop.property.srv.domain.extend.FreezeDOView;
import com.lawu.eshop.property.srv.domain.extend.PropertyInfoDOEiditView;
import com.lawu.eshop.property.srv.mapper.FreezeDOMapper;
import com.lawu.eshop.property.srv.mapper.extend.FreezeDOMapperExtend;
import com.lawu.eshop.property.srv.mapper.extend.PropertyInfoDOMapperExtend;
import com.lawu.eshop.property.srv.service.OrderService;
import com.lawu.eshop.property.srv.service.PropertyService;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
import com.lawu.eshop.utils.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

	private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
			
	@Autowired
	private TransactionDetailService transactionDetailService;
	@Autowired
	private PropertyInfoDOMapperExtend propertyInfoDOMapperExtend;
	@Autowired
	private FreezeDOMapper freezeDOMapper;
	@Autowired
	private PropertyService propertyService;
	@Autowired
	private FreezeDOMapperExtend freezeDOMapperExtend;

	@Autowired
	private PropertySrvConfig propertySrvConfig;

	@Autowired
	@Qualifier("payOrderTransactionMainServiceImpl")
	private TransactionMainService<Reply> payOrderTransactionMainServiceImpl;

	@Autowired
	@Qualifier("shoppingOrderPaymentTransactionMainServiceImpl")
	private TransactionMainService<Reply> shoppingOrderPaymentTransactionMainServiceImpl;

	/**
	 * 商品订单第三方付款后回调处理：新增会员交易记录，<更新订单状态>
	 * 
	 * 此时暂不保存商家交易明细和加余额，确认收货后处理
	 * 
	 * @param param
	 * @return
	 */
	@Override
	public int doHandleOrderPayNotify(NotifyCallBackParam param) {
		
		boolean isPay = transactionDetailService.verifyOrderIsPaySuccess(param);
		if(isPay){
			logger.info("重复回调(判断幂等)");
			return ResultCode.SUCCESS;
		}
		
		// 新增会员交易记录
		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		tdsParam.setTitle(TransactionTitleEnum.ORDER_PAY.val);
		tdsParam.setUserNum(param.getUserNum());
		tdsParam.setTransactionAccount(param.getBuyerLogonId());
		tdsParam.setTransactionType(MemberTransactionTypeEnum.PAY_ORDERS.getValue());
		tdsParam.setTransactionAccountType(param.getTransactionPayTypeEnum().val);
		tdsParam.setAmount(new BigDecimal(param.getTotalFee()));
		tdsParam.setBizId(param.getBizIds());
		tdsParam.setThirdTransactionNum(param.getTradeNo());
		tdsParam.setDirection(PropertyInfoDirectionEnum.OUT.val);
		tdsParam.setBizNum(param.getOutTradeNo());
		transactionDetailService.save(tdsParam);

		/*
		 * 更新订单状态 发送MQ消息更新订单状态
		 */
		shoppingOrderPaymentTransactionMainServiceImpl.sendNotice(tdsParam.getId());

		return ResultCode.SUCCESS;
	}

	@Override
	@Transactional
	public int doHandlePayOrderNotify(NotifyCallBackParam param) {

		boolean isPay = transactionDetailService.verifyOrderIsPaySuccess(param);
		if(isPay){
			logger.info("重复回调(判断幂等)");
			return ResultCode.SUCCESS;
		}
		
		// 新增会员交易记录，加商家交易明细，加商家财产余额，<更新订单状态>

		// 新增会员交易明细
		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		tdsParam.setTitle(TransactionTitleEnum.PAY.val);
		tdsParam.setUserNum(param.getUserNum());
		tdsParam.setTransactionAccount(param.getBuyerLogonId());
		tdsParam.setTransactionType(MemberTransactionTypeEnum.PAY.getValue());
		tdsParam.setTransactionAccountType(param.getTransactionPayTypeEnum().val);
		tdsParam.setAmount(new BigDecimal(param.getTotalFee()));
		tdsParam.setDirection(PropertyInfoDirectionEnum.OUT.val);
		tdsParam.setBizId(param.getBizIds());
		tdsParam.setThirdTransactionNum(param.getTradeNo());
		tdsParam.setBizNum(param.getOutTradeNo());
		transactionDetailService.save(tdsParam);

		// 新增商家交易明细
		TransactionDetailSaveDataParam tdsParam1 = new TransactionDetailSaveDataParam();
		tdsParam1.setTitle(TransactionTitleEnum.PAY.val);
		tdsParam1.setUserNum(param.getSideUserNum());
		tdsParam1.setTransactionAccount(param.getBuyerLogonId());
		tdsParam1.setTransactionType(MerchantTransactionTypeEnum.PAY.getValue());
		tdsParam1.setTransactionAccountType(param.getTransactionPayTypeEnum().val);
		tdsParam1.setAmount(new BigDecimal(param.getTotalFee()));
		tdsParam1.setBizId(param.getBizIds());
		tdsParam1.setThirdTransactionNum(param.getTradeNo());
		tdsParam1.setDirection(PropertyInfoDirectionEnum.IN.val);
		tdsParam.setBizNum(param.getOutTradeNo());
		transactionDetailService.save(tdsParam1);

		// 加商家财产余额
		PropertyInfoDOEiditView infoDoView = new PropertyInfoDOEiditView();
		infoDoView.setUserNum(param.getSideUserNum());
		infoDoView.setBalance(new BigDecimal(param.getTotalFee()));
		infoDoView.setGmtModified(new Date());
		propertyInfoDOMapperExtend.updatePropertyInfoAddBalance(infoDoView);

		// 更新订单状态
		payOrderTransactionMainServiceImpl.sendNotice(Long.valueOf(param.getBizIds()));

		return ResultCode.SUCCESS;
	}

	@Transactional
	@Override
	public int comfirmDelivery(OrderComfirmDataParam param) {
		/*
		 * 保证MQ事务的幂等性
		 */
		FreezeDOExample freezeDOExample = new FreezeDOExample();
		FreezeDOExample.Criteria criteria = freezeDOExample.createCriteria();
		criteria.andBizIdEqualTo(Long.valueOf(param.getBizId()));
		int count = freezeDOMapper.countByExample(freezeDOExample);
		if (count > 0) {
			return ResultCode.SUCCESS;
		}

		FreezeDO freezeDO = new FreezeDO();
		freezeDO.setUserNum(param.getUserNum());
		freezeDO.setMoney(new BigDecimal(param.getTotalOrderMoney()));
		freezeDO.setOriginalMoney(new BigDecimal(param.getTotalOrderMoney()));
		freezeDO.setFundType(FreezeTypeEnum.PRODUCT_ORDER.val);
		freezeDO.setBizId(Long.valueOf(param.getBizId()));
		freezeDO.setStatus(FreezeStatusEnum.FREEZE.val);
		freezeDO.setGmtCreate(new Date());
		String days = propertyService.getValue(PropertyType.PRODUCT_ORDER_MONEY_FREEZE_DAYS);
		if ("".equals(days)) {
			days = PropertyType.PRODUCT_ORDER_MONEY_FREEZE_DAYS_DEFAULT;
		}
		freezeDO.setDays(Integer.valueOf(days));
		freezeDOMapper.insertSelective(freezeDO);

		return ResultCode.SUCCESS;
	}

	@Override
	@Transactional
	public int doRefundScopeInside(OrderRefundDataParam param) throws Exception {

		// 商家同意订单退款（确认收货后7天内）,区分余额支付和第三方支付
		// （校验：区分是否是最后一次退款，校验冻结资金记录是否存在和数量，退款金额不能大于冻结金额）
		// 余额支付：处理商家冻结资金，新增会员交易订单退款交易记录，加会员财产余额
		// 第三方支付：处理商家冻结资金，新增会员交易订单退款交易记录，原路退回会员支付账户
		// <异步通知修改订单状态>
		if (OrderRefundStatusEnum.FINISH.val.equals(param.getOrderRefundStatusEnum().val)) {
			FreezeDOExample example = new FreezeDOExample();
			if (param.isLast()) {
				FreezeDO freezeDO = new FreezeDO();
				freezeDO.setMoney(new BigDecimal("0"));
				freezeDO.setStatus(FreezeStatusEnum.RELEASE.val);
				freezeDO.setGmtModified(new Date());
				example.createCriteria().andUserNumEqualTo(param.getUserNum())
						.andFundTypeEqualTo(FreezeTypeEnum.PRODUCT_ORDER.val)
						.andBizIdEqualTo(Long.valueOf(param.getOrderId()))
						.andStatusEqualTo(FreezeStatusEnum.FREEZE.val);
				freezeDOMapper.updateByExample(freezeDO, example);
			} else {
				// 校验退款金额不能大于冻结金额
				example.createCriteria().andUserNumEqualTo(param.getUserNum())
						.andFundTypeEqualTo(FreezeTypeEnum.PRODUCT_ORDER.val)
						.andBizIdEqualTo(Long.valueOf(param.getOrderId()));
				List<FreezeDO> freezeDOS = freezeDOMapper.selectByExample(example);
				Long freezeId = 0L;
				if (freezeDOS == null || freezeDOS.isEmpty()) {
					return ResultCode.FREEZE_NULL;
				} else if (freezeDOS.size() > 1) {
					return ResultCode.FREEZE_ROWS_OUT;
				} else {
					FreezeDO freeze = freezeDOS.get(0);
					if (freeze.getMoney().compareTo(new BigDecimal(param.getRefundMoney())) < 0) {
						return ResultCode.FREEZE_MONEY_LESS_REFUND_MONEY;
					}
					freezeId = freeze.getId();
				}

				FreezeDOView freezeDoView = new FreezeDOView();
				freezeDoView.setId(freezeId);
				freezeDoView.setMoney(new BigDecimal(param.getRefundMoney()));
				freezeDoView.setGmtModified(new Date());
				freezeDOMapperExtend.updateMinusMoney(freezeDoView);
			}
		}

		// 新增会员订单退款交易记录
		TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
		tdsParam.setTitle(TransactionTitleEnum.ORDER_PAY_REFUND.val);
		tdsParam.setUserNum(param.getSideUserNum());
		tdsParam.setTransactionType(MemberTransactionTypeEnum.REFUND_ORDERS.getValue());
		tdsParam.setTransactionAccount("");
		tdsParam.setTransactionAccountType(param.getTransactionPayTypeEnum().val);
		tdsParam.setAmount(new BigDecimal(param.getRefundMoney()));
		tdsParam.setBizId(param.getOrderItemIds().split(",")[0]);
		tdsParam.setDirection(PropertyInfoDirectionEnum.IN.val);
		tdsParam.setBizNum(param.getTradeNo());
		transactionDetailService.save(tdsParam);

		JsonResult jsonResult = new JsonResult();
		if (TransactionPayTypeEnum.BALANCE.val.equals(param.getTransactionPayTypeEnum().val)) {
			// 加会员财产余额
			PropertyInfoDOEiditView infoDoView = new PropertyInfoDOEiditView();
			infoDoView.setUserNum(param.getSideUserNum());
			infoDoView.setBalance(new BigDecimal(param.getRefundMoney()));
			infoDoView.setGmtModified(new Date());
			propertyInfoDOMapperExtend.updatePropertyInfoAddBalance(infoDoView);
			jsonResult.setSuccess(true);

		} else {
			ThirdPayRefundParam rparam = new ThirdPayRefundParam();
			rparam.setRefundId(StringUtil.getRandomNumAppend(param.getOrderItemIds().replaceAll(",", "")));
			rparam.setRefundMoney(param.getRefundMoney());
			rparam.setTotalMoney(rparam.getTotalMoney());
			rparam.setTradeNo(param.getTradeNo());
			if (TransactionPayTypeEnum.ALIPAY.val.equals(param.getTransactionPayTypeEnum().val)) {
				AliPayConfigParam aliPayConfigParam = new AliPayConfigParam();
				aliPayConfigParam.setAlipayRefundUrl(propertySrvConfig.getAlipayRefundUrl());
				aliPayConfigParam.setAlipayAppIdMember(propertySrvConfig.getAlipayAppIdMember());
				aliPayConfigParam.setAlipayPrivateKey(propertySrvConfig.getAlipayPrivateKey());
				aliPayConfigParam.setAlipayEdianMemberPublicKey(propertySrvConfig.getAlipayEdianMemberPublicKey());
				aliPayConfigParam.setAlipayPublicKey(propertySrvConfig.getAlipayPublicKey());
				aliPayConfigParam.setAlipayPartner(propertySrvConfig.getAlipayPartner());
				aliPayConfigParam.setAlipayHttpsVerifyUrl(propertySrvConfig.getAlipayHttpsVerifyUrl());
				aliPayConfigParam.setAlipaySignType(propertySrvConfig.getAlipaySignType());
				aliPayConfigParam.setAlipayInputCharset(propertySrvConfig.getAlipayInputCharset());
				AlipayBusinessHandle.refund(rparam, jsonResult,aliPayConfigParam);

			} else if (TransactionPayTypeEnum.WX.val.equals(param.getTransactionPayTypeEnum().val)) {
				WxPayConfigParam wxPayConfigParam = new WxPayConfigParam();
				wxPayConfigParam.setWxpayAppIdMember(propertySrvConfig.getWxpayAppIdMember());
				wxPayConfigParam.setWxpayMchIdMember(propertySrvConfig.getWxpayMchIdMember());
				wxPayConfigParam.setWxpayKey(propertySrvConfig.getWxpayKey());
				wxPayConfigParam.setWxpayAppId(propertySrvConfig.getWxpayAppId());
				wxPayConfigParam.setWxpayMchId(propertySrvConfig.getWxpayMchId());
				wxPayConfigParam.setWxpayCertLocalPathMember(propertySrvConfig.getWxpayCertLocalPathMember());
				wxPayConfigParam.setWxpayCertPasswordMember(propertySrvConfig.getWxpayCertPasswordMember());
				wxPayConfigParam.setWxpayCertBasePath(propertySrvConfig.getWxpayCertLocalPathMember());
				wxPayConfigParam.setWxpayRefundApi(propertySrvConfig.getWxpayRefundApi());
				wxPayConfigParam.setWxpayHttpsRequestClassName(propertySrvConfig.getWxpayHttpsRequestClassName());
				wxPayConfigParam.setWxpayKeyApp(propertySrvConfig.getWxpayKeyApp());
				WxpayBusinessHandle.refund(rparam, jsonResult,wxPayConfigParam);
			}
		}

		if (jsonResult.isSuccess()) {
			// TODO 更新订单item状态

		} else {
			throw new Exception(jsonResult.getMessage());
		}
		return ResultCode.SUCCESS;
	}

	/**
	 * 定时任务调用 确认收货后7天，订单冻结金额自动加入商家余额账户 :新增商家订单付款交易记录，释放冻结资金，加商家财产余额
	 */
	@Override
	public int comfirmReleaseFreeze(OrderReleaseFreezeParam param) {
		String[] userNums = param.getUserNums().split(",");
		String[] orderIds = param.getOrderIds().split(",");
		//String[] accounts = param.getAccounts().split(",");
		Byte[] payWays = param.getPayWays();
		FreezeDOExample example = new FreezeDOExample();
		List<String> finishOrderIds = new ArrayList<String>();//成功处理的订单ID
		List<FreezeDO> freezeDOS = null;
		for (int i = 0; i < userNums.length; i++) {
			example.clear();
			example.createCriteria().andUserNumEqualTo(userNums[i]).andFundTypeEqualTo(FreezeTypeEnum.PRODUCT_ORDER.val)
					.andBizIdEqualTo(Long.valueOf(orderIds[i])).andStatusEqualTo(FreezeStatusEnum.FREEZE.val);
			freezeDOS = freezeDOMapper.selectByExample(example);
			if (freezeDOS != null && !freezeDOS.isEmpty() && freezeDOS.size() == 1) {
				FreezeDO freeze = freezeDOS.get(0);
				Long freezeId = freeze.getId();

				// 新增商家订单付款交易记录
				TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
				tdsParam.setTitle(MerchantTransactionTypeEnum.ORDER.getName());
				tdsParam.setUserNum(userNums[i]);
				tdsParam.setTransactionType(MerchantTransactionTypeEnum.ORDER.getValue());
				//tdsParam.setTransactionAccount(accounts[i]);
				tdsParam.setTransactionAccountType(payWays[i]);
				tdsParam.setAmount(freeze.getMoney());
				tdsParam.setBizId(orderIds[i]);
				tdsParam.setDirection(PropertyInfoDirectionEnum.IN.val);
				transactionDetailService.save(tdsParam);

				// 释放冻结资金
				FreezeDO freezeDO = new FreezeDO();
				freezeDO.setStatus(FreezeStatusEnum.RELEASE.val);
				freezeDO.setGmtModified(new Date());
				example.createCriteria().andIdEqualTo(freezeId);
				freezeDOMapper.updateByExample(freezeDO, example);

				// 加会员财产余额
				PropertyInfoDOEiditView infoDoView = new PropertyInfoDOEiditView();
				infoDoView.setUserNum(userNums[i]);
				infoDoView.setBalance(freeze.getMoney());
				infoDoView.setGmtModified(new Date());
				propertyInfoDOMapperExtend.updatePropertyInfoAddBalance(infoDoView);

				finishOrderIds.add(orderIds[i]);
			}
		}

		return ResultCode.SUCCESS;
	}

	@Override
	@Transactional
	public int comfirmSysJob(OrderSysJobParam param) {
		String[] userNums = param.getUserNums().split(",");
		String[] orderIds = param.getOrderIds().split(",");
		//String[] accounts = param.getAccounts().split(",");
		Byte[] payWays = param.getPayWays();
		String[] orderActualMoneys = param.getOrderActualMoney().split(",");
		for (int i = 0; i < userNums.length; i++) {
			// 新增商家订单付款交易记录
			TransactionDetailSaveDataParam tdsParam = new TransactionDetailSaveDataParam();
			tdsParam.setTitle(MerchantTransactionTypeEnum.ORDER.getName());
			tdsParam.setUserNum(userNums[i]);
			tdsParam.setTransactionType(MerchantTransactionTypeEnum.ORDER.getValue());
			//tdsParam.setTransactionAccount(accounts[i]);
			tdsParam.setTransactionAccountType(payWays[i]);
			tdsParam.setAmount(new BigDecimal(orderActualMoneys[i]));
			tdsParam.setBizId(orderIds[i]);
			tdsParam.setDirection(PropertyInfoDirectionEnum.IN.val);
			transactionDetailService.save(tdsParam);

			// 加会员财产余额
			PropertyInfoDOEiditView infoDoView = new PropertyInfoDOEiditView();
			infoDoView.setUserNum(userNums[i]);
			infoDoView.setBalance(new BigDecimal(orderActualMoneys[i]));
			infoDoView.setGmtModified(new Date());
			propertyInfoDOMapperExtend.updatePropertyInfoAddBalance(infoDoView);
		}
		return ResultCode.SUCCESS;
	}

}
