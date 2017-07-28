package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.MerchantProfileService;
import com.lawu.eshop.user.dto.MerchantProfileDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class MockMerchantProfileService extends BaseController implements MerchantProfileService {


	@Override
	public Result<MerchantProfileDTO> getMerchantProfile(@RequestParam("merchantId") Long merchantId) {
		MerchantProfileDTO dto = new MerchantProfileDTO();
		dto.setJdUrl("/jd/1.jpg");
		dto.setTaobaoUrl("/taobao/1.jpg");
		dto.setTmallUrl("/tm/1.jpg");
		dto.setWebsiteUrl("/website/1.jpg");
		return successCreated(dto);
	}
}
