package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.property.srv.bo.PropertyInfoBO;
import com.lawu.eshop.property.srv.converter.PropertyInfoConverter;
import com.lawu.eshop.property.srv.domain.PropertyInfoDO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDOExample;
import com.lawu.eshop.property.srv.mapper.PropertyInfoDOMapper;
import com.lawu.eshop.property.srv.service.PropertyInfoService;
import com.lawu.eshop.utils.MD5;

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

    @Override
    public PropertyInfoBO getPropertyInfoByUserNo(String userNo) {
        PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
        propertyInfoDOExample.createCriteria().andUserNumEqualTo(userNo);
        List<PropertyInfoDO> propertyInfoDOS = propertyInfoDOMapper.selectByExample(propertyInfoDOExample);
        return propertyInfoDOS.isEmpty() ? null : PropertyInfoConverter.convertBO(propertyInfoDOS.get(0));
    }

    @Override
    public void updatePayPwd(String userNum, String originalPwd, String newPwd) {
        PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
        propertyInfoDO.setUserNum(userNum);
        propertyInfoDO.setPayPassword(MD5.MD5Encode(newPwd));
        PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
        propertyInfoDOExample.createCriteria().andUserNumEqualTo(userNum).andPayPasswordEqualTo(MD5.MD5Encode(newPwd));
        propertyInfoDOMapper.updateByExampleSelective(propertyInfoDO, propertyInfoDOExample);
    }

    @Override
    public BigDecimal getPropertyBalanceByUserNum(String userNum) {
        PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
        propertyInfoDOExample.createCriteria().andUserNumEqualTo(userNum);
        List<PropertyInfoDO> propertyInfoDOS = propertyInfoDOMapper.selectByExample(propertyInfoDOExample);

        if (propertyInfoDOS == null || propertyInfoDOS.isEmpty()) {
            return null;
        }

        return propertyInfoDOS.get(0).getBalance();
    }
    
    @Override
	public Integer getPropertyPointByUserNum(String userNum) {
		PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
		propertyInfoDOExample.createCriteria().andUserNumEqualTo(userNum);
		List<PropertyInfoDO> propertyInfoDOS = propertyInfoDOMapper.selectByExample(propertyInfoDOExample);

		if (propertyInfoDOS == null || propertyInfoDOS.isEmpty()) {
			return null;
		}
		
		return propertyInfoDOS.get(0).getPoint();
	}
}
