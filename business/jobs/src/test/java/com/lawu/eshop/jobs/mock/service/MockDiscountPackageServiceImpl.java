package com.lawu.eshop.jobs.mock.service;


import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.jobs.service.DiscountPackageService;
import com.lawu.eshop.mall.dto.DiscountPackageQueryDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class MockDiscountPackageServiceImpl implements DiscountPackageService {


	@Override
	public Result<Page<DiscountPackageQueryDTO>> listForMember(@PathVariable("merchantId") Long merchantId) {
		return null;
	}
}
