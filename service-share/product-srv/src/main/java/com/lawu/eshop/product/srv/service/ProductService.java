package com.lawu.eshop.product.srv.service;


import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.param.EditProductDataParam;
import com.lawu.eshop.product.query.ProductDataQuery;
import com.lawu.eshop.product.srv.bo.ProductEditInfoBO;
import com.lawu.eshop.product.srv.bo.ProductImageBO;
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
	 * 用户端商品详情，根据商品ID获取商品信息
	 * @param id
	 * @return
	 */
	ProductInfoBO selectProductById(Long id);

	/**
	 * 商家端编辑商品时，根据ID查询商品
	 * @param productId
	 * @return
	 */
	ProductEditInfoBO selectEditProductById(Long productId);
	
	/**
	 * 编辑商品
	 * @param productId 商品ID
	 * @param product
	 */
	void eidtProduct(Long productId, EditProductDataParam product);
	
	/**
	 * 根据商品id返回商品图片
	 * 
	 * @param id
	 * @return
	 */
	List<ProductImageBO> getProductImageByIds(List<Long> ids);
	
}
