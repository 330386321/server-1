package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.ProductModelService;
import com.lawu.eshop.product.dto.ShoppingCartProductModelDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
class MockProductModelService implements ProductModelService {


	@Override
	public Result<ShoppingCartProductModelDTO> getShoppingCartProductModel(@PathVariable("id") Long id) {
		return null;
	}

	@Override
	public Result<List<ShoppingCartProductModelDTO>> getShoppingCartProductModel(@RequestParam("ids") List<Long> ids) {
		return null;
	}
}
