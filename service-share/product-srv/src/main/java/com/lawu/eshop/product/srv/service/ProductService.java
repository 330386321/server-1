package com.lawu.eshop.product.srv.service;


import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.query.ProductQuery;
import com.lawu.eshop.product.srv.bo.ProductInfoBO;
import com.lawu.eshop.product.srv.bo.ProductQueryBO;

/**
 * Created by Yangqh on 2017/3/23.
 */
public interface ProductService {

	/**
	 * 商品列表查询
	 * @param query
	 * @return
	 */
	Page<ProductQueryBO> selectProduct(ProductQuery query);

	/**
	 * 批量处理
	 * @param ids
	 * @param status
	 * @return
	 */
	int updateProductStatus(String ids, Integer status);

	/**
	 * 根据商品ID获取商品信息
	 * @param id
	 * @return
	 */
	ProductInfoBO selectProductById(Long id);
}
