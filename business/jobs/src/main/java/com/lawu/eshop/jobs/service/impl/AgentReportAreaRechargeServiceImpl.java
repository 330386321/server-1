package com.lawu.eshop.jobs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.jobs.service.AgentReportAreaRechargeService;
import com.lawu.eshop.jobs.service.PropertyRechargeService;
import com.lawu.eshop.jobs.service.StatisticsReportAreaRechargeService;
import com.lawu.eshop.property.constants.ThirdPayStatusEnum;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.dto.ReportAreaRechargeDailyDTO;
import com.lawu.eshop.property.param.AgentReportRechargeQueryParam;
import com.lawu.eshop.statistics.param.AgentReportRechargeSaveParam;
import com.lawu.eshop.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentReportAreaRechargeServiceImpl implements AgentReportAreaRechargeService {

	private static Logger logger = LoggerFactory.getLogger(AgentReportAreaRechargeServiceImpl.class);

	@Autowired
	private PropertyRechargeService propertyRechargeService;
	@Autowired
	private StatisticsReportAreaRechargeService statisticsReportAreaRechargeService;
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void executeCollectDailyData() {
		String today = DateUtil.getDateFormat(DateUtil.getDayBefore(new Date()),"yyyy-MM-dd");
		statisticsReportAreaRechargeService.deleteDailyByReportDate(today);

		AgentReportRechargeQueryParam param = new AgentReportRechargeQueryParam();
		param.setDate(today);
		param.setStatus(ThirdPayStatusEnum.SUCCESS.getVal());
		param.setChannel(TransactionPayTypeEnum.BALANCE.getVal());
		Result<List<ReportAreaRechargeDailyDTO>> rtnResult = propertyRechargeService.selectAgentAreaReportRechargeListByDate(param);
		if(ResultCode.SUCCESS != rtnResult.getRet()){
			logger.error("充值报表统计(按日)定时采集数据异常：{}",rtnResult.getMsg());
			return;
		}
		List<ReportAreaRechargeDailyDTO> rntList = rtnResult.getModel();
		if(rntList.isEmpty()){
			logger.info("充值报表统计(按日)定时采集数据srv返回空！");
		}
		List<AgentReportRechargeSaveParam> saveParams = new ArrayList<>();
		for(ReportAreaRechargeDailyDTO dto : rntList){
			AgentReportRechargeSaveParam saveParam = new AgentReportRechargeSaveParam();
			saveParam.setGmtCreate(dto.getGmtCreate());
			saveParam.setGmtReport(dto.getGmtReport());
			saveParam.setMemberRechargeBalance(dto.getMemberRechargeBalance());
			saveParam.setMemberRechargePoint(dto.getMemberRechargePoint());
			saveParam.setMerchantRechargeBalance(dto.getMerchantRechargeBalance());
			saveParam.setMerchantRechargePoint(dto.getMerchantRechargePoint());
			saveParam.setTotalRechargeBalance(dto.getTotalRechargeBalance());
			saveParam.setTotalRechargePoint(dto.getTotalRechargePoint());
			saveParam.setProvinceId(dto.getProvinceId());
			saveParam.setCityId(dto.getCityId());
			saveParam.setAreaId(dto.getAreaId());
			saveParams.add(saveParam);
		}
		Result result = statisticsReportAreaRechargeService.saveDaily(saveParams);
		if(result.getRet() != ResultCode.SUCCESS){
			logger.error("充值报表统计(按日)采集数据保存report_area_recharge_daily表异常！");
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void executeCollectMonthData() {
		String month = DateUtil.getDateFormat(DateUtil.getMonthBefore(new Date()),"yyyy-MM");
		statisticsReportAreaRechargeService.deleteMonthByReportDate(month);

		Result<List<com.lawu.eshop.statistics.dto.ReportAreaRechargeDailyDTO>> rtnResult = statisticsReportAreaRechargeService.getDailyList(month);
		List<com.lawu.eshop.statistics.dto.ReportAreaRechargeDailyDTO> rntList = rtnResult.getModel();
		if(rntList.isEmpty()){
			logger.info("充值报表统计(按月)定时采集数据srv返回空！");
		}
		List<AgentReportRechargeSaveParam> saveParams = new ArrayList<>();
		for(com.lawu.eshop.statistics.dto.ReportAreaRechargeDailyDTO dto : rtnResult.getModel()){
			AgentReportRechargeSaveParam saveParam = new AgentReportRechargeSaveParam();
			saveParam.setGmtCreate(dto.getGmtCreate());
			saveParam.setGmtReport(dto.getGmtReport());
			saveParam.setMemberRechargeBalance(dto.getMemberRechargeBalance());
			saveParam.setMemberRechargePoint(dto.getMemberRechargePoint());
			saveParam.setMerchantRechargeBalance(dto.getMerchantRechargeBalance());
			saveParam.setMerchantRechargePoint(dto.getMerchantRechargePoint());
			saveParam.setTotalRechargeBalance(dto.getTotalRechargeBalance());
			saveParam.setTotalRechargePoint(dto.getTotalRechargePoint());
			saveParam.setProvinceId(dto.getProvinceId());
			saveParam.setCityId(dto.getCityId());
			saveParam.setAreaId(dto.getAreaId());
			saveParams.add(saveParam);
		}
		Result result = statisticsReportAreaRechargeService.saveMonth(saveParams);
		if(result.getRet() != ResultCode.SUCCESS){
			logger.error("充值报表统计(按月)定时采集数据保存report_area_recharge_month表异常！");
		}
	}
	
}
