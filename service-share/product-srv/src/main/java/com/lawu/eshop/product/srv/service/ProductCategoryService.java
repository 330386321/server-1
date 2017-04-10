package com.lawu.eshop.product.srv.service;


import com.lawu.eshop.product.srv.bo.ProductCategoryBO;

import java.util.List;

/**
 * 产品服务接口
 *
 * @author yangqh
 * @date 2017/3/22
 */
public interface ProductCategoryService {

    /**
     * 查询所有产品分类
     *
     * @return
     */
    List<ProductCategoryBO> findAll();

    /**
     * 通过ID查询
     * @return
     */
	ProductCategoryBO getById(Integer id);
	
	/**
	 * 获取商品分类全名称
	 * @param id
	 * @return
	 */
	String getFullName(Integer id);

	/**
	 * 查询推荐商品类别
	 * @return
	 */
	List<ProductCategoryBO> listRecommendProductCategory();
}
