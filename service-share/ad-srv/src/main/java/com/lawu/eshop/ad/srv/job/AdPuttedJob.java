package com.lawu.eshop.ad.srv.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.srv.AdSrvConfig;
import com.lawu.eshop.ad.srv.converter.AdConverter;
import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.ad.srv.domain.AdDOExample;
import com.lawu.eshop.ad.srv.mapper.AdDOMapper;
import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.solr.service.SolrService;
import com.lawu.jobsextend.AbstractPageJob;

/**
 * @author zhangrc
 * @date 2017/4/14
 */
public class AdPuttedJob extends AbstractPageJob<AdDO> {
    
	@Autowired
	private AdDOMapper adDOMapper;

	@Autowired
	private SolrService solrService;

	@Autowired
	private AdSrvConfig adSrvConfig;
	
	@Autowired
	@Qualifier("adMerchantAddPointTransactionMainServiceImpl")
	private TransactionMainService<Reply> matransactionMainAddService;

	@Override
	public List<AdDO> queryPage(int offset, int pageSize) {
		AdDOExample example = new AdDOExample();
		example.createCriteria().andStatusEqualTo(AdStatusEnum.AD_STATUS_PUTING.val).andTypeEqualTo(AdTypeEnum.AD_TYPE_PRAISE.getVal());
		RowBounds rowBounds = new RowBounds(offset, pageSize);
		List<AdDO> list = adDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		return list;
	}

	@Override
	public void executeSingle(AdDO adDO) {
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.MINUTE, -20);
		if ((nowTime.getTime().getTime() - adDO.getBeginTime().getTime()) > 0) {
			adDO.setStatus(AdStatusEnum.AD_STATUS_PUTED.val);
			adDO.setGmtModified(new Date());
			adDOMapper.updateByPrimaryKey(adDO);
			// 将没有领完的积分退还给用户
			matransactionMainAddService.sendNotice(adDO.getId());
            SolrInputDocument document = AdConverter.convertSolrInputDocument(adDO);
            solrService.addSolrDocs(document, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(),adSrvConfig.getIsCloudSolr());
	    }
	}
	
	@Override
	public boolean continueWhenSinglePageFail() {
		return true;
	}

	@Override
	public boolean isStatusData() {
		return false;
	}
}
