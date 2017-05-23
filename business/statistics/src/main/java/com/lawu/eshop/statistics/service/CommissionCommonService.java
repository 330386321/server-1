package com.lawu.eshop.statistics.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.user.dto.CommissionInvitersUserDTO;

@FeignClient(value = "user-srv")
public interface CommissionCommonService {

	/**
	 * 根据被邀请人查询出该人所有level邀请人编号
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "user/common/selectHigherLevelInviters/{invitedUserNum}")
	List<CommissionInvitersUserDTO> selectHigherLevelInviters(@PathVariable("invitedUserNum") String invitedUserNum,
			@RequestParam("level") int level, @RequestBody boolean isLevel);

}
