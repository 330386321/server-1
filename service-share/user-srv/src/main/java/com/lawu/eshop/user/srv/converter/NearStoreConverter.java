package com.lawu.eshop.user.srv.converter;

import com.lawu.eshop.user.dto.NearStoreDTO;
import com.lawu.eshop.user.srv.bo.NearStoreBO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
public class NearStoreConverter {

    /**
     * BO转换
     *
     * @param merchantStoreDOS
     * @return
     */
    public static List<NearStoreBO> convertBO(List<MerchantStoreDO> merchantStoreDOS) {
        if (merchantStoreDOS == null) {
            return null;
        }

        List<NearStoreBO> nearStoreBOS = new ArrayList<NearStoreBO>(merchantStoreDOS.size());
        for (MerchantStoreDO merchantStoreDO : merchantStoreDOS) {
            NearStoreBO nearStoreBO = new NearStoreBO();
            nearStoreBO.setMerchantId(merchantStoreDO.getMerchantId());
            nearStoreBO.setName(merchantStoreDO.getName());
            nearStoreBO.setLongitude(merchantStoreDO.getLongitude());
            nearStoreBO.setLatitude(merchantStoreDO.getLatitude());
            nearStoreBO.setIndustryPath(merchantStoreDO.getIndustryPath());
            nearStoreBOS.add(nearStoreBO);
        }
        return nearStoreBOS;
    }

    /**
     * DTO转换
     *
     * @param nearStoreBOS
     * @return
     */
    public static List<NearStoreDTO> convertDTO(List<NearStoreBO> nearStoreBOS) {
        if (nearStoreBOS == null) {
            return null;
        }

        List<NearStoreDTO> nearStoreDTOS = new ArrayList<NearStoreDTO>(nearStoreBOS.size());
        for (NearStoreBO nearStoreBO : nearStoreBOS) {
            NearStoreDTO nearStoreDTO = new NearStoreDTO();
            nearStoreDTO.setMerchantId(nearStoreBO.getMerchantId());
            nearStoreDTO.setName(nearStoreBO.getName());
            nearStoreDTO.setIndustryPath(nearStoreBO.getIndustryPath());
            nearStoreDTO.setLogo(nearStoreBO.getLogo());
            nearStoreDTO.setDistance(nearStoreBO.getDistance());
            nearStoreDTO.setFavCount(nearStoreBO.getFavCount());
            nearStoreDTOS.add(nearStoreDTO);
        }
        return nearStoreDTOS;
    }
}
