package com.lawu.eshop.statistics.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.constants.PropertyType;
import com.lawu.eshop.statistics.service.CommonPropertyService;
import com.lawu.eshop.statistics.service.PropertySrvPropertyService;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月24日 下午7:42:46
 *
 */
@Service
public class CommonPropertyServiceImpl implements CommonPropertyService {

	@Autowired
	private PropertySrvPropertyService propertyService;

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, BigDecimal> getCommissionPropertys() {
		Result result = propertyService.getValue(PropertyType.ad_commission_1);
		String ad_commission_1 = result.getModel().toString();
		if("".equals(ad_commission_1)){
			ad_commission_1 = PropertyType.ad_commission_1_default;
		}
		result = propertyService.getValue(PropertyType.ad_commission_2);
		String ad_commission_2 = result.getModel().toString();
		if("".equals(ad_commission_2)){
			ad_commission_2 = PropertyType.ad_commission_2_default;
		}
		result = propertyService.getValue(PropertyType.ad_commission_3);
		String ad_commission_3 = result.getModel().toString();
		if("".equals(ad_commission_3)){
			ad_commission_3 = PropertyType.ad_commission_3_default;
		}
		result = propertyService.getValue(PropertyType.love_account_scale);
		String love_account_scale = result.getModel().toString();
		if ("".equals(love_account_scale)) {
			love_account_scale = PropertyType.love_account_scale_default;
		}
		double d_love_account_scale = Double.valueOf(love_account_scale).doubleValue();
		double d_acture_in = 1 - d_love_account_scale;	//用户实际进账比例：1-爱心账户比例
		
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		map.put("ad_commission_1", new BigDecimal(ad_commission_1));			//上1级提成比例
		map.put("ad_commission_2", new BigDecimal(ad_commission_2));			//上2级提成比例
		map.put("ad_commission_3", new BigDecimal(ad_commission_3));			//上3级提成比例
		map.put("acture_in_scale", new BigDecimal(String.valueOf(d_acture_in)));//实际收入比例
		map.put("love_account_scale", new BigDecimal(love_account_scale));		//爱心账户比例
		
		return map;
	}
	
	
}
