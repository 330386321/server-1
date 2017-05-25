package com.lawu.eshop.statistics.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.param.CommissionJobParam;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.order.dto.ShoppingOrderCommissionDTO;
import com.lawu.eshop.property.constants.LoveTypeEnum;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.statistics.service.CommissionCommonService;
import com.lawu.eshop.statistics.service.CommonPropertyService;
import com.lawu.eshop.statistics.service.PropertySrvService;
import com.lawu.eshop.statistics.service.SaleAndVolumeCommissionService;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.dto.CommissionInvitersUserDTO;

@Service
public class SaleAndVolumeCommissionServiceImpl implements SaleAndVolumeCommissionService {

	private static Logger logger = LoggerFactory.getLogger(SaleAndVolumeCommissionServiceImpl.class);

	@Autowired
	private CommonPropertyService commonPropertyService;
	@Autowired
	private CommissionCommonService userCommonService;
	@Autowired
	private PropertySrvService propertySrvService;

	/**
	 * // 查询订单相关用户商家的上级邀请关系 //
	 * 计算提成：实际提成(算等级)=actualMoney*(实际提成(4‰)+提成幅度(0.0005)*等级)*0.997 // //
	 * 第1级提成:actualMoney*(4‰+0.005*level)*0.997，进余额--每升级一个等级提成比例+0.0005 //
	 * 第2级提成：actualMoney*(3‰+0.005*level)*0.997，进余额--每升级一个等级提成比例+0.0005 //
	 * 第3级提成：actualMoney*(1‰+0.005*level)*0.997 ，若A为用户进余额，若A为商家进广告积分
	 */
	@Override
	public void commission(List<Long> successOrderIds, List<ShoppingOrderCommissionDTO> orders, String msg) {
		if (orders != null && orders.size() > 0) {

			Map<String, BigDecimal> property = commonPropertyService.getSaleCommissionPropertys();
			BigDecimal sale_commission_add_scope = property.get("sale_commission_add_scope");// 每上升一个级别提成的幅度
			BigDecimal loveAccountScale = property.get("love_account_scale");// 爱心账户比例
			BigDecimal actualCommissionScope = property.get("acture_in_scale");// 实际提成比例=1-爱心账户(0.003)

			for (ShoppingOrderCommissionDTO order : orders) {
				
				logger.info("["+msg+"]，订单ID="+order.getId()+"，用户编号="+order.getMemberNum()+"，商家编号="+order.getMerchantNum()+"，金额="+order.getActualAmount());
				
				if((order.getMemberNum() == null || "".equals(order.getMemberNum())) 
					&& (order.getMerchantNum() == null || "".equals(order.getMerchantNum()))){
					logger.error("["+msg+"]查询未计算提成数据时用户编号和商家编号均为空！orderId="+order.getId());
					continue;
				}
				
				// 查询买家和卖家的上3级推荐
				List<CommissionInvitersUserDTO> memberInviters = userCommonService.selectHigherLevelInviters(order.getMemberNum(), 3, true);
				List<CommissionInvitersUserDTO> merchantInviters = userCommonService.selectHigherLevelInviters(order.getMerchantNum(), 3, true);

				if ((memberInviters == null || memberInviters.isEmpty())
						&& (merchantInviters == null || merchantInviters.isEmpty())) {// 均无上限，直接修改为已算提成
					successOrderIds.add(order.getId());
					continue;
				}
				
				int retCode1 = ResultCode.FAIL;
				if (memberInviters != null && !memberInviters.isEmpty() && memberInviters.size() > 0) {
					int m = 0;
					for (int i = 0; i < memberInviters.size(); i++) {
						CommissionJobParam param = new CommissionJobParam();
						param.setUserNum(memberInviters.get(i).getUserNum());
						param.setBizId(order.getId());
						BigDecimal actualMoney = order.getActualAmount();

						BigDecimal level = new BigDecimal(memberInviters.get(i).getLevel());// 等级
						BigDecimal sale_commission = null;
						if (i == 0) {
							sale_commission = property.get("sale_commission_1");

						} else if (i == 1) {
							sale_commission = property.get("sale_commission_2");

						} else if (i == 2) {
							param.setLast(true);
							sale_commission = property.get("sale_commission_3");
						}

						BigDecimal actualCommission = sale_commission.add(sale_commission_add_scope.multiply(level.subtract(new BigDecimal("1"))));//没升一个级别+0.005
						BigDecimal actureMoneyIn = null;
						BigDecimal actureLoveIn = null;
						if (i == 2) {
							actureMoneyIn = actualMoney.multiply(actualCommission).setScale(6, BigDecimal.ROUND_HALF_UP);// 第三级进积分，无爱心账户
						} else {
							actureMoneyIn = actualMoney.multiply(actualCommission).multiply(actualCommissionScope).setScale(6, BigDecimal.ROUND_HALF_UP);// 实际所得余额
							actureLoveIn = actualMoney.multiply(actualCommission).multiply(loveAccountScale).setScale(6, BigDecimal.ROUND_HALF_UP);// 爱心账户
						
							//如果计算出爱心账户为0.000000时默认赋值0.000001
							if(actureLoveIn.compareTo(BigDecimal.ZERO) == 0){
								actureLoveIn = new BigDecimal("0.000001");
							}
						}
						
						//如果计算出实际提成为0.000000时默认赋值0.000001
						if(actureMoneyIn.compareTo(BigDecimal.ZERO) == 0){
							actureMoneyIn = new BigDecimal("0.000001");
						}
						
						param.setActureMoneyIn(actureMoneyIn);
						param.setActureLoveIn(actureLoveIn);

						if (memberInviters.get(i).getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)) {
							param.setTypeVal(MemberTransactionTypeEnum.SALES_COMMISSION.getValue());
							param.setTypeName(MemberTransactionTypeEnum.SALES_COMMISSION.getName());
						} else if (memberInviters.get(i).getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
							param.setTypeVal(MerchantTransactionTypeEnum.SALES_COMMISSION.getValue());
							param.setTypeName(MerchantTransactionTypeEnum.SALES_COMMISSION.getName());
						}
						param.setLoveTypeVal(LoveTypeEnum.SALES_COMMISSION.getValue());
						param.setLoveTypeName(LoveTypeEnum.SALES_COMMISSION.getName());

						logger.info("["+msg+"-memberInviters],actualCommission="+actualCommission+",actualCommissionScope="+actualCommissionScope+",loveAccountScale="+loveAccountScale);
						logger.info("["+msg+"-memberInviters],actualMoney="+actualMoney+",actureMoneyIn="+param.getActureMoneyIn()+",actureLoveIn="+param.getActureLoveIn());
						
						retCode1 = propertySrvService.calculation(param);
						if (ResultCode.SUCCESS == retCode1) {
							m++;
						}
					}
					// 所有上线提成计算成功才算成功
					if (m == memberInviters.size()) {
						retCode1 = ResultCode.SUCCESS;
					}
				}else{
					retCode1 = ResultCode.SUCCESS;
				}
				
				int retCode2 = ResultCode.FAIL;
				if (merchantInviters != null && !merchantInviters.isEmpty() && merchantInviters.size() > 0) {
					int m = 0;
					for (int i = 0; i < merchantInviters.size(); i++) {
						CommissionJobParam param = new CommissionJobParam();
						param.setUserNum(merchantInviters.get(i).getUserNum());
						param.setBizId(order.getId());
						BigDecimal actualMoney = order.getActualAmount();

						BigDecimal level = new BigDecimal(merchantInviters.get(i).getLevel());// 等级
						BigDecimal sale_commission = null;
						if (i == 0) {
							sale_commission = property.get("sale_commission_1");

						} else if (i == 1) {
							sale_commission = property.get("sale_commission_2");

						} else if (i == 2) {
							param.setLast(true);
							sale_commission = property.get("sale_commission_3");
						}

						BigDecimal actualCommission = sale_commission.add(sale_commission_add_scope.multiply(level.subtract(new BigDecimal("1"))));
						BigDecimal actureMoneyIn = null;
						BigDecimal actureLoveIn = null;
						if (i == 2) {
							actureMoneyIn = actualMoney.multiply(actualCommission).setScale(6, BigDecimal.ROUND_HALF_UP);// 第三级进积分，无爱心账户
						} else {
							actureMoneyIn = actualMoney.multiply(actualCommission).multiply(actualCommissionScope).setScale(6, BigDecimal.ROUND_HALF_UP);// 实际所得余额
							actureLoveIn = actualMoney.multiply(actualCommission).multiply(loveAccountScale).setScale(6, BigDecimal.ROUND_HALF_UP);// 爱心账户
						
							//如果计算出爱心账户为0.000000时默认赋值0.000001
							if(actureLoveIn.compareTo(BigDecimal.ZERO) == 0){
								actureLoveIn = new BigDecimal("0.000001");
							}
						}
						
						//如果计算出实际提成为0.000000时默认赋值0.000001
						if(actureMoneyIn.compareTo(BigDecimal.ZERO) == 0){
							actureMoneyIn = new BigDecimal("0.000001");
						}
						
						param.setActureMoneyIn(actureMoneyIn);
						param.setActureLoveIn(actureLoveIn);

						if (merchantInviters.get(i).getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)) {
							param.setTypeVal(MemberTransactionTypeEnum.VOLUME_COMMISSION.getValue());
							param.setTypeName(MemberTransactionTypeEnum.VOLUME_COMMISSION.getName());
						} else if (merchantInviters.get(i).getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
							param.setTypeVal(MerchantTransactionTypeEnum.VOLUME_COMMISSION.getValue());
							param.setTypeName(MerchantTransactionTypeEnum.VOLUME_COMMISSION.getName());
						}
						param.setLoveTypeVal(LoveTypeEnum.VOLUME_COMMISSION.getValue());
						param.setLoveTypeName(LoveTypeEnum.VOLUME_COMMISSION.getName());

						logger.info("["+msg+"-merchantInviters],actualCommission="+actualCommission+",actualCommissionScope="+actualCommissionScope+",loveAccountScale="+loveAccountScale);
						logger.info("["+msg+"-merchantInviters],actualMoney="+actualMoney+",actureMoneyIn="+param.getActureLoveIn()+",actureLoveIn="+param.getActureLoveIn());
						
						retCode2 = propertySrvService.calculation(param);
						if (ResultCode.SUCCESS == retCode2) {
							m++;
						}
					}
					// 所有上线提成计算成功才算成功
					if (m == merchantInviters.size()) {
						retCode2 = ResultCode.SUCCESS;
					}
				}else{
					retCode2 = ResultCode.SUCCESS;
				}

				// 修改订单是否计算提成状态
				if (ResultCode.SUCCESS == retCode1 && ResultCode.SUCCESS == retCode2) {
					successOrderIds.add(order.getId());
				} else {
					logger.error(msg + "提成计算上级收益时返回错误,orderId={},retCode1={},retCode2={}", order.getId(), retCode1,
							retCode2);
				}
			}
		}

	}

}
