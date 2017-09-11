package com.lawu.eshop.ad.srv.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.dto.ReportAdEarningsDTO;
import com.lawu.eshop.ad.srv.bo.ReportEarningsBO;
import com.lawu.eshop.ad.srv.service.ReportEarningsService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;

@RestController
@RequestMapping(value = "reportEarnings/")
public class ReportEarningsController extends BaseController{
	
	@Autowired
	private ReportEarningsService reportEarningsService;
	
	
	@RequestMapping(value = "getReportEarnings", method = RequestMethod.GET)
    public Result<List<ReportAdEarningsDTO>> getReportEarnings(@RequestParam String date) {
		List<ReportEarningsBO> list=reportEarningsService.getReportEarnings(date);
		List<ReportAdEarningsDTO> dtoList=new ArrayList<>();
		for (ReportEarningsBO reportEarningsBO : list) {
			
			ReportAdEarningsDTO dto=new ReportAdEarningsDTO();
			dto.setAdPoint(reportEarningsBO.getAdPoint());
			dto.setId(reportEarningsBO.getId());
			dtoList.add(dto);
			
		}
		 return  successCreated(dtoList);
    }

}
