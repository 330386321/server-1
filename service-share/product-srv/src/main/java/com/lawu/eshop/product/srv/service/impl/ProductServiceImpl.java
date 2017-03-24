package com.lawu.eshop.product.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.lawu.eshop.framework.core.page.Page;
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
	public Page<ProductBO> selectProduct(ProductQuery query) {
		ProductDOExample example = new ProductDOExample();
		example.createCriteria().andMerchantIdEqualTo(query.getMerchantId())
			.andNameLike("%" + query.getName() + "%")
			.andStatusEqualTo(Utils.intToByte(query.getStatus()));
		
		//查询总数
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		Page<ProductBO> page = new Page<>();
		page.setTotalCount(productDOMapper.countByExample(example));
		page.setCurrentPage(query.getCurrentPage());
		List<ProductDO> productDOS = productDOMapper.selectByExampleWithBLOBsWithRowbounds(example, rowBounds);
		
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
		page.setRecords(productBOS);
		
		return page;
	}

	@Override
	@Transactional
	public int updateProductStatus(String ids, Integer status) {
		int rows = 0;
		String idArray[] = ids.split(",");
		ProductDOExample examle = new ProductDOExample();
		for(int i = 0 ; i < idArray.length ; i++){
			examle.clear();
			ProductDO productDO = new ProductDO();
			productDO.setId(Long.valueOf(idArray[i]));
			productDO.setStatus(Utils.intToByte(status));
			productDO.setGmtModified(new Date());
			int row = productDOMapper.updateByPrimaryKeySelective(productDO);
			rows = rows + row;
		}
		return rows;
	}

}
