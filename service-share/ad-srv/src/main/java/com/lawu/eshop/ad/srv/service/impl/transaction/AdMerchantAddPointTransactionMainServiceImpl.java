package com.lawu.eshop.ad.srv.service.impl.transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.srv.constants.TransactionConstant;
import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.ad.srv.domain.PointPoolDO;
import com.lawu.eshop.ad.srv.domain.PointPoolDOExample;
import com.lawu.eshop.ad.srv.domain.PointPoolDOExample.Criteria;
import com.lawu.eshop.ad.srv.mapper.AdDOMapper;
import com.lawu.eshop.ad.srv.mapper.PointPoolDOMapper;
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
	private PointPoolDOMapper pointPoolDOMapper;

    @Override
    public AdPointNotification selectNotification(Long adId) {
    	 AdDO ad=adDOMapper.selectByPrimaryKey(adId);
    	 AdPointNotification notification=new AdPointNotification();
    	 notification.setUserNum(ad.getMerchantNum());
    	 if(ad.getType()==3 || ad.getType()==4){
    		PointPoolDOExample ppexample=new PointPoolDOExample();
    		 Criteria cr=ppexample.createCriteria();
 			 cr.andAdIdEqualTo(ad.getId()).andStatusEqualTo(new Byte("0"));
 			 if(ad.getType()==3){
 				 cr.andTypeEqualTo(new Byte("1"));
 			 }else{
 				 cr.andTypeEqualTo(new Byte("2"));
 			 }
 			List<PointPoolDO> list=pointPoolDOMapper.selectByExample(ppexample);
 			BigDecimal sum=new BigDecimal(0);
 			for (PointPoolDO pointPoolDO : list) {
 				BigDecimal  point=pointPoolDO.getPoint();
 				sum=sum.add(point);
 			}
 			notification.setPoint(sum); 
    	 }else{
    		 Integer hits=ad.getHits();
        	 BigDecimal point=ad.getPoint();
        	 BigDecimal totalPoint=ad.getTotalPoint();
        	 if(hits==null)
        		 hits=0;
        	 notification.setPoint(totalPoint.subtract(point.multiply(new BigDecimal(hits)))); 
    	 }
    	notification.setRegionPath("");
        return notification;
    }


}
