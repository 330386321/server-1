package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.member.api.service.PropertySrvPropertyService;
import org.springframework.stereotype.Service;


@Service
public class MockPropertySrvPropertyService implements PropertySrvPropertyService {

	@Override
	public String getValue(String name) {
		return null;
	}
}
