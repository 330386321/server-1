package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.AddressService;
import com.lawu.eshop.user.dto.AddressDTO;
import com.lawu.eshop.user.param.AddressParam;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class MockAddressService extends BaseController implements AddressService {

	@Override
	public Result update(@RequestBody AddressParam address, @PathVariable("id") Long id, @RequestParam("userNum") String userNum) {
		return null;
	}

	@Override
	public Result<AddressDTO> get(@PathVariable("id") Long id) {
		return null;
	}

	@Override
	public Result delete(@PathVariable("id") Long userId, @RequestParam("userNum") String userNum) {
		return null;
	}

	@Override
	public Result updateDefault(@PathVariable("id") Long id, @RequestParam("userNum") String userNum) {
		return null;
	}

	@Override
	public Result<List<AddressDTO>> selectByUserNum(@PathVariable("userNum") String userNum) {
		return null;
	}

	@Override
	public Result saveWithUserNum(@PathVariable("userNum") String userNum, @RequestBody @Validated AddressParam addressDO) {
		return null;
	}
}
