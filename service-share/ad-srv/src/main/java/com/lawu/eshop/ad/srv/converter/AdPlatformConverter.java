package com.lawu.eshop.ad.srv.converter;

import com.lawu.eshop.ad.dto.AdPlatformDTO;
import com.lawu.eshop.ad.srv.bo.AdPlatformBO;
import com.lawu.eshop.ad.srv.domain.AdPlatformDO;

import java.util.ArrayList;
import java.util.List;

/**
 * 平台广告实体转化
 *
 * @author zhangrc
 * @date 2017/3/5
 */
public class AdPlatformConverter {

    /**
     * DO转BO
     *
     * @param adPlatformDO
     * @return
     */
    public static AdPlatformBO convertBO(AdPlatformDO adPlatformDO) {
        if (adPlatformDO == null) {
            return null;
        }
        AdPlatformBO BO = new AdPlatformBO();
        AdPlatformBO adPlatformBO = new AdPlatformBO();
        adPlatformBO.setId(adPlatformDO.getId());
        adPlatformBO.setTitle(adPlatformDO.getTitle());
        adPlatformBO.setType(adPlatformDO.getType());
        adPlatformBO.setContent(adPlatformDO.getContent());
        adPlatformBO.setMediaUrl(adPlatformDO.getMediaUrl());
        if (adPlatformDO.getType() == 1) {
            adPlatformBO.setLinkUrl(adPlatformDO.getLinkUrl());
        } else {
            adPlatformBO.setProductId(adPlatformDO.getProductId());
        }
        return BO;

    }

    /**
     * BO 转DTO
     *
     * @param adPlatformBO
     * @return
     */
    public static AdPlatformDTO convertDTO(AdPlatformBO adPlatformBO) {
        if (adPlatformBO == null) {
            return null;
        }
        AdPlatformDTO DTO = new AdPlatformDTO();
        AdPlatformDTO adPlatformDTO = new AdPlatformDTO();
        adPlatformDTO.setId(adPlatformBO.getId());
        adPlatformDTO.setTitle(adPlatformBO.getTitle());
        adPlatformDTO.setMediaUrl(adPlatformBO.getMediaUrl());
        adPlatformDTO.setContent(adPlatformBO.getContent());
        if (adPlatformBO.getType() == 1) {
            adPlatformDTO.setLinkUrl(adPlatformBO.getLinkUrl());
        } else {
            adPlatformDTO.setProductId(adPlatformBO.getProductId());
        }
        return DTO;
    }

    /**
     * DOS转BOS
     *
     * @param list
     * @return
     */
    public static List<AdPlatformBO> convertBOS(List<AdPlatformDO> list) {
        List<AdPlatformBO> BOS = new ArrayList<AdPlatformBO>();
        if (list == null || list.isEmpty()) {
            return BOS;
        }

        for (AdPlatformDO adPlatformDO : list) {
            AdPlatformBO adPlatformBO = new AdPlatformBO();
            adPlatformBO.setId(adPlatformDO.getId());
            adPlatformBO.setTitle(adPlatformDO.getTitle());
            adPlatformBO.setType(adPlatformDO.getType());
            adPlatformBO.setMediaUrl(adPlatformDO.getMediaUrl());
            adPlatformBO.setContent(adPlatformDO.getContent());
            if (adPlatformDO.getMerchantStoreId() != null)
                adPlatformBO.setMerchantStoreId(adPlatformDO.getMerchantStoreId());
            if (adPlatformDO.getType() == 1) {
                adPlatformBO.setLinkUrl(adPlatformDO.getLinkUrl());
            } else {
                adPlatformBO.setProductId(adPlatformDO.getProductId());
            }
            BOS.add(adPlatformBO);
        }
        return BOS;

    }

    /**
     * BOS 转DTOS
     *
     * @param bOS
     * @return
     */
    public static List<AdPlatformDTO> convertDTOS(List<AdPlatformBO> bOS) {
        List<AdPlatformDTO> DTOS = new ArrayList<AdPlatformDTO>();
        if (bOS == null || bOS.isEmpty()) {
            return DTOS;
        }

        for (AdPlatformBO adPlatformBO : bOS) {
            AdPlatformDTO adPlatformDTO = new AdPlatformDTO();
            adPlatformDTO.setId(adPlatformBO.getId());
            adPlatformDTO.setTitle(adPlatformBO.getTitle());
            adPlatformDTO.setMediaUrl(adPlatformBO.getMediaUrl());
            adPlatformDTO.setContent(adPlatformBO.getContent());
            adPlatformDTO.setMerchantStoreId(adPlatformBO.getMerchantStoreId());
            if (adPlatformBO.getType() == 1) {
                adPlatformDTO.setLinkUrl(adPlatformBO.getLinkUrl());
            } else {
                adPlatformDTO.setProductId(adPlatformBO.getProductId());
            }
            DTOS.add(adPlatformDTO);
        }
        return DTOS;
    }
}
