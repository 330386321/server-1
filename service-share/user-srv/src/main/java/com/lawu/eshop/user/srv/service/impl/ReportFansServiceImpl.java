package com.lawu.eshop.user.srv.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.user.constants.FansMerchantChannelEnum;
import com.lawu.eshop.user.constants.ReportFansRiseRateEnum;
import com.lawu.eshop.user.dto.ReportRiseRateDTO;
import com.lawu.eshop.user.dto.ReportRiseRerouceDTO;
import com.lawu.eshop.user.param.ReportDataParam;
import com.lawu.eshop.user.srv.converter.ReportConvert;
import com.lawu.eshop.user.srv.domain.extend.FansMerchantDOReportView;
import com.lawu.eshop.user.srv.mapper.extend.FansMerchantDOMapperExtend;
import com.lawu.eshop.user.srv.service.ReportFansService;
import com.lawu.eshop.utils.DateUtil;
import com.lawu.eshop.utils.StringUtil;

@Service
public class ReportFansServiceImpl implements ReportFansService {

	@Autowired
	private FansMerchantDOMapperExtend fansMerchantDOMapperExtend;

	@Override
	public ReportRiseRateDTO fansRiseRate(ReportDataParam dparam) {
		List<FansMerchantDOReportView> list = new ArrayList<FansMerchantDOReportView>();
		int x = 0;
		if (ReportFansRiseRateEnum.DAY.getValue().equals(dparam.getFlag().getValue())) {
			list = fansMerchantDOMapperExtend.fansRiseRate(DateUtil.getDateFormat(new Date(), "yyyyMM"),
					dparam.getFlag().getValue(), dparam.getMerchantId());
			x = DateUtil.getNowMonthDay();
		} else if (ReportFansRiseRateEnum.MONTH.getValue().equals(dparam.getFlag().getValue())) {
			list = fansMerchantDOMapperExtend.fansRiseRate(DateUtil.getDateFormat(new Date(), "yyyy"),
					dparam.getFlag().getValue(), dparam.getMerchantId());
			x = 12;
		}
		ReportRiseRateDTO dto = ReportConvert.reportBrokeLineShow(list, x);
		return dto;
	}
	
	@Override
	public List<ReportRiseRerouceDTO> fansRiseSource(ReportDataParam dparam) {
		List<FansMerchantDOReportView> list = new ArrayList<FansMerchantDOReportView>();
		if (ReportFansRiseRateEnum.DAY.getValue().equals(dparam.getFlag().getValue())) {
			list = fansMerchantDOMapperExtend.fansRiseSource(DateUtil.getDateFormat(new Date(), "yyyyMMdd"),
					dparam.getFlag().getValue(), dparam.getMerchantId());
		} else if (ReportFansRiseRateEnum.MONTH.getValue().equals(dparam.getFlag().getValue())) {
			list = fansMerchantDOMapperExtend.fansRiseSource(DateUtil.getDateFormat(new Date(), "yyyyMM"),
					dparam.getFlag().getValue(), dparam.getMerchantId());
		}
		int total = 0;
		for (FansMerchantDOReportView view : list) {
			int t = Integer.valueOf(view.getNum()).intValue();
			total = total + t;
		}
		Double todayDouble = Double.valueOf(total);
		List<ReportRiseRerouceDTO> dtos = new ArrayList<ReportRiseRerouceDTO>();
		for (FansMerchantDOReportView view : list) {
			ReportRiseRerouceDTO dto = new ReportRiseRerouceDTO();
			int num = Integer.valueOf(view.getNum()).intValue();
			Double numDouble = Double.valueOf(num);
			float p = (float) ((float) (numDouble / todayDouble) * 100);
			DecimalFormat df = new DecimalFormat("0.00");
			String ps = df.format(p) + "%";

			dto.setName(ps + FansMerchantChannelEnum
					.getEnum(StringUtil.intToByte(Integer.valueOf(view.getKeyTxt()).intValue())).getName());
			dto.setValue(view.getNum());
			dtos.add(dto);
		}
		return dtos;
	}

	// public static void main(String[] args) {
	// List<FansMerchantDOReportView> list = new
	// ArrayList<FansMerchantDOReportView>();
	// FansMerchantDOReportView view1 = new FansMerchantDOReportView();
	// view1.setDate("01");
	// view1.setNum("10");
	// list.add(view1);
	// FansMerchantDOReportView view3 = new FansMerchantDOReportView();
	// view3.setDate("03");
	// view3.setNum("18");
	// list.add(view3);
	// test(ReportFansRiseRateEnum.DAY.getValue(),list);
	// }
	//
	// public static void test(Byte b,List<FansMerchantDOReportView> list) {
	// if (ReportFansRiseRateEnum.DAY.getValue().equals(b)) {
	// int days = DateUtil.getNowMonthDay();
	// for (int i = 0; i < days; i++) {
	// boolean f = true;
	// int j = i + 1;
	// for (FansMerchantDOReportView view : list) {
	// int num = Integer.valueOf(view.getDate()).intValue();
	// if (num == j) {
	// f = false;
	// break;
	// }
	// }
	// if (f) {
	// FansMerchantDOReportView view = new FansMerchantDOReportView();
	// view.setDate(j + "");
	// view.setNum("0");
	// list.add(i, view);
	// }
	// }
	// } else if (ReportFansRiseRateEnum.MONTH.getValue().equals(b)) {
	// for (int i = 0; i < 12; i++) {
	// boolean f = true;
	// int j = i + 1;
	// for (FansMerchantDOReportView view : list) {
	// int num = Integer.valueOf(view.getDate()).intValue();
	// if (num == j) {
	// f = false;
	// break;
	// }
	// }
	// if (f) {
	// FansMerchantDOReportView view = new FansMerchantDOReportView();
	// view.setDate(j + "");
	// view.setNum("0");
	// list.add(i, view);
	// }
	// }
	// }
	//
	// List<String> dates = new ArrayList<String>();
	// List<String> nums = new ArrayList<String>();
	// for (FansMerchantDOReportView view : list) {
	// dates.add(Integer.valueOf(view.getDate()).toString());
	// nums.add(view.getNum());
	// }
	// for(String d : dates){
	// System.out.print(d+",");
	// }
	// System.out.println();
	// for(String n : nums){
	// System.out.print(n+",");
	// }
	// }

}
