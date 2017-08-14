package com.lawu.eshop.statistics.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lawu.eshop.statistics.dto.ReportCommonBackDTO;
import com.lawu.eshop.statistics.param.AgentReportRechargeSaveParam;
import com.lawu.eshop.statistics.srv.bo.ReportAreaRechargeDailyBO;
import com.lawu.eshop.statistics.srv.domain.ReportAreaRechargeDailyDO;
import com.lawu.eshop.statistics.srv.domain.ReportAreaRechargeDailyDOExample;
import com.lawu.eshop.statistics.srv.domain.ReportAreaRechargeMonthDO;
import com.lawu.eshop.statistics.srv.domain.ReportAreaRechargeMonthDOExample;
import com.lawu.eshop.statistics.srv.mapper.ReportAreaRechargeDailyDOMapper;
import com.lawu.eshop.statistics.srv.mapper.ReportAreaRechargeMonthDOMapper;
import com.lawu.eshop.statistics.srv.service.ReportAreaRechargeService;
import com.lawu.eshop.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportAreaRechargeServiceImpl implements ReportAreaRechargeService {

    @Autowired
    private ReportAreaRechargeDailyDOMapper reportAreaRechargeDailyDOMapper;
    @Autowired
    private ReportAreaRechargeMonthDOMapper reportAreaRechargeMonthDOMapper;

    @Override
    @Transactional
    public void saveDaily(List<AgentReportRechargeSaveParam> saveParams) {
        ReportAreaRechargeDailyDO record = new ReportAreaRechargeDailyDO();
        for (AgentReportRechargeSaveParam param : saveParams) {
            record.setGmtCreate(param.getGmtCreate());
            record.setGmtReport(param.getGmtReport());
            record.setMemberRechargeBalance(param.getMemberRechargeBalance());
            record.setMemberRechargePoint(param.getMemberRechargePoint());
            record.setMerchantRechargeBalance(param.getMerchantRechargeBalance());
            record.setMerchantRechargePoint(param.getMerchantRechargePoint());
            record.setTotalRechargeBalance(param.getTotalRechargeBalance());
            record.setTotalRechargePoint(param.getTotalRechargePoint());
            reportAreaRechargeDailyDOMapper.insertSelective(record);
        }
    }

    @Override
    public void saveMonth(List<AgentReportRechargeSaveParam> saveParams) {
        ReportAreaRechargeMonthDO record = new ReportAreaRechargeMonthDO();
        for (AgentReportRechargeSaveParam param : saveParams) {
            record.setGmtCreate(param.getGmtCreate());
            record.setGmtReport(param.getGmtReport());
            record.setMemberRechargeBalance(param.getMemberRechargeBalance());
            record.setMemberRechargePoint(param.getMemberRechargePoint());
            record.setMerchantRechargeBalance(param.getMerchantRechargeBalance());
            record.setMerchantRechargePoint(param.getMerchantRechargePoint());
            record.setTotalRechargeBalance(param.getTotalRechargeBalance());
            record.setTotalRechargePoint(param.getTotalRechargePoint());
            reportAreaRechargeMonthDOMapper.insertSelective(record);
        }
    }

    @Override
    public List<ReportAreaRechargeDailyBO> getDailyList(String reportDate) {
        ReportAreaRechargeDailyDOExample example = new ReportAreaRechargeDailyDOExample();
        Date begin = DateUtil.formatDate(reportDate + "-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
        Date end = DateUtil.getLastDayOfMonth(begin);
        example.createCriteria().andGmtReportBetween(begin, end);
        List<ReportAreaRechargeDailyDO> rntList = reportAreaRechargeDailyDOMapper.selectByExample(example);
        List<ReportAreaRechargeDailyBO> boList = new ArrayList<>();
        for (ReportAreaRechargeDailyDO rdo : rntList) {
            ReportAreaRechargeDailyBO bo = new ReportAreaRechargeDailyBO();
            bo.setGmtCreate(rdo.getGmtCreate());
            bo.setGmtReport(rdo.getGmtReport());
            bo.setId(rdo.getId());
            bo.setMemberRechargeBalance(rdo.getMemberRechargeBalance());
            bo.setMemberRechargePoint(rdo.getMemberRechargePoint());
            bo.setMerchantRechargeBalance(rdo.getMerchantRechargeBalance());
            bo.setMerchantRechargePoint(rdo.getMerchantRechargePoint());
            bo.setTotalRechargeBalance(rdo.getTotalRechargeBalance());
            bo.setTotalRechargePoint(rdo.getTotalRechargePoint());
            bo.setProvinceId(rdo.getProvinceId());
            bo.setCityId(rdo.getCityId());
            bo.setAreaId(rdo.getAreaId());
            boList.add(bo);
        }
        return boList;
    }

    @Override
    public void deleteDailyByReportDate(String reportDate) {
        ReportAreaRechargeDailyDOExample example = new ReportAreaRechargeDailyDOExample();
        example.createCriteria().andGmtReportEqualTo(DateUtil.formatDate(reportDate, "yyyy-MM-dd"));
        reportAreaRechargeDailyDOMapper.deleteByExample(example);
    }

    @Override
    public void deleteMonthByReportDate(String reportDate) {
        ReportAreaRechargeMonthDOExample example = new ReportAreaRechargeMonthDOExample();
        example.createCriteria().andGmtReportEqualTo(DateUtil.formatDate(reportDate, "yyyy-MM"));
        reportAreaRechargeMonthDOMapper.deleteByExample(example);
    }

    @Override
    public ReportCommonBackDTO selectReport(String bdate, String edate) {
//		if("".equals(bdate) || "".equals(edate)){
//			bdate = DateUtil.getDateFormat(new Date(), "yyyy-MM")+"-01";
//			edate = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd");
//		}
//		ReportCommonBackDTO dto = new ReportCommonBackDTO();
//		List<String> xAxisData = new ArrayList<>();
//		List<BigDecimal> yAxisMemberData = new ArrayList<>();
//		List<BigDecimal> yAxisMerchantData = new ArrayList<>();
//		List<BigDecimal> yAxisTotalData = new ArrayList<>();
//		if(bdate.length() > 7){
//			ReportWithdrawDailyDOExample example = new ReportWithdrawDailyDOExample();
//			Date begin = DateUtil.formatDate(bdate, "yyyy-MM-dd");
//			Date end = DateUtil.formatDate(edate, "yyyy-MM-dd");
//			example.createCriteria().andGmtReportBetween(begin, end);
//			example.setOrderByClause(" gmt_report asc ");
//			List<ReportWithdrawDailyDO> rntList = reportWithdrawDailyDOMapper.selectByExample(example);
//			for(ReportWithdrawDailyDO rdo : rntList){
//				String day = DateUtil.getDateFormat(rdo.getGmtReport(), "MM-dd");
//				xAxisData.add(day);
//				yAxisMemberData.add(rdo.getMemberMoney().setScale(2));
//				yAxisMerchantData.add(rdo.getMerchantMoney().setScale(2));
//				yAxisTotalData.add(rdo.getTotalMoney().setScale(2));
//			}
//		}else {
//			ReportWithdrawMonthDOExample example = new ReportWithdrawMonthDOExample();
//			Date begin = DateUtil.formatDate(bdate+"-01", "yyyy-MM-dd");
//			Date endFirst = DateUtil.formatDate(edate+"-01", "yyyy-MM-dd");
//			Date end = DateUtil.getLastDayOfMonth(endFirst);
//			example.createCriteria().andGmtReportBetween(begin, end);
//			example.setOrderByClause(" gmt_report asc ");
//			List<ReportWithdrawMonthDO> rntList = reportWithdrawMonthDOMapper.selectByExample(example);
//			for(ReportWithdrawMonthDO rdo : rntList){
//				String day = DateUtil.getDateFormat(rdo.getGmtReport(), "yyyy-MM");
//				xAxisData.add(day);
//				yAxisMemberData.add(rdo.getMemberMoney().setScale(2));
//				yAxisMerchantData.add(rdo.getMerchantMoney().setScale(2));
//				yAxisTotalData.add(rdo.getTotalMoney().setScale(2));
//			}
//		}
//		dto.setxAxisData(xAxisData);
//		dto.setyAxisMemberData(yAxisMemberData);
//		dto.setyAxisMerchantData(yAxisMerchantData);
//		dto.setyAxisTotalData(yAxisTotalData);
//		dto.setBdate(bdate);
//		dto.setEdate(edate);
//		return dto;
        return null;
    }

}
