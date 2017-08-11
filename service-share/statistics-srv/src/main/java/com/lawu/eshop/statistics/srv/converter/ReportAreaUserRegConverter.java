package com.lawu.eshop.statistics.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.statistics.dto.AgentUserRegUserListDTO;
import com.lawu.eshop.statistics.srv.bo.ReportAreaUserRegDailyBO;
import com.lawu.eshop.statistics.srv.domain.ReportAreaUserRegDailyDO;

/**
 * @author zhangyong
 * @date 2017/8/11.
 */
public class ReportAreaUserRegConverter {

    public static List<ReportAreaUserRegDailyBO> coverBOS(List<ReportAreaUserRegDailyDO> userRegDailyDOS) {
        if (userRegDailyDOS.isEmpty()) {
            return new ArrayList<>();
        }
        List<ReportAreaUserRegDailyBO> list = new ArrayList<>();
        ReportAreaUserRegDailyBO reportAreaUserRegDailyBO;
        for (ReportAreaUserRegDailyDO userRegDailyDO : userRegDailyDOS) {
            reportAreaUserRegDailyBO = new ReportAreaUserRegDailyBO();
            reportAreaUserRegDailyBO.setMerchantCount(userRegDailyDO.getMerchantCount());
            reportAreaUserRegDailyBO.setMerchantNormalCount(userRegDailyDO.getMerchantNormalCount());
            reportAreaUserRegDailyBO.setMerchantEntityCount(userRegDailyDO.getMerchantEntityCount());
            reportAreaUserRegDailyBO.setMemberCount(userRegDailyDO.getMemberCount());
            reportAreaUserRegDailyBO.setCityId(userRegDailyDO.getCityId());
            reportAreaUserRegDailyBO.setCityName(userRegDailyDO.getCityName());
            reportAreaUserRegDailyBO.setGmtReport(userRegDailyDO.getGmtReport());
            list.add(reportAreaUserRegDailyBO);
        }
        return list;
    }

    public static List<AgentUserRegUserListDTO> coverDTOS(List<ReportAreaUserRegDailyBO> listBOS) {
        if (listBOS.isEmpty()) {
            return new ArrayList<>();
        }
        List<AgentUserRegUserListDTO> list = new ArrayList<>();
        AgentUserRegUserListDTO userRegUserListDTO;
        for (ReportAreaUserRegDailyBO userRegDailyBO : listBOS) {
            userRegUserListDTO = new AgentUserRegUserListDTO();
            userRegUserListDTO.setMerchantCount(userRegDailyBO.getMerchantCount());
            userRegUserListDTO.setMerchantCommonCount(userRegDailyBO.getMerchantNormalCount());
            userRegUserListDTO.setMerchantEntityCount(userRegDailyBO.getMerchantEntityCount());
            userRegUserListDTO.setMemberCount(userRegDailyBO.getMemberCount());
            userRegUserListDTO.setCityId(userRegDailyBO.getCityId());
            userRegUserListDTO.setCityName(userRegDailyBO.getCityName());
            userRegUserListDTO.setGmtReport(userRegDailyBO.getGmtReport());
            list.add(userRegUserListDTO);
        }
        return list;
    }
}
