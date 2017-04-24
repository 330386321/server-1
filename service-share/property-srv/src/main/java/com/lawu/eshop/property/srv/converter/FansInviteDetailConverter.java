package com.lawu.eshop.property.srv.converter;

import com.lawu.eshop.property.dto.FansInviteDetailDTO;
import com.lawu.eshop.property.srv.bo.FansInviteDetailBO;
import com.lawu.eshop.property.srv.domain.FansInviteDetailDO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/24.
 */
public class FansInviteDetailConverter {

    /**
     * DO转BO
     *
     * @param fansInviteDetailDO
     * @return
     */
    public static FansInviteDetailBO convertBO(FansInviteDetailDO fansInviteDetailDO) {
        if (fansInviteDetailDO == null) {
            return null;
        }

        FansInviteDetailBO fansInviteDetailBO = new FansInviteDetailBO();
        fansInviteDetailBO.setRegionName(fansInviteDetailDO.getRegionName());
        fansInviteDetailBO.setInviteFansCount(fansInviteDetailDO.getInviteFansCount());
        fansInviteDetailBO.setConsumePoint(fansInviteDetailDO.getConsumePoint());
        fansInviteDetailBO.setGmtCreate(fansInviteDetailDO.getGmtCreate());
        return fansInviteDetailBO;
    }

    /**
     * BO转DTO
     *
     * @param fansInviteDetailBO
     * @return
     */
    public static FansInviteDetailDTO convertDTO(FansInviteDetailBO fansInviteDetailBO) {
        if (fansInviteDetailBO == null) {
            return null;
        }

        FansInviteDetailDTO fansInviteDetailDTO = new FansInviteDetailDTO();
        fansInviteDetailDTO.setRegionName(fansInviteDetailBO.getRegionName());
        fansInviteDetailDTO.setInviteFansCount(fansInviteDetailBO.getInviteFansCount());
        fansInviteDetailDTO.setConsumePoint(fansInviteDetailBO.getConsumePoint());
        fansInviteDetailDTO.setGmtCreate(fansInviteDetailBO.getGmtCreate());
        return fansInviteDetailDTO;
    }

    /**
     * DO转BO
     *
     * @param fansInviteDetailDOList
     * @return
     */
    public static List<FansInviteDetailBO> convertBO(List<FansInviteDetailDO> fansInviteDetailDOList) {
        if (fansInviteDetailDOList == null || fansInviteDetailDOList.isEmpty()) {
            return null;
        }

        List<FansInviteDetailBO> fansInviteDetailBOS = new ArrayList<>();
        for (FansInviteDetailDO fansInviteDetailDO : fansInviteDetailDOList) {
            fansInviteDetailBOS.add(convertBO(fansInviteDetailDO));
        }
        return fansInviteDetailBOS;
    }

    /**
     * BO转DTO
     *
     * @param fansInviteDetailBOList
     * @return
     */
    public static List<FansInviteDetailDTO> convertDTO(List<FansInviteDetailBO> fansInviteDetailBOList) {
        if (fansInviteDetailBOList == null || fansInviteDetailBOList.isEmpty()) {
            return null;
        }

        List<FansInviteDetailDTO> fansInviteDetailDTOS = new ArrayList<>();
        for (FansInviteDetailBO fansInviteDetailBO : fansInviteDetailBOList) {
            fansInviteDetailDTOS.add(convertDTO(fansInviteDetailBO));
        }
        return fansInviteDetailDTOS;
    }
}
