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
import com.lawu.eshop.jobs.service.StatisticsWithdrawCashService;
import com.lawu.eshop.jobs.service.WithdrawCashReportService;
import com.lawu.eshop.property.constants.CashStatusEnum;
import com.lawu.eshop.property.dto.WithdrawCashReportDTO;
import com.lawu.eshop.property.param.WithdrawCashReportParam;
import com.lawu.eshop.statistics.param.ReportWithdrawParam;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.constants.UserTypeEnum;
import com.lawu.eshop.utils.DateUtil;

@Service
public class WithdrawCashReportServiceImpl implements WithdrawCashReportService {

	private static Logger logger = LoggerFactory.getLogger(WithdrawCashReportServiceImpl.class);

	@Autowired
	private PropertyWithdrawCashService propertyWithdrawCashService;
	@Autowired
	private StatisticsWithdrawCashService statisticsWithdrawCashService;
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void executeCollectDailyData() {
		WithdrawCashReportParam param = new WithdrawCashReportParam();
		String today = DateUtil.getDateFormat(DateUtil.getDayBefore(new Date()),"yyyy-MM-dd");
		param.setDate(today);
		param.setStatus(CashStatusEnum.SUCCESS.getVal());
		Result<List<WithdrawCashReportDTO>> rntResult = propertyWithdrawCashService.selectWithdrawCashListByDateAndStatus(param);
		
		if(ResultCode.SUCCESS != rntResult.getRet()){
			logger.error("提现报表统计定时采集数据异常：{}",rntResult.getMsg());
			return;
		}
		
		List<WithdrawCashReportDTO> rntList = rntResult.getModel();
		if(rntList.isEmpty()){
			logger.info("提现报表统计定时采集数据srv返回空！");
			return;
		}
		
		BigDecimal memberMoney = new BigDecimal("0");
		BigDecimal merchantMoney = new BigDecimal("0");
		for(WithdrawCashReportDTO dto : rntResult.getModel()){
			if(dto.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
				memberMoney = memberMoney.add(dto.getCashMoney());
			}else if(dto.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)){
				merchantMoney = merchantMoney.add(dto.getCashMoney());
			}
		}
		
		ReportWithdrawParam reportWithdraw = new ReportWithdrawParam();
		reportWithdraw.setGmtCreate(new Date());
		reportWithdraw.setGmtReport(DateUtil.formatDate(today, "yyyy-MM-dd"));
		reportWithdraw.setMoney(memberMoney);
		reportWithdraw.setUserType(UserTypeEnum.MEMBER.val);
		Result result = statisticsWithdrawCashService.saveDaily(reportWithdraw);
		if(result.getRet() != ResultCode.SUCCESS){
			logger.error("提现报表统计定时采集数据保存用户提现记录(report_withdraw_daily)异常！");
		}
		
		reportWithdraw.setMoney(merchantMoney);
		reportWithdraw.setUserType(UserTypeEnum.MEMCHANT.val);
		result = statisticsWithdrawCashService.saveDaily(reportWithdraw);
		if(result.getRet() != ResultCode.SUCCESS){
			logger.error("提现报表统计定时采集数据保存商家提现记录(report_withdraw_daily)异常！");
		}
	}

	@Override
	public void executeCollectMonthData() {
		// TODO Auto-generated method stub
		
	}
	
}
