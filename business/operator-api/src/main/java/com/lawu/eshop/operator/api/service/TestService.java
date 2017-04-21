package com.lawu.eshop.operator.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.QueryPropertyDTO;
import com.lawu.eshop.property.param.TestQueryParam;

@FeignClient(value = "property-srv")
public interface TestService {

	@RequestMapping(method = RequestMethod.POST, value = "property/query")
	Result<Page<QueryPropertyDTO>> query(@RequestBody TestQueryParam param);

   
}
