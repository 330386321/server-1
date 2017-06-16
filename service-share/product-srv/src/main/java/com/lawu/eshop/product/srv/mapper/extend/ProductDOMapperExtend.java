package com.lawu.eshop.product.srv.mapper.extend;

import com.lawu.eshop.product.srv.domain.extend.ProductDOView;
import com.lawu.eshop.product.srv.domain.extend.ProductNumsView;

import java.util.List;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月20日 下午12:56:25
 *
 */
public interface ProductDOMapperExtend {

	void editTotalInventory(ProductNumsView view);

	void editTotalSaleVolume(ProductNumsView view);

	void editTotalFavorite(ProductNumsView view);
	
	List<ProductDOView> listProductByIds(List<Long> ids);
   
}