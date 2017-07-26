package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.FavoriteProductService;
import com.lawu.eshop.product.dto.FavoriteProductDTO;
import com.lawu.eshop.product.query.FavoriteProductQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Service
public class MockFavoriteProductService extends BaseController implements FavoriteProductService {


	@Override
	public Result save(@RequestParam("memberId") Long memberId, @RequestParam("productId") Long productId) {
		return successCreated();
	}

	@Override
	public Result remove(@PathVariable("productId") Long productId, @RequestParam("memberId") Long memberId) {
		return successCreated();
	}

	@Override
	public Result<Page<FavoriteProductDTO>> selectMyFavoriteProduct(@RequestParam("memberId") Long id, @RequestBody FavoriteProductQuery query) {
		FavoriteProductDTO dto = new FavoriteProductDTO();
		List<FavoriteProductDTO> list = new ArrayList<>();
		list.add(dto);
		Page<FavoriteProductDTO> page = new Page();
		page.setCurrentPage(1);
		page.setTotalCount(100);
		page.setRecords(list);
		return successCreated(page);
	}

	@Override
	public boolean getUserFavorite(@PathVariable("productId") Long productId, @RequestParam("memberId") Long memberId) {
		return false;
	}
}
