package com.lawu.eshop.property.srv.service.impl.transaction;

import com.lawu.eshop.property.constants.AdTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.ad.AdPointNotification;
import com.lawu.eshop.property.constants.LoveTypeEnum;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.param.PropertyInfoDataParam;
import com.lawu.eshop.property.srv.service.PropertyInfoDataService;

/**
 * 平面&视频广告 --- 从事务
 * @author zhangrc
 * @date 2017/4/12
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_AD_SRV, tags = MqConstant.TAG_AD_USER_CLICK_POINT)
public class UserClickAdTransactionFollowServiceImpl extends AbstractTransactionFollowService<AdPointNotification, Reply> {

	private static Logger logger = LoggerFactory.getLogger(UserClickAdTransactionFollowServiceImpl.class);

	@Autowired
	private PropertyInfoDataService propertyInfoDataService;

	@Override
	public void execute(AdPointNotification notification) {
		PropertyInfoDataParam param = new PropertyInfoDataParam();
		param.setPoint(notification.getPoint().toString());
		param.setUserNum(notification.getUserNum());
		if(notification.getType().equals(AdTypeEnum.AD_TYPE_FLAT.getVal())){
			param.setMemberTransactionTypeEnum(MemberTransactionTypeEnum.AD_PLANE);
		} else if(notification.getType().equals(AdTypeEnum.AD_TYPE_VIDEO.getVal())){
			param.setMemberTransactionTypeEnum(MemberTransactionTypeEnum.AD_VIDEO);
		} else {
			logger.error("用户点看一看&猜一猜加余额从事务，缺少传参广告type。");
			return;
		}
		param.setLoveTypeEnum(LoveTypeEnum.AD_CLICK);
		param.setRegionPath(notification.getRegionPath());
		param.setTempBizId(notification.getAdId() == null ? "0" : notification.getAdId().toString());
		param.setTitle(notification.getTitle());
		propertyInfoDataService.doHanlderBalanceIncome(param);
	}
}
