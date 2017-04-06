package com.lawu.eshop.member.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.product.dto.ShoppingCartProductModelDTO;
import com.lawu.eshop.product.param.ProductModeUpdateInventoryParam;

/**
 * 商品型号接口
 * 
 * @author Sunny
 * @date 2017/3/30
 */
@FeignClient(value = "product-srv")
public interface ProductModelService {

	/**
	 * 根据商品型号ID查询购物车商品信息
	 * 
	 * @param id
	 *            商品型号ID
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "productModel/shoppingCart/{id}")
	Result<ShoppingCartProductModelDTO> getShoppingCartProductModel(@PathVariable("id") Long id);

	/**
	 * 根据商品型号ID列表查询购物车商品信息
	 * 
	 * @param ids
	 *            商品型号ID列表
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "productModel/shoppingCart/list")
	Result<List<ShoppingCartProductModelDTO>> getShoppingCartProductModel(@RequestParam("ids") List<Long> ids);

	/**
	 * 批量更新商品型号库存
	 * 
	 * @param param
	 *            更新商品型号库存参数
	 * @return
	 */
	@RequestMapping(value = "productModel/shoppingCart/updateInventory", method = RequestMethod.PUT)
	public Result updateInventory(@RequestBody List<ProductModeUpdateInventoryParam> params);

}
