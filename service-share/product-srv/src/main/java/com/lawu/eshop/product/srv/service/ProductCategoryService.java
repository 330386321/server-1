package com.lawu.eshop.product.srv.service;


import com.lawu.eshop.product.srv.bo.ProductCategoryBO;

import java.util.List;

/**
 * 产品服务接口
 *
 * @author Leach
 * @date 2017/3/22
 */
public interface ProductCategoryService {

    /**
     * 查询所有产品分类
     *
     * @return
     */
    List<ProductCategoryBO> findAll();
}
