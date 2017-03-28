package com.lawu.eshop.product.srv.service;


import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.param.EditDataProductParam;
import com.lawu.eshop.product.param.EditProductParam;
import com.lawu.eshop.product.query.ProductDataQuery;
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
	Page<ProductQueryBO> selectProduct(ProductDataQuery query);

	/**
	 * 批量处理
	 * @param ids
	 * @param status
	 * @return
	 */
	int updateProductStatus(String ids, ProductStatusEnum productStatus);

	/**
	 * 根据商品ID获取商品信息
	 * @param id
	 * @return
	 */
	ProductInfoBO selectProductById(Long id);

	/**
	 * 保存商品
	 * @param product
	 */
	void saveProduct(EditDataProductParam product);

	/**
	 * 编辑商品
	 * @param id	商品ID
	 * @param product
	 */
	void updateProductById(Long id, EditProductParam product);
}
