package com.lawu.eshop.user.srv.converter;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.dto.MerchantDTO;
import com.lawu.eshop.user.srv.bo.MerchantBO;
import com.lawu.eshop.user.srv.bo.MerchantInfoBO;
import com.lawu.eshop.user.srv.domain.MerchantDO;

import java.util.ArrayList;
import java.util.List;

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
        merchantDTO.setHeadimg(merchantBO.getHeadimg());
        merchantDTO.setLevel(merchantBO.getLevel());
        return merchantDTO;
    }

    /**
     * DOS 转BOS
     *
     * @param merchantDOS
     * @return
     * @author zhangrc
     * @date 2013/03/23
     */
    public static List<MerchantBO> convertBOS(List<MerchantDO> merchantDOS) {
        if (merchantDOS == null) {
            return null;
        }
        List<MerchantBO> merchantBOS = new ArrayList<MerchantBO>();
        for (MerchantDO merchantDO : merchantDOS) {
            merchantBOS.add(convertBO(merchantDO));
        }
        return merchantBOS;
    }


    public static List<MerchantDTO> convertListDOTS(List<MerchantBO> merchantBOS) {
        if (merchantBOS == null) {
            return null;
        }
        List<MerchantDTO> memberDTOS = new ArrayList<MerchantDTO>();
        for (MerchantBO merchantBO : merchantBOS) {
            memberDTOS.add(convertDTO(merchantBO));
        }
        return memberDTOS;
    }

    /**
     * 商家主页信息BO转换
     *
     * @param merchantDO
     * @return
     */
    public static MerchantInfoBO convertInfoBO(MerchantDO merchantDO) {
        if (merchantDO == null) {
            return null;
        }

        MerchantInfoBO merchantBO = new MerchantInfoBO();
        merchantBO.setAccount(merchantDO.getAccount());
        merchantBO.setHeadimg(merchantDO.getHeadimg());

        return merchantBO;
    }


    /**
     * 描述：将pageBOS转成pageDTOS
     *
     * @param pageMerchantBOS
     * @return
     */
    public static Page<MerchantDTO> convertPageDOTS(Page<MerchantBO> pageMerchantBOS) {
        Page<MerchantDTO> pageDTO = new Page<MerchantDTO>();
        List<MerchantBO> BOS = pageMerchantBOS.getRecords();
        List<MerchantDTO> DTOS = pageDTO.getRecords();
        for (MerchantBO merchantBO : BOS) {
            DTOS.add(convertDTO(merchantBO));
        }
        return pageDTO;
    }


}
