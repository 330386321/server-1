package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.PropertyInfoService;
import com.lawu.eshop.property.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Service
class MockPropertyInfoService implements PropertyInfoService {


	@Override
	public Result updatePayPwd(@PathVariable("userNum") String userNum, @RequestParam("originalPwd") String originalPwd, @RequestParam("newPwd") String newPwd) {
		return null;
	}

	@Override
	public Result resetPayPwd(@PathVariable("userNum") String userNum, @RequestParam("newPwd") String newPwd) {
		return null;
	}

	@Override
	public Result setPayPwd(@PathVariable("userNum") String userNum, @RequestParam("newPwd") String newPwd) {
		return null;
	}

	@Override
	public Result updatePayPwd(@PathVariable("userNum") String userNum, @RequestParam("originalPwd") String originalPwd, @RequestParam("newPwd") String newPwd, @RequestParam("type") Integer type) {
		return null;
	}

	@Override
	public Result isSetPayPwd(@PathVariable("userNum") String userNum) {
		return null;
	}

	@Override
	public Result<PropertyBalanceDTO> getPropertyBalance(@PathVariable("userNum") String userNum) {
		return null;
	}

	@Override
	public Result<PropertyPointDTO> getPropertyPoint(@PathVariable("userNum") String userNum) {
		return null;
	}

	@Override
	public Result varifyPayPwd(@RequestParam("userNum") String userNum, @RequestParam("payPwd") String payPwd) {
		return null;
	}

	@Override
	public Result<PropertyPointAndBalanceDTO> getPropertyInfoMoney(@PathVariable("userNum") String userNum) {
		return null;
	}

	@Override
	public Result<PropertyLoveAccountDTO> selectLoveAccount(@PathVariable("userNum") String userNum) {
		return null;
	}

	@Override
	public Result<PropertyInfoFreezeDTO> getPropertyinfoFreeze(@PathVariable("userNum") String userNum) {
		return null;
	}
}
