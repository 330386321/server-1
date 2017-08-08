package com.lawu.eshop.mall.srv.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        SolrDocument solrDocument = solrService.getSolrDocsById(storeId, mallSrvConfig.getSolrUrl(), mallSrvConfig.getSolrMerchantCore(), mallSrvConfig.getIsCloudSolr());
        if (solrDocument == null) {
            return i;
        }
        String favoreInfo = "";
        double discountOrdinal = 1.0;
        if (param.getTypeEnum().val.byteValue() == MerchantFavoredTypeEnum.TYPE_FULL.val) {
            favoreInfo = "买单每满" + param.getReachAmount().intValue() + "减" + param.getFavoredAmount().intValue() + "元";
            discountOrdinal = (param.getReachAmount().subtract(param.getFavoredAmount())).divide(param.getReachAmount(), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } else if (param.getTypeEnum().val.byteValue() == MerchantFavoredTypeEnum.TYPE_FULL_REDUCE.val) {
            favoreInfo = "买单满" + param.getReachAmount().intValue() + "减" + param.getFavoredAmount().intValue() + "元";
            discountOrdinal = (param.getReachAmount().subtract(param.getFavoredAmount())).divide(param.getReachAmount(), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } else if (param.getTypeEnum().val.byteValue() == MerchantFavoredTypeEnum.TYPE_DISCOUNT.val) {
            NumberFormat numberFormat = NumberFormat.getInstance();
            favoreInfo = "买单" + numberFormat.format(param.getDiscountRate()) + "折";
            discountOrdinal = param.getDiscountRate().divide(BigDecimal.valueOf(10), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        SolrInputDocument document = SolrDocumentConverter.converFavoredSolrInputDocument(solrDocument);
        document.addField("favoreInfo_s", favoreInfo);
        document.addField("discountOrdinal_d", discountOrdinal);
        solrService.addSolrDocs(document, mallSrvConfig.getSolrUrl(), mallSrvConfig.getSolrMerchantCore(), mallSrvConfig.getIsCloudSolr());
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
        SolrInputDocument document = SolrDocumentConverter.converFavoredSolrInputDocument(solrDocument);
        document.addField("favoreInfo_s", "");
        document.addField("discountOrdinal_d", 1.0);
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

        SolrDocument solrDocument = solrService.getSolrDocsById(storeId, mallSrvConfig.getSolrUrl(), mallSrvConfig.getSolrMerchantCore(), mallSrvConfig.getIsCloudSolr());
        if (solrDocument == null) {
            return i;
        }
        String favoreInfo = "";
        double discountOrdinal = 1.0;
        if (param.getTypeEnum().val.byteValue() == MerchantFavoredTypeEnum.TYPE_FULL.val) {
            favoreInfo = "买单每满" + param.getReachAmount().intValue() + "减" + param.getFavoredAmount().intValue() + "元";
            discountOrdinal = (param.getReachAmount().subtract(param.getFavoredAmount())).divide(param.getReachAmount(), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } else if (param.getTypeEnum().val.byteValue() == MerchantFavoredTypeEnum.TYPE_FULL_REDUCE.val) {
            favoreInfo = "买单满" + param.getReachAmount().intValue() + "减" + param.getFavoredAmount().intValue() + "元";
            discountOrdinal = (param.getReachAmount().subtract(param.getFavoredAmount())).divide(param.getReachAmount(), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } else if (param.getTypeEnum().val.byteValue() == MerchantFavoredTypeEnum.TYPE_DISCOUNT.val) {
            NumberFormat numberFormat = NumberFormat.getInstance();
            favoreInfo = "买单" + numberFormat.format(param.getDiscountRate()) + "折";
            discountOrdinal = param.getDiscountRate().divide(BigDecimal.valueOf(10), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        SolrInputDocument document = SolrDocumentConverter.converFavoredSolrInputDocument(solrDocument);
        document.addField("favoreInfo_s", favoreInfo);
        document.addField("discountOrdinal_d", discountOrdinal);
        solrService.addSolrDocs(document, mallSrvConfig.getSolrUrl(), mallSrvConfig.getSolrMerchantCore(), mallSrvConfig.getIsCloudSolr());
        return i;
    }
}
