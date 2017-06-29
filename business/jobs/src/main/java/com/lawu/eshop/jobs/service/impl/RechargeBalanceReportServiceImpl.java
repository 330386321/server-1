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
import com.lawu.eshop.jobs.service.PropertyRechargeService;
import com.lawu.eshop.jobs.service.RechargeBalanceReportService;
import com.lawu.eshop.jobs.service.StatisticsRechargeBalanceService;
import com.lawu.eshop.property.constants.PayTypeEnum;
import com.lawu.eshop.property.constants.ThirdPayStatusEnum;
import com.lawu.eshop.property.dto.RechargeReportDTO;
import com.lawu.eshop.property.param.RechargeReportParam;
import com.lawu.eshop.statistics.dto.RechargeBalanceDailyDTO;
import com.lawu.eshop.statistics.param.ReportKCommonParam;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.utils.DateUtil;

@Service
public class RechargeBalanceReportServiceImpl implements RechargeBalanceReportService {

	private static Logger logger = LoggerFactory.getLogger(RechargeBalanceReportServiceImpl.class);

	@Autowired
	private PropertyRechargeService propertyRechargeService;
	@Autowired
	private StatisticsRechargeBalanceService statisticsRechargeBalanceService;
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void executeCollectDailyData() {
		String today = DateUtil.getDateFormat(DateUtil.getDayBefore(new Date()),"yyyy-MM-dd");
		statisticsRechargeBalanceService.deleteDailyByReportDate(today);
		
		RechargeReportParam param = new RechargeReportParam();
		param.setDate(today);
		param.setStatus(ThirdPayStatusEnum.SUCCESS.getVal());
		param.setRechargeType(PayTypeEnum.BALANCE.getVal());
		Result<List<RechargeReportDTO>> rntResult = propertyRechargeService.selectWithdrawCashListByDateAndStatus(param);
		
		if(ResultCode.SUCCESS != rntResult.getRet()){
			logger.error("充值余额报表统计定时采集数据异常：{}",rntResult.getMsg());
			return;
		}
		
		List<RechargeReportDTO> rntList = rntResult.getModel();
		if(rntList.isEmpty()){
			logger.info("充值余额报表统计(按日)定时采集数据srv返回空！");
		}
		
		BigDecimal memberMoney = new BigDecimal("0");
		BigDecimal merchantMoney = new BigDecimal("0");
		for(RechargeReportDTO dto : rntList){
			if(dto.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
				memberMoney = memberMoney.add(dto.getRechargeMoney());
			}else if(dto.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)){
				merchantMoney = merchantMoney.add(dto.getRechargeMoney());
			}
		}
		
		ReportKCommonParam reportWithdraw = new ReportKCommonParam();
		reportWithdraw.setGmtCreate(new Date());
		reportWithdraw.setGmtReport(DateUtil.formatDate(today, "yyyy-MM-dd"));
		reportWithdraw.setMemberMoney(memberMoney);
		reportWithdraw.setMerchantMoney(merchantMoney);
		reportWithdraw.setTotalMoney(memberMoney.add(merchantMoney));
		Result result = statisticsRechargeBalanceService.saveDaily(reportWithdraw);
		if(result.getRet() != ResultCode.SUCCESS){
			logger.error("充值余额报表统计时采集数据保存report_recharge_balance_daily表异常！");
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void executeCollectMonthData() {
		String month = DateUtil.getDateFormat(DateUtil.getMonthBefore(new Date()),"yyyy-MM");
		statisticsRechargeBalanceService.deleteMonthByReportDate(month);
		
		Result<List<RechargeBalanceDailyDTO>> rntResult = statisticsRechargeBalanceService.getDailyList(month);
		List<RechargeBalanceDailyDTO> rntList = rntResult.getModel();
		if(rntList.isEmpty()){
			logger.info("充值余额报表统计(按月)定时采集数据srv返回空！");
		}
		
		BigDecimal memberMoney = new BigDecimal("0");
		BigDecimal merchantMoney = new BigDecimal("0");
		BigDecimal totalMoney = new BigDecimal("0");
		for(RechargeBalanceDailyDTO dto : rntList){
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
		Result result = statisticsRechargeBalanceService.saveMonth(reportWithdraw);
		if(result.getRet() != ResultCode.SUCCESS){
			logger.error("充值余额报表统计定时采集数据保存report_recharge_balance_month表异常！");
		}
	}
	
}
