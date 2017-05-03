package com.lawu.eshop.order.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.order.srv.converter.ReportConvert;
import com.lawu.eshop.order.srv.domain.extend.ReportRiseRateView;
import com.lawu.eshop.order.srv.mapper.extend.PayOrderExtendDOMapper;
import com.lawu.eshop.order.srv.service.ReportPayService;
import com.lawu.eshop.user.constants.ReportFansRiseRateEnum;
import com.lawu.eshop.user.dto.ReportRiseRateDTO;
import com.lawu.eshop.user.param.ReportDataParam;
import com.lawu.eshop.utils.DateUtil;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年5月3日 下午2:23:26
 *
 */
@Service
public class ReportPayServiceImpl implements ReportPayService {

	@Autowired
	private PayOrderExtendDOMapper payOrderExtendDOMapper;
	
	@Override
	public ReportRiseRateDTO payVolumeRiseRate(ReportDataParam dparam) {
		List<ReportRiseRateView> list = new ArrayList<ReportRiseRateView>();
		int x = 0;
		if (ReportFansRiseRateEnum.DAY.getValue().equals(dparam.getFlag().getValue())) {
			list = payOrderExtendDOMapper.payVolumeRiseRate(DateUtil.getDateFormat(new Date(), "yyyyMM"),
					dparam.getFlag().getValue(),dparam.getMerchantId());
			x = DateUtil.getNowMonthDay();
		} else if (ReportFansRiseRateEnum.MONTH.getValue().equals(dparam.getFlag().getValue())) {
			list = payOrderExtendDOMapper.payVolumeRiseRate(DateUtil.getDateFormat(new Date(), "yyyy"),
					dparam.getFlag().getValue(),dparam.getMerchantId());
			x = 12;
		}
		ReportRiseRateDTO dto = ReportConvert.reportBrokeLineShow(list, x);
		
		return dto;
	}
	
}
