package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.WxPayService;
import com.lawu.eshop.property.param.ThirdPayDataParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class MockWxPayService implements WxPayService {


	@Override
	public Result getPrepayInfo(@RequestBody ThirdPayDataParam param) {
		return null;
	}
}
