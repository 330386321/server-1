package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.ad.dto.FavoriteAdDOViewDTO;
import com.lawu.eshop.ad.param.FavoriteAdParam;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.FavoriteAdService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class MockFavoriteAdService extends BaseController implements FavoriteAdService {

	@Override
	public Result save(@RequestParam("memberId") Long memberId, @RequestParam("adId") Long adId, @RequestParam("userNum") String userNum) {
		return successCreated();
	}

	@Override
	public Result remove(@PathVariable("adId") Long adId, @RequestParam("memberId") Long memberId) {
		return successCreated();
	}

	@Override
	public Result<Page<FavoriteAdDOViewDTO>> selectMyFavoriteAd(@RequestParam("memberId") Long id, @RequestBody FavoriteAdParam param) {
		FavoriteAdDOViewDTO dto = new FavoriteAdDOViewDTO();
		dto.setMerchantId(1L);
		List<FavoriteAdDOViewDTO> list = new ArrayList<>();
		list.add(dto);
		Page<FavoriteAdDOViewDTO> page = new Page();
		page.setCurrentPage(1);
		page.setTotalCount(100);
		page.setRecords(list);
		return successCreated(page);
	}

	@Override
	public Result<Boolean> isFavoriteAd(@RequestParam("adId") Long adId, @RequestParam("memberId") Long memberId) {
		return successCreated(true);
	}
}
