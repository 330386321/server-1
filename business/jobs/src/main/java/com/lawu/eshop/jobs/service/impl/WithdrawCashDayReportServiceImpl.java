package com.lawu.eshop.jobs.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.jobs.service.PropertyWithdrawCashService;
import com.lawu.eshop.jobs.service.WithdrawCashDayReportService;
import com.lawu.eshop.property.constants.CashStatusEnum;
import com.lawu.eshop.property.dto.WithdrawCashReportDTO;
import com.lawu.eshop.property.param.WithdrawCashReportParam;
import com.lawu.eshop.utils.DateUtil;

@Service
public class WithdrawCashDayReportServiceImpl implements WithdrawCashDayReportService {

	private static Logger logger = LoggerFactory.getLogger(WithdrawCashDayReportServiceImpl.class);

	@Autowired
	private PropertyWithdrawCashService propertyWithdrawCashService;
	
	@Override
	public void executeCollectData() {
		WithdrawCashReportParam param = new WithdrawCashReportParam();
		param.setDate(DateUtil.getDateFormat(DateUtil.getDayBefore(new Date()),"yyyy-MM-dd"));
		param.setStatus(CashStatusEnum.SUCCESS.getVal());
		Result<List<WithdrawCashReportDTO>> rntResult = propertyWithdrawCashService.selectWithdrawCashListByDateAndStatus(param);
		
	}


}
