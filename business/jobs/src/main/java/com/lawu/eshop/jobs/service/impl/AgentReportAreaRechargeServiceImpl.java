package com.lawu.eshop.jobs.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.jobs.service.PropertyRechargeService;
import com.lawu.eshop.jobs.service.PropertyWithdrawCashService;
import com.lawu.eshop.jobs.service.StatisticsReportAreaRechargeService;
import com.lawu.eshop.jobs.service.StatisticsWithdrawCashService;
import com.lawu.eshop.jobs.service.WithdrawCashReportService;
import com.lawu.eshop.property.constants.CashStatusEnum;
import com.lawu.eshop.property.constants.PayTypeEnum;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.dto.AgentReportRechargeQueryDTO;
import com.lawu.eshop.property.dto.WithdrawCashReportDTO;
import com.lawu.eshop.property.param.AgentRechargeReportParam;
import com.lawu.eshop.property.param.AgentReportRechargeQueryParam;
import com.lawu.eshop.property.param.RechargeReportParam;
import com.lawu.eshop.property.param.WithdrawCashReportParam;
import com.lawu.eshop.statistics.dto.ReportWithdrawDailyDTO;
import com.lawu.eshop.statistics.param.ReportKCommonParam;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.constants.UserTypeEnum;
import com.lawu.eshop.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentReportAreaRechargeServiceImpl implements WithdrawCashReportService {

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
		param.setStatus(CashStatusEnum.SUCCESS.getVal());
		param.setChannel(TransactionPayTypeEnum.BALANCE.getVal());
		Result<List<AgentReportRechargeQueryDTO>> rntResult = propertyRechargeService.selectAgentAreaReportRechargeListByDate(param);

		if(ResultCode.SUCCESS != rntResult.getRet()){
			logger.error("充值报表统计(按日)定时采集数据异常：{}",rntResult.getMsg());
			return;
		}

		List<AgentReportRechargeQueryDTO> rntList = rntResult.getModel();
		if(rntList.isEmpty()){
			logger.info("充值报表统计(按日)定时采集数据srv返回空！");
		}

		AgentRechargeReportParam reportRecharge = new AgentRechargeReportParam();
		reportRecharge.setGmtCreate(new Date());
		reportRecharge.setGmtReport(DateUtil.formatDate(today, "yyyy-MM-dd"));
		for(AgentReportRechargeQueryDTO dto : rntList){
			if(dto.getUserType().equals(UserTypeEnum.MEMBER.val)){
				if(PayTypeEnum.BALANCE.getVal().equals(dto.getRechargeType())){
					reportRecharge.setMemberRechargeBalance(dto.getRechargeMoney());
				} else if(PayTypeEnum.POINT.getVal().equals(dto.getRechargeType())){
					reportRecharge.setMemberRechargePoint(dto.getRechargeMoney());
				}
			} else if(dto.getUserType().equals(UserTypeEnum.MEMCHANT.val)){
				if(PayTypeEnum.BALANCE.getVal().equals(dto.getRechargeType())){
					reportRecharge.setMerchantRechargeBalance(dto.getRechargeMoney());
				} else if(PayTypeEnum.POINT.getVal().equals(dto.getRechargeType())){
					reportRecharge.setMerchantRechargePoint(dto.getRechargeMoney());
				}
			}
		}
		reportRecharge.setMemberRechargeBalance(reportRecharge.getMemberRechargeBalance() == null ? new BigDecimal(0) : reportRecharge.getMemberRechargeBalance());
		reportRecharge.setMemberRechargePoint(reportRecharge.getMemberRechargePoint() == null ? new BigDecimal(0) : reportRecharge.getMemberRechargePoint());
		reportRecharge.setMerchantRechargeBalance(reportRecharge.getMerchantRechargeBalance() == null ? new BigDecimal(0) : reportRecharge.getMerchantRechargeBalance());
		reportRecharge.setMerchantRechargePoint(reportRecharge.getMerchantRechargePoint() == null ? new BigDecimal(0) : reportRecharge.getMerchantRechargePoint());
		reportRecharge.setTotalRechargeBalance(reportRecharge.getMemberRechargeBalance().add(reportRecharge.getMerchantRechargeBalance()));
		reportRecharge.setTotalRechargePoint(reportRecharge.getMemberRechargePoint().add(reportRecharge.getMerchantRechargePoint()));

		Result result = statisticsReportAreaRechargeService.saveDaily(reportRecharge);
		if(result.getRet() != ResultCode.SUCCESS){
			logger.error("提现报表统计时采集数据保存report_withdraw_daily表异常！");
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void executeCollectMonthData() {
//		String month = DateUtil.getDateFormat(DateUtil.getMonthBefore(new Date()),"yyyy-MM");
//		statisticsWithdrawCashService.deleteMonthByReportDate(month);
//
//		Result<List<ReportWithdrawDailyDTO>> rntResult = statisticsWithdrawCashService.getDailyList(month);
//		List<ReportWithdrawDailyDTO> rntList = rntResult.getModel();
//		if(rntList.isEmpty()){
//			logger.info("提现报表统计(按月)定时采集数据srv返回空！");
//		}
//
//		BigDecimal memberMoney = new BigDecimal("0");
//		BigDecimal merchantMoney = new BigDecimal("0");
//		BigDecimal totalMoney = new BigDecimal("0");
//		for(ReportWithdrawDailyDTO dto : rntList){
//			memberMoney = memberMoney.add(dto.getMemberMoney());
//			merchantMoney = merchantMoney.add(dto.getMerchantMoney());
//			totalMoney = totalMoney.add(dto.getTotalMoney());
//		}
//
//		ReportKCommonParam reportWithdraw = new ReportKCommonParam();
//		reportWithdraw.setGmtCreate(new Date());
//		reportWithdraw.setGmtReport(DateUtil.formatDate(month+"-01", "yyyy-MM-dd"));
//		reportWithdraw.setMemberMoney(memberMoney);
//		reportWithdraw.setMerchantMoney(merchantMoney);
//		reportWithdraw.setTotalMoney(totalMoney);
//		Result result = statisticsWithdrawCashService.saveMonth(reportWithdraw);
//		if(result.getRet() != ResultCode.SUCCESS){
//			logger.error("提现报表统计定时采集数据保存report_withdraw_month表异常！");
//		}
	}
	
}
