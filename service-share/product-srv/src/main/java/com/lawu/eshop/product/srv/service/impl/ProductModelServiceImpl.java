package com.lawu.eshop.product.srv.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.product.param.ProductModeUpdateInventoryParam;
import com.lawu.eshop.product.srv.bo.ShoppingCartProductModelBO;
import com.lawu.eshop.product.srv.converter.ShoppingCartProductModelConverter;
import com.lawu.eshop.product.srv.domain.ProductDO;
import com.lawu.eshop.product.srv.domain.ProductDOExample;
import com.lawu.eshop.product.srv.domain.ProductModelDO;
import com.lawu.eshop.product.srv.domain.ProductModelDOExample;
import com.lawu.eshop.product.srv.mapper.ProductDOMapper;
import com.lawu.eshop.product.srv.mapper.ProductModelDOMapper;
import com.lawu.eshop.product.srv.service.ProductModelService;

@Service
public class ProductModelServiceImpl implements ProductModelService {

	@Autowired
	private ProductDOMapper productDOMapper;
	
	@Autowired
	private ProductModelDOMapper productModelDOMapper;

	@Override
	public ShoppingCartProductModelBO getShoppingCartProductModel(Long id) {
		ProductModelDO productModelDO =  productModelDOMapper.selectByPrimaryKey(id);
		
		ProductDO productDO = null;
		if (productModelDO != null && productModelDO.getProductId() != null) {
			productDO = productDOMapper.selectByPrimaryKey(productModelDO.getProductId());
		}
		
		return ShoppingCartProductModelConverter.convert(productModelDO, productDO);
	}
	
	@Override
	public List<ShoppingCartProductModelBO> getShoppingCartProductModel(List<Long> ids) {
		
		ProductModelDOExample productModelDOExample = new ProductModelDOExample();
		productModelDOExample.createCriteria().andIdIn(ids);
		
		List<ProductModelDO> productModelDOS =  productModelDOMapper.selectByExample(productModelDOExample);
		
		// 剔除重复
		Set<Long> productIds = new HashSet<Long>();
		for (ProductModelDO productModelDO : productModelDOS) {
			productIds.add(productModelDO.getProductId());
		}
		
		ProductDOExample productDOExample = new ProductDOExample();
		productDOExample.createCriteria().andIdIn(new ArrayList<Long>(productIds));
		
		List<ProductDO> productDOs = productDOMapper.selectByExample(productDOExample);
		
		return ShoppingCartProductModelConverter.convert(productModelDOS, productDOs);
	}
	
	/**
	 * 批量更新商品库存
	 * 
	 * @param params 商品模型更新库存参数
	 * @return 受影响的行数
	 */
	@Transactional
	@Override
	public List<Integer> updateInventory(List<ProductModeUpdateInventoryParam> params) {
		List<Integer> rtn = new ArrayList<Integer>();
		
		for (ProductModeUpdateInventoryParam param : params) {
			ProductModelDO productModelDO = new ProductModelDO();
			productModelDO.setId(param.getId());
			Integer inventory = productModelDOMapper.selectByPrimaryKey(param.getId()).getInventory() - param.getQuantity();
			productModelDO.setInventory(inventory);
			rtn.add(productModelDOMapper.updateByPrimaryKeySelective(productModelDO));
		}
		
		return rtn;
	}
}
