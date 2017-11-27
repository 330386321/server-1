package com.lawu.eshop.user.srv.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.lawu.eshop.user.srv.bo.MerchantAdInfoBO;
import com.lawu.eshop.user.srv.bo.MerchantInfoBO;
import com.lawu.eshop.user.srv.bo.MerchantStoreAdInfoBO;
import com.lawu.eshop.user.srv.bo.MerchantStoreBO;
import com.lawu.eshop.user.srv.bo.MerchantStoreFavorInfoBO;
import com.lawu.eshop.user.srv.bo.MerchantStoreStatusBO;
import com.lawu.eshop.user.srv.bo.NewMerchantStoreBO;
import com.lawu.eshop.user.srv.bo.RecommendFoodBO;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import com.lawu.eshop.user.srv.domain.MerchantDO;
import com.lawu.eshop.user.srv.domain.MerchantDOExample;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDOExample;
import com.lawu.eshop.user.srv.domain.MerchantStoreImageDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreImageDOExample;
import com.lawu.eshop.user.srv.domain.MerchantStoreProfileDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreProfileDOExample;
import com.lawu.eshop.user.srv.domain.extend.MerchantAdInfoView;
import com.lawu.eshop.user.srv.domain.extend.NewMerchantStoreDOView;
import com.lawu.eshop.user.srv.domain.extend.RecommendFoodDOview;
import com.lawu.eshop.user.srv.mapper.MerchantDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreImageDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreProfileDOMapper;
import com.lawu.eshop.user.srv.mapper.extend.MerchantStoreDOMapperExtend;
import com.lawu.eshop.user.srv.service.MerchantStoreService;

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
        if(list.isEmpty()){
            return null;
        }

        MerchantStoreDO merchantStoreDO = list.get(0);
        MerchantStoreProfileDOExample mpExample = new  MerchantStoreProfileDOExample();
        mpExample.createCriteria().andMerchantIdEqualTo(list.get(0).getMerchantId());
        List<MerchantStoreProfileDO>  mpList = merchantStoreProfileDOMapper.selectByExample(mpExample);

        MerchantStoreBO bo = MerchantStoreConverter.convertStoreBO(merchantStoreDO);
		if(!mpList.isEmpty()){
			bo.setManageTypeEnum(MerchantStoreTypeEnum.getEnum(mpList.get(0).getManageType()));
		}
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
                document.addField("favoreEndTime_s", param.getFavoreEndTime());
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
            MerchantDO merchantDO = merchantDOMapper.selectByPrimaryKey(merchantStoreDO.getMerchantId());
            if (merchantDO.getIsFreeze()) {//冻结账户
                storeStatusBO.setExist(false);
                return storeStatusBO;
            }
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
            bo.setReginPath(merchantStoreDO.getRegionPath());
    		if(!mpList.isEmpty()){
    			bo.setManageType(MerchantStoreTypeEnum.getEnum(mpList.get(0).getManageType()));
    		}

        }
		return bo;
	}

	@Override
	public String getPrincipalName(Long merchantId) {
		MerchantStoreDOExample example = new MerchantStoreDOExample();
        example.createCriteria().andMerchantIdEqualTo(merchantId);
        List<MerchantStoreDO> list = merchantStoreDOMapper.selectByExample(example);
        if(!list.isEmpty()){
        	return list.get(0).getPrincipalName();
        }
		return null;
	}

	@Override
	public MerchantStoreFavorInfoBO selectMerchantStoreFavor(Long merchantId) {
		MerchantStoreDOExample example = new MerchantStoreDOExample();
        example.createCriteria().andMerchantIdEqualTo(merchantId);
        List<MerchantStoreDO> list = merchantStoreDOMapper.selectByExample(example);
        MerchantStoreFavorInfoBO info = new MerchantStoreFavorInfoBO();
        
        MerchantDO merchantDO =  merchantDOMapper.selectByPrimaryKey(merchantId);
        info.setUserNum(merchantDO.getNum());
        
        if(!list.isEmpty()){
        	info.setName(list.get(0).getName());
        	MerchantStoreImageDOExample  msiExample = new MerchantStoreImageDOExample();
        	msiExample.createCriteria().andMerchantIdEqualTo(merchantId).andTypeEqualTo(MerchantStoreImageEnum.STORE_IMAGE_STORE.val);
        	List<MerchantStoreImageDO> imgs = merchantStoreImageDOMapper.selectByExample(msiExample);
        	if(!imgs.isEmpty()){
        		info.setPicStore(imgs.get(0).getPath());
        	}
        }
		return info;
	}

}
