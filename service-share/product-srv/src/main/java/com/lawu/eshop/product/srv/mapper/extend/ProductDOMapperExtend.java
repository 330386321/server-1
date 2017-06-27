package com.lawu.eshop.product.srv.mapper.extend;

import com.lawu.eshop.product.srv.domain.extend.ProductDOEditView;
import com.lawu.eshop.product.srv.domain.extend.ProductDOView;
import com.lawu.eshop.product.param.ListShoppingProductParam;
import com.lawu.eshop.product.srv.domain.extend.ProductNumsView;
import com.lawu.eshop.product.srv.domain.extend.ShoppingProductDOView;

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

	List<ShoppingProductDOView> listHotProduct(ListShoppingProductParam listShoppingProductParam);

	List<ShoppingProductDOView> listAllProduct(ListShoppingProductParam listShoppingProductParam);

	List<ShoppingProductDOView> listNewProduct(ListShoppingProductParam listShoppingProductParam);

	List<ProductDOView> listProductByIds(List<Long> ids);

	/**
	 * 修改商品价格库存销量
	 * @param productDO
	 * @author yangqh
	 * @date 2017年6月27日 下午2:34:16
	 */
	void updateByExampleSelective(ProductDOEditView productDO);
   
}