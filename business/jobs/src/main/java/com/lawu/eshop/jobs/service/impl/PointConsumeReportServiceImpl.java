package com.lawu.eshop.jobs.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.jobs.service.PointConsumeReportService;
import com.lawu.eshop.jobs.service.PropertyPointDetailService;
import com.lawu.eshop.jobs.service.StatisticsPointConsumeService;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.PropertyInfoDirectionEnum;
import com.lawu.eshop.property.dto.PointConsumeReportDTO;
import com.lawu.eshop.property.param.PointDetailReportParam;
import com.lawu.eshop.statistics.dto.PointConsumeDailyDTO;
import com.lawu.eshop.statistics.param.ReportKCommonParam;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.utils.DateUtil;

@Service
public class PointConsumeReportServiceImpl implements PointConsumeReportService {

	private static Logger logger = LoggerFactory.getLogger(PointConsumeReportServiceImpl.class);

	@Autowired
	private PropertyPointDetailService propertyPointDetailService;
	@Autowired
	private StatisticsPointConsumeService statisticsPointConsumeService;
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void executeCollectDailyData() {
		Date lastDay = statisticsPointConsumeService.getDaily();
		if(lastDay != null && !DateUtils.isSameDay(lastDay, DateUtil.getSomeDay(new Date(), -2))) {
			int betweenDay = DateUtil.daysOfTwo(lastDay);
			for(int i = 0; i < betweenDay - 1; i++) {
				String today = DateUtil.getDateFormat(DateUtil.getDayBefore(DateUtil.getSomeDay(lastDay, 2 + i)),"yyyy-MM-dd");
				statisticsPointConsumeService.deleteDailyByReportDate(today);
				
				PointDetailReportParam param = new PointDetailReportParam();
				param.setDate(today);
				param.setDirection(PropertyInfoDirectionEnum.OUT.getVal());
				Result<List<PointConsumeReportDTO>> rntResult = propertyPointDetailService.selectPointDetailListByDateAndDirection(param);
				
				if(ResultCode.SUCCESS != rntResult.getRet()){
					logger.error("积分消费报表统计定时采集数据异常：{}",rntResult.getMsg());
					return;
				}
				
				List<PointConsumeReportDTO> rntList = rntResult.getModel();
				if(rntList.isEmpty()){
					logger.info("积分消费报表统计(按日)定时采集数据srv返回空！");
				}
				
				BigDecimal memberPoint = new BigDecimal("0");
				BigDecimal merchantPoint = new BigDecimal("0");
				for(PointConsumeReportDTO dto : rntList){
					if(dto.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
						memberPoint = memberPoint.add(dto.getPoint());
					}else if(dto.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)){
						merchantPoint = merchantPoint.add(dto.getPoint());
					}
				}
				
				//查询当日退回的积分总额
				param.setDirection(PropertyInfoDirectionEnum.IN.getVal());
				param.setPointType(MerchantTransactionTypeEnum.AD_RETURN_POINT.getValue());
				Result<List<PointConsumeReportDTO>> rntPointBackResult = propertyPointDetailService.selectPointDetailListByDateAndDirectionAndPointType(param);
				List<PointConsumeReportDTO> rntBackList = rntPointBackResult.getModel();
				BigDecimal merchantBackPoint = new BigDecimal("0");
				for(PointConsumeReportDTO dto : rntBackList){
					merchantBackPoint = merchantBackPoint.add(dto.getPoint());
				}
				merchantPoint = merchantPoint.subtract(merchantBackPoint);//需要减去退回部分积分
				if(merchantPoint.compareTo(BigDecimal.ZERO) == -1)
					merchantPoint = new BigDecimal("0");
				ReportKCommonParam reportWithdraw = new ReportKCommonParam();
				reportWithdraw.setGmtCreate(new Date());
				reportWithdraw.setGmtReport(DateUtil.formatDate(today, "yyyy-MM-dd"));
				reportWithdraw.setMemberMoney(memberPoint);
				reportWithdraw.setMerchantMoney(merchantPoint);
				reportWithdraw.setTotalMoney(memberPoint.add(merchantPoint));
				Result result = statisticsPointConsumeService.saveDaily(reportWithdraw);
				if(result.getRet() != ResultCode.SUCCESS){
					logger.error("积分消费报表统计时采集数据保存report_point_consume_daily表异常！");
				}
			}
		} else {
			String today = DateUtil.getDateFormat(DateUtil.getDayBefore(new Date()),"yyyy-MM-dd");
			statisticsPointConsumeService.deleteDailyByReportDate(today);
			
			PointDetailReportParam param = new PointDetailReportParam();
			param.setDate(today);
			param.setDirection(PropertyInfoDirectionEnum.OUT.getVal());
			Result<List<PointConsumeReportDTO>> rntResult = propertyPointDetailService.selectPointDetailListByDateAndDirection(param);
			
			if(ResultCode.SUCCESS != rntResult.getRet()){
				logger.error("积分消费报表统计定时采集数据异常：{}",rntResult.getMsg());
				return;
			}
			
			List<PointConsumeReportDTO> rntList = rntResult.getModel();
			if(rntList.isEmpty()){
				logger.info("积分消费报表统计(按日)定时采集数据srv返回空！");
			}
			
			BigDecimal memberPoint = new BigDecimal("0");
			BigDecimal merchantPoint = new BigDecimal("0");
			for(PointConsumeReportDTO dto : rntList){
				if(dto.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
					memberPoint = memberPoint.add(dto.getPoint());
				}else if(dto.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)){
					merchantPoint = merchantPoint.add(dto.getPoint());
				}
			}
			
			//查询当日退回的积分总额
			param.setDirection(PropertyInfoDirectionEnum.IN.getVal());
			param.setPointType(MerchantTransactionTypeEnum.AD_RETURN_POINT.getValue());
			Result<List<PointConsumeReportDTO>> rntPointBackResult = propertyPointDetailService.selectPointDetailListByDateAndDirectionAndPointType(param);
			List<PointConsumeReportDTO> rntBackList = rntPointBackResult.getModel();
			BigDecimal merchantBackPoint = new BigDecimal("0");
			for(PointConsumeReportDTO dto : rntBackList){
				merchantBackPoint = merchantBackPoint.add(dto.getPoint());
			}
			merchantPoint = merchantPoint.subtract(merchantBackPoint);//需要减去退回部分积分
			
			ReportKCommonParam reportWithdraw = new ReportKCommonParam();
			reportWithdraw.setGmtCreate(new Date());
			reportWithdraw.setGmtReport(DateUtil.formatDate(today, "yyyy-MM-dd"));
			reportWithdraw.setMemberMoney(memberPoint);
			reportWithdraw.setMerchantMoney(merchantPoint);
			reportWithdraw.setTotalMoney(memberPoint.add(merchantPoint));
			Result result = statisticsPointConsumeService.saveDaily(reportWithdraw);
			if(result.getRet() != ResultCode.SUCCESS){
				logger.error("积分消费报表统计时采集数据保存report_point_consume_daily表异常！");
			}
		}
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void executeCollectMonthData() {
		Date lastDay = statisticsPointConsumeService.getMonth();
		
		//如果最后一条统计的数据不是前两个月的第一天
		if(lastDay != null && !DateUtils.isSameDay(lastDay, DateUtil.getFirstDayOfMonth(DateUtil.getMonthBefore(DateUtil.getMonthBefore(new Date()))))) {
			int i = 0;
			while(true) {
				//统计lastday的后面几个月直到上个月 
				Date date = DateUtil.nextMonthFirstDate(lastDay, 1 + i);	
				if(DateUtils.isSameDay(date, DateUtil.getFirstDayOfMonth(new Date()))) {
					break;
				}
				i++;
				//要统计的年月
				String month = DateUtil.getDateFormat(date,"yyyy-MM");
				statisticsPointConsumeService.deleteMonthByReportDate(month);
				
				Result<List<PointConsumeDailyDTO>> rntResult = statisticsPointConsumeService.getDailyList(month);
				List<PointConsumeDailyDTO> rntList = rntResult.getModel();
				if(rntList.isEmpty()){
					logger.info("积分消费报表统计(按月)定时采集数据srv返回空！");
				}
				
				BigDecimal memberPoint = new BigDecimal("0");
				BigDecimal merchantPoint = new BigDecimal("0");
				BigDecimal totalPoint = new BigDecimal("0");
				for(PointConsumeDailyDTO dto : rntList){
					memberPoint = memberPoint.add(dto.getMemberPoint());
					merchantPoint = merchantPoint.add(dto.getMerchantPoint());
					totalPoint = totalPoint.add(dto.getTotalPoint());
				}
				
				ReportKCommonParam reportWithdraw = new ReportKCommonParam();
				reportWithdraw.setGmtCreate(new Date());
				reportWithdraw.setGmtReport(DateUtil.formatDate(month+"-01", "yyyy-MM-dd"));
				reportWithdraw.setMemberMoney(memberPoint);
				reportWithdraw.setMerchantMoney(merchantPoint);
				reportWithdraw.setTotalMoney(totalPoint);
				Result result = statisticsPointConsumeService.saveMonth(reportWithdraw);
				if(result.getRet() != ResultCode.SUCCESS){
					logger.error("积分消费报表统计定时采集数据保存report_point_consume_month表异常！");
				}
			}
		} else {
			String month = DateUtil.getDateFormat(DateUtil.getMonthBefore(new Date()),"yyyy-MM");
			statisticsPointConsumeService.deleteMonthByReportDate(month);
			
			Result<List<PointConsumeDailyDTO>> rntResult = statisticsPointConsumeService.getDailyList(month);
			List<PointConsumeDailyDTO> rntList = rntResult.getModel();
			if(rntList.isEmpty()){
				logger.info("积分消费报表统计(按月)定时采集数据srv返回空！");
			}
			
			BigDecimal memberPoint = new BigDecimal("0");
			BigDecimal merchantPoint = new BigDecimal("0");
			BigDecimal totalPoint = new BigDecimal("0");
			for(PointConsumeDailyDTO dto : rntList){
				memberPoint = memberPoint.add(dto.getMemberPoint());
				merchantPoint = merchantPoint.add(dto.getMerchantPoint());
				totalPoint = totalPoint.add(dto.getTotalPoint());
			}
			
			ReportKCommonParam reportWithdraw = new ReportKCommonParam();
			reportWithdraw.setGmtCreate(new Date());
			reportWithdraw.setGmtReport(DateUtil.formatDate(month+"-01", "yyyy-MM-dd"));
			reportWithdraw.setMemberMoney(memberPoint);
			reportWithdraw.setMerchantMoney(merchantPoint);
			reportWithdraw.setTotalMoney(totalPoint);
			Result result = statisticsPointConsumeService.saveMonth(reportWithdraw);
			if(result.getRet() != ResultCode.SUCCESS){
				logger.error("积分消费报表统计定时采集数据保存report_point_consume_month表异常！");
			}
		}
	}
	
}
