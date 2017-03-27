package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.property.srv.bo.PropertyInfoBO;
import com.lawu.eshop.property.srv.converter.PropertyInfoConverter;
import com.lawu.eshop.property.srv.domain.PropertyInfoDO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDOExample;
import com.lawu.eshop.property.srv.mapper.PropertyInfoDOMapper;
import com.lawu.eshop.property.srv.service.PropertyInfoService;
import com.lawu.eshop.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public void savePropertyInfo(String userNo) {
        PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
        propertyInfoDO.setUserNo(userNo);
        propertyInfoDO.setGmtCreate(new Date());
        propertyInfoDOMapper.insertSelective(propertyInfoDO);
    }

    @Override
    public PropertyInfoBO getPropertyInfoByUserNo(String userNo) {
        PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
        propertyInfoDOExample.createCriteria().andUserNoEqualTo(userNo);
        List<PropertyInfoDO> propertyInfoDOS = propertyInfoDOMapper.selectByExample(propertyInfoDOExample);
        return propertyInfoDOS.isEmpty() ? null : PropertyInfoConverter.convertBO(propertyInfoDOS.get(0));
    }

    @Override
    public void updatePayPwd(String userNo, String originalPwd, String newPwd) {
        PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
        propertyInfoDO.setUserNo(userNo);
        propertyInfoDO.setPayPassword(MD5.MD5Encode(newPwd));
        PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
        propertyInfoDOExample.createCriteria().andUserNoEqualTo(userNo).andPayPasswordEqualTo(MD5.MD5Encode(newPwd));
        propertyInfoDOMapper.updateByExampleSelective(propertyInfoDO, propertyInfoDOExample);
    }
}
