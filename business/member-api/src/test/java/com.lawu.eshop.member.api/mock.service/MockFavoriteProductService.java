package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.FavoriteProductService;
import com.lawu.eshop.product.dto.FavoriteProductDTO;
import com.lawu.eshop.product.query.FavoriteProductQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


@Service
public class MockFavoriteProductService implements FavoriteProductService {


	@Override
	public Result save(@RequestParam("memberId") Long memberId, @RequestParam("productId") Long productId) {
		return null;
	}

	@Override
	public Result remove(@PathVariable("productId") Long productId, @RequestParam("memberId") Long memberId) {
		return null;
	}

	@Override
	public Result<Page<FavoriteProductDTO>> selectMyFavoriteProduct(@RequestParam("memberId") Long id, @RequestBody FavoriteProductQuery query) {
		return null;
	}

	@Override
	public boolean getUserFavorite(@PathVariable("productId") Long productId, @RequestParam("memberId") Long memberId) {
		return false;
	}
}
