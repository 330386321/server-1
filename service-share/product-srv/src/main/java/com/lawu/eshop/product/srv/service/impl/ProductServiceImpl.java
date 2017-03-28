package com.lawu.eshop.product.srv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.param.EditDataProductParam;
import com.lawu.eshop.product.query.ProductDataQuery;
import com.lawu.eshop.product.srv.bo.ProductCategoryDataBO;
import com.lawu.eshop.product.srv.bo.ProductEditInfoBO;
import com.lawu.eshop.product.srv.bo.ProductInfoBO;
import com.lawu.eshop.product.srv.bo.ProductModelBO;
import com.lawu.eshop.product.srv.bo.ProductQueryBO;
import com.lawu.eshop.product.srv.converter.ProductConverter;
import com.lawu.eshop.product.srv.converter.ProductModelConverter;
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
	public Page<ProductQueryBO> selectProduct(ProductDataQuery query) {
		ProductDOExample example = new ProductDOExample();
		example.createCriteria().andMerchantIdEqualTo(query.getMerchantId())
			.andNameLike("%" + query.getName() + "%")
			.andStatusEqualTo(query.getStatus().val);
		
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
	public int updateProductStatus(String ids, ProductStatusEnum productStatus) {
		int rows = 0;
		String idArray[] = ids.split(",");
		ProductDOExample examle = new ProductDOExample();
		for(int i = 0 ; i < idArray.length ; i++){
			examle.clear();
			ProductDO productDO = new ProductDO();
			productDO.setId(Long.valueOf(idArray[i]));
			productDO.setStatus(productStatus.val);
			productDO.setGmtModified(new Date());
			int row = productDOMapper.updateByPrimaryKeySelective(productDO);
			rows = rows + row;
		}
		return rows;
	}

	@Override
	public ProductInfoBO selectProductById(Long id) {
		ProductDO productDO = productDOMapper.selectByPrimaryKey(id);
		if(productDO == null){
			return null;
		}
		
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
	
	@Override
	public ProductEditInfoBO selectEditProductById(Long productId) {
		ProductDO productDO = productDOMapper.selectByPrimaryKey(productId);
		if(productDO == null){
			return null;
		}
		
		ProductEditInfoBO productEditInfoBO = ProductConverter.convertEditInfoBO(productDO);
		
		//查询商品型号
		ProductModelDOExample modelExample = new ProductModelDOExample();
		modelExample.createCriteria().andProductIdEqualTo(productDO.getId());
		List<ProductModelDO> productModelDOS = productModelDOMapper.selectByExample(modelExample);
		
		List<ProductModelBO> ProductModelBOS = new ArrayList<ProductModelBO>();
		for(ProductModelDO productModelDO : productModelDOS){
			ProductModelBO productModelBO = ProductModelConverter.convertBO(productModelDO);
			ProductModelBOS.add(productModelBO);
		}
		String specJson = JSON.toJSONString(ProductModelBOS);
		productEditInfoBO.setSpec(specJson);
		
		//查询型号图片
		ProductImageDOExample imageExample = new ProductImageDOExample();
		imageExample.createCriteria().andProductIdEqualTo(productDO.getId());
		List<ProductImageDO> imageDOS = productImageDOMapper.selectByExample(imageExample);
		List<String> images = new ArrayList<String>();
		for(ProductImageDO image : imageDOS){
			images.add(image.getImagePath());
		}
		String iamgesJson = JSON.toJSONString(images);
		productEditInfoBO.setImagesUrl(iamgesJson);
		
		return productEditInfoBO;
	}

	@Override
	@Transactional
	public void saveProduct(EditDataProductParam product) {
		
		//保存商品信息
		ProductDO productDO = ProductConverter.convertDO(product,0L);
		int productId = productDOMapper.insert(productDO);
		
		//保存商品型号信息
		String spec = product.getSpec();
		List<ProductCategoryDataBO> speclist = JSON.parseArray(spec, ProductCategoryDataBO.class);
		ProductModelDO pmDO = null;
		for(ProductCategoryDataBO dataBO : speclist){
			pmDO = new ProductModelDO();
			pmDO.setMerchantId(product.getMerchantId());
			pmDO.setProductId(Long.valueOf(productId));
			pmDO.setName(dataBO.getName());
			pmDO.setOriginalPrice(new BigDecimal(dataBO.getOriginalPrice()));
			pmDO.setPrice(new BigDecimal(dataBO.getPrice()));
			pmDO.setInventory(Integer.valueOf(dataBO.getInventory()));
			pmDO.setGmtCreate(new Date());
			pmDO.setGmtModified(new Date());
			productModelDOMapper.insertSelective(pmDO);
		}
		
		//保存商品图片信息
		ProductImageDO pcDO = null;
		String imageUrl = product.getImageUrl();
		String []imageUrls = imageUrl.split(",");
		for(int i = 0 ; i < imageUrls.length ; i++){
			pcDO = new ProductImageDO();
			pcDO.setProductId(Long.valueOf(productId));
			pcDO.setImagePath(imageUrls[i]);
			pcDO.setGmtCreate(new Date());
			pcDO.setGmtModified(new Date());
			productImageDOMapper.insert(pcDO);
		}
		
		
	}

	@Override
	@Transactional
	public void updateProductById(Long id, EditDataProductParam product) {
		
		//修改商品基本信息
		ProductDO productDO = ProductConverter.convertDO(product,id);
		productDO.setId(id);
		productDOMapper.updateByPrimaryKey(productDO);
		
		
		
	}

	
}
