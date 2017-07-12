package com.lawu.eshop.jobs.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.dto.MemberAdRecodeCommissionDTO;
import com.lawu.eshop.ad.param.CommissionJobParam;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.LoveTypeEnum;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.jobs.service.AdSrvService;
import com.lawu.eshop.jobs.service.ClickAdCommissionService;
import com.lawu.eshop.jobs.service.CommissionCommonService;
import com.lawu.eshop.jobs.service.CommonPropertyService;
import com.lawu.eshop.jobs.service.PropertySrvService;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.dto.CommissionInvitersUserDTO;

@Service
public class ClickAdCommissionServiceImpl implements ClickAdCommissionService {

	private static Logger logger = LoggerFactory.getLogger(ClickAdCommissionServiceImpl.class);

	@Autowired
	private AdSrvService adService;
	@Autowired
	private CommissionCommonService userCommonService;
	@Autowired
	private CommonPropertyService commonPropertyService;
	@Autowired
	private PropertySrvService propertySrvService;

	@Override
	public void executeAutoClickAdCommission() {

		// 获取未计算提成的会员点击广告记录
		// 计算提成：
		//// 第1级提成:0.5*16%*0.997，进余额
		//// 第2级提成：0.5*3%*0.997，进余额
		// 第3级提成：0.5*1%，进积分

		List<MemberAdRecodeCommissionDTO> ads = adService.getNoneCommissionAds();

		// 获取提成比例
		if (ads != null && !ads.isEmpty()) {

			Map<String, BigDecimal> property = commonPropertyService.getAdCommissionPropertys();
			BigDecimal loveAccountScale = property.get("love_account_scale");// 爱心账户比例
			BigDecimal actualCommissionScope = property.get("acture_in_scale");// 实际提成比例=1-爱心账户(0.003)
			BigDecimal adCommission0 = property.get("ad_commission_0");

			for (MemberAdRecodeCommissionDTO dto : ads) {
				if(dto.getMemberNum() == null || "".equals(dto.getMemberNum())){
					logger.error("查询未计算提成的点广告记录，返回用户编号为空！");
					continue;
				}
				// 查询用户上3级推荐
				List<CommissionInvitersUserDTO> inviters = userCommonService.selectHigherLevelInviters(dto.getMemberNum(), 3, false);

				int retCode = ResultCode.FAIL;
				if (inviters != null && !inviters.isEmpty()) {
					int m = 0;
					for (int i = 0; i < inviters.size(); i++) {
						CommissionJobParam param = new CommissionJobParam();
						param.setUserNum(inviters.get(i).getUserNum());
						param.setBizId(dto.getId());
						param.setTempBidId(dto.getAdId());
						
						BigDecimal clickMoney = dto.getPoint();

						BigDecimal sale_commission = null;
						if (i == 0) {
							sale_commission = property.get("ad_commission_1");

						} else if (i == 1) {
							sale_commission = property.get("ad_commission_2");

						} else if (i == 2) {
							param.setLast(true);
							sale_commission = property.get("ad_commission_3");

						}
						BigDecimal actureMoneyIn;
						BigDecimal actureLoveIn = null;
						if(i == 2){
							actureMoneyIn = clickMoney.multiply(adCommission0).multiply(sale_commission).setScale(6, BigDecimal.ROUND_HALF_UP);// 第三级进积分，无爱心账户
						}else{
							actureMoneyIn = clickMoney.multiply(adCommission0).multiply(sale_commission).multiply(actualCommissionScope).setScale(6, BigDecimal.ROUND_HALF_UP);// 实际所得余额
							actureLoveIn = clickMoney.multiply(adCommission0).multiply(sale_commission).multiply(loveAccountScale).setScale(6, BigDecimal.ROUND_HALF_UP);// 爱心账户
						
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
						
						if(inviters.get(i).getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
							param.setTypeVal(MemberTransactionTypeEnum.LOWER_INCOME.getValue());
							param.setTypeName(MemberTransactionTypeEnum.LOWER_INCOME.getName());
						}else if(inviters.get(i).getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)){
							param.setTypeVal(MerchantTransactionTypeEnum.LOWER_INCOME.getValue());
							param.setTypeName(MerchantTransactionTypeEnum.LOWER_INCOME.getName());
						}
						param.setLoveTypeVal(LoveTypeEnum.AD_COMMISSION.getValue());
						param.setLoveTypeName(LoveTypeEnum.AD_COMMISSION.getName());
						
						logger.info("点广告比例：ad_commission_0={},commission={},actualCommissionScope={},loveAccountScale={}",adCommission0,sale_commission,actualCommissionScope,loveAccountScale);
						logger.info("点广告：actureMoneyIn={},actureLoveIn={}",param.getActureMoneyIn(),param.getActureLoveIn());
						
						retCode = propertySrvService.calculation(param);
						if (ResultCode.SUCCESS == retCode) {
							m++;
						}
					}
					// 所有上线提成计算成功才算成功
					if (m == inviters.size()) {
						retCode = ResultCode.SUCCESS;
					}
				} else {
					retCode = ResultCode.SUCCESS;
				}

				// 修改用户点击广告记录状态为已计算提成
				if (ResultCode.SUCCESS == retCode) {
					retCode = adService.updateMemberAdRecardStatus(dto.getId());
					if (ResultCode.SUCCESS != retCode) {
						logger.error("广告点击提成修改用户点击记录状态时返回错误,memberAdRecordId={},retCode={}", dto.getId(), retCode);
					}
				} else {
					logger.error("广告点击提成计算上级收益时返回错误,memberAdRecordId={},retCode={}", dto.getId(), retCode);
				}

			}
		}

	}

}