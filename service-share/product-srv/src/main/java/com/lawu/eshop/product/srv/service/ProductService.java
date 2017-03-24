package com.lawu.eshop.product.srv.service;


import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.query.ProductQuery;
import com.lawu.eshop.product.srv.bo.ProductBO;

/**
 * Created by Yangqh on 2017/3/23.
 */
public interface ProductService {

	/**
	 * 商品列表查询
	 * @param query
	 * @return
	 */
	Page<ProductBO> selectProduct(ProductQuery query);

	/**
	 * 批量处理
	 * @param ids
	 * @param status
	 * @return
	 */
	int updateProductStatus(String ids, Integer status);
}
