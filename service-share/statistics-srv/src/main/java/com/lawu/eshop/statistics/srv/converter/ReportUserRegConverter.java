package com.lawu.eshop.statistics.srv.converter;

import com.lawu.eshop.statistics.dto.ReportUserRegAreaDTO;
import com.lawu.eshop.statistics.dto.ReportUserRegDTO;
import com.lawu.eshop.statistics.srv.bo.ReportUserRegAreaBO;
import com.lawu.eshop.statistics.srv.bo.ReportUserRegBO;
import com.lawu.eshop.statistics.srv.domain.ReportUserRegAreaDO;
import com.lawu.eshop.statistics.srv.domain.ReportUserRegDailyDO;
import com.lawu.eshop.statistics.srv.domain.ReportUserRegMonthDO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/6/30.
 */
public class ReportUserRegConverter {

    /**
     * DO转BO
     *
     * @param regDailyDOList
     * @return
     */
    public static List<ReportUserRegBO> convertDailyBO(List<ReportUserRegDailyDO> regDailyDOList) {
        List<ReportUserRegBO> regBOList = new ArrayList<>();
        if (regDailyDOList == null || regDailyDOList.isEmpty()) {
            return regBOList;
        }
        for (ReportUserRegDailyDO regDailyDO : regDailyDOList) {
            ReportUserRegBO regBO = new ReportUserRegBO();
            regBO.setMemberCount(regDailyDO.getMemberCount());
            regBO.setMerchantCount(regDailyDO.getMerchantCount());
            regBO.setGmtReport(regDailyDO.getGmtReport());
            regBOList.add(regBO);
        }
        return regBOList;
    }

    /**
     * DO转BO
     *
     * @param regMonthDOList
     * @return
     */
    public static List<ReportUserRegBO> convertMonthBO(List<ReportUserRegMonthDO> regMonthDOList) {
        List<ReportUserRegBO> regBOList = new ArrayList<>();
        if (regMonthDOList == null || regMonthDOList.isEmpty()) {
            return regBOList;
        }
        for (ReportUserRegMonthDO regMonthDO : regMonthDOList) {
            ReportUserRegBO regBO = new ReportUserRegBO();
            regBO.setMemberCount(regMonthDO.getMemberCount());
            regBO.setMerchantCount(regMonthDO.getMerchantCount());
            regBO.setGmtReport(regMonthDO.getGmtReport());
            regBOList.add(regBO);
        }
        return regBOList;
    }

    /**
     * DO转BO
     *
     * @param regAreaDOList
     * @return
     */
    public static List<ReportUserRegAreaBO> convertAreaBO(List<ReportUserRegAreaDO> regAreaDOList) {
        List<ReportUserRegAreaBO> regAreaBOList = new ArrayList<>();
        if (regAreaDOList == null || regAreaDOList.isEmpty()) {
            return regAreaBOList;
        }
        for (ReportUserRegAreaDO regAreaDO : regAreaDOList) {
            ReportUserRegAreaBO regAreaBO = new ReportUserRegAreaBO();
            regAreaBO.setMemberCount(regAreaDO.getMemberCount());
            regAreaBO.setMerchantCount(regAreaDO.getMerchantCount());
            regAreaBO.setMerchantCommonCount(regAreaDO.getMerchantCommonCount());
            regAreaBO.setMerchantEntityCount(regAreaDO.getMerchantEntityCount());
            regAreaBO.setCityId(regAreaDO.getCityId());
            regAreaBO.setCityName(regAreaDO.getCityName());
            regAreaBOList.add(regAreaBO);
        }
        return regAreaBOList;
    }

    /**
     * BO转DTO
     *
     * @param regBOList
     * @return
     */
    public static List<ReportUserRegDTO> convertDailyDTO(List<ReportUserRegBO> regBOList) {
        List<ReportUserRegDTO> regDTOList = new ArrayList<>();
        if (regBOList == null || regBOList.isEmpty()) {
            return regDTOList;
        }
        for (ReportUserRegBO regBO : regBOList) {
            ReportUserRegDTO regDTO = new ReportUserRegDTO();
            regDTO.setMemberCount(regBO.getMemberCount());
            regDTO.setMerchantCount(regBO.getMerchantCount());
            regDTO.setGmtReport(regBO.getGmtReport());
            regDTOList.add(regDTO);
        }
        return regDTOList;
    }

    /**
     * BO转DTO
     *
     * @param regBOList
     * @return
     */
    public static List<ReportUserRegDTO> convertMonthDTO(List<ReportUserRegBO> regBOList) {
        List<ReportUserRegDTO> regDTOList = new ArrayList<>();
        if (regBOList == null || regBOList.isEmpty()) {
            return regDTOList;
        }
        for (ReportUserRegBO regBO : regBOList) {
            ReportUserRegDTO regDTO = new ReportUserRegDTO();
            regDTO.setMemberCount(regBO.getMemberCount());
            regDTO.setMerchantCount(regBO.getMerchantCount());
            regDTO.setGmtReport(regBO.getGmtReport());
            regDTOList.add(regDTO);
        }
        return regDTOList;
    }

    /**
     * BO转DTO
     *
     * @param regAreaBOList
     * @return
     */
    public static List<ReportUserRegAreaDTO> convertAreaDTO(List<ReportUserRegAreaBO> regAreaBOList) {
        List<ReportUserRegAreaDTO> regAreaDTOList = new ArrayList<>();
        if (regAreaBOList == null || regAreaBOList.isEmpty()) {
            return regAreaDTOList;
        }
        for (ReportUserRegAreaBO regAreaBO : regAreaBOList) {
            ReportUserRegAreaDTO regAreaDTO = new ReportUserRegAreaDTO();
            regAreaDTO.setMemberCount(regAreaBO.getMemberCount());
            regAreaDTO.setMerchantCount(regAreaBO.getMerchantCount());
            regAreaDTO.setMerchantCommonCount(regAreaBO.getMerchantCommonCount());
            regAreaDTO.setMerchantEntityCount(regAreaBO.getMerchantEntityCount());
            regAreaDTO.setCityId(regAreaBO.getCityId());
            regAreaDTO.setCityName(regAreaBO.getCityName());
            regAreaDTOList.add(regAreaDTO);
        }
        return regAreaDTOList;
    }
}
