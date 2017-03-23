package com.lawu.eshop.product.srv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.product.query.ProductQuery;
import com.lawu.eshop.product.srv.bo.ProductBO;
import com.lawu.eshop.product.srv.converter.Utils;
import com.lawu.eshop.product.srv.domain.ProductDO;
import com.lawu.eshop.product.srv.domain.ProductDOExample;
import com.lawu.eshop.product.srv.domain.ProductModelDO;
import com.lawu.eshop.product.srv.domain.ProductModelDOExample;
import com.lawu.eshop.product.srv.mapper.ProductDOMapper;
import com.lawu.eshop.product.srv.mapper.ProductModelDOMapper;
import com.lawu.eshop.product.srv.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDOMapper productDOMapper;
	
	@Autowired
	private ProductModelDOMapper productModelDOMapper;
	
	@Override
	public List<ProductBO> getProductList(ProductQuery query) {
		ProductDOExample example = new ProductDOExample();
		example.createCriteria().andMerchantIdEqualTo(query.getMerchantId())
			.andNameLike(query.getName())
			.andStatusEqualTo(Utils.intToByte(query.getStatus()));
		List<ProductDO> productDTOS = productDOMapper.selectByExample(example);
		
		ProductModelDOExample modelExample = null;
		for(ProductDO d : productDTOS){
			Long id = d.getId();
			modelExample = new ProductModelDOExample();
			modelExample.createCriteria().andProductIdEqualTo(id);
			List<ProductModelDO> productModelDO = productModelDOMapper.selectByExample(modelExample);
			
		}
		return null;
	}

}
