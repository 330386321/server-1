package com.lawu.eshop.user.srv.converter;

import com.lawu.eshop.user.dto.MerchantDTO;
import com.lawu.eshop.user.srv.bo.MerchantBO;
import com.lawu.eshop.user.srv.domain.MerchantDO;

/**
 * 商户信息转换器
 *
 * @author meishuquan
 * @date 2017/3/23
 */
public class MerchantConverter {

    /**
     * BO转换
     *
     * @param merchantDO
     * @return
     */
    public static MerchantBO convertBO(MerchantDO merchantDO) {
        if (merchantDO == null) {
            return null;
        }

        MerchantBO merchantBO = new MerchantBO();
        merchantBO.setId(merchantDO.getId());
        merchantBO.setNum(merchantDO.getNum());
        merchantBO.setAccount(merchantDO.getAccount());
        merchantBO.setPwd(merchantDO.getPwd());
        merchantBO.setMobile(merchantDO.getMobile());
        merchantBO.setHeadimg(merchantDO.getHeadimg());
        merchantBO.setStatus(merchantDO.getStatus());
        merchantBO.setInviterId(merchantDO.getInviterId());
        merchantBO.setInviterType(merchantDO.getInviterType());
        merchantBO.setLevel(merchantDO.getLevel());
        merchantBO.setGmtModified(merchantDO.getGmtModified());
        merchantBO.setGmtCreate(merchantDO.getGmtCreate());
        return merchantBO;
    }

    /**
     * DTO转换
     *
     * @param merchantBO
     * @return
     */
    public static MerchantDTO convertDTO(MerchantBO merchantBO) {
        if (merchantBO == null) {
            return null;
        }

        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setId(merchantBO.getId());
        merchantDTO.setNum(merchantBO.getNum());
        merchantDTO.setAccount(merchantBO.getAccount());
        merchantDTO.setPwd(merchantBO.getPwd());
        merchantDTO.setMobile(merchantBO.getMobile());
        merchantDTO.setHeadimg(merchantBO.getHeadimg());
        merchantDTO.setStatus(merchantBO.getStatus());
        merchantDTO.setInviterId(merchantBO.getInviterId());
        merchantDTO.setInviterType(merchantBO.getInviterType());
        merchantDTO.setLevel(merchantBO.getLevel());
        merchantDTO.setGmtModified(merchantBO.getGmtModified());
        merchantDTO.setGmtCreate(merchantBO.getGmtCreate());
        return merchantDTO;
    }
}
