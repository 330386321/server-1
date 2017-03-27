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
import com.lawu.eshop.product.srv.bo.ProductInfoBO;
import com.lawu.eshop.product.srv.bo.ProductModelBO;
import com.lawu.eshop.product.srv.bo.ProductQueryBO;
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
	public Page<ProductQueryBO> selectProduct(ProductQuery query) {
		ProductDOExample example = new ProductDOExample();
		example.createCriteria().andMerchantIdEqualTo(query.getMerchantId())
			.andNameLike("%" + query.getName() + "%")
			.andStatusEqualTo(Utils.intToByte(query.getStatus()));
		
		//查询总数
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		Page<ProductQueryBO> page = new Page<>();
		page.setTotalCount(productDOMapper.countByExample(example));
		page.setCurrentPage(query.getCurrentPage());
		List<ProductDO> productDOS = productDOMapper.selectByExampleWithBLOBsWithRowbounds(example, rowBounds);
		
		List<ProductQueryBO> productBOS = new ArrayList<ProductQueryBO>();
		
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
			
			ProductQueryBO productBO = ProductConverter.convertQueryBO(productDO);
			productBO.setSpec(specJson);
			
			String category = productCategoryService.getFullName(productDO.getCategoryId());
			productBO.setCategory(category);
			
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

	@Override
	public ProductInfoBO selectProductById(Long id) {
		ProductDO productDO = productDOMapper.selectByPrimaryKey(id);
		ProductInfoBO productInfoBO = ProductConverter.convertInfoBO(productDO);
		
		//查询商品型号
		ProductModelDOExample modelExample = new ProductModelDOExample();
		modelExample.createCriteria().andProductIdEqualTo(productDO.getId());
		List<ProductModelDO> productModelDOS = productModelDOMapper.selectByExample(modelExample);
		
		List<ProductModelBO> ProductModelBOS = new ArrayList<ProductModelBO>();
		Integer totalSales = 0;
		boolean rangePrice = true;
		if(productModelDOS.size() == 1){
			rangePrice = false;
		}
		double max = productModelDOS.get(0).getPrice().doubleValue();
		double min = max;
		for(ProductModelDO productModelDO : productModelDOS){
			ProductModelBO productModelBO = ProductModelConverter.convertBO(productModelDO);
			ProductModelBOS.add(productModelBO);
			
			Integer salesVolume = productModelDO.getSalesVolume();
			totalSales = totalSales + salesVolume;
			
			double price = 0;
			if(rangePrice){
				price = productModelDO.getPrice().doubleValue();
				if(max < price){
					max = price;
				}
				if(min > price){
					min = price;
				}
			}
		}
		String specJson = JSON.toJSONString(ProductModelBOS);
		productInfoBO.setSpec(specJson);
		productInfoBO.setTotalSales(totalSales);
		productInfoBO.setPriceMax(String.valueOf(max));
		productInfoBO.setPriceMin(String.valueOf(min));	
		
		//查询型号图片
		ProductImageDOExample imageExample = new ProductImageDOExample();
		imageExample.createCriteria().andProductIdEqualTo(productDO.getId());
		List<ProductImageDO> imageDOS = productImageDOMapper.selectByExample(imageExample);
		List<String> images = new ArrayList<String>();
		for(ProductImageDO image : imageDOS){
			images.add(image.getImagePath());
		}
		String iamgesJson = JSON.toJSONString(images);
		productInfoBO.setImagesUrl(iamgesJson);
		
		return productInfoBO;
	}

}
