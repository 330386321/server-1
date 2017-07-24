package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.FavoriteMerchantService;
import com.lawu.eshop.user.dto.FavoriteMerchantDTO;
import com.lawu.eshop.user.param.FavoriteMerchantParam;
import com.lawu.eshop.user.param.FavoriteStoreParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class MockFavoriteMerchantService implements FavoriteMerchantService {

	@Override
	public Result save(@RequestParam("memberId") Long memberId, @RequestBody FavoriteStoreParam param) {
		return null;
	}

	@Override
	public Result<Page<FavoriteMerchantDTO>> getMyFavoriteMerchant(@RequestParam("memberId") Long memberId, @RequestBody FavoriteMerchantParam pageQuery) {
		return null;
	}

	@Override
	public Result remove(@RequestBody FavoriteStoreParam param, @RequestParam("memberId") Long memberId) {
		return null;
	}
}
