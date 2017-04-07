package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.srv.bo.PropertyBalanceBO;
import com.lawu.eshop.property.srv.bo.PropertyInfoBO;
import com.lawu.eshop.property.srv.bo.PropertyPointBO;
import com.lawu.eshop.property.srv.converter.PropertyBalanceConverter;
import com.lawu.eshop.property.srv.converter.PropertyInfoConverter;
import com.lawu.eshop.property.srv.converter.PropertyPointConverter;
import com.lawu.eshop.property.srv.domain.PropertyInfoDO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDOExample;
import com.lawu.eshop.property.srv.domain.extend.PropertyInfoDOEiditView;
import com.lawu.eshop.property.srv.mapper.PropertyInfoDOMapper;
import com.lawu.eshop.property.srv.mapper.extend.PropertyInfoDOMapperExtend;
import com.lawu.eshop.property.srv.service.PointDetailService;
import com.lawu.eshop.property.srv.service.PropertyInfoService;
import com.lawu.eshop.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 资产管理服务实现
 *
 * @author meishuquan
 * @date 2017/3/27
 */
@Service
public class PropertyInfoServiceImpl implements PropertyInfoService {

	@Autowired
	private PropertyInfoDOMapper propertyInfoDOMapper;
	@Autowired
	private PropertyInfoDOMapperExtend propertyInfoDOMapperExtend;

	@Autowired
	private PointDetailService pointDetailService;



	@Override
	public PropertyInfoBO getPropertyInfoByUserNum(String userNum) {
		PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
		propertyInfoDOExample.createCriteria().andUserNumEqualTo(userNum);
		List<PropertyInfoDO> propertyInfoDOS = propertyInfoDOMapper.selectByExample(propertyInfoDOExample);
		return propertyInfoDOS.isEmpty() ? null : PropertyInfoConverter.convertBO(propertyInfoDOS.get(0));
	}

	@Override
	@Transactional
	public void updatePayPwd(String userNum, String originalPwd, String newPwd) {
		PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
		propertyInfoDO.setUserNum(userNum);
		propertyInfoDO.setPayPassword(MD5.MD5Encode(newPwd));
		PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
		propertyInfoDOExample.createCriteria().andUserNumEqualTo(userNum).andPayPasswordEqualTo(MD5.MD5Encode(newPwd));
		propertyInfoDOMapper.updateByExampleSelective(propertyInfoDO, propertyInfoDOExample);
	}

	@Override
	public PropertyBalanceBO getPropertyBalanceByUserNum(String userNum) {
		PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
		propertyInfoDOExample.createCriteria().andUserNumEqualTo(userNum);
		List<PropertyInfoDO> propertyInfoDOS = propertyInfoDOMapper.selectByExample(propertyInfoDOExample);

		if (propertyInfoDOS == null || propertyInfoDOS.isEmpty()) {
			return null;
		}

		return PropertyBalanceConverter.convert(propertyInfoDOS.get(0));
	}

	@Override
	public PropertyPointBO getPropertyPointByUserNum(String userNum) {
		PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
		propertyInfoDOExample.createCriteria().andUserNumEqualTo(userNum);
		List<PropertyInfoDO> propertyInfoDOS = propertyInfoDOMapper.selectByExample(propertyInfoDOExample);

		if (propertyInfoDOS == null || propertyInfoDOS.isEmpty()) {
			return null;
		}

		return PropertyPointConverter.convert(propertyInfoDOS.get(0));
	}

	@Override
	public int updatePropertyNumbers(String userNum, String column, String flag, BigDecimal number) {
		if(("B".equals(column) || "P".equals(column) || "L".equals(column)) && ("A".equals(flag) || "M".equals(flag))) {
			PropertyInfoDOEiditView editView = new PropertyInfoDOEiditView();
			editView.setUserNum(userNum);
			if("B".equals(column)){
				editView.setBalance(number);
				if ("A".equals(flag)) {
					propertyInfoDOMapperExtend.updatePropertyInfoAddBalance(editView);
				} else if ("M".equals(flag)) {
					propertyInfoDOMapperExtend.updatePropertyInfoMinusBalance(editView);
				}
			}else if("P".equals(column)){
				editView.setPoint(number);
				if ("A".equals(flag)) {
					propertyInfoDOMapperExtend.updatePropertyInfoAddPoint(editView);
				} else if ("M".equals(flag)) {
					propertyInfoDOMapperExtend.updatePropertyInfoMinusPoint(editView);
				}
			}else if("L".equals(column)){
				editView.setLoveAccount(number);
				if ("A".equals(flag)) {
					propertyInfoDOMapperExtend.updatePropertyInfoAddLove(editView);
				} else if ("M".equals(flag)) {
					propertyInfoDOMapperExtend.updatePropertyInfoMinusLove(editView);
				}
			}
		}else{
			return 0;
		}

		return ResultCode.SUCCESS;
	}

}
