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
		String today = DateUtil.getDateFormat(DateUtil.getDayBefore(new Date()),"yyyy-MM-dd");
		statisticsPointConsumeService.deleteDailyByReportDate(today);
		
		PointDetailReportParam param = new PointDetailReportParam();
		param.setDate(today);
		param.setDirection(PropertyInfoDirectionEnum.OUT.val);
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
		param.setDirection(PropertyInfoDirectionEnum.IN.val);
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

	@SuppressWarnings("rawtypes")
	@Override
	public void executeCollectMonthData() {
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
