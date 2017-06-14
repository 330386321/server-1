package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.solr.SolrUtil;
import com.lawu.eshop.user.param.FavoriteMerchantParam;
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
            SolrDocument solrDocument = SolrUtil.getSolrDocsById(list.get(0).getId(), userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore());
            if (solrDocument != null) {
                SolrInputDocument document = MerchantStoreConverter.convertSolrInputDocument(solrDocument);
                document.addField("favoriteNumber_i", count);
                SolrUtil.addSolrDocs(document, userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore());
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
            if(count<0){
            	count=0;
            }
            merchantStoreDO.setFavoriteNumber(count);
            merchantStoreDOMapper.updateByPrimaryKeySelective(merchantStoreDO);

            //更新solr门店收藏人数
            SolrDocument solrDocument = SolrUtil.getSolrDocsById(merchantStoreDO.getId(), userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore());
            if (solrDocument != null) {
                SolrInputDocument document = MerchantStoreConverter.convertSolrInputDocument(solrDocument);
                document.addField("favoriteNumber_i", count);
                SolrUtil.addSolrDocs(document, userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore());
            }
        }
        return i;
    }

    @Override
    public Page<FavoriteMerchantBO> getMyFavoriteMerchant(Long memberId, FavoriteMerchantParam pageQuery) {
    	FavoriteMerchantDOExample exmple=new FavoriteMerchantDOExample();
    	exmple.createCriteria().andMemberIdEqualTo(memberId);
    	FavoriteMerchantDOView view=new FavoriteMerchantDOView();
    	view.setMemberId(memberId);
    	view.setType(pageQuery.getManageTypeEnum().val);
        RowBounds rowBounds = new RowBounds(pageQuery.getOffset(), pageQuery.getPageSize());
        List<FavoriteMerchantDOView> list=favoriteMerchantDOMapperExtend.selectFavoriteMerchantByRowbounds(view, rowBounds);
        List<FavoriteMerchantBO> listBO=new ArrayList<>();
        for (FavoriteMerchantDOView favoriteMerchantDOView : list) {
        	//获取粉丝数量
        	FansMerchantDOExample  example=new FansMerchantDOExample();
        	example.createCriteria().andMerchantIdEqualTo(favoriteMerchantDOView.getMerchantId());
        	int count=fansMerchantDOMapper.countByExample(example);
        	FavoriteMerchantBO favoriteMerchantBO =new FavoriteMerchantBO();
        	favoriteMerchantBO.setFansCount(count);
        	favoriteMerchantBO=FavoriteMerchantConverter.convertListBO(favoriteMerchantDOView);
        	FansMerchantDOExample fmExample=new FansMerchantDOExample();
        	fmExample.createCriteria().andMerchantIdEqualTo(favoriteMerchantDOView.getMerchantId());
        	Integer fansCount=0;
        	fansCount=fansMerchantDOMapper.countByExample(fmExample);
        	favoriteMerchantBO.setFansCount(fansCount);
        	MerchantStoreDOExample merchantStoreDOExample=new MerchantStoreDOExample();
        	merchantStoreDOExample.createCriteria().andMerchantIdEqualTo(favoriteMerchantDOView.getMerchantId());
        	List<MerchantStoreDO> merchantStoreDOList=merchantStoreDOMapper.selectByExample(merchantStoreDOExample);
        	if(!merchantStoreDOList.isEmpty()){ //获取距离
        		favoriteMerchantBO.setMerchantStoreId(merchantStoreDOList.get(0).getId());
        		if(pageQuery.getLongitude()!=null && pageQuery.getLatitude()!=null){
        			 int distance= DistanceUtil.getDistance(pageQuery.getLongitude(), pageQuery.getLatitude(), 
            				 merchantStoreDOList.get(0).getLongitude().doubleValue(),  merchantStoreDOList.get(0).getLatitude().doubleValue());
            		 favoriteMerchantBO.setDistance(distance);
        		}
        		
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
        Page<FavoriteMerchantBO> page = new Page<FavoriteMerchantBO>();
        page.setTotalCount(favoriteMerchantDOMapper.countByExample(exmple));
        page.setCurrentPage(pageQuery.getCurrentPage());
        page.setRecords(listBO);
        return page;
    }

}
