package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.DiscountPackageDetailDTO;
import com.lawu.eshop.mall.dto.DiscountPackageQueryDTO;
import com.lawu.eshop.member.api.service.DiscountPackageService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


@Service
public class MockDiscountPackageService implements DiscountPackageService {

	@Override
	public Result<Page<DiscountPackageQueryDTO>> listForMember(@PathVariable("merchantId") Long merchantId) {
		return null;
	}

	@Override
	public Result<DiscountPackageDetailDTO> get(@PathVariable("id") Long id) {
		return null;
	}
}
