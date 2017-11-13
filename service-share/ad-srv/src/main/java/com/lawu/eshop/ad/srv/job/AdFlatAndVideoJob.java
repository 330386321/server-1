package com.lawu.eshop.ad.srv.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.srv.AdSrvConfig;
import com.lawu.eshop.ad.srv.converter.AdConverter;
import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.ad.srv.domain.AdDOExample;
import com.lawu.eshop.ad.srv.mapper.AdDOMapper;
import com.lawu.eshop.solr.service.SolrService;
import com.lawu.jobsextend.AbstractPageJob;

/**
 * 修改平面、视频两个星期下架
 * @author zhangrc
 *
 */
public class AdFlatAndVideoJob extends AbstractPageJob<AdDO>{

    
    @Autowired
	private AdDOMapper adDOMapper;
    
    @Autowired
	private SolrService solrService;
    
    @Autowired
	private AdSrvConfig adSrvConfig;
    
	@Override
	public List<AdDO> queryPage(int currentPage, int pageSize) {
		AdDOExample example = new AdDOExample();
		List<Byte> bytes = new ArrayList<>();
		bytes.add(AdTypeEnum.AD_TYPE_FLAT.getVal());
		bytes.add(AdTypeEnum.AD_TYPE_VIDEO.getVal());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -14); // 设置为14天前
		Date before14days = calendar.getTime();
		example.createCriteria().andStatusEqualTo(AdStatusEnum.AD_STATUS_PUTING.val).andTypeIn(bytes)
				.andBeginTimeLessThan(before14days);
		RowBounds rowBounds = new RowBounds((currentPage - 1) * pageSize, pageSize);
		List<AdDO> list = adDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		return list;
	}

	@Override
	public void executeSingle(AdDO adDO) {
		if (adDO.getHits() >= adDO.getAdCount()) {
			adDO.setStatus(AdStatusEnum.AD_STATUS_PUTED.val); // 投放结束
			adDO.setGmtModified(new Date());
			adDOMapper.updateByPrimaryKey(adDO);
			SolrInputDocument document = AdConverter.convertSolrInputDocument(adDO);
			solrService.addSolrDocs(document, adSrvConfig.getSolrUrl(), adSrvConfig.getSolrAdCore(),
					adSrvConfig.getIsCloudSolr());
		}

	}

}
