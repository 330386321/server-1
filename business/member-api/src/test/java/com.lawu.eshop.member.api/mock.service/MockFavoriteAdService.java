package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.ad.dto.FavoriteAdDOViewDTO;
import com.lawu.eshop.ad.param.FavoriteAdParam;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.FavoriteAdService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
public class MockFavoriteAdService implements FavoriteAdService {

	@Override
	public Result save(@RequestParam("memberId") Long memberId, @RequestParam("adId") Long adId) {
		return null;
	}

	@Override
	public Result remove(@PathVariable("adId") Long adId, @RequestParam("memberId") Long memberId) {
		return null;
	}

	@Override
	public Result<Page<FavoriteAdDOViewDTO>> selectMyFavoriteAd(@RequestParam("memberId") Long id, @RequestBody FavoriteAdParam param) {
		return null;
	}

	@Override
	public Result<Boolean> isFavoriteAd(@RequestParam("adId") Long adId, @RequestParam("memberId") Long memberId) {
		return null;
	}
}
