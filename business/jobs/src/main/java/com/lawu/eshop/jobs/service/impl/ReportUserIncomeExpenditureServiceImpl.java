package com.lawu.eshop.jobs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.jobs.service.ReportUserIncomeExpenditureExtendService;
import com.lawu.eshop.jobs.service.ReportUserIncomeExpenditureService;
import com.lawu.eshop.jobs.service.TransactionDetailService;
import com.lawu.eshop.jobs.service.UserService;
import com.lawu.eshop.property.dto.UserIncomeExpenditureDTO;
import com.lawu.eshop.property.dto.UserIncomeExpenditureDatailDTO;
import com.lawu.eshop.property.param.UserIncomeExpenditureQueryParam;
import com.lawu.eshop.statistics.param.ReportUserIncomeExpenditureSaveParam;
import com.lawu.eshop.statistics.param.ReportUserIncomeExpenditureSaveWrapperParam;
import com.lawu.eshop.user.dto.UserIncomeExpenditureUserInfoDTO;
import com.lawu.eshop.user.dto.UserIncomeExpenditureUserInfoWrapperDTO;
import com.lawu.eshop.user.param.UserIncomeExpenditureUserInfoQueryParam;
import com.lawu.eshop.utils.DateUtil;

@Service
public class ReportUserIncomeExpenditureServiceImpl extends BaseController implements ReportUserIncomeExpenditureExtendService {
	
	private static Logger logger = LoggerFactory.getLogger(ReportUserIncomeExpenditureServiceImpl.class);
	
	@Autowired
	private ReportUserIncomeExpenditureService reportUserIncomeExpenditureService;
	
	@Autowired
	private TransactionDetailService transactionDetailService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 定时任务<p>
	 * 保存用户支出和消费记录
	 * 
	 * @author Sunny
	 * @date 2017年7月3日
	 */
	@SuppressWarnings("rawtypes")
	public void executeSave() {
		Date date = DateUtil.getMonthBefore(DateUtil.getNowDate());
		UserIncomeExpenditureQueryParam userIncomeExpenditureQueryParam = new UserIncomeExpenditureQueryParam();
		userIncomeExpenditureQueryParam.setDate(date);
		Result<UserIncomeExpenditureDatailDTO> selectUserIncomeExpenditureResult = transactionDetailService.selectUserIncomeExpenditure(userIncomeExpenditureQueryParam);
		if (!isSuccess(selectUserIncomeExpenditureResult)) {
			logger.error("服务调用异常", selectUserIncomeExpenditureResult.getMsg());
			return;
		}
		UserIncomeExpenditureDatailDTO userIncomeExpenditureDatailDTO = selectUserIncomeExpenditureResult.getModel();
		// 如果当天没有消费和支出记录,直接返回
		if (userIncomeExpenditureDatailDTO == null || userIncomeExpenditureDatailDTO.getUserIncomeExpenditureList() == null || userIncomeExpenditureDatailDTO.getUserIncomeExpenditureList().isEmpty()) {
			return;
		}
		List<String> userNums = new ArrayList<>();
		for (UserIncomeExpenditureDTO userIncomeExpenditureDTO : userIncomeExpenditureDatailDTO.getUserIncomeExpenditureList()) {
			userNums.add(userIncomeExpenditureDTO.getUserNum());
		}
		
		UserIncomeExpenditureUserInfoQueryParam userIncomeExpenditureUserInfoQueryParam = new  UserIncomeExpenditureUserInfoQueryParam();
		userIncomeExpenditureUserInfoQueryParam.setUserNums(userNums);
		Result<UserIncomeExpenditureUserInfoWrapperDTO> selectAccountResult = userService.selectAccount(userIncomeExpenditureUserInfoQueryParam);
		if (!isSuccess(selectAccountResult)) {
			logger.error("服务调用异常", selectAccountResult.getMsg());
			return;
		}
		List<UserIncomeExpenditureUserInfoDTO> userInfoList = selectAccountResult.getModel().getUserInfoList();
		Map<String, UserIncomeExpenditureUserInfoDTO> userInfoMap = new HashMap<>();
		for (UserIncomeExpenditureUserInfoDTO userIncomeExpenditureUserInfoDTO : userInfoList) {
			userInfoMap.put(userIncomeExpenditureUserInfoDTO.getUserNum(), userIncomeExpenditureUserInfoDTO);
		}
		
		ReportUserIncomeExpenditureSaveWrapperParam reportUserIncomeExpenditureSaveWrapperParam = new ReportUserIncomeExpenditureSaveWrapperParam();
		List<ReportUserIncomeExpenditureSaveParam> reportUserIncomeExpenditureSaveParamList = new ArrayList<>();
		for (UserIncomeExpenditureDTO userIncomeExpenditureDTO : userIncomeExpenditureDatailDTO.getUserIncomeExpenditureList()) {
			ReportUserIncomeExpenditureSaveParam reportUserIncomeExpenditureSaveParam = new ReportUserIncomeExpenditureSaveParam();
			reportUserIncomeExpenditureSaveParam.setExpenditure(userIncomeExpenditureDTO.getExpenditure());
			reportUserIncomeExpenditureSaveParam.setIncome(userIncomeExpenditureDTO.getIncome());
			reportUserIncomeExpenditureSaveParam.setUserNum(reportUserIncomeExpenditureSaveParam.getUserNum());
			reportUserIncomeExpenditureSaveParam.setAccount(userInfoMap.get(reportUserIncomeExpenditureSaveParam.getUserNum()).getAccount());
			reportUserIncomeExpenditureSaveParamList.add(reportUserIncomeExpenditureSaveParam);
		}
		Result batchSaveResult = reportUserIncomeExpenditureService.batchSave(reportUserIncomeExpenditureSaveWrapperParam);
		if (!isSuccess(batchSaveResult)) {
			logger.error("服务调用异常", batchSaveResult.getMsg());
		}
		
	}
	
}
