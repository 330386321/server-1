package com.lawu.eshop.jobs.service;

import java.util.List;

import com.lawu.eshop.ad.dto.MemberAdRecodeCommissionDTO;
import com.lawu.jobsextend.JobsExtendPageException;

public interface ClickAdCommissionService {


	void executeAutoClickAdCommission(List<MemberAdRecodeCommissionDTO> list) throws JobsExtendPageException;

}
