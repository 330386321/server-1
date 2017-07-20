package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.solr.SolrUtil;
import com.lawu.eshop.user.param.FavoriteMerchantParam;
import com.lawu.eshop.user.param.FavoriteStoreParam;
import com.lawu.eshop.user.srv.UserSrvConfig;
import com.lawu.eshop.user.srv.bo.FavoriteMerchantBO;
import com.lawu.eshop.user.srv.converter.FavoriteMerchantConverter;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import com.lawu.eshop.user.srv.domain.*;
import com.lawu.eshop.user.srv.domain.extend.FavoriteMerchantDOView;
import com.lawu.eshop.user.srv.mapper.FansMerchantDOMapper;
import com.lawu.eshop.user.srv.mapper.FavoriteMerchantDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreImageDOMapper;
import com.lawu.eshop.user.srv.mapper.extend.FavoriteMerchantDOMapperExtend;
import com.lawu.eshop.user.srv.service.FavoriteMerchantService;
import com.lawu.eshop.utils.DistanceUtil;
import org.apache.ibatis.session.RowBounds;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FavoriteMerchantServiceImpl implements FavoriteMerchantService {

    @Resource
    private FavoriteMerchantDOMapper favoriteMerchantDOMapper;

    @Resource
    private MerchantStoreDOMapper merchantStoreDOMapper;
    
    @Resource
    private FavoriteMerchantDOMapperExtend favoriteMerchantDOMapperExtend;
    
    @Resource
    private FansMerchantDOMapper fansMerchantDOMapper;
    
    @Resource
    private MerchantStoreImageDOMapper  merchantStoreImageDOMapper;

    @Autowired
    private UserSrvConfig userSrvConfig;

    @Override
    @Transactional
    public Integer save(Long memberId, FavoriteStoreParam param) {
        FavoriteMerchantDO favoriteMerchant = new FavoriteMerchantDO();
        favoriteMerchant.setMemberId(memberId);
        favoriteMerchant.setMerchantId(param.getMerchantId());
        favoriteMerchant.setManageType(param.getManageTypeEnum().val);
        favoriteMerchant.setGmtCreate(new Date());
        int row = favoriteMerchantDOMapper.insert(favoriteMerchant);
        MerchantStoreDOExample example = new MerchantStoreDOExample();
        example.createCriteria().andMerchantIdEqualTo(param.getMerchantId());
        List<MerchantStoreDO> list = merchantStoreDOMapper.selectByExample(example);
        if (!list.isEmpty()) {
            MerchantStoreDO merchantStoreDO = list.get(0);
            Integer count = merchantStoreDO.getFavoriteNumber();
            count += 1;
            merchantStoreDO.setFavoriteNumber(count);
            merchantStoreDOMapper.updateByPrimaryKeySelective(merchantStoreDO);

            //更新solr门店收藏人数
            SolrDocument solrDocument = SolrUtil.getSolrDocsById(list.get(0).getId(), userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore(), userSrvConfig.getIsCloudSolr());
            if (solrDocument != null) {
                SolrInputDocument document = MerchantStoreConverter.convertSolrInputDocument(solrDocument);
                document.addField("favoriteNumber_i", count);
                SolrUtil.addSolrDocs(document, userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore(), userSrvConfig.getIsCloudSolr());
            }
        }
        return row;
    }

    @Override
    @Transactional
    public Integer remove(FavoriteStoreParam param,Long memberId) {
    	FavoriteMerchantDOExample example = new FavoriteMerchantDOExample();
        example.createCriteria().andMemberIdEqualTo(memberId).andMerchantIdEqualTo(param.getMerchantId()).andManageTypeEqualTo(param.getManageTypeEnum().val);
        Integer i = favoriteMerchantDOMapper.deleteByExample(example);
        MerchantStoreDOExample storeDOExample = new MerchantStoreDOExample();
        storeDOExample.createCriteria().andMerchantIdEqualTo(param.getMerchantId());
        List<MerchantStoreDO> merchantStoreDOS = merchantStoreDOMapper.selectByExample(storeDOExample);
        if (!merchantStoreDOS.isEmpty()) {
            MerchantStoreDO merchantStoreDO = merchantStoreDOS.get(0);
            Integer count = merchantStoreDO.getFavoriteNumber();
            count -= 1;
            if(count<0){
            	count=0;
            }
            merchantStoreDO.setFavoriteNumber(count);
            merchantStoreDOMapper.updateByPrimaryKeySelective(merchantStoreDO);

            //更新solr门店收藏人数
            SolrDocument solrDocument = SolrUtil.getSolrDocsById(merchantStoreDO.getId(), userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore(), userSrvConfig.getIsCloudSolr());
            if (solrDocument != null) {
                SolrInputDocument document = MerchantStoreConverter.convertSolrInputDocument(solrDocument);
                document.addField("favoriteNumber_i", count);
                SolrUtil.addSolrDocs(document, userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore(), userSrvConfig.getIsCloudSolr());
            }
        }
        return i;
    }

    @Override
    public Page<FavoriteMerchantBO> getMyFavoriteMerchant(Long memberId, FavoriteMerchantParam pageQuery) {
    	FavoriteMerchantDOExample exmple=new FavoriteMerchantDOExample();
    	exmple.createCriteria().andMemberIdEqualTo(memberId).andManageTypeEqualTo(pageQuery.getManageTypeEnum().val);
    	FavoriteMerchantDOView view=new FavoriteMerchantDOView();
    	view.setMemberId(memberId);
    	view.setType(pageQuery.getManageTypeEnum().val);
        RowBounds rowBounds = new RowBounds(pageQuery.getOffset(), pageQuery.getPageSize());
        List<FavoriteMerchantDOView> list=favoriteMerchantDOMapperExtend.selectFavoriteMerchantByRowbounds(view, rowBounds);
        List<FavoriteMerchantBO> listBO=new ArrayList<>();
        for (FavoriteMerchantDOView favoriteMerchantDOView : list) {
        	FavoriteMerchantBO favoriteMerchantBO=FavoriteMerchantConverter.convertListBO(favoriteMerchantDOView);
    		if(pageQuery.getLongitude()!=null && pageQuery.getLatitude()!=null){
    			 int distance= DistanceUtil.getDistance(pageQuery.getLongitude(), pageQuery.getLatitude(), 
    					 favoriteMerchantDOView.getLongitude().doubleValue(),  favoriteMerchantDOView.getLatitude().doubleValue());
        		 favoriteMerchantBO.setDistance(distance);
    		}
        	//获取门店logo
        	MerchantStoreImageDOExample msidExample=new MerchantStoreImageDOExample();
        	msidExample.createCriteria().andMerchantIdEqualTo(favoriteMerchantDOView.getMerchantId()).andStatusEqualTo(true).andTypeEqualTo(new Byte("3"));
        	List<MerchantStoreImageDO>  msiList= merchantStoreImageDOMapper.selectByExample(msidExample);
        	if(!msiList.isEmpty()){
        		favoriteMerchantBO.setPath(msiList.get(0).getPath());
        	}
        	listBO.add(favoriteMerchantBO);
		}
        Page<FavoriteMerchantBO> page = new Page<>();
        Long count=favoriteMerchantDOMapper.countByExample(exmple);
        page.setTotalCount(count.intValue());
        page.setCurrentPage(pageQuery.getCurrentPage());
        page.setRecords(listBO);
        return page;
    }

	@Override
	public Boolean get(Long memberId, FavoriteStoreParam pageQuery) {
		FavoriteMerchantDOExample exmple=new FavoriteMerchantDOExample();
    	exmple.createCriteria().andMemberIdEqualTo(memberId).andManageTypeEqualTo(pageQuery.getManageTypeEnum().val);
    	long count = favoriteMerchantDOMapper.countByExample(exmple);
    	return count > 0;
	}

}
