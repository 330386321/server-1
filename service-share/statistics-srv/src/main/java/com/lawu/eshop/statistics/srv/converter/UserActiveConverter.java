package com.lawu.eshop.statistics.srv.converter;

import com.lawu.eshop.statistics.dto.ReportUserActiveAreaDTO;
import com.lawu.eshop.statistics.dto.UserActiveDTO;
import com.lawu.eshop.statistics.dto.UserActiveListDTO;
import com.lawu.eshop.statistics.srv.bo.ReportUserActiveAreaDailyBO;
import com.lawu.eshop.statistics.srv.bo.ReportUserActiveAreaMonthBO;
import com.lawu.eshop.statistics.srv.bo.ReportUserActiveBO;
import com.lawu.eshop.statistics.srv.bo.UserActiveBO;
import com.lawu.eshop.statistics.srv.domain.ReportUserActiveAreaDailyDO;
import com.lawu.eshop.statistics.srv.domain.ReportUserActiveAreaMonthDO;
import com.lawu.eshop.statistics.srv.domain.extend.ReportUserActiveDOView;
import com.lawu.eshop.statistics.srv.domain.extend.UserActiveDOView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/6/30.
 */
public class UserActiveConverter {
    public static List<UserActiveBO> coverBOS(List<UserActiveDOView> userActiveDOViews) {
        if (userActiveDOViews.isEmpty()) {
            return new ArrayList<>();
        }
        List<UserActiveBO> userActiveBOS = new ArrayList<>();
        for (UserActiveDOView userActiveDOView : userActiveDOViews) {
            UserActiveBO userActiveBO = new UserActiveBO();
            userActiveBO.setCityId(userActiveDOView.getCityId());
            userActiveBO.setCityName(userActiveDOView.getCityName());
            userActiveBO.setVisitDate(userActiveDOView.getVisitDate());
            userActiveBO.setUserCount(userActiveDOView.getUserCount());
            userActiveBOS.add(userActiveBO);
        }

        return userActiveBOS;
    }

    public static List<UserActiveDTO> coverDTOS(List<UserActiveBO> userActiveBOS) {
        if (userActiveBOS.isEmpty()) {
            return new ArrayList<>();
        }
        List<UserActiveDTO> userActiveDTOS = new ArrayList<>();
        for (UserActiveBO userActiveBO : userActiveBOS) {
            UserActiveDTO userActiveDTO = new UserActiveDTO();
            userActiveDTO.setCityId(userActiveBO.getCityId());
            userActiveDTO.setCityName(userActiveBO.getCityName());
            userActiveDTO.setUserCount(userActiveBO.getUserCount());
            userActiveDTO.setVisitDate(userActiveBO.getVisitDate());
            userActiveDTOS.add(userActiveDTO);
        }
        return userActiveDTOS;
    }

    public static List<ReportUserActiveBO> coverReportBOS(List<ReportUserActiveDOView> userActiveDOViews) {
        if(userActiveDOViews.isEmpty()){
            return new ArrayList<>();
        }
        List<ReportUserActiveBO> reportUserActiveBOS = new ArrayList<>();
        for(ReportUserActiveDOView reportUserActiveDOView : userActiveDOViews){
            ReportUserActiveBO reportUserActiveBO = new ReportUserActiveBO();
            reportUserActiveBO.setMerchantCount(reportUserActiveDOView.getMerchantCount());
            reportUserActiveBO.setMemberCount(reportUserActiveDOView.getMemberCount());
            reportUserActiveBO.setGmtReport(reportUserActiveDOView.getGmtReport());
            reportUserActiveBOS.add(reportUserActiveBO);
        }
        return reportUserActiveBOS;
    }

    public static List<UserActiveListDTO> coverReportDTOS(List<ReportUserActiveBO> listBOS) {
        if (listBOS.isEmpty()) {
            return new ArrayList<>();
        }
        List<UserActiveListDTO> listDTOS = new ArrayList<>();

        for (ReportUserActiveBO userActiveBO : listBOS) {
            UserActiveListDTO userActiveListDTO = new UserActiveListDTO();
            userActiveListDTO.setMemberCount(userActiveBO.getMemberCount());
            userActiveListDTO.setGmtReport(userActiveBO.getGmtReport());
            userActiveListDTO.setMerchantCount(userActiveBO.getMerchantCount());
            listDTOS.add(userActiveListDTO);
        }
        return listDTOS;
    }

    public static List<ReportUserActiveAreaDailyBO> coverReportAreaBOS(List<ReportUserActiveAreaDailyDO> areaDailyDOS) {
        if(areaDailyDOS.isEmpty()){
            return new ArrayList<>();
        }
        List<ReportUserActiveAreaDailyBO> reportUserActiveBOS = new ArrayList<>();
        for(ReportUserActiveAreaDailyDO reportUserActiveDO : areaDailyDOS){
            ReportUserActiveAreaDailyBO reportUserActiveBO = new ReportUserActiveAreaDailyBO();
            reportUserActiveBO.setMerchantCount(reportUserActiveDO.getMerchantCount());
            reportUserActiveBO.setMemberCount(reportUserActiveDO.getMemberCount());
            reportUserActiveBO.setGmtReport(reportUserActiveDO.getGmtReport());
            reportUserActiveBO.setCityId(reportUserActiveDO.getCityId());
            reportUserActiveBO.setCityName(reportUserActiveDO.getCityName());
            reportUserActiveBOS.add(reportUserActiveBO);
        }
        return reportUserActiveBOS;
    }

    public static List<ReportUserActiveAreaDTO> coverReportAreaDTOS(List<ReportUserActiveAreaDailyBO> listBOS) {
        if (listBOS.isEmpty()) {
            return new ArrayList<>();
        }
        List<ReportUserActiveAreaDTO> reportUserActiveDTOS = new ArrayList<>();
        for (ReportUserActiveAreaDailyBO reportUserActiveAreaDailyBO : listBOS) {
            ReportUserActiveAreaDTO reportUserActiveAreaDTO = new ReportUserActiveAreaDTO();
            reportUserActiveAreaDTO.setMerchantCount(reportUserActiveAreaDailyBO.getMerchantCount());
            reportUserActiveAreaDTO.setMemberCount(reportUserActiveAreaDailyBO.getMemberCount());
            reportUserActiveAreaDTO.setCityId(reportUserActiveAreaDailyBO.getCityId());
            reportUserActiveAreaDTO.setCityName(reportUserActiveAreaDailyBO.getCityName());
            reportUserActiveDTOS.add(reportUserActiveAreaDTO);
        }
        return reportUserActiveDTOS;
    }

    public static List<ReportUserActiveAreaMonthBO> coverReportAreaMonthBOS(List<ReportUserActiveAreaMonthDO> areaMonthDOS) {
        if (areaMonthDOS.isEmpty()) {
            return new ArrayList<>();
        }
        List<ReportUserActiveAreaMonthBO> reportUserActiveBOS = new ArrayList<>();
        for (ReportUserActiveAreaMonthDO reportUserActiveDO : areaMonthDOS) {
            ReportUserActiveAreaMonthBO reportUserActiveBO = new ReportUserActiveAreaMonthBO();
            reportUserActiveBO.setMerchantCount(reportUserActiveDO.getMerchantCount());
            reportUserActiveBO.setMemberCount(reportUserActiveDO.getMemberCount());
            reportUserActiveBO.setGmtReport(reportUserActiveDO.getGmtReport());
            reportUserActiveBO.setCityId(reportUserActiveDO.getCityId());
            reportUserActiveBO.setCityName(reportUserActiveDO.getCityName());
            reportUserActiveBOS.add(reportUserActiveBO);
        }
        return reportUserActiveBOS;
    }

    public static List<ReportUserActiveAreaDTO> coverReportAreaMonthDTOS(List<ReportUserActiveAreaMonthBO> listBOS) {
        if (listBOS.isEmpty()) {
            return new ArrayList<>();
        }
        List<ReportUserActiveAreaDTO> reportUserActiveDTOS = new ArrayList<>();
        for (ReportUserActiveAreaMonthBO reportUserActiveAreaBO : listBOS) {
            ReportUserActiveAreaDTO reportUserActiveAreaDTO = new ReportUserActiveAreaDTO();
            reportUserActiveAreaDTO.setMerchantCount(reportUserActiveAreaBO.getMerchantCount());
            reportUserActiveAreaDTO.setMemberCount(reportUserActiveAreaBO.getMemberCount());
            reportUserActiveAreaDTO.setCityId(reportUserActiveAreaBO.getCityId());
            reportUserActiveAreaDTO.setCityName(reportUserActiveAreaBO.getCityName());
            reportUserActiveDTOS.add(reportUserActiveAreaDTO);
        }
        return reportUserActiveDTOS;
    }
}
