package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.solr.service.SolrService;
import com.lawu.eshop.user.constants.UserStatusEnum;
import com.lawu.eshop.user.dto.MerchantStatusEnum;
import com.lawu.eshop.user.dto.MerchantStoreImageEnum;
import com.lawu.eshop.user.dto.MerchantStoreTypeEnum;
import com.lawu.eshop.user.param.ListMerchantStoreParam;
import com.lawu.eshop.user.param.MerchantStoreParam;
import com.lawu.eshop.user.param.StoreIndexParam;
import com.lawu.eshop.user.param.StoreStatisticsParam;
import com.lawu.eshop.user.srv.UserSrvConfig;
import com.lawu.eshop.user.srv.bo.*;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import com.lawu.eshop.user.srv.domain.*;
import com.lawu.eshop.user.srv.domain.extend.MerchantAdInfoView;
import com.lawu.eshop.user.srv.domain.extend.NewMerchantStoreDOView;
import com.lawu.eshop.user.srv.domain.extend.RecommendFoodDOview;
import com.lawu.eshop.user.srv.mapper.MerchantDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreImageDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreProfileDOMapper;
import com.lawu.eshop.user.srv.mapper.extend.MerchantStoreDOMapperExtend;
import com.lawu.eshop.user.srv.service.MerchantStoreService;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MerchantStoreServiceImpl implements MerchantStoreService {

    @Autowired
    private MerchantStoreDOMapper merchantStoreDOMapper;

    @Autowired
    private UserSrvConfig userSrvConfig;

    @Autowired
    private MerchantStoreDOMapperExtend merchantStoreDOMapperExtend;

    @Autowired
    private MerchantStoreImageDOMapper merchantStoreImageDOMapper;

    @Autowired
    private MerchantDOMapper merchantDOMapper;

    @Autowired
    private SolrService solrService;
    
    @Autowired
    private MerchantStoreProfileDOMapper  merchantStoreProfileDOMapper;

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
    public List<MerchantStoreBO> listMerchantStore(ListMerchantStoreParam listMerchantStoreParam) {
        List<MerchantStoreDO> merchantStoreDOS = merchantStoreDOMapperExtend.listMerchantStore(listMerchantStoreParam);
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

        SolrDocument solrDocument = solrService.getSolrDocsById(id, userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore(), userSrvConfig.getIsCloudSolr());
        if (solrDocument != null) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", solrDocument.get("id"));
            document.addField("merchantId_l", solrDocument.get("merchantId_l"));
            document.addField("name", solrDocument.get("name"));
            document.addField("regionPath_s", solrDocument.get("regionPath_s"));
            document.addField("latLon_p", solrDocument.get("latLon_p"));
            document.addField("industryPath_s", solrDocument.get("industryPath_s"));
            document.addField("industryName_s", solrDocument.get("industryName_s"));
            document.addField("storePic_s", solrDocument.get("storePic_s"));
            document.addField("averageConsumeAmount_d", param.getAverageConsumeAmount() == null ? 0 : param.getAverageConsumeAmount().toString());
            document.addField("averageScore_d", param.getAverageScore() == null ? 0 : param.getAverageScore().toString());
            document.addField("favoriteNumber_i", solrDocument.get("favoriteNumber_i"));
            document.addField("discountOrdinal_d", solrDocument.get("discountOrdinal_d"));
            document.addField("favoreInfo_s", solrDocument.get("favoreInfo_s"));
            document.addField("discountPackage_s", solrDocument.get("discountPackage_s"));
            document.addField("keywords", solrDocument.get("keywords"));
            solrService.addSolrDocs(document, userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore(), userSrvConfig.getIsCloudSolr());
        }
    }

    @Deprecated
    @Override
    public void updateStoreIndex(Long id) {
        MerchantStoreDO merchantStoreDO = merchantStoreDOMapper.selectByPrimaryKey(id);
        if(merchantStoreDO == null){
            return;
        }

        SolrDocument solrDocument = solrService.getSolrDocsById(id, userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore(), userSrvConfig.getIsCloudSolr());
        if(solrDocument == null){
            MerchantStoreImageDOExample merchantStoreImageDOExample = new MerchantStoreImageDOExample();
            merchantStoreImageDOExample.createCriteria().andMerchantStoreIdEqualTo(merchantStoreDO.getId()).andTypeEqualTo(MerchantStoreImageEnum.STORE_IMAGE_STORE.val).andStatusEqualTo(true);
            List<MerchantStoreImageDO> merchantStoreImageDOS = merchantStoreImageDOMapper.selectByExample(merchantStoreImageDOExample);
            String storePic = merchantStoreImageDOS.isEmpty() ? "" : merchantStoreImageDOS.get(0).getPath();
            SolrInputDocument document = MerchantStoreConverter.convertSolrInputDocument(merchantStoreDO, storePic);
            solrService.addSolrDocs(document, userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore(), userSrvConfig.getIsCloudSolr());
        }
    }

    @Override
    public void rebuildStoreIndex(List<StoreIndexParam> indexParamList) {
        if (indexParamList == null || indexParamList.isEmpty()) {
            return;
        }

        Collection<SolrInputDocument> documents = new ArrayList<>();
        for (StoreIndexParam param : indexParamList) {
            MerchantStoreImageDOExample merchantStoreImageDOExample = new MerchantStoreImageDOExample();
            merchantStoreImageDOExample.createCriteria().andMerchantStoreIdEqualTo(param.getMerchantStoreId()).andTypeEqualTo(MerchantStoreImageEnum.STORE_IMAGE_STORE.val).andStatusEqualTo(true);
            List<MerchantStoreImageDO> merchantStoreImageDOS = merchantStoreImageDOMapper.selectByExample(merchantStoreImageDOExample);
            String storePic = merchantStoreImageDOS.isEmpty() ? "" : merchantStoreImageDOS.get(0).getPath();
            MerchantStoreDO merchantStoreDO = merchantStoreDOMapper.selectByPrimaryKey(param.getMerchantStoreId());
            if (merchantStoreDO != null) {
                SolrInputDocument document = MerchantStoreConverter.convertSolrInputDocument(merchantStoreDO, storePic);
                document.addField("favoreInfo_s", param.getFavoreInfo());
                document.addField("discountPackage_s", param.getDiscountPackage());
                document.addField("discountOrdinal_d", param.getDiscountOrdinal());
                documents.add(document);
            }
        }
        solrService.addSolrDocsList(documents, userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore(), userSrvConfig.getIsCloudSolr());
    }

    @Override
    public void delInvalidStoreIndex() {
        ListMerchantStoreParam listMerchantStoreParam = new ListMerchantStoreParam();
        listMerchantStoreParam.setStatus(MerchantStatusEnum.MERCHANT_STATUS_CHECKED.val);
        listMerchantStoreParam.setManageType(MerchantStoreTypeEnum.ENTITY_MERCHANT.val);
        listMerchantStoreParam.setPageSize(1000);
        int currentPage = 0;

        while (true) {
            currentPage ++;
            listMerchantStoreParam.setCurrentPage(currentPage);
            List<MerchantStoreDO> merchantStoreDOS = merchantStoreDOMapperExtend.listInvalidMerchantStore(listMerchantStoreParam);
            if (merchantStoreDOS == null || merchantStoreDOS.isEmpty()) {
                return ;
            }

            List<String> ids = new ArrayList<>();
            for (MerchantStoreDO merchantStoreDO : merchantStoreDOS) {
                ids.add(String.valueOf(merchantStoreDO.getId()));
            }
            solrService.delSolrDocsByIds(ids, userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore(), userSrvConfig.getIsCloudSolr());
        }
    }

	@Override
	public List<MerchantAdInfoBO> getAdMerchantStoreByIds(List<Long> merchantIds) {
		 List<MerchantAdInfoView> list= merchantStoreDOMapperExtend.getAdMerchantStoreByIds(merchantIds);
		 List<MerchantAdInfoBO> BOList=new ArrayList<>();
		 for (MerchantAdInfoView merchantAdInfoView : list) {
			 MerchantAdInfoBO bo=new MerchantAdInfoBO();
			 bo.setMerchantId(merchantAdInfoView.getMerchantId());
			 bo.setMerchantStoreId(merchantAdInfoView.getMerchantStoreId());
			 bo.setName(merchantAdInfoView.getName());
			 bo.setPath(merchantAdInfoView.getPath());
			 bo.setManageType(merchantAdInfoView.getManageType());
			 BOList.add(bo);
		}
		return BOList;
	}

    @Override
    public MerchantStoreStatusBO merchantStoreIsExist(Long id) {
        MerchantStoreDO merchantStoreDO = merchantStoreDOMapper.selectByPrimaryKey(id);
        MerchantStoreStatusBO storeStatusBO = new MerchantStoreStatusBO();
        if (merchantStoreDO == null) {
            storeStatusBO.setExist(false);
            return storeStatusBO;
        } else {
            storeStatusBO.setExist(true);
            storeStatusBO.setStatus(merchantStoreDO.getStatus());
            return storeStatusBO;
        }
    }

    @Override
    public MerchantInfoBO findAccountAndRegionPathByNum(String merchantNum) {
        MerchantDOExample example = new MerchantDOExample();
        example.createCriteria().andNumEqualTo(merchantNum).andStatusEqualTo(UserStatusEnum.MEMBER_STATUS_VALID.val);
        List<MerchantDO> merchantDOS = merchantDOMapper.selectByExample(example);
        MerchantInfoBO merchantInfoBO = new MerchantInfoBO();
        if(!merchantDOS.isEmpty()){
            merchantInfoBO.setAccount(merchantDOS.get(0).getAccount());
            MerchantStoreDOExample storeDOExample = new MerchantStoreDOExample();
            storeDOExample.createCriteria().andMerchantIdEqualTo(merchantDOS.get(0).getId());
            List<MerchantStoreDO> storeDOS = merchantStoreDOMapper.selectByExample(storeDOExample);
            if (!storeDOS.isEmpty()){
                merchantInfoBO.setRegionPath(storeDOS.get(0).getRegionPath());
            }
        }
        return merchantInfoBO;
    }

    @Override
    public List<NewMerchantStoreBO> listNewMerchant(String regionPath) {
        List<NewMerchantStoreDOView> storeDOViews = merchantStoreDOMapperExtend.listNewMerchant(regionPath);
        return MerchantStoreConverter.convertNewStoreBO(storeDOViews);
    }

    @Override
    public List<RecommendFoodBO> listRecommendFoodConsume(Integer industryId, String regionPath) {
        Map<String, Object> map = new HashMap<>();
        map.put("industryId", industryId);
        map.put("regionPath", regionPath);
        List<RecommendFoodDOview> foodDOviews = merchantStoreDOMapperExtend.listRecommendFoodConsume(map);
        return MerchantStoreConverter.convertRecommendStoreBO(foodDOviews);
    }

    @Override
    public List<RecommendFoodBO> listRecommendFoodComment(Integer industryId, String regionPath) {
        Map<String, Object> map = new HashMap<>();
        map.put("industryId", industryId);
        map.put("regionPath", regionPath);
        List<RecommendFoodDOview> foodDOviews = merchantStoreDOMapperExtend.listRecommendFoodComment(map);
        return MerchantStoreConverter.convertRecommendStoreBO(foodDOviews);
    }

	@Override
	public MerchantStoreAdInfoBO selectMerchantStoreAdInfo(Long merchantId) {
		MerchantStoreDOExample example = new MerchantStoreDOExample();
        example.createCriteria().andMerchantIdEqualTo(merchantId);
        List<MerchantStoreDO> list = merchantStoreDOMapper.selectByExample(example);
        MerchantStoreAdInfoBO bo = new MerchantStoreAdInfoBO();
        if (!list.isEmpty()) {
        	MerchantStoreDO merchantStoreDO = list.get(0);
            MerchantStoreProfileDOExample mpExample = new  MerchantStoreProfileDOExample();
            mpExample.createCriteria().andMerchantIdEqualTo(list.get(0).getMerchantId());
            List<MerchantStoreProfileDO>  mpList = merchantStoreProfileDOMapper.selectByExample(mpExample);		
            bo.setMerchantStoreId(merchantStoreDO.getId());
            bo.setName(merchantStoreDO.getName());
            bo.setLatitude(merchantStoreDO.getLatitude());
            bo.setLongitude(merchantStoreDO.getLongitude());
    		if(!mpList.isEmpty()){
    			bo.setManageType(MerchantStoreTypeEnum.getEnum(mpList.get(0).getManageType()));
    		}
    		
        }
		return bo;
	}

    @Override
    @Transactional
    public void updateKeywordsById(Long id, Long merchantId, String keywords) {
        MerchantStoreDO merchantStoreDO = new MerchantStoreDO();
        merchantStoreDO.setKeywords(keywords);
        MerchantStoreDOExample example = new MerchantStoreDOExample();
        example.createCriteria().andIdEqualTo(id).andMerchantIdEqualTo(merchantId);
        merchantStoreDOMapper.updateByExampleSelective(merchantStoreDO, example);

        SolrDocument solrDocument = solrService.getSolrDocsById(id, userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore(), userSrvConfig.getIsCloudSolr());
        if (solrDocument != null) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", solrDocument.get("id"));
            document.addField("merchantId_l", solrDocument.get("merchantId_l"));
            document.addField("name", solrDocument.get("name"));
            document.addField("regionPath_s", solrDocument.get("regionPath_s"));
            document.addField("latLon_p", solrDocument.get("latLon_p"));
            document.addField("industryPath_s", solrDocument.get("industryPath_s"));
            document.addField("industryName_s", solrDocument.get("industryName_s"));
            document.addField("storePic_s", solrDocument.get("storePic_s"));
            document.addField("averageConsumeAmount_d", solrDocument.get("averageConsumeAmount_d"));
            document.addField("averageScore_d", solrDocument.get("averageScore_d"));
            document.addField("favoriteNumber_i", solrDocument.get("favoriteNumber_i"));
            document.addField("discountOrdinal_d", solrDocument.get("discountOrdinal_d"));
            document.addField("favoreInfo_s", solrDocument.get("favoreInfo_s"));
            document.addField("discountPackage_s", solrDocument.get("discountPackage_s"));
            document.addField("keywords", keywords);
            solrService.addSolrDocs(document, userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore(), userSrvConfig.getIsCloudSolr());
        }
    }
}
