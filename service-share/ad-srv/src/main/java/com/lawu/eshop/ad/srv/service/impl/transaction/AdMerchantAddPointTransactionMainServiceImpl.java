package com.lawu.eshop.ad.srv.service.impl.transaction;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.srv.constants.TransactionConstant;
import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.ad.srv.domain.extend.PointPoolDOView;
import com.lawu.eshop.ad.srv.mapper.AdDOMapper;
import com.lawu.eshop.ad.srv.mapper.PointPoolDOMapper;
import com.lawu.eshop.ad.srv.mapper.extend.PointPoolDOMapperExtend;
import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.ad.AdPointNotification;

/**
 * @author zhangrc
 * @date 2017/4/12
 */
@Service("adMerchantAddPointTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.AD_ME_ADD_POINT, topic = MqConstant.TOPIC_AD_SRV, tags = MqConstant.TAG_AD_ME_ADD_POINT)
public class AdMerchantAddPointTransactionMainServiceImpl extends AbstractTransactionMainService<AdPointNotification, Reply> {

	@Autowired
	private AdDOMapper adDOMapper;
	
	@Autowired
	private PointPoolDOMapperExtend pointPoolDOMapperExtend;

    @Override
    public AdPointNotification selectNotification(Long id) {
    	 AdDO ad=adDOMapper.selectByPrimaryKey(id);
    	 AdPointNotification notification=new AdPointNotification();
    	 notification.setUserNum(ad.getMerchantNum());
    	 if(ad.getType()==AdTypeEnum.AD_TYPE_PRAISE.getVal() || ad.getType()==AdTypeEnum.AD_TYPE_PACKET.getVal()){
    		PointPoolDOView view =  pointPoolDOMapperExtend.getTotlePoint(id);
    		BigDecimal subMoney=new BigDecimal(0);
    		//剩余积分
    		if(view == null){
    		    subMoney=ad.getTotalPoint().subtract(BigDecimal.valueOf(0));
    		}else{
    			subMoney=ad.getTotalPoint().subtract(view.getPoint());
    		}
 			notification.setPoint(subMoney); 
    	 }else{
    		 Integer hits=ad.getHits();
        	 BigDecimal point=ad.getPoint();
        	 if(hits==null) hits=0;
        	 notification.setPoint(ad.getTotalPoint().subtract(point.multiply(new BigDecimal(hits)))); 
    	 }
        return notification;
    }


}
