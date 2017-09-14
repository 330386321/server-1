package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.PropertySrvPropertyService;
import org.springframework.stereotype.Service;


@Service
public class MockPropertySrvPropertyService implements PropertySrvPropertyService {

	@Override
	public Result getValue(String name) {
		return null;
	}
}
