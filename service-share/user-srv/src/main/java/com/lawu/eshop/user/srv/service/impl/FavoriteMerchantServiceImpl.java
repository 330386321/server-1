package com.lawu.eshop.user.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.solr.SolrUtil;
import com.lawu.eshop.user.param.FavoriteMerchantParam;
import com.lawu.eshop.user.srv.bo.FavoriteMerchantBO;
import com.lawu.eshop.user.srv.converter.FavoriteMerchantConverter;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import com.lawu.eshop.user.srv.domain.FansMerchantDOExample;
import com.lawu.eshop.user.srv.domain.FavoriteMerchantDO;
import com.lawu.eshop.user.srv.domain.FavoriteMerchantDOExample;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDOExample;
import com.lawu.eshop.user.srv.domain.extend.FavoriteMerchantDOView;
import com.lawu.eshop.user.srv.mapper.FansMerchantDOMapper;
import com.lawu.eshop.user.srv.mapper.FavoriteMerchantDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.mapper.extend.FavoriteMerchantDOMapperExtend;
import com.lawu.eshop.user.srv.service.FavoriteMerchantService;
import com.lawu.eshop.utils.DistanceUtil;

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

    @Override
    @Transactional
    public Integer save(Long memberId, Long merchantId) {
        FavoriteMerchantDO favoriteMerchant = new FavoriteMerchantDO();
        favoriteMerchant.setMemberId(memberId);
        favoriteMerchant.setMerchantId(merchantId);
        favoriteMerchant.setGmtCreate(new Date());
        int row = favoriteMerchantDOMapper.insert(favoriteMerchant);
        MerchantStoreDOExample example = new MerchantStoreDOExample();
        example.createCriteria().andMerchantIdEqualTo(merchantId);
        List<MerchantStoreDO> list = merchantStoreDOMapper.selectByExample(example);
        if (!list.isEmpty()) {
            MerchantStoreDO merchantStoreDO = list.get(0);
            Integer count = merchantStoreDO.getFavoriteNumber();
            count += 1;
            merchantStoreDO.setFavoriteNumber(count);
            merchantStoreDOMapper.updateByPrimaryKeySelective(merchantStoreDO);

            //更新solr门店收藏人数
            SolrDocument solrDocument = SolrUtil.getSolrDocsById(list.get(0).getId(), SolrUtil.SOLR_MERCHANT_CORE);
            if (solrDocument != null) {
                SolrInputDocument document = MerchantStoreConverter.convertSolrInputDocument(solrDocument);
                document.addField("favoriteNumber_i", count);
                SolrUtil.addSolrDocs(document, SolrUtil.SOLR_MERCHANT_CORE);
            }
        }
        return row;
    }

    @Override
    @Transactional
    public Integer remove(Long merchantId,Long memberId) {
    	FavoriteMerchantDOExample example = new FavoriteMerchantDOExample();
        example.createCriteria().andMemberIdEqualTo(memberId).andMerchantIdEqualTo(merchantId);
        Integer i = favoriteMerchantDOMapper.deleteByExample(example);
        MerchantStoreDO merchantStoreDO = merchantStoreDOMapper.selectByPrimaryKey(merchantId);
        if (merchantStoreDO!=null) {
            Integer count = merchantStoreDO.getFavoriteNumber();
            count -= 1;
            merchantStoreDO.setFavoriteNumber(count);
            merchantStoreDOMapper.updateByPrimaryKeySelective(merchantStoreDO);

            //更新solr门店收藏人数
            SolrDocument solrDocument = SolrUtil.getSolrDocsById(merchantStoreDO.getId(), SolrUtil.SOLR_MERCHANT_CORE);
            if (solrDocument != null) {
                SolrInputDocument document = MerchantStoreConverter.convertSolrInputDocument(solrDocument);
                document.addField("favoriteNumber_i", count);
                SolrUtil.addSolrDocs(document, SolrUtil.SOLR_MERCHANT_CORE);
            }
        }
        return i;
    }

    @Override
    public Page<FavoriteMerchantBO> getMyFavoriteMerchant(Long memberId, FavoriteMerchantParam pageQuery) {
    	FavoriteMerchantDOView view=new FavoriteMerchantDOView();
    	view.setMemberId(memberId);
    	view.setType(pageQuery.getManageTypeEnum().val);
        RowBounds rowBounds = new RowBounds(pageQuery.getCurrentPage(), pageQuery.getPageSize());
        List<FavoriteMerchantDOView> list=favoriteMerchantDOMapperExtend.selectFavoriteMerchantByRowbounds(view, rowBounds);
        List<FavoriteMerchantBO> listBO=new ArrayList<>();
        for (FavoriteMerchantDOView favoriteMerchantDOView : list) {
        	FansMerchantDOExample  example=new FansMerchantDOExample();
        	example.createCriteria().andMerchantIdEqualTo(favoriteMerchantDOView.getMerchantId());
        	int count=fansMerchantDOMapper.countByExample(example);
        	FavoriteMerchantBO favoriteMerchantBO =new FavoriteMerchantBO();
        	favoriteMerchantBO.setFansCount(count);
        	favoriteMerchantBO=FavoriteMerchantConverter.convertListBO(favoriteMerchantDOView);
        	if(pageQuery.getLongitude()!=null && pageQuery.getLatitude()!=null){
				 int distance= DistanceUtil.getDistance(pageQuery.getLongitude(), pageQuery.getLatitude(), 
						 favoriteMerchantDOView.getLongitude().doubleValue(), favoriteMerchantDOView.getLatitude().doubleValue());
				 favoriteMerchantBO.setDistance(distance); 
        	}
        	
        	listBO.add(favoriteMerchantBO);
		}
        Page<FavoriteMerchantBO> page = new Page<FavoriteMerchantBO>();
        page.setTotalCount(list.size());
        page.setCurrentPage(pageQuery.getCurrentPage());
        page.setRecords(listBO);
        return page;
    }

}
