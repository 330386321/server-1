package com.lawu.eshop.product.srv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.product.srv.bo.ShoppingCartProductModelBO;
import com.lawu.eshop.product.srv.converter.ShoppingCartProductModelConverter;
import com.lawu.eshop.product.srv.domain.ProductDO;
import com.lawu.eshop.product.srv.domain.ProductModelDO;
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
	
}
