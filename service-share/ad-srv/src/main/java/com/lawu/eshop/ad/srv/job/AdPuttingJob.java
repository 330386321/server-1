package com.lawu.eshop.ad.srv.job;

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
public class AdPuttingJob extends AbstractPageJob<AdDO> {
    
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
	public List<AdDO> queryPage(int currentPage, int pageSize) {
    	AdDOExample example = new AdDOExample();
		example.createCriteria().andStatusEqualTo(AdStatusEnum.AD_STATUS_ADD.val).andTypeNotEqualTo(AdTypeEnum.AD_TYPE_PACKET.getVal());
		RowBounds rowBounds = new RowBounds((currentPage - 1) * pageSize, pageSize);
		List<AdDO> list = adDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		return list;
	}

	@Override
	public void executeSingle(AdDO adDO) {
		Date date = new Date();
		if (adDO.getBeginTime().getTime() <= date.getTime()) {
			adDO.setStatus(AdStatusEnum.AD_STATUS_PUTING.val);
			adDO.setGmtModified(date);
			adDOMapper.updateByPrimaryKey(adDO);
			SolrInputDocument document = AdConverter.convertSolrInputDocument(adDO);
			solrService.addSolrDocs(document, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(),adSrvConfig.getIsCloudSolr());
		}
	}
}
