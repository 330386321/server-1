package com.lawu.eshop.product.srv.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lawu.eshop.product.query.ProductQuery;
import com.lawu.eshop.product.srv.bo.ProductBO;
import com.lawu.eshop.product.srv.bo.ProductModelBO;
import com.lawu.eshop.product.srv.converter.ProductConverter;
import com.lawu.eshop.product.srv.converter.ProductModelConverter;
import com.lawu.eshop.product.srv.converter.Utils;
import com.lawu.eshop.product.srv.domain.ProductDO;
import com.lawu.eshop.product.srv.domain.ProductDOExample;
import com.lawu.eshop.product.srv.domain.ProductImageDO;
import com.lawu.eshop.product.srv.domain.ProductImageDOExample;
import com.lawu.eshop.product.srv.domain.ProductModelDO;
import com.lawu.eshop.product.srv.domain.ProductModelDOExample;
import com.lawu.eshop.product.srv.mapper.ProductDOMapper;
import com.lawu.eshop.product.srv.mapper.ProductImageDOMapper;
import com.lawu.eshop.product.srv.mapper.ProductModelDOMapper;
import com.lawu.eshop.product.srv.service.ProductCategoryService;
import com.lawu.eshop.product.srv.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDOMapper productDOMapper;
	
	@Autowired
	private ProductModelDOMapper productModelDOMapper;
	
	@Autowired
	private ProductImageDOMapper productImageDOMapper;
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Override
	public List<ProductBO> getProductList(ProductQuery query) {
		ProductDOExample example = new ProductDOExample();
		example.createCriteria().andMerchantIdEqualTo(query.getMerchantId())
			.andNameLike(query.getName())
			.andStatusEqualTo(Utils.intToByte(query.getStatus()));
		List<ProductDO> productDOS = productDOMapper.selectByExample(example);
		List<ProductBO> productBOS = new ArrayList<ProductBO>();
		
		ProductModelDOExample modelExample = null;
		for(ProductDO productDO : productDOS){
			
			modelExample = new ProductModelDOExample();
			modelExample.createCriteria().andProductIdEqualTo(productDO.getId());
			//查询商品型号
			List<ProductModelDO> productModelDOS = productModelDOMapper.selectByExample(modelExample);
			List<ProductModelBO> ProductModelBOS = new ArrayList<ProductModelBO>();
			for(ProductModelDO productModelDO : productModelDOS){
				ProductModelBO productModelBO = ProductModelConverter.convertBO(productModelDO);
				ProductModelBOS.add(productModelBO);				
			}
			String specJson = JSON.toJSONString(ProductModelBOS);
			
			ProductBO productBO = ProductConverter.convertBO(productDO);
			productBO.setSpec(specJson);
			
			String category = productCategoryService.getFullName(productDO.getCategoryId());
			productBO.setCategory(category);
			
			ProductImageDOExample imageExample = new ProductImageDOExample();
			imageExample.createCriteria().andProductIdEqualTo(productDO.getId());
			List<ProductImageDO> imageDOS = productImageDOMapper.selectByExample(imageExample);
			List<String> images = new ArrayList<String>();
			for(ProductImageDO image : imageDOS){
				images.add(image.getImagePath());
			}
			String iamgesJson = JSON.toJSONString(images);
			productBO.setImagesUrl(iamgesJson);
			
			productBOS.add(productBO);
		}
		return productBOS;
	}

}
