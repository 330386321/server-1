package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.mall.constants.MerchantFavoredTypeEnum;
import com.lawu.eshop.mall.constants.StatusEnum;
import com.lawu.eshop.mall.param.MerchantFavoredParam;
import com.lawu.eshop.mall.srv.MallSrvConfig;
import com.lawu.eshop.mall.srv.bo.MerchantFavoredBO;
import com.lawu.eshop.mall.srv.converter.MerchantFavoredConverter;
import com.lawu.eshop.mall.srv.converter.SolrDocumentConverter;
import com.lawu.eshop.mall.srv.domain.MerchantFavoredDO;
import com.lawu.eshop.mall.srv.domain.MerchantFavoredDOExample;
import com.lawu.eshop.mall.srv.mapper.MerchantFavoredDOMapper;
import com.lawu.eshop.mall.srv.service.MerchantFavoredService;
import com.lawu.eshop.solr.service.SolrService;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/10.
 */
@Service
public class MerchantFavoredServiceImpl implements MerchantFavoredService {
    @Autowired
    private MerchantFavoredDOMapper merchantFavoredDOMapper;

    @Autowired
    private SolrService solrService;

    @Autowired
    private MallSrvConfig mallSrvConfig;

    @Override
    @Transactional
    public Integer saveMerchantFavoredInfo(Long merchantId, Long storeId, MerchantFavoredParam param) {
        MerchantFavoredDO merchantFavoredDO = new MerchantFavoredDO();
        merchantFavoredDO.setMerchantId(merchantId);
        merchantFavoredDO.setType(param.getTypeEnum().val);
        if (param.getReachAmount() != null) {
            merchantFavoredDO.setReachAmount(param.getReachAmount());
        }
        if (param.getFavoredAmount() != null) {
            merchantFavoredDO.setFavoredAmount(param.getFavoredAmount());
        }
        if (param.getDiscountRate() != null) {
            merchantFavoredDO.setDiscountRate(param.getDiscountRate());
        }
        merchantFavoredDO.setValidWeekTime(param.getValidWeekTime());
        merchantFavoredDO.setValidDayBeginTime(param.getValidDayBeginTime());
        merchantFavoredDO.setValidDayEndTime(param.getValidDayEndTime());
        merchantFavoredDO.setEntireBeginTime(param.getEntireBeginTime());
        merchantFavoredDO.setEntireEndTime(param.getEntireEndTime());
        merchantFavoredDO.setStatus(StatusEnum.STATUS_VALID.val);
        merchantFavoredDO.setGmtCreate(new Date());
        merchantFavoredDO.setGmtModified(new Date());
        int i = merchantFavoredDOMapper.insert(merchantFavoredDO);
        if (i > 0) {
            SolrDocument solrDocument = solrService.getSolrDocsById(storeId, mallSrvConfig.getSolrUrl(), mallSrvConfig.getSolrMerchantCore(), mallSrvConfig.getIsCloudSolr());
            if (solrDocument == null) {
                return i;
            }
            String favoreInfo = "";
            if (param.getTypeEnum().val.byteValue() == MerchantFavoredTypeEnum.TYPE_FULL.val) {
                favoreInfo = "买单每满" + param.getReachAmount() + "减" + param.getFavoredAmount() + "元";
            } else if (param.getTypeEnum().val.byteValue() == MerchantFavoredTypeEnum.TYPE_FULL_REDUCE.val) {
                favoreInfo = "买单满" + param.getReachAmount() + "减" + param.getFavoredAmount() + "元";
            } else if (param.getTypeEnum().val.byteValue() == MerchantFavoredTypeEnum.TYPE_DISCOUNT.val) {
                favoreInfo = "买单" + param.getDiscountRate() + "折";
            }
            SolrInputDocument document = SolrDocumentConverter.converSolrInputDocument(solrDocument);
            document.addField("favoreInfo_s", favoreInfo);
            solrService.addSolrDocs(document, mallSrvConfig.getSolrUrl(), mallSrvConfig.getSolrMerchantCore(), mallSrvConfig.getIsCloudSolr());
        }
        return i;
    }

    @Override
    public MerchantFavoredBO findFavoredByMerchantId(Long merchantId) {
        MerchantFavoredDOExample example = new MerchantFavoredDOExample();
        example.createCriteria().andMerchantIdEqualTo(merchantId).andStatusEqualTo(StatusEnum.STATUS_VALID.val);
        List<MerchantFavoredDO> merchantFavoredDOS = merchantFavoredDOMapper.selectByExample(example);
        if (!merchantFavoredDOS.isEmpty()) {
            return MerchantFavoredConverter.coverBO(merchantFavoredDOS.get(0));
        }
        return null;
    }

    @Override
    @Transactional
    public void delMerchantFavoredInfoById(Long id, Long merchantId, Long storeId) {
        MerchantFavoredDO merchantFavoredDO = new MerchantFavoredDO();
        merchantFavoredDO.setStatus(StatusEnum.STATUS_INVALID.val);
        MerchantFavoredDOExample example = new MerchantFavoredDOExample();
        example.createCriteria().andIdEqualTo(id).andMerchantIdEqualTo(merchantId);
        merchantFavoredDOMapper.updateByExampleSelective(merchantFavoredDO,example);

        SolrDocument solrDocument = solrService.getSolrDocsById(storeId, mallSrvConfig.getSolrUrl(), mallSrvConfig.getSolrMerchantCore(), mallSrvConfig.getIsCloudSolr());
        if (solrDocument == null) {
            return;
        }
        SolrInputDocument document = SolrDocumentConverter.converSolrInputDocument(solrDocument);
        document.addField("favoreInfo_s", "");
        solrService.addSolrDocs(document, mallSrvConfig.getSolrUrl(), mallSrvConfig.getSolrMerchantCore(), mallSrvConfig.getIsCloudSolr());
    }

    @Override
    public MerchantFavoredBO findFavoredById(Long id) {
        MerchantFavoredDOExample example = new MerchantFavoredDOExample();
        example.createCriteria().andIdEqualTo(id).andStatusEqualTo(StatusEnum.STATUS_VALID.val);
        List<MerchantFavoredDO> merchantFavoredDOS = merchantFavoredDOMapper.selectByExample(example);
        if (!merchantFavoredDOS.isEmpty()) {
            return MerchantFavoredConverter.coverBO(merchantFavoredDOS.get(0));
        }
        return null;
    }

    @Override
    public Integer updateMerchantFavoredInfo(Long merchantId, Long storeId, MerchantFavoredParam param) {
        MerchantFavoredDOExample example = new MerchantFavoredDOExample();
        example.createCriteria().andMerchantIdEqualTo(merchantId);
        MerchantFavoredDO merchantFavoredDO = new MerchantFavoredDO();
        merchantFavoredDO.setType(param.getTypeEnum().val);
        if (param.getReachAmount() != null && param.getFavoredAmount() != null) {
            merchantFavoredDO.setReachAmount(param.getReachAmount());
            merchantFavoredDO.setFavoredAmount(param.getFavoredAmount());
            merchantFavoredDO.setDiscountRate(null);
        }
        if (param.getDiscountRate() != null) {
            merchantFavoredDO.setDiscountRate(param.getDiscountRate());
            merchantFavoredDO.setReachAmount(null);
            merchantFavoredDO.setFavoredAmount(null);
        }
        merchantFavoredDO.setValidWeekTime(param.getValidWeekTime());
        merchantFavoredDO.setValidDayBeginTime(param.getValidDayBeginTime());
        merchantFavoredDO.setValidDayEndTime(param.getValidDayEndTime());
        merchantFavoredDO.setEntireBeginTime(param.getEntireBeginTime());
        merchantFavoredDO.setEntireEndTime(param.getEntireEndTime());
        merchantFavoredDO.setGmtModified(new Date());
        int i = merchantFavoredDOMapper.updateByExampleSelective(merchantFavoredDO, example);
        if (i > 0) {
            SolrDocument solrDocument = solrService.getSolrDocsById(storeId, mallSrvConfig.getSolrUrl(), mallSrvConfig.getSolrMerchantCore(), mallSrvConfig.getIsCloudSolr());
            if (solrDocument == null) {
                return i;
            }
            String favoreInfo = "";
            if (param.getTypeEnum().val.byteValue() == MerchantFavoredTypeEnum.TYPE_FULL.val) {
                favoreInfo = "买单每满" + param.getReachAmount() + "减" + param.getFavoredAmount() + "元";
            } else if (param.getTypeEnum().val.byteValue() == MerchantFavoredTypeEnum.TYPE_FULL_REDUCE.val) {
                favoreInfo = "买单满" + param.getReachAmount() + "减" + param.getFavoredAmount() + "元";
            } else if (param.getTypeEnum().val.byteValue() == MerchantFavoredTypeEnum.TYPE_DISCOUNT.val) {
                favoreInfo = "买单" + param.getDiscountRate() + "折";
            }
            SolrInputDocument document = SolrDocumentConverter.converSolrInputDocument(solrDocument);
            document.addField("favoreInfo_s", favoreInfo);
            solrService.addSolrDocs(document, mallSrvConfig.getSolrUrl(), mallSrvConfig.getSolrMerchantCore(), mallSrvConfig.getIsCloudSolr());
        }
        return i;
    }
}
