package com.lawu.eshop.jobs.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lawu.eshop.property.dto.AdCommissionResultDTO;
import com.lawu.eshop.property.param.CommissionResultParam;
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
	@Autowired
	private CommissionUtilImpl commissionUtilImpl;

	@Override
	public void executeAutoClickAdCommission(MemberAdRecodeCommissionDTO memberAdRecodeCommissionDTO) {

		Map<String, BigDecimal> property = commonPropertyService.getAdCommissionPropertys();
		BigDecimal loveAccountScale = property.get("love_account_scale");// 爱心账户比例
		BigDecimal actualCommissionScope = property.get("acture_in_scale");// 实际提成比例=1-爱心账户(0.003)
		BigDecimal adCommission0 = property.get("ad_commission_0");

		MemberAdRecodeCommissionDTO dto = memberAdRecodeCommissionDTO;
		if(dto.getMemberNum() == null || "".equals(dto.getMemberNum())){
			logger.error("查询未计算提成的点广告记录用户编号为空！");
			throw new RuntimeException();
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

				CommissionResultParam commissionResultparam = new CommissionResultParam();
				commissionResultparam.setBeforeMoney(clickMoney);
				commissionResultparam.setCommission0(adCommission0);
				commissionResultparam.setCurrentCommission(sale_commission);
				commissionResultparam.setActualCommissionScope(actualCommissionScope);
				commissionResultparam.setLoveAccountScale(loveAccountScale);
				commissionResultparam.setDept(i);
				AdCommissionResultDTO rntDTO = commissionUtilImpl.getCommissionResult(commissionResultparam);
				param.setActureMoneyIn(rntDTO.getActureMoneyIn());
				param.setActureLoveIn(rntDTO.getActureLoveIn());

				if(inviters.get(i).getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
					param.setTypeVal(MemberTransactionTypeEnum.LOWER_INCOME.getValue());
					param.setTypeName(MemberTransactionTypeEnum.LOWER_INCOME.getName());
				}else if(inviters.get(i).getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)){
					param.setTypeVal(MerchantTransactionTypeEnum.LOWER_INCOME.getValue());
					param.setTypeName(MerchantTransactionTypeEnum.LOWER_INCOME.getName());
				}
				param.setLoveTypeVal(LoveTypeEnum.AD_COMMISSION.getValue());
				param.setLoveTypeName(LoveTypeEnum.AD_COMMISSION.getName());

				logger.info("点广告比例：提成基础金额比例={},提成比例={},所得比例={},爱心比例={},所得：实际收益={},爱心账户={}",adCommission0,sale_commission,actualCommissionScope,loveAccountScale,param.getActureMoneyIn(),param.getActureLoveIn());

				try {
					retCode = propertySrvService.calculation(param);
					if (ResultCode.SUCCESS == retCode) {
						m++;
					}
				}catch (Exception e){
					throw new RuntimeException();
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
				throw new RuntimeException();
			}
		} else {
			logger.error("广告点击提成计算上级收益时返回错误,memberAdRecordId={},retCode={}", dto.getId(), retCode);
		}

	}

}
