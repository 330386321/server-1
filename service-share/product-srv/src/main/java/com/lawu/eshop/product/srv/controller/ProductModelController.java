package com.lawu.eshop.product.srv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.product.dto.ShoppingCartProductModelDTO;
import com.lawu.eshop.product.srv.bo.ShoppingCartProductModelBO;
import com.lawu.eshop.product.srv.converter.ShoppingCartProductModelConverter;
import com.lawu.eshop.product.srv.service.ProductModelService;

/**
 * 
 * @author Sunny
 * @date 2017/3/30
 */
@RestController
@RequestMapping(value = "productModel/")
public class ProductModelController extends BaseController {

	@Autowired
	private ProductModelService productModelService;

	/**
	 * 根据商品型号ID查询购物车商品信息
	 * 
	 * @param id
	 *            商品型号ID
	 * @return
	 */
	@RequestMapping(value = "shoppingCart/{id}", method = RequestMethod.GET)
	public Result<ShoppingCartProductModelDTO> getShoppingCartProductModel(@PathVariable("id") Long id) {

		ShoppingCartProductModelBO shoppingCartProductModelBO = productModelService.getShoppingCartProductModel(id);

		if (shoppingCartProductModelBO == null) {
			successGet(ResultCode.RESOURCE_NOT_FOUND);
		}

		if (StringUtils.isEmpty(shoppingCartProductModelBO.getProductName())) {
			successGet(ResultCode.GOODS_DO_NOT_EXIST);
		}

		return successGet(ShoppingCartProductModelConverter.convert(shoppingCartProductModelBO));
	}

	/**
	 * 根据商品型号ID列表查询购物车商品信息
	 * 
	 * @param ids
	 *            商品型号ID列表
	 * @return
	 */
	@RequestMapping(value = "shoppingCart/list", method = RequestMethod.GET)
	public Result<List<ShoppingCartProductModelDTO>> getShoppingCartProductModel(@RequestParam("ids") List<Long> ids) {

		List<ShoppingCartProductModelBO> shoppingCartProductModelBOS = productModelService
				.getShoppingCartProductModel(ids);

		if (shoppingCartProductModelBOS == null || shoppingCartProductModelBOS.isEmpty()) {
			successGet(ResultCode.RESOURCE_NOT_FOUND);
		}

		return successGet(ShoppingCartProductModelConverter.convert(shoppingCartProductModelBOS));
	}
}
