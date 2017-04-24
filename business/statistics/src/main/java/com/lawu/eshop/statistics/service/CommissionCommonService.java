package com.lawu.eshop.statistics.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value= "user-srv")
public interface CommissionCommonService {

	/**
	 * 根据被邀请人查询出该人所有level邀请人编号
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "user/common/selectHigherLevelInviters/{invitedUserNum}")
	List<String> selectHigherLevelInviters(@PathVariable("invitedUserNum") String invitedUserNum, @RequestParam int level);

}
