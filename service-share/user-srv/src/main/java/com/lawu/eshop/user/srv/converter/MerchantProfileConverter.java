package com.lawu.eshop.user.srv.converter;

import com.lawu.eshop.user.dto.MerchantProfileDTO;
import com.lawu.eshop.user.dto.param.MerchantProfileParam;
import com.lawu.eshop.user.srv.bo.MerchantProfileBO;
import com.lawu.eshop.user.srv.domain.MerchantProfileDO;

/**
 * 商家扩展信息转换器
 * Created by zhangyong on 2017/3/23.
 */
public class MerchantProfileConverter {

    /**
     * BO转换
     *
     * @param memberDO
     * @return
     */
    public static MerchantProfileBO convertBO(MerchantProfileDO merchantProfileDO) {
        if (merchantProfileDO == null) {
            return null;
        }

        MerchantProfileBO merchantProfileBO = new MerchantProfileBO();
        merchantProfileBO.setId(merchantProfileDO.getId());
        merchantProfileBO.setJdUrl(merchantProfileDO.getJdUrl());
        merchantProfileBO.setInviteMemberCount(merchantProfileDO.getInviteMemberCount());
        merchantProfileBO.setTaobaoUrl(merchantProfileDO.getTaobaoUrl());
        merchantProfileBO.setTmallUrl(merchantProfileDO.getTmallUrl());
        merchantProfileBO.setWebsiteUrl(merchantProfileDO.getWebsiteUrl());
        merchantProfileBO.setInviteMerchantCount(merchantProfileDO.getInviteMerchantCount());
        merchantProfileBO.setGmtCreate(merchantProfileDO.getGmtCreate());
        merchantProfileBO.setGmtModified(merchantProfileDO.getGmtModified());

        return merchantProfileBO;

    }

    /**
     * param 转DO
     * @param merchantProfileParam
     * @return
     */
    public static MerchantProfileDO paramConvertDO(MerchantProfileParam merchantProfileParam) {
        if (merchantProfileParam == null) {
            return null;
        }

        MerchantProfileDO merchantProfileDO = new MerchantProfileDO();
        merchantProfileDO.setId(merchantProfileParam.getId());
        merchantProfileDO.setJdUrl(merchantProfileParam.getJdUrl());
        merchantProfileDO.setInviteMemberCount(merchantProfileParam.getInviteMemberCount());
        merchantProfileDO.setTaobaoUrl(merchantProfileParam.getTaobaoUrl());
        merchantProfileDO.setTmallUrl(merchantProfileParam.getTmallUrl());
        merchantProfileDO.setWebsiteUrl(merchantProfileParam.getWebsiteUrl());
        merchantProfileDO.setInviteMerchantCount(merchantProfileParam.getInviteMerchantCount());
        merchantProfileDO.setGmtCreate(merchantProfileParam.getGmtCreate());
        merchantProfileDO.setGmtModified(merchantProfileParam.getGmtModified());

        return merchantProfileDO;

    }

    /**
     *
     * @param merchantProfileBO
     * @return
     */
    public static MerchantProfileDTO coverConverDTO(MerchantProfileBO merchantProfileBO){
        if(merchantProfileBO == null){
            return null;
        }

        MerchantProfileDTO merchantProfileDTO = new MerchantProfileDTO();
        merchantProfileDTO.setId(merchantProfileBO.getId());
        merchantProfileDTO.setJdUrl(merchantProfileBO.getJdUrl());
        merchantProfileDTO.setInviteMemberCount(merchantProfileBO.getInviteMemberCount());
        merchantProfileDTO.setTaobaoUrl(merchantProfileBO.getTaobaoUrl());
        merchantProfileDTO.setTmallUrl(merchantProfileBO.getTmallUrl());
        merchantProfileDTO.setWebsiteUrl(merchantProfileBO.getWebsiteUrl());
        merchantProfileDTO.setInviteMerchantCount(merchantProfileBO.getInviteMerchantCount());

        return merchantProfileDTO;

    }

}
