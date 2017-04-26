package com.lawu.eshop.statistics.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.dto.MemberAdRecodeCommissionDTO;
import com.lawu.eshop.ad.param.AdCommissionJobParam;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.statistics.service.AdSrvService;
import com.lawu.eshop.statistics.service.ClickAdCommissionService;
import com.lawu.eshop.statistics.service.CommissionCommonService;
import com.lawu.eshop.statistics.service.CommonPropertyService;
import com.lawu.eshop.statistics.service.PropertySrvService;

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
		List<MemberAdRecodeCommissionDTO> ads = adService.getNoneCommissionAds();

		// 获取提成比例
		if (ads != null && ads.size() > 0) {

			Map<String, BigDecimal> property = commonPropertyService.getCommissionPropertys();

			for (MemberAdRecodeCommissionDTO dto : ads) {
				// 查询用户上3级推荐
				List<String> inviters = userCommonService.selectHigherLevelInviters(dto.getMemberNum(), 3);
				if (inviters == null || inviters.isEmpty() || inviters.size() < 1) {
					continue;
				}
				
				int retCode = ResultCode.SUCCESS;
				for (int i = 0; i < inviters.size(); i++) {
					AdCommissionJobParam param = new AdCommissionJobParam();
					param.setUserNum(dto.getMemberNum());
					param.setMemberAdRecordId(dto.getId());
					BigDecimal clickMoney = param.getActureMoneyIn();
					if (i == 0) {
						// 第1级提成:0.5*16%*0.997，进余额
						BigDecimal actureMoneyIn = clickMoney.multiply(property.get("ad_commission_1")).multiply(property.get("acture_in_scale"));// 实际所得余额
						BigDecimal actureLoveIn = clickMoney.multiply(property.get("ad_commission_1")).multiply(property.get("love_account_scale"));// 爱心账户
						param.setActureLoveIn(actureMoneyIn);
						param.setActureLoveIn(actureLoveIn);
						retCode = propertySrvService.calculation(param);
					} else if (i == 1) {
						//第2级提成：0.5*3%*0.997，进余额
						BigDecimal actureMoneyIn = clickMoney.multiply(property.get("ad_commission_2")).multiply(property.get("acture_in_scale"));// 实际所得余额
						BigDecimal actureLoveIn = clickMoney.multiply(property.get("ad_commission_2")).multiply(property.get("love_account_scale"));// 爱心账户
						param.setActureLoveIn(actureMoneyIn);
						param.setActureLoveIn(actureLoveIn);
						retCode = propertySrvService.calculation(param);
					} else if (i == 2) {
						//第3级提成：0.5*1%，进积分
						param.setLast(true);
						BigDecimal actureMoneyIn = clickMoney.multiply(property.get("ad_commission_3")).multiply(property.get("acture_in_scale"));// 实际所得余额
						//BigDecimal actureLoveIn = clickMoney.multiply(property.get("ad_commission_3")).multiply(property.get("love_account_scale"));// 爱心账户
						param.setActureLoveIn(actureMoneyIn);
						//param.setActureLoveIn(actureLoveIn);
						retCode = propertySrvService.calculation(param);
					}
				}
				
				//修改用户点击广告记录状态为已计算提成
				if(ResultCode.SUCCESS == retCode){
					retCode = adService.updateMemberAdRecardStatus(dto.getId());
					if(ResultCode.SUCCESS != retCode){
						logger.error("广告点击提成修改用户点击记录状态时返回错误,memberAdRecordId={},retCode={}",dto.getId(),retCode);
					}
				}else{
					logger.error("广告点击提成计算上级收益时返回错误,memberAdRecordId={},retCode={}",dto.getId(),retCode);
				}
				
			}
		}

	}

}
