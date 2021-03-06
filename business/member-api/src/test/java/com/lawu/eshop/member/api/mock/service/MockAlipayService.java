package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.AlipayService;
import com.lawu.eshop.property.param.PcAlipayParam;
import com.lawu.eshop.property.param.ThirdPayDataParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class MockAlipayService extends BaseController implements AlipayService {


	@Override
	public Result getAppAlipayReqParams(@RequestBody ThirdPayDataParam param) {
		return successCreated();
	}

	@Override
	public Result initPcPay(PcAlipayParam param) {
		return null;
	}
}
