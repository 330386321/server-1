package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.ShoppingCartService;
import com.lawu.eshop.order.dto.ShoppingCartDTO;
import com.lawu.eshop.order.param.ShoppingCartSaveParam;
import com.lawu.eshop.order.param.ShoppingCartUpdateParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Service
class MockShoppingCartService implements ShoppingCartService {

	@Override
	public Result save(@PathVariable("memberId") Long memberId, @RequestBody ShoppingCartSaveParam parm) {
		return null;
	}

	@Override
	public Result<List<ShoppingCartDTO>> findListByMemberId(@PathVariable(name = "memberId") Long memberId) {
		return null;
	}

	@Override
	public Result update(@PathVariable(name = "id") Long id, @RequestParam("memberId") Long memberId, @RequestBody ShoppingCartUpdateParam parm) {
		return null;
	}

	@Override
	public Result delete(@RequestParam("memberId") Long memberId, @RequestBody List<Long> ids) {
		return null;
	}

	@Override
	public Result<List<ShoppingCartDTO>> findListByIds(@PathVariable("memberId") Long memberId, @RequestParam(name = "ids") List<Long> ids) {
		return null;
	}

	@Override
	public Result<Long> findListByIds(@PathVariable("memberId") Long memberId) {
		return null;
	}
}
