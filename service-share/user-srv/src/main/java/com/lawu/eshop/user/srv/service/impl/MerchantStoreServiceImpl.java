package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.solr.SolrUtil;
import com.lawu.eshop.user.dto.MerchantStatusEnum;
import com.lawu.eshop.user.param.MerchantStoreParam;
import com.lawu.eshop.user.param.StoreStatisticsParam;
import com.lawu.eshop.user.srv.bo.MerchantStoreBO;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDOExample;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.service.MerchantStoreService;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MerchantStoreServiceImpl implements MerchantStoreService {

    @Autowired
    private MerchantStoreDOMapper merchantStoreDOMapper;

    @Override
    public MerchantStoreBO selectMerchantStore(Long merchantId) {

        MerchantStoreDOExample example = new MerchantStoreDOExample();
        example.createCriteria().andMerchantIdEqualTo(merchantId);
        List<MerchantStoreDO> list = merchantStoreDOMapper.selectByExample(example);
        MerchantStoreDO merchantStoreDO = null;
        if (!list.isEmpty()) {
            merchantStoreDO = list.get(0);
        }
        MerchantStoreBO bo = MerchantStoreConverter.convertStoreBO(merchantStoreDO);
        return bo;
    }

    @Override
    @Transactional
    public void updateNoReasonReturn(Long id) {
        MerchantStoreDO merchantStoreDO = new MerchantStoreDO();
        merchantStoreDO.setId(id);
        merchantStoreDO.setIsNoReasonReturn(true);
        merchantStoreDOMapper.updateByPrimaryKeySelective(merchantStoreDO);
    }

    @Override
    public MerchantStoreBO getMerchantStoreById(Long id) {
        MerchantStoreDO merchantStoreDO = merchantStoreDOMapper.selectByPrimaryKey(id);
        return MerchantStoreConverter.convertStoreBO(merchantStoreDO);
    }

	@Override
	public List<MerchantStoreBO> selectAllMerchantStore(MerchantStoreParam param) {
		 MerchantStoreDOExample example = new MerchantStoreDOExample();
	        example.createCriteria().andStatusEqualTo(new Byte("1"));
	        List<MerchantStoreDO> list = merchantStoreDOMapper.selectByExample(example);
	        List<MerchantStoreBO> boList=new ArrayList<>();
	        if (!list.isEmpty()) {
	        	for (MerchantStoreDO merchantStoreDO : list) {
	        		MerchantStoreBO bo=new MerchantStoreBO();
	        		bo.setId(merchantStoreDO.getId());
	        		bo.setName(merchantStoreDO.getName());
	        		boList.add(bo);
				}
	        }
		return boList;
	}

    @Override
    public List<MerchantStoreBO> listMerchantStore() {
        MerchantStoreDOExample example = new MerchantStoreDOExample();
        example.createCriteria().andStatusEqualTo(MerchantStatusEnum.MERCHANT_STATUS_CHECKED.val);
        List<MerchantStoreDO> merchantStoreDOS = merchantStoreDOMapper.selectByExample(example);
        return MerchantStoreConverter.convertStoreBO(merchantStoreDOS);
    }

    @Override
    @Transactional
    public void updateStoreStatisticsById(Long id, StoreStatisticsParam param) {
        MerchantStoreDO merchantStoreDO = new MerchantStoreDO();
        merchantStoreDO.setId(id);
        merchantStoreDO.setAverageConsumeAmount(param.getAverageConsumeAmount());
        merchantStoreDO.setAverageScore(param.getAverageScore());
        merchantStoreDO.setFeedbackRate(param.getFeedbackRate());
        merchantStoreDOMapper.updateByPrimaryKeySelective(merchantStoreDO);

        SolrDocument solrDocument = SolrUtil.getSolrDocsById(id, SolrUtil.SOLR_MERCHANT_CORE);
        if (solrDocument != null) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", solrDocument.get("id"));
            document.addField("merchantId_l", solrDocument.get("merchantId_l"));
            document.addField("name_s", solrDocument.get("name_s"));
            document.addField("regionPath_s", solrDocument.get("regionPath_s"));
            document.addField("latLon_p", solrDocument.get("latLon_p"));
            document.addField("industryPath_s", solrDocument.get("industryPath_s"));
            document.addField("industryName_s", solrDocument.get("industryName_s"));
            document.addField("storePic_s", solrDocument.get("storePic_s"));
            document.addField("averageConsumeAmount_d", param.getAverageConsumeAmount());
            document.addField("averageScore_d", param.getAverageScore());
            document.addField("favoriteNumber_i", solrDocument.get("favoriteNumber_i"));
            SolrUtil.addSolrDocs(document, SolrUtil.SOLR_MERCHANT_CORE);
        }
    }

}
