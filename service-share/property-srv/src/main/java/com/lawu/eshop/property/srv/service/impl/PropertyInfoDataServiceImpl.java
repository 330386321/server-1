package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.param.PointDetailSaveDataParam;
import com.lawu.eshop.property.param.PropertyInfoDataParam;
import com.lawu.eshop.property.srv.service.PointDetailService;
import com.lawu.eshop.property.srv.service.PropertyInfoDataService;
import com.lawu.eshop.property.srv.service.PropertyInfoService;
import com.lawu.eshop.utils.StringUtil;

/**
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月12日 下午12:54:03
 *
 */
@Service
public class PropertyInfoDataServiceImpl implements PropertyInfoDataService {

	@Autowired
	private PointDetailService pointDetailService;

	@Autowired
	private PropertyInfoService propertyInfoService;

	@Override
	@Transactional
	public int doHanlder(PropertyInfoDataParam param) {
		int retCode = propertyInfoService.validatePoint(param.getUserNum(), param.getPoint());
		if (retCode != ResultCode.SUCCESS) {
			return retCode;
		}

		// 插入积分明细
		PointDetailSaveDataParam pointDetailSaveDataParam = new PointDetailSaveDataParam();
		pointDetailSaveDataParam.setTitle(param.getTitle());
		pointDetailSaveDataParam.setPointNum(StringUtil.getRandomNum(""));
		pointDetailSaveDataParam.setUserNum(param.getUserNum());
		pointDetailSaveDataParam.setPointType(param.getMerchantTransactionTypeEnum().getValue());
		pointDetailSaveDataParam.setPoint(new BigDecimal("-" + param.getPoint()));
		pointDetailService.save(pointDetailSaveDataParam);

		// 更新用户资产
		BigDecimal point = new BigDecimal(param.getPoint());
		propertyInfoService.updatePropertyNumbers(param.getUserNum(), "P", "M", point);

		return ResultCode.SUCCESS;
	}
}
