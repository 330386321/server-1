package com.lawu.eshop.product.srv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.product.dto.CommentProductInfoDTO;
import com.lawu.eshop.product.dto.ShoppingCartProductModelDTO;
import com.lawu.eshop.product.srv.bo.CommentProductInfoBO;
import com.lawu.eshop.product.srv.bo.ShoppingCartProductModelBO;
import com.lawu.eshop.product.srv.converter.ShoppingCartProductModelConverter;
import com.lawu.eshop.product.srv.service.ProductModelService;
import com.lawu.eshop.utils.BeanUtil;

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
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
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

		List<ShoppingCartProductModelBO> shoppingCartProductModelBOS = productModelService.getShoppingCartProductModel(ids);

		if (shoppingCartProductModelBOS == null || shoppingCartProductModelBOS.isEmpty()) {
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}

		return successGet(ShoppingCartProductModelConverter.convert(shoppingCartProductModelBOS));
	}

	/**
	 * 商家查看评价时，显示商品信息和其型号信息
	 * 
	 * @param productModelId
	 * @return
	 * @author Yangqh
	 * @throws Exception 
	 */
	@RequestMapping(value = "selectCommentProductInfo/{productModelId}", method = RequestMethod.GET)
	public Result<CommentProductInfoDTO> selectCommentProductInfo(@PathVariable("productModelId") Long productModelId) throws Exception {

		CommentProductInfoBO commentProductInfoBO = productModelService.selectCommentProductInfo(productModelId);

		if (commentProductInfoBO == null) {
			successGet(ResultCode.RESOURCE_NOT_FOUND);
		}

		CommentProductInfoDTO dto = new CommentProductInfoDTO();
		BeanUtil.copyProperties(commentProductInfoBO, dto);

		return successGet(dto);
	}

}
