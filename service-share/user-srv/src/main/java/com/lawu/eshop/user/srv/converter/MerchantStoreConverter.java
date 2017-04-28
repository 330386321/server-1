package com.lawu.eshop.user.srv.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.BeanUtils;

import com.lawu.eshop.user.dto.CertifTypeEnum;
import com.lawu.eshop.user.dto.MerchantStatusEnum;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.dto.MerchantStoreNoReasonReturnDTO;
import com.lawu.eshop.user.dto.MerchantStoreTypeEnum;
import com.lawu.eshop.user.dto.ShoppingStoreDetailDTO;
import com.lawu.eshop.user.dto.StoreDetailDTO;
import com.lawu.eshop.user.dto.StoreSolrDTO;
import com.lawu.eshop.user.param.MerchantStoreParam;
import com.lawu.eshop.user.srv.bo.MerchantStoreBO;
import com.lawu.eshop.user.srv.bo.MerchantStoreInfoBO;
import com.lawu.eshop.user.srv.bo.MerchantStoreNoReasonReturnBO;
import com.lawu.eshop.user.srv.bo.MerchantStoreProfileBO;
import com.lawu.eshop.user.srv.bo.ShoppingStoreDetailBO;
import com.lawu.eshop.user.srv.bo.StoreDetailBO;
import com.lawu.eshop.user.srv.domain.MerchantDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreProfileDO;
import com.lawu.eshop.utils.DataTransUtil;

/**
 * 商家门店信息转换
 * Created by Administrator on 2017/3/24.
 */
public class MerchantStoreConverter {
    /**
     * BO转换
     *
     * @param merchantStoreProfileDO
     * @return
     */
    public static MerchantStoreProfileBO convertBO(MerchantStoreProfileDO merchantStoreProfileDO) {
        if (merchantStoreProfileDO == null) {
            return null;
        }

        MerchantStoreProfileBO merchantStoreProfileBO = new MerchantStoreProfileBO();
        merchantStoreProfileBO.setMerchantId(merchantStoreProfileDO.getMerchantId());
        merchantStoreProfileBO.setRegNumber(merchantStoreProfileDO.getRegNumber());
        merchantStoreProfileBO.setOperatorCardId(merchantStoreProfileDO.getOperatorCardId());
        merchantStoreProfileBO.setManageType(merchantStoreProfileDO.getManageType());
        merchantStoreProfileBO.setCertifType(merchantStoreProfileDO.getCertifType());
        return merchantStoreProfileBO;

    }

    public static MerchantStoreInfoBO coverter(MerchantStoreDO merchantStoreDO) {
        if (merchantStoreDO == null) {
            return null;
        }
        MerchantStoreInfoBO merchantStoreInfoBO = new MerchantStoreInfoBO();
        merchantStoreInfoBO.setMerchantStoreId(merchantStoreDO.getId());
        merchantStoreInfoBO.setName(merchantStoreDO.getName());
        merchantStoreInfoBO.setPrincipalName(merchantStoreDO.getPrincipalName());
        merchantStoreInfoBO.setRegionPath(merchantStoreDO.getRegionPath());
        merchantStoreInfoBO.setAddress(merchantStoreDO.getAddress());
        merchantStoreInfoBO.setIndustryPath(merchantStoreDO.getIndustryPath());
        merchantStoreInfoBO.setLongitude(merchantStoreDO.getLongitude());
        merchantStoreInfoBO.setLatitude(merchantStoreDO.getLatitude());
        merchantStoreInfoBO.setIntro(merchantStoreDO.getIntro());
        merchantStoreInfoBO.setPrincipalMobile(merchantStoreDO.getPrincipalMobile());
        merchantStoreInfoBO.setIsNoReasonReturn(merchantStoreDO.getIsNoReasonReturn());
        merchantStoreInfoBO.setStatusEnum(MerchantStatusEnum.getEnum(merchantStoreDO.getStatus()));
        merchantStoreInfoBO.setRegionName(merchantStoreDO.getRegionName());
        merchantStoreInfoBO.setIndustryName(merchantStoreDO.getIndustryName());
        return merchantStoreInfoBO;
    }

    /**
     * BO转DTO
     *
     * @param merchantStoreInfoBO
     * @return
     */
    public static MerchantStoreDTO coverDTO(MerchantStoreInfoBO merchantStoreInfoBO) {
        if (merchantStoreInfoBO == null) {
            return null;
        }
        MerchantStoreDTO merchantStoreDTO = new MerchantStoreDTO();
        merchantStoreDTO.setMerchantStoreId(merchantStoreInfoBO.getMerchantStoreId());
        merchantStoreDTO.setName(merchantStoreInfoBO.getName());
        merchantStoreDTO.setPrincipalName(merchantStoreInfoBO.getPrincipalName());
        merchantStoreDTO.setRegionPath(merchantStoreInfoBO.getRegionPath());
        merchantStoreDTO.setAddress(merchantStoreInfoBO.getAddress());
        merchantStoreDTO.setIndustryPath(merchantStoreInfoBO.getIndustryPath());
        merchantStoreDTO.setLongitude(merchantStoreInfoBO.getLongitude());
        merchantStoreDTO.setLatitude(merchantStoreInfoBO.getLatitude());
        merchantStoreDTO.setIntro(merchantStoreInfoBO.getIntro());
        merchantStoreDTO.setPrincipalMobile(merchantStoreInfoBO.getPrincipalMobile());

        merchantStoreDTO.setCompanyAddress(merchantStoreInfoBO.getCompanyAddress());
        merchantStoreDTO.setCompanyName(merchantStoreInfoBO.getCompanyName());
        merchantStoreDTO.setRegNumber(merchantStoreInfoBO.getRegNumber());
        merchantStoreDTO.setLicenseIndate(merchantStoreInfoBO.getLicenseIndate());
        merchantStoreDTO.setManageType(MerchantStoreTypeEnum.getEnum(merchantStoreInfoBO.getManageType()));
        merchantStoreDTO.setCertifType(CertifTypeEnum.getEnum(merchantStoreInfoBO.getCertifType()));
        merchantStoreDTO.setOperatorCardId(merchantStoreInfoBO.getOperatorCardId());
        merchantStoreDTO.setOperatorName(merchantStoreInfoBO.getOperatorName());

        merchantStoreDTO.setEnvironmentUrl(merchantStoreInfoBO.getEnvironmentUrl());
        merchantStoreDTO.setIdcardUrl(merchantStoreInfoBO.getIdcardUrl());
        merchantStoreDTO.setLicenseUrl(merchantStoreInfoBO.getLicenseUrl());
        merchantStoreDTO.setLogoUrl(merchantStoreInfoBO.getLogoUrl());
        merchantStoreDTO.setOtherUrl(merchantStoreInfoBO.getOtherUrl());
        merchantStoreDTO.setStoreUrl(merchantStoreInfoBO.getStoreUrl());
        merchantStoreDTO.setAuditSuccess(merchantStoreInfoBO.isAuditSuccess());
        merchantStoreDTO.setMerchantStatus(merchantStoreInfoBO.getStatusEnum());
        merchantStoreDTO.setRegionName(merchantStoreInfoBO.getRegionName());
        merchantStoreDTO.setIndustryName(merchantStoreInfoBO.getIndustryName());

        return merchantStoreDTO;
    }

    /**
     * 门店参数对象转DO
     *
     * @param merchantStoreParam
     * @param type
     * @return
     */
    public static Object couverDOByParam(MerchantStoreParam merchantStoreParam, int type) {
        if (merchantStoreParam == null) {
            return null;
        }
        if (type == 1) {//商店基本信息转换DO
            MerchantStoreDO merchantStoreDO = new MerchantStoreDO();
            merchantStoreDO.setIndustryPath(merchantStoreParam.getIndustryPath());
            merchantStoreDO.setPrincipalName(merchantStoreParam.getPrincipalName());
            merchantStoreDO.setName(merchantStoreParam.getName());
            merchantStoreDO.setAddress(merchantStoreParam.getAddress());
            merchantStoreDO.setIntro(merchantStoreParam.getIntro());
            merchantStoreDO.setLatitude(merchantStoreParam.getLatitude());
            merchantStoreDO.setLongitude(merchantStoreParam.getLongitude());
            merchantStoreDO.setPrincipalMobile(merchantStoreParam.getPrincipalMobile());
            merchantStoreDO.setRegionPath(merchantStoreParam.getRegionPath());
            merchantStoreDO.setRegionName(merchantStoreParam.getRegionName());
            merchantStoreDO.setIndustryName(merchantStoreParam.getIndustryName());

            return merchantStoreDO;
        } else if (type == 2) {
            MerchantStoreProfileDO merchantStoreProfileDO = new MerchantStoreProfileDO();

            merchantStoreProfileDO.setCertifType(merchantStoreParam.getCertifType().val);
            merchantStoreProfileDO.setCompanyAddress(merchantStoreParam.getCompanyAddress());
            merchantStoreProfileDO.setLicenseIndate(merchantStoreParam.getLicenseIndate());
            merchantStoreProfileDO.setOperatorCardId(merchantStoreParam.getOperatorCardId());
            merchantStoreProfileDO.setCompanyName(merchantStoreParam.getCompanyName());
            merchantStoreProfileDO.setManageType(merchantStoreParam.getManageType().val);
            merchantStoreProfileDO.setOperatorName(merchantStoreParam.getOperatorName());
            merchantStoreProfileDO.setRegNumber(merchantStoreParam.getRegNumber());

            return merchantStoreProfileDO;
        } else {

            return null;
        }
    }

    /**
     * DTO转换
     *
     * @param storeDetailBO
     * @return
     */
    public static StoreDetailDTO convertDTO(StoreDetailBO storeDetailBO) {
        if (storeDetailBO == null) {
            return null;
        }

        StoreDetailDTO storeDetailDTO = new StoreDetailDTO();
        storeDetailDTO.setMerchantId(storeDetailBO.getMerchantId());
        storeDetailDTO.setName(storeDetailBO.getName());
        storeDetailDTO.setAddress(storeDetailBO.getAddress());
        storeDetailDTO.setPrincipalMobile(storeDetailBO.getPrincipalMobile());
        storeDetailDTO.setStorePic(storeDetailBO.getStorePic());
        storeDetailDTO.setPicCount(storeDetailBO.getPicCount());
        storeDetailDTO.setIntro(storeDetailBO.getIntro());
        storeDetailDTO.setFavoriteNumber(storeDetailBO.getFavoriteNumber());
        storeDetailDTO.setFavorite(storeDetailBO.getFavorite());
        storeDetailDTO.setAverageConsumeAmount(storeDetailBO.getAverageConsumeAmount());
        storeDetailDTO.setAverageScore(storeDetailBO.getAverageScore());
        storeDetailDTO.setFeedbackRate(storeDetailBO.getFeedbackRate());
        storeDetailDTO.setBuyNumbers(storeDetailBO.getBuyNumbers());
        return storeDetailDTO;
    }

    /**
     * DTO转换
     *
     * @param shoppingStoreDetailBO
     * @return
     */
    public static ShoppingStoreDetailDTO convertDTO(ShoppingStoreDetailBO shoppingStoreDetailBO) {
        if (shoppingStoreDetailBO == null) {
            return null;
        }

        ShoppingStoreDetailDTO shoppingStoreDetailDTO = new ShoppingStoreDetailDTO();
        shoppingStoreDetailDTO.setMerchantId(shoppingStoreDetailBO.getMerchantId());
        shoppingStoreDetailDTO.setName(shoppingStoreDetailBO.getName());
        shoppingStoreDetailDTO.setLogoPic(shoppingStoreDetailBO.getLogoPic());
        shoppingStoreDetailDTO.setFansCount(shoppingStoreDetailBO.getFansCount());
        shoppingStoreDetailDTO.setFans(shoppingStoreDetailBO.getFans());
        shoppingStoreDetailDTO.setFavorite(shoppingStoreDetailBO.getFavorite());
        return shoppingStoreDetailDTO;
    }

    /**
     * BO转换
     *
     * @param merchantStoreDO
     * @return
     */
    public static StoreDetailBO convertBO(MerchantStoreDO merchantStoreDO) {
        if (merchantStoreDO == null) {
            return null;
        }

        StoreDetailBO storeDetailBO = new StoreDetailBO();
        storeDetailBO.setMerchantId(merchantStoreDO.getMerchantId());
        storeDetailBO.setName(merchantStoreDO.getName());
        storeDetailBO.setAddress(merchantStoreDO.getAddress());
        storeDetailBO.setPrincipalMobile(merchantStoreDO.getPrincipalMobile());
        storeDetailBO.setIntro(merchantStoreDO.getIntro());
        storeDetailBO.setFavoriteNumber(merchantStoreDO.getFavoriteNumber());
        storeDetailBO.setAverageConsumeAmount(merchantStoreDO.getAverageConsumeAmount());
        storeDetailBO.setAverageScore(merchantStoreDO.getAverageScore());
        storeDetailBO.setFeedbackRate(merchantStoreDO.getFeedbackRate());
        storeDetailBO.setBuyNumbers(merchantStoreDO.getBuyNumbers());
        return storeDetailBO;
    }

    /**
     * MerchantStoreNoReasonReturnBO转换
     *
     * @param merchantStoreDO
     * @return
     */
    public static MerchantStoreNoReasonReturnBO convert(MerchantStoreDO merchantStoreDO, MerchantDO merchantDO) {
        if (merchantStoreDO == null) {
            return null;
        }

        MerchantStoreNoReasonReturnBO merchantStoreNoReasonReturnBO = new MerchantStoreNoReasonReturnBO();
        merchantStoreNoReasonReturnBO.setIsNoReasonReturn(merchantStoreDO.getIsNoReasonReturn());
        merchantStoreNoReasonReturnBO.setMerchantId(merchantStoreDO.getMerchantId());
        merchantStoreNoReasonReturnBO.setMerchantNum(merchantDO.getNum());
        
        return merchantStoreNoReasonReturnBO;
    }

    /**
     * MerchantStoreNoReasonReturnBO List转换
     *
     * @param merchantStoreDOList
     * @return
     */
    public static List<MerchantStoreNoReasonReturnBO> convertMerchantStoreNoReasonReturnBOList(List<MerchantStoreDO> merchantStoreDOList, List<MerchantDO> merchantDOList) {
        if (merchantStoreDOList == null || merchantStoreDOList.isEmpty()
                || merchantDOList == null || merchantDOList.isEmpty()) {
            return null;
        }

        Map<Long, MerchantDO> merchantDOMap = new HashMap<Long, MerchantDO>();
        for (MerchantDO merchantDO : merchantDOList) {
            merchantDOMap.put(merchantDO.getId(), merchantDO);
        }

        List<MerchantStoreNoReasonReturnBO> merchantStoreNoReasonReturnBOList = new ArrayList<MerchantStoreNoReasonReturnBO>();
        for (MerchantStoreDO merchantStoreDO : merchantStoreDOList) {
            merchantStoreNoReasonReturnBOList.add(convert(merchantStoreDO, merchantDOMap.get(merchantStoreDO.getMerchantId())));
        }

        return merchantStoreNoReasonReturnBOList;
    }

    /**
     * MerchantStoreNoReasonReturnDTO转换
     *
     * @param merchantStoreNoReasonReturnBO
     * @return
     */
    public static MerchantStoreNoReasonReturnDTO convert(MerchantStoreNoReasonReturnBO merchantStoreNoReasonReturnBO) {
    	MerchantStoreNoReasonReturnDTO rtn = null;
        if (merchantStoreNoReasonReturnBO == null) {
            return rtn;
        }

        rtn = new MerchantStoreNoReasonReturnDTO();
        BeanUtils.copyProperties(merchantStoreNoReasonReturnBO, rtn);

        return rtn;
    }

    /**
     * MerchantStoreNoReasonReturnDTO List转换
     *
     * @param merchantStoreNoReasonReturnBOList
     * @return
     */
    public static List<MerchantStoreNoReasonReturnDTO> convertMerchantStoreNoReasonReturnDTOList(List<MerchantStoreNoReasonReturnBO> merchantStoreNoReasonReturnBOList) {
        if (merchantStoreNoReasonReturnBOList == null || merchantStoreNoReasonReturnBOList.isEmpty()) {
            return null;
        }

        List<MerchantStoreNoReasonReturnDTO> merchantStoreNoReasonReturnDTOList = new ArrayList<MerchantStoreNoReasonReturnDTO>();
        for (MerchantStoreNoReasonReturnBO merchantStoreNoReasonReturnBO : merchantStoreNoReasonReturnBOList) {
            merchantStoreNoReasonReturnDTOList.add(convert(merchantStoreNoReasonReturnBO));
        }

        return merchantStoreNoReasonReturnDTOList;
    }

    public static MerchantStoreBO convertStoreBO(MerchantStoreDO merchantStoreDO) {
        if (merchantStoreDO == null) {
            return null;
        }
        MerchantStoreBO bo = new MerchantStoreBO();
        bo.setLongitude(merchantStoreDO.getLongitude());
        bo.setLatitude(merchantStoreDO.getLatitude());
        bo.setName(merchantStoreDO.getName());
        bo.setId(merchantStoreDO.getId());
        bo.setMerchantId(merchantStoreDO.getMerchantId());
        bo.setIndustryPath(merchantStoreDO.getIndustryPath());
        bo.setIndustryName(merchantStoreDO.getIndustryName());
        return bo;
    }

    public static MerchantStoreDTO convertStoreDTO(MerchantStoreBO merchantStoreBO) {
        if (merchantStoreBO == null) {
            return null;
        }
        MerchantStoreDTO dto = new MerchantStoreDTO();
        dto.setLongitude(merchantStoreBO.getLongitude());
        dto.setLatitude(merchantStoreBO.getLatitude());
        dto.setName(merchantStoreBO.getName());
        dto.setMerchantStoreId(merchantStoreBO.getId());
        dto.setMerchantId(merchantStoreBO.getMerchantId());
        dto.setIndustryPath(merchantStoreBO.getIndustryPath());
        dto.setIndustryName(merchantStoreBO.getIndustryName());
        return dto;
    }

    public static List<MerchantStoreBO> convertStoreBO(List<MerchantStoreDO> merchantStoreDOList) {
        List<MerchantStoreBO> merchantStoreBOS = new ArrayList<>();
        if (merchantStoreDOList == null || merchantStoreDOList.isEmpty()) {
            return merchantStoreBOS;
        }
        for (MerchantStoreDO merchantStoreDO : merchantStoreDOList) {
            merchantStoreBOS.add(convertStoreBO(merchantStoreDO));
        }
        return merchantStoreBOS;
    }

    public static List<MerchantStoreDTO> convertStoreDTO(List<MerchantStoreBO> merchantStoreBOList) {
        List<MerchantStoreDTO> merchantStoreDTOS = new ArrayList<>();
        if (merchantStoreBOList == null || merchantStoreBOList.isEmpty()) {
            return merchantStoreDTOS;
        }
        for (MerchantStoreBO merchantStoreBO : merchantStoreBOList) {
            merchantStoreDTOS.add(convertStoreDTO(merchantStoreBO));
        }
        return merchantStoreDTOS;
    }

    /**
     * SolrInputDocument
     *
     * @param merchantId
     * @param merchantStoreId
     * @param merchantStoreParam
     * @return
     */
    public static SolrInputDocument convertSolrInputDocument(Long merchantId, Long merchantStoreId, MerchantStoreParam merchantStoreParam) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", merchantStoreId);
        document.addField("merchantId_l", merchantId);
        document.addField("name_s", merchantStoreParam.getName());
        document.addField("regionPath_s", merchantStoreParam.getRegionPath());
        document.addField("latLon_p", merchantStoreParam.getLatitude() + "," + merchantStoreParam.getLongitude());
        document.addField("industryPath_s", merchantStoreParam.getIndustryPath());
        document.addField("industryName_s", merchantStoreParam.getIndustryName());
        return document;
    }

    /**
     * StoreSolrDTO
     *
     * @param solrDocumentList
     * @return
     */
    public static List<StoreSolrDTO> convertDTO(SolrDocumentList solrDocumentList) {
        List<StoreSolrDTO> storeSolrDTOS = new ArrayList<>();
        if (solrDocumentList == null || solrDocumentList.isEmpty()) {
            return storeSolrDTOS;
        }

        for (SolrDocument solrDocument : solrDocumentList) {
            StoreSolrDTO storeSolrDTO = new StoreSolrDTO();
            storeSolrDTO.setMerchantId(Long.valueOf(solrDocument.get("merchantId_l").toString()));
            storeSolrDTO.setMerchantStoreId(Long.valueOf(solrDocument.get("id").toString()));
            storeSolrDTO.setName(solrDocument.get("name_s").toString());
            storeSolrDTO.setIndustryPath(solrDocument.get("industryPath_s").toString());
            storeSolrDTO.setIndustryName(solrDocument.get("industryName_s").toString());
            storeSolrDTO.setStorePic(solrDocument.get("storePic_s").toString());
            storeSolrDTO.setDistance(DataTransUtil.objectToDobule(solrDocument.get("distance"), 3));
            storeSolrDTO.setFavoriteNumber(Integer.valueOf(solrDocument.get("favoriteNumber_i").toString()));
            storeSolrDTO.setAverageConsumeAmount(Double.valueOf(solrDocument.get("averageConsumeAmount_d").toString()));
            storeSolrDTO.setAverageScore(Double.valueOf(solrDocument.get("averageScore_d").toString()));
            storeSolrDTOS.add(storeSolrDTO);
        }
        return storeSolrDTOS;
    }

    /**
     * SolrInputDocument
     *
     * @param solrDocument
     * @return
     */
    public static SolrInputDocument convertSolrInputDocument(SolrDocument solrDocument) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", solrDocument.get("id"));
        document.addField("merchantId_l", solrDocument.get("merchantId_l"));
        document.addField("name_s", solrDocument.get("name_s"));
        document.addField("regionPath_s", solrDocument.get("regionPath_s"));
        document.addField("latLon_p", solrDocument.get("latLon_p"));
        document.addField("industryPath_s", solrDocument.get("industryPath_s"));
        document.addField("industryName_s", solrDocument.get("industryName_s"));
        document.addField("storePic_s", solrDocument.get("storePic_s"));
        document.addField("averageConsumeAmount_d", solrDocument.get("averageConsumeAmount_d"));
        document.addField("averageScore_d", solrDocument.get("averageScore_d"));
        return document;
    }

}
