package com.lawu.eshop.property.srv.service.impl.transaction;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.property.param.PointDetailSaveDataParam;
import com.lawu.eshop.property.srv.bo.AdPointNotification;
import com.lawu.eshop.property.srv.domain.PropertyInfoDO;
import com.lawu.eshop.property.srv.domain.PropertyInfoDOExample;
import com.lawu.eshop.property.srv.mapper.PropertyInfoDOMapper;
import com.lawu.eshop.property.srv.service.PointDetailService;
import com.lawu.eshop.utils.DataTransUtil;
import com.lawu.eshop.utils.StringUtil;

/**
 * @author zhangrc
 * @date 2017/4/12
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_AD_SRV, tags = MqConstant.TAG_AD_USER_ADD_POINT)
public class AdUserAddPointTransactionFollowServiceImpl extends AbstractTransactionFollowService<AdPointNotification, Reply> {

    @Autowired
    private PropertyInfoDOMapper propertyInfoDOMapper;
    
    @Autowired
    private PointDetailService pointDetailService;

    @Override
    public Reply execute(AdPointNotification notification) {
        PropertyInfoDOExample propertyInfoDOExample = new PropertyInfoDOExample();
        propertyInfoDOExample.createCriteria().andUserNumEqualTo(notification.getUserNum());
        List<PropertyInfoDO> propertyInfoDOList = propertyInfoDOMapper.selectByExample(propertyInfoDOExample);
        PropertyInfoDO propertyInfoDO = new PropertyInfoDO();
        if (!propertyInfoDOList.isEmpty()) {
        	propertyInfoDO=propertyInfoDOList.get(0);
        	propertyInfoDO.setPoint(propertyInfoDO.getPoint().add(notification.getPoint()));
        	propertyInfoDO.setGmtModified(new Date());
        	propertyInfoDOMapper.updateByPrimaryKeySelective(propertyInfoDO);
        	 //插入积分明细
            PointDetailSaveDataParam pointDetailSaveDataParam = new PointDetailSaveDataParam();
            pointDetailSaveDataParam.setTitle("广告获取积分");
            pointDetailSaveDataParam.setPointNum(StringUtil.getRandomNum(""));
            pointDetailSaveDataParam.setUserNum(notification.getUserNum());
            pointDetailSaveDataParam.setPointType(DataTransUtil.intToByte(0));
            pointDetailSaveDataParam.setPoint(notification.getPoint());
            pointDetailService.save(pointDetailSaveDataParam);
        }
        return new Reply();
    }
}
