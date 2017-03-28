package com.lawu.eshop.user.srv.converter;

import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.param.MerchantStoreParam;
import com.lawu.eshop.user.srv.bo.MerchantStoreInfoBO;
import com.lawu.eshop.user.srv.bo.MerchantStoreProfileBO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreProfileDO;

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
        return merchantStoreProfileBO;

    }

    public static MerchantStoreInfoBO coverter(MerchantStoreDO merchantStoreDO){
        if(merchantStoreDO == null){
            return null;
        }
        MerchantStoreInfoBO merchantStoreInfoBO = new MerchantStoreInfoBO();
        merchantStoreInfoBO.setName(merchantStoreDO.getName());
        merchantStoreInfoBO.setPrincipalName(merchantStoreDO.getPrincipalName());
        merchantStoreInfoBO.setRegionPath(merchantStoreDO.getRegionPath());
        merchantStoreInfoBO.setAddress(merchantStoreDO.getAddress());
        merchantStoreInfoBO.setIndustryPath(merchantStoreDO.getIndustryPath());
        merchantStoreInfoBO.setLongitude(merchantStoreDO.getLongitude());
        merchantStoreInfoBO.setLatitude(merchantStoreDO.getLatitude());
        merchantStoreInfoBO.setIntro(merchantStoreDO.getIntro());
        merchantStoreInfoBO.setPrincipalMobile(merchantStoreDO.getPrincipalMobile());

        return merchantStoreInfoBO;
    }

    /**
     * BO转DTO
     * @param merchantStoreInfoBO
     * @return
     */
    public static MerchantStoreDTO coverDTO(MerchantStoreInfoBO merchantStoreInfoBO){
        if(merchantStoreInfoBO == null){
            return null;
        }
        MerchantStoreDTO merchantStoreDTO = new MerchantStoreDTO();
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
        merchantStoreDTO.setManageType(merchantStoreInfoBO.getManageType());
        merchantStoreDTO.setCertifType(merchantStoreInfoBO.getCertifType());
        merchantStoreDTO.setOperatorCardId(merchantStoreInfoBO.getOperatorCardId());
        merchantStoreDTO.setOperatorName(merchantStoreInfoBO.getOperatorName());

        merchantStoreDTO.setType(merchantStoreInfoBO.getType());
        merchantStoreDTO.setPath(merchantStoreInfoBO.getPath());

        return merchantStoreDTO;
    }

    /**
     * 门店参数对象转DO
     * @param merchantStoreParam
     * @param type
     * @return
     */
    public  static  Object couverDOByParam(MerchantStoreParam merchantStoreParam, int type){
        if(merchantStoreParam == null){
            return null;
        }
        if(type == 1){//商店基本信息转换DO
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

            return merchantStoreDO;
        }else if(type == 2){
            MerchantStoreProfileDO merchantStoreProfileDO = new MerchantStoreProfileDO();

            merchantStoreProfileDO.setCertifType(merchantStoreParam.getCertifType());
            merchantStoreProfileDO.setCompanyAddress(merchantStoreParam.getCompanyAddress());
            merchantStoreProfileDO.setLicenseIndate(merchantStoreParam.getLicenseIndate());
            merchantStoreProfileDO.setOperatorCardId(merchantStoreParam.getOperatorCardId());
            merchantStoreProfileDO.setCompanyName(merchantStoreParam.getCompanyName());
            merchantStoreProfileDO.setManageType(merchantStoreParam.getManageType());
            merchantStoreProfileDO.setOperatorName(merchantStoreProfileDO.getOperatorName());
            merchantStoreProfileDO.setRegNumber(merchantStoreProfileDO.getRegNumber());

            return  merchantStoreProfileDO;
        }else{

            return null;
        }
    }

}
