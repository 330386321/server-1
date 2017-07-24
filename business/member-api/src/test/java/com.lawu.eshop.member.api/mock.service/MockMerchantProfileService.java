package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.MerchantProfileService;
import com.lawu.eshop.user.dto.MerchantProfileDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class MockMerchantProfileService implements MerchantProfileService {


	@Override
	public Result<MerchantProfileDTO> getMerchantProfile(@RequestParam("merchantId") Long merchantId) {
		return null;
	}
}
