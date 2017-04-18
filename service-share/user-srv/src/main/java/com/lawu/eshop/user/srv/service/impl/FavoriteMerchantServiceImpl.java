package com.lawu.eshop.user.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.solr.SolrUtil;
import com.lawu.eshop.user.query.FavoriteMerchantParam;
import com.lawu.eshop.user.srv.bo.FavoriteMerchantBO;
import com.lawu.eshop.user.srv.converter.FavoriteMerchantConverter;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import com.lawu.eshop.user.srv.domain.FavoriteMerchantDO;
import com.lawu.eshop.user.srv.domain.FavoriteMerchantDOExample;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDOExample;
import com.lawu.eshop.user.srv.mapper.FavoriteMerchantDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.service.FavoriteMerchantService;
import org.apache.ibatis.session.RowBounds;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
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
    public Integer remove(Long id) {
        FavoriteMerchantDO favoriteMerchantDO = favoriteMerchantDOMapper.selectByPrimaryKey(id);
        Integer i = favoriteMerchantDOMapper.deleteByPrimaryKey(id);
        MerchantStoreDOExample example = new MerchantStoreDOExample();
        example.createCriteria().andMerchantIdEqualTo(favoriteMerchantDO.getMerchantId());
        List<MerchantStoreDO> list = merchantStoreDOMapper.selectByExample(example);
        if (!list.isEmpty()) {
            MerchantStoreDO merchantStoreDO = list.get(0);
            Integer count = merchantStoreDO.getFavoriteNumber();
            count -= 1;
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
        return i;
    }

    @Override
    public Page<FavoriteMerchantBO> getMyFavoriteMerchant(Long memberId, FavoriteMerchantParam pageQuery) {
        FavoriteMerchantDOExample example = new FavoriteMerchantDOExample();
        example.createCriteria().andMemberIdEqualTo(memberId);
        int totalCount = favoriteMerchantDOMapper.countByExample(example);
        RowBounds rowBounds = new RowBounds(pageQuery.getCurrentPage(), pageQuery.getPageSize());
        List<FavoriteMerchantDO> FMDOS = favoriteMerchantDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        List<MerchantStoreDO> MDOS = new ArrayList<MerchantStoreDO>();
        for (FavoriteMerchantDO favoriteMerchantDO : FMDOS) {
            MerchantStoreDOExample msExample = new MerchantStoreDOExample();
            msExample.createCriteria().andMerchantIdEqualTo(favoriteMerchantDO.getMerchantId())
                    .andStatusEqualTo(new Byte("1"));
            List<MerchantStoreDO> list = merchantStoreDOMapper.selectByExample(msExample);
            if (!list.isEmpty()) {
                MerchantStoreDO merchantStoreDO = list.get(0);
                MDOS.add(merchantStoreDO);
            }

        }
        List<FavoriteMerchantBO> fmBOS = FavoriteMerchantConverter.convertListBOS(MDOS, FMDOS);
        Page<FavoriteMerchantBO> page = new Page<FavoriteMerchantBO>();
        page.setTotalCount(totalCount);
        page.setCurrentPage(pageQuery.getCurrentPage());
        page.setRecords(fmBOS);
        return page;
    }

}
