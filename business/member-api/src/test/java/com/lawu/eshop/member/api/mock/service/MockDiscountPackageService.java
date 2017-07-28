package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.DiscountPackageDetailDTO;
import com.lawu.eshop.mall.dto.DiscountPackageQueryDTO;
import com.lawu.eshop.member.api.service.DiscountPackageService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;


@Service
public class MockDiscountPackageService extends BaseController implements DiscountPackageService {

	@Override
	public Result<Page<DiscountPackageQueryDTO>> listForMember(@PathVariable("merchantId") Long merchantId) {
		DiscountPackageQueryDTO dto = new DiscountPackageQueryDTO();
		List<DiscountPackageQueryDTO> list = new ArrayList<>();
		list.add(dto);
		Page<DiscountPackageQueryDTO> page = new Page();
		page.setRecords(list);
		page.setCurrentPage(1);
		page.setTotalCount(100);
		return successCreated(page);
	}

	@Override
	public Result<DiscountPackageDetailDTO> get(@PathVariable("id") Long id) {
		DiscountPackageDetailDTO dto = new DiscountPackageDetailDTO();
		return successCreated(dto);
	}
}
