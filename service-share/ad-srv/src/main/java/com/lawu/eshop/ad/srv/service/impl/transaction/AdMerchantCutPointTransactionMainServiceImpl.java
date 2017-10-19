package com.lawu.eshop.ad.srv.service.impl.transaction;

import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.srv.AdSrvConfig;
import com.lawu.eshop.ad.srv.constants.TransactionConstant;
import com.lawu.eshop.ad.srv.converter.AdConverter;
import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.ad.srv.mapper.AdDOMapper;
import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.ad.AdPointNotification;
import com.lawu.eshop.mq.dto.ad.reply.AdPointReply;
import com.lawu.eshop.solr.service.SolrService;

/**
 * 商家投放广告扣除积分 -- 主事务
 * @author zhangrc
 * @date 2017/4/12
 */
@Service("adMerchantCutPointTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.AD_ME_CUT_POINT, topic = MqConstant.TOPIC_AD_SRV, tags = MqConstant.TAG_AD_ME_CUT_POINT)
public class AdMerchantCutPointTransactionMainServiceImpl extends AbstractTransactionMainService<AdPointNotification, AdPointReply> {

	@Autowired
	private AdDOMapper adDOMapper;
	
	@Autowired
	private SolrService solrService;
	
	@Autowired
	private AdSrvConfig adSrvConfig;

    @Override
    public AdPointNotification selectNotification(Long adId) {
    	 AdDO ad=adDOMapper.selectByPrimaryKey(adId);
    	 AdPointNotification notification=new AdPointNotification();
    	 notification.setUserNum(ad.getMerchantNum());
    	 notification.setPoint(ad.getTotalPoint());
    	 notification.setAdId(adId);
    	 notification.setRegionPath(ad.getMerchantRegionPath());
         return notification;
    }

    @Override
    public void afterSuccess(Long adId, AdPointReply reply) {
    	
    	if(reply.isFlag()){
    		
    		AdDO  ad=adDOMapper.selectByPrimaryKey(adId);
        	ad.setIsPay(true);
    		ad.setStatus(AdStatusEnum.AD_STATUS_ADD.val);
    		
        	if(ad.getType()==AdTypeEnum.AD_TYPE_VIDEO.getVal()){
        		ad.setStatus(AdStatusEnum.AD_STATUS_AUDIT.val);
        	}
        	adDOMapper.updateByPrimaryKeySelective(ad);
    	}
    	
    }
}
