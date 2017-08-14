package com.lawu.eshop.jobs.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.jobs.service.PropertyWithdrawCashService;
import com.lawu.eshop.jobs.service.RegionService;
import com.lawu.eshop.jobs.service.StatisticsWithdrawCashService;
import com.lawu.eshop.jobs.service.WithdrawCashReportService;
import com.lawu.eshop.mall.dto.RegionDTO;
import com.lawu.eshop.property.constants.CashStatusEnum;
import com.lawu.eshop.property.dto.WithdrawCashReportDTO;
import com.lawu.eshop.property.param.AgentWithdrawCashReportParam;
import com.lawu.eshop.property.param.WithdrawCashReportParam;
import com.lawu.eshop.statistics.dto.ReportWithdrawDailyDTO;
import com.lawu.eshop.statistics.param.AgentWithdrawCashParam;
import com.lawu.eshop.statistics.param.ReportKCommonParam;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.utils.DateUtil;

@Service
public class WithdrawCashReportServiceImpl implements WithdrawCashReportService {

	private static Logger logger = LoggerFactory.getLogger(WithdrawCashReportServiceImpl.class);

	@Autowired
	private PropertyWithdrawCashService propertyWithdrawCashService;
	@Autowired
	private StatisticsWithdrawCashService statisticsWithdrawCashService;

	@Autowired
	private RegionService regionService;
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void executeCollectDailyData() {
		String today = DateUtil.getDateFormat(DateUtil.getDayBefore(new Date()),"yyyy-MM-dd");
		statisticsWithdrawCashService.deleteDailyByReportDate(today);
		
		WithdrawCashReportParam param = new WithdrawCashReportParam();
		param.setDate(today);
		param.setStatus(CashStatusEnum.SUCCESS.getVal());
		Result<List<WithdrawCashReportDTO>> rntResult = propertyWithdrawCashService.selectWithdrawCashListByDateAndStatus(param);
		
		if(ResultCode.SUCCESS != rntResult.getRet()){
			logger.error("提现报表统计定时采集数据异常：{}",rntResult.getMsg());
			return;
		}
		
		List<WithdrawCashReportDTO> rntList = rntResult.getModel();
		if(rntList.isEmpty()){
			logger.info("提现报表统计(按日)定时采集数据srv返回空！");
		}
		
		BigDecimal memberMoney = new BigDecimal("0");
		BigDecimal merchantMoney = new BigDecimal("0");
		for(WithdrawCashReportDTO dto : rntList){
			if(dto.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
				memberMoney = memberMoney.add(dto.getCashMoney());
			}else if(dto.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)){
				merchantMoney = merchantMoney.add(dto.getCashMoney());
			}
		}
		
		ReportKCommonParam reportWithdraw = new ReportKCommonParam();
		reportWithdraw.setGmtCreate(new Date());
		reportWithdraw.setGmtReport(DateUtil.formatDate(today, "yyyy-MM-dd"));
		reportWithdraw.setMemberMoney(memberMoney);
		reportWithdraw.setMerchantMoney(merchantMoney);
		reportWithdraw.setTotalMoney(memberMoney.add(merchantMoney));
		Result result = statisticsWithdrawCashService.saveDaily(reportWithdraw);
		if(result.getRet() != ResultCode.SUCCESS){
			logger.error("提现报表统计时采集数据保存report_withdraw_daily表异常！");
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void executeCollectMonthData() {
		String month = DateUtil.getDateFormat(DateUtil.getMonthBefore(new Date()),"yyyy-MM");
		statisticsWithdrawCashService.deleteMonthByReportDate(month);
		
		Result<List<ReportWithdrawDailyDTO>> rntResult = statisticsWithdrawCashService.getDailyList(month);
		List<ReportWithdrawDailyDTO> rntList = rntResult.getModel();
		if(rntList.isEmpty()){
			logger.info("提现报表统计(按月)定时采集数据srv返回空！");
		}
		
		BigDecimal memberMoney = new BigDecimal("0");
		BigDecimal merchantMoney = new BigDecimal("0");
		BigDecimal totalMoney = new BigDecimal("0");
		for(ReportWithdrawDailyDTO dto : rntList){
			memberMoney = memberMoney.add(dto.getMemberMoney());
			merchantMoney = merchantMoney.add(dto.getMerchantMoney());
			totalMoney = totalMoney.add(dto.getTotalMoney());
		}
		
		ReportKCommonParam reportWithdraw = new ReportKCommonParam();
		reportWithdraw.setGmtCreate(new Date());
		reportWithdraw.setGmtReport(DateUtil.formatDate(month+"-01", "yyyy-MM-dd"));
		reportWithdraw.setMemberMoney(memberMoney);
		reportWithdraw.setMerchantMoney(merchantMoney);
		reportWithdraw.setTotalMoney(totalMoney);
		Result result = statisticsWithdrawCashService.saveMonth(reportWithdraw);
		if(result.getRet() != ResultCode.SUCCESS){
			logger.error("提现报表统计定时采集数据保存report_withdraw_month表异常！");
		}
	}

	@Override
	public void executeCollectAgentDailyData() {
		//查询二级城市Path

		Result<List<RegionDTO>> regionResult = regionService.getRegionLevelTwo();
		if (regionResult.getModel().isEmpty()) {
			return;
		}
		String today = DateUtil.getDateFormat(DateUtil.getDayBefore(new Date()),"yyyy-MM-dd");
		AgentWithdrawCashReportParam param = new AgentWithdrawCashReportParam();
		param.setDate(today);
		param.setStatus(CashStatusEnum.SUCCESS.getVal());
		for (RegionDTO regionDTO : regionResult.getModel()) {
			param.setCityId(regionDTO.getId());
			Result<List<WithdrawCashReportDTO>> rntResult = propertyWithdrawCashService.selectAgentWithdrawCashList(param);
			if(!rntResult.getModel().isEmpty()){
				//存在提现记录
				BigDecimal memberMoney = new BigDecimal("0");
				BigDecimal merchantMoney = new BigDecimal("0");
				for(WithdrawCashReportDTO dto : rntResult.getModel()){
					if(dto.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
						memberMoney = memberMoney.add(dto.getCashMoney());
					}else if(dto.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)){
						merchantMoney = merchantMoney.add(dto.getCashMoney());
					}
				}
				AgentWithdrawCashParam reportWithdraw = new AgentWithdrawCashParam();
				reportWithdraw.setGmtReport(DateUtil.formatDate(today, "yyyy-MM-dd"));
				reportWithdraw.setMemberMoney(memberMoney);
				reportWithdraw.setMerchantMoney(merchantMoney);
				reportWithdraw.setTotalMoney(memberMoney.add(merchantMoney));
				reportWithdraw.setCityId(regionDTO.getId());
				reportWithdraw.setCityName(regionDTO.getName());
				statisticsWithdrawCashService.saveAgentDaily(reportWithdraw);
			}
		}
	}

}
