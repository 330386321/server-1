package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.ExpressCompanyDTO;
import com.lawu.eshop.mall.dto.ExpressCompanyQueryDTO;
import com.lawu.eshop.mall.dto.ExpressCompanyRetrieveDTO;
import com.lawu.eshop.member.api.service.ExpressCompanyService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Service
public class MockExpressCompanyService implements ExpressCompanyService {

	@Override
	public Result<List<ExpressCompanyDTO>> list() {
		return null;
	}

	@Override
	public Result<ExpressCompanyQueryDTO> group() {
		return null;
	}

	@Override
	public Result<ExpressCompanyDTO> get(@PathVariable("id") Integer id) {
		return null;
	}

	@Override
	public Result<ExpressCompanyRetrieveDTO> listByKeyWord(@RequestParam("keyWord") String keyWord) {
		return null;
	}
}
