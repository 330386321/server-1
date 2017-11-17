package com.lawu.eshop.jobs.mock.service;

import java.util.List;

import com.lawu.eshop.ad.dto.MemberAdRecodeCommissionDTO;
import com.lawu.eshop.ad.dto.ReportAdDTO;
import com.lawu.eshop.ad.dto.ReportAdEarningsDTO;
import com.lawu.eshop.ad.dto.ViewDTO;
import com.lawu.eshop.ad.param.AdReportParam;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.jobs.service.AdSrvService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class MockAdSrvServiceImpl implements AdSrvService {


	@Override
	public List<MemberAdRecodeCommissionDTO> getNoneCommissionAds(@RequestParam("offset") int offset, @RequestParam("pageSize") int pageSize) {
		return null;
	}

	@Override
	public int updateMemberAdRecardStatus(Long id) {
		return ResultCode.SUCCESS;
	}

	@Override
	public Result<List<ViewDTO>> getAllAd() {
		return null;
	}

	@Override
	public Result<List<Long>> updateViewCount(@PathVariable("id") Long id, @RequestParam("count") Integer count) {
		return null;
	}

	@Override
	public Result<List<ReportAdDTO>> selectReportAdEarnings() {
		return null;
	}

	@Override
	public Result<List<ReportAdEarningsDTO>> getReportEarnings(@RequestBody AdReportParam param) {
		return null;
	}

	@Override
	public Result rebuildAdIndex(@RequestParam("pageSize") Integer pageSize) {
		return null;
	}

	@Override
	public Result delInvalidAdIndex() {
		return null;
	}
}
