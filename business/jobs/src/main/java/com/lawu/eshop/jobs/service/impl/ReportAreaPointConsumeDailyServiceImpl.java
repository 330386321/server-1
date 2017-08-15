package com.lawu.eshop.jobs.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.jobs.service.PropertySrvService;
import com.lawu.eshop.jobs.service.ReportAreaPointConsumeDailyService;
import com.lawu.eshop.jobs.service.StatisticsSrvService;
import com.lawu.eshop.property.dto.AreaPointConsumeDTO;
import com.lawu.eshop.property.dto.AreaRechargePointDTO;
import com.lawu.eshop.statistics.param.ReportAreaPointConsumeDailyParam;
import com.lawu.eshop.utils.DateUtil;

@Service
public class ReportAreaPointConsumeDailyServiceImpl implements ReportAreaPointConsumeDailyService{

	@Autowired
	private PropertySrvService propertySrvService;
	
	@Autowired
	private StatisticsSrvService statisticsSrvService;
	
	@Override
	public void executeCollectReportAreaPointConsumeDaily() {
		Date date = DateUtil.getDayBefore(new Date());
		String bdate = DateUtil.getDateFormat(date) + " 00:00:00";
		String edate = DateUtil.getDateFormat(date) + " 23:59:59";
		Result<List<AreaPointConsumeDTO>> result = propertySrvService.getAreaPointConsume(bdate, edate);
		Result<List<AreaRechargePointDTO>> rechargeResult = propertySrvService.selectAreaRechargePoint(bdate, edate);

		Set<Integer> set = new HashSet<Integer>();
		for(AreaPointConsumeDTO dto : result.getModel()){
			set.add(dto.getAreaId());
		}
		for(AreaRechargePointDTO dto : rechargeResult.getModel()) {
			set.add(dto.getAreaId());
		}
		for(Integer i : set) {
			ReportAreaPointConsumeDailyParam param = new ReportAreaPointConsumeDailyParam();
			param.setAreaId(i);
			param.setMemberPoint(new BigDecimal(0));
			param.setMerchantPoint(new BigDecimal(0));
			param.setGmtReport(date);
			for(AreaPointConsumeDTO dto : result.getModel()) {
				if(dto.getAreaId() == i && "M".equals(dto.getType())) {
					param.setCityId(dto.getCityId());
					param.setProvinceId(dto.getProvinceId());
					param.setMemberPoint(dto.getTotalPoint());
				} else if(dto.getAreaId() == i && "B".equals(dto.getType())) {
					param.setCityId(dto.getCityId());
					param.setProvinceId(dto.getProvinceId());
					param.setMerchantPoint(dto.getTotalPoint());
				}
			}
			param.setTotalPoint(param.getMemberPoint().add(param.getMerchantPoint()));
			param.setMemberRechargePoint(new BigDecimal(0));
			param.setMerchantRechargePoint(new BigDecimal(0));
			for(AreaRechargePointDTO dto : rechargeResult.getModel()) {
				if(dto.getAreaId() == i && "M".equals(dto.getType())) {
					param.setCityId(dto.getCityId());
					param.setProvinceId(dto.getProvinceId());
					param.setMemberRechargePoint(dto.getTotalMoney());
				} else if(dto.getAreaId() == i && "B".equals(dto.getType())) {
					param.setCityId(dto.getCityId());
					param.setProvinceId(dto.getProvinceId());
					param.setMerchantRechargePoint(dto.getTotalMoney());
				}
			}
			param.setTotalRechargePoint(param.getMerchantRechargePoint().add(param.getMemberRechargePoint()));
			statisticsSrvService.insertReportAreaPointConsumeDaily(param);
		}
	}
}
