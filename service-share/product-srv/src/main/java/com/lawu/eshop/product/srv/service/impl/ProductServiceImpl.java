package com.lawu.eshop.product.srv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.constant.ProductImgTypeEnum;
import com.lawu.eshop.product.constant.ProductModelInventoryTypeEnum;
import com.lawu.eshop.product.constant.ProductStatusEnum;
import com.lawu.eshop.product.param.EditProductDataParam;
import com.lawu.eshop.product.param.EditProductDataParam_bak;
import com.lawu.eshop.product.query.ProductDataQuery;
import com.lawu.eshop.product.srv.bo.ProductEditInfoBO;
import com.lawu.eshop.product.srv.bo.ProductInfoBO;
import com.lawu.eshop.product.srv.bo.ProductModelBO;
import com.lawu.eshop.product.srv.bo.ProductQueryBO;
import com.lawu.eshop.product.srv.converter.ProductConverter;
import com.lawu.eshop.product.srv.converter.ProductModelConverter;
import com.lawu.eshop.product.srv.domain.ProductDO;
import com.lawu.eshop.product.srv.domain.ProductDOExample;
import com.lawu.eshop.product.srv.domain.ProductDOExample.Criteria;
import com.lawu.eshop.product.srv.domain.ProductImageDO;
import com.lawu.eshop.product.srv.domain.ProductImageDOExample;
import com.lawu.eshop.product.srv.domain.ProductModelDO;
import com.lawu.eshop.product.srv.domain.ProductModelDOExample;
import com.lawu.eshop.product.srv.domain.ProductModelInventoryDO;
import com.lawu.eshop.product.srv.domain.extend.ProductNumsView;
import com.lawu.eshop.product.srv.mapper.ProductDOMapper;
import com.lawu.eshop.product.srv.mapper.ProductImageDOMapper;
import com.lawu.eshop.product.srv.mapper.ProductModelDOMapper;
import com.lawu.eshop.product.srv.mapper.ProductModelInventoryDOMapper;
import com.lawu.eshop.product.srv.mapper.extend.ProductDOMapperExtend;
import com.lawu.eshop.product.srv.service.ProductCategoryService;
import com.lawu.eshop.product.srv.service.ProductService;
import com.lawu.eshop.solr.SolrUtil;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDOMapper productDOMapper;

	@Autowired
	private ProductModelDOMapper productModelDOMapper;

	@Autowired
	private ProductModelInventoryDOMapper productModelInventoryDOMapper;

	@Autowired
	private ProductImageDOMapper productImageDOMapper;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private ProductDOMapperExtend productDOMapperExtend;

	@Autowired
	@Qualifier("delProductCommentTransactionMainServiceImpl")
	private TransactionMainService<Reply> delProductCommentTransactionMainServiceImpl;

	@Override
	public Page<ProductQueryBO> selectProduct(ProductDataQuery query) {
		ProductDOExample example = new ProductDOExample();
		Criteria criteria = example.createCriteria();
		criteria.andMerchantIdEqualTo(query.getMerchantId()).andStatusEqualTo(query.getProductStatus().val);
		if (query.getName() != null && !"".equals(query.getName())) {
			criteria.andNameLike("%" + query.getName() + "%");
		}
		example.setOrderByClause(query.getProductSortFieldEnum().val + " " + query.getOrderType());

		// 查询总数
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		Page<ProductQueryBO> page = new Page<>();
		page.setTotalCount(productDOMapper.countByExample(example));
		page.setCurrentPage(query.getCurrentPage());
		List<ProductDO> productDOS = productDOMapper.selectByExampleWithBLOBsWithRowbounds(example, rowBounds);

		List<ProductQueryBO> productBOS = new ArrayList<ProductQueryBO>();

		ProductModelDOExample modelExample = null;
		for (ProductDO productDO : productDOS) {

			String specJson = "";
			String category = "";
			if (!query.getIsApp()) {
				modelExample = new ProductModelDOExample();
				modelExample.createCriteria().andProductIdEqualTo(productDO.getId()).andStatusEqualTo(true);
				// 查询商品型号
				List<ProductModelDO> productModelDOS = productModelDOMapper.selectByExample(modelExample);
				List<ProductModelBO> ProductModelBOS = new ArrayList<ProductModelBO>();
				for (ProductModelDO productModelDO : productModelDOS) {
					ProductModelBO productModelBO = ProductModelConverter.convertBO(productModelDO);
					ProductModelBOS.add(productModelBO);
				}
				specJson = JSON.toJSONString(ProductModelBOS);
				category = productCategoryService.getFullName(productDO.getCategoryId());
			}
			ProductQueryBO productBO = ProductConverter.convertQueryBO(productDO);
			productBO.setSpec(specJson);

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
		for (int i = 0; i < idArray.length; i++) {
			examle.clear();
			ProductDO productDO = new ProductDO();
			productDO.setId(Long.valueOf(idArray[i]));
			productDO.setStatus(productStatus.val);
			productDO.setGmtModified(new Date());
			int row = productDOMapper.updateByPrimaryKeySelective(productDO);
			rows = rows + row;
		}
		if (rows == idArray.length) {
			if (productStatus.val == ProductStatusEnum.PRODUCT_STATUS_DEL.val
					|| productStatus.val == ProductStatusEnum.PRODUCT_STATUS_DOWN.val) {
				for (String id : idArray) {
					SolrUtil.delSolrDocsById(Long.valueOf(id), SolrUtil.SOLR_PRODUCT_CORE);
				}
			} else if (productStatus.val == ProductStatusEnum.PRODUCT_STATUS_UP.val) {
				for (String id : idArray) {
					ProductDO productDO = productDOMapper.selectByPrimaryKey(Long.valueOf(id));
					SolrInputDocument document = ProductConverter.convertSolrInputDocument(productDO);

					ProductModelDOExample example = new ProductModelDOExample();
					example.createCriteria().andProductIdEqualTo(productDO.getId()).andStatusEqualTo(true);
					List<ProductModelDO> productModelDOS = productModelDOMapper.selectByExample(example);
					int inventory = 0;
					int salesVolume = 0;
					double originalPrice = 0;
					double price = 0;
					int traverseCnt = 0;
					if (!productModelDOS.isEmpty()) {
						for (ProductModelDO productModelDO : productModelDOS) {
							if (traverseCnt == 0) {
								price = productModelDO.getPrice().doubleValue();
							}
							if (productModelDO.getOriginalPrice().doubleValue() > originalPrice) {
								originalPrice = productModelDO.getOriginalPrice().doubleValue();
							}
							if (productModelDO.getPrice().doubleValue() < price) {
								price = productModelDO.getPrice().doubleValue();
							}
							inventory += productModelDO.getInventory();
							salesVolume += productModelDO.getSalesVolume();
							traverseCnt++;
						}
					}
					document.addField("originalPrice_d", originalPrice);
					document.addField("price_d", price);
					document.addField("inventory_i", inventory);
					document.addField("salesVolume_i", salesVolume);
					SolrUtil.addSolrDocs(document, SolrUtil.SOLR_PRODUCT_CORE);
				}
			}
		}
		return rows;
	}

	@Override
	public ProductInfoBO selectProductById(Long id) {
		ProductDO productDO = productDOMapper.selectByPrimaryKey(id);
		if (productDO == null) {
			return null;
		}

		ProductInfoBO productInfoBO = ProductConverter.convertInfoBO(productDO);

		// 查询商品型号
		ProductModelDOExample modelExample = new ProductModelDOExample();
		modelExample.createCriteria().andProductIdEqualTo(productDO.getId()).andStatusEqualTo(true);
		List<ProductModelDO> productModelDOS = productModelDOMapper.selectByExample(modelExample);
		if (productModelDOS == null || productModelDOS.isEmpty()) {
			return null;
		}

		List<ProductModelBO> ProductModelBOS = new ArrayList<ProductModelBO>();
		Integer totalSales = 0;
		boolean rangePrice = true;
		if (productModelDOS.size() == 1) {
			rangePrice = false;
		}
		double max = productModelDOS.get(0).getPrice().doubleValue();
		double min = max;
		for (ProductModelDO productModelDO : productModelDOS) {
			ProductModelBO productModelBO = ProductModelConverter.convertBO(productModelDO);
			ProductModelBOS.add(productModelBO);

			Integer salesVolume = productModelDO.getSalesVolume();
			totalSales = totalSales + salesVolume;

			double price = 0;
			if (rangePrice) {
				price = productModelDO.getPrice().doubleValue();
				if (max < price) {
					max = price;
				}
				if (min > price) {
					min = price;
				}
			}
		}
		String specJson = JSON.toJSONString(ProductModelBOS);
		productInfoBO.setSpec(specJson);
		productInfoBO.setTotalSales(totalSales);
		productInfoBO.setPriceMax(String.valueOf(max));
		productInfoBO.setPriceMin(String.valueOf(min));

		// 查询商品图片
		ProductImageDOExample imageExample = new ProductImageDOExample();
		imageExample.createCriteria().andProductIdEqualTo(productDO.getId()).andStatusEqualTo(true);
		List<ProductImageDO> imageDOS = productImageDOMapper.selectByExample(imageExample);
		List<String> imagesHead = new ArrayList<String>();
		List<String> imagesDetail = new ArrayList<String>();
		for (ProductImageDO image : imageDOS) {
			if (image.getImgType().byteValue() == ProductImgTypeEnum.PRODUCT_IMG_HEAD.val.byteValue()) {
				imagesHead.add(image.getImagePath());
			} else if (image.getImgType().byteValue() == ProductImgTypeEnum.PRODUCT_IMG_DETAIL.val.byteValue()) {
				imagesDetail.add(image.getImagePath());
			}
		}
		String imagesHeadJson = JSON.toJSONString(imagesHead);
		String imagesDetailJson = JSON.toJSONString(imagesDetail);
		productInfoBO.setImagesHeadUrl(imagesHeadJson);
		productInfoBO.setImageDetailUrl(imagesDetailJson);

		return productInfoBO;
	}
	
	
	//备份方法，支持商品描述多张
//	@Override
//	public ProductEditInfoBO selectEditProductById(Long productId) {
//		ProductDO productDO = productDOMapper.selectByPrimaryKey(productId);
//		if (productDO == null) {
//			return null;
//		}
//
//		ProductEditInfoBO productEditInfoBO = ProductConverter.convertEditInfoBO(productDO);
//
//		// 查询商品型号
//		ProductModelDOExample modelExample = new ProductModelDOExample();
//		modelExample.createCriteria().andProductIdEqualTo(productDO.getId()).andStatusEqualTo(true);
//		List<ProductModelDO> productModelDOS = productModelDOMapper.selectByExample(modelExample);
//
//		List<ProductModelBO> ProductModelBOS = new ArrayList<ProductModelBO>();
//		for (ProductModelDO productModelDO : productModelDOS) {
//			ProductModelBO productModelBO = ProductModelConverter.convertBO(productModelDO);
//			ProductModelBOS.add(productModelBO);
//		}
//		String specJson = JSON.toJSONString(ProductModelBOS);
//		productEditInfoBO.setSpec(specJson);
//
//		// 查询滚动图片
//		ProductImageDOExample imageExample = new ProductImageDOExample();
//		imageExample.createCriteria().andProductIdEqualTo(productDO.getId())
//				.andImgTypeEqualTo(ProductImgTypeEnum.PRODUCT_IMG_HEAD.val).andStatusEqualTo(true);
//		List<ProductImageDO> imageDOS = productImageDOMapper.selectByExample(imageExample);
//		List<String> images = new ArrayList<String>();
//		for (ProductImageDO image : imageDOS) {
//			images.add(image.getImagePath());
//		}
//		String iamgesJson = JSON.toJSONString(images);
//		productEditInfoBO.setImagesUrl(iamgesJson);
//
//		// 查询详情图片
//		imageExample.clear();
//		imageExample.createCriteria().andProductIdEqualTo(productDO.getId())
//				.andImgTypeEqualTo(ProductImgTypeEnum.PRODUCT_IMG_DETAIL.val).andStatusEqualTo(true);
//		List<ProductImageDO> imageDetailDOS = productImageDOMapper.selectByExample(imageExample);
//
//		Map<String, List<String>> detailImageMap = new HashMap<String, List<String>>();
//		for (ProductImageDO image : imageDetailDOS) {
//			String key = ProductImagePrefix.productDetailImage + "-" + image.getSortid();
//			if (!detailImageMap.containsKey(key)) {
//				List<String> list = new ArrayList<String>();
//				list.add(image.getImagePath());
//				detailImageMap.put(key, list);
//			} else {
//				List<String> list = detailImageMap.get(key);
//				list.add(image.getImagePath());
//				detailImageMap.put(key, list);
//			}
//		}
//		String imageDetailUrl = JSON.toJSONString(detailImageMap);
//		productEditInfoBO.setImageDetailUrl(imageDetailUrl);
//
//		String category = productCategoryService.getFullName(productDO.getCategoryId());
//		productEditInfoBO.setCategoryName(category);
//
//		return productEditInfoBO;
//	}

	@Override
	public ProductEditInfoBO selectEditProductById(Long productId) {
		ProductDO productDO = productDOMapper.selectByPrimaryKey(productId);
		if (productDO == null) {
			return null;
		}

		ProductEditInfoBO productEditInfoBO = ProductConverter.convertEditInfoBO(productDO);

		// 查询商品型号
		ProductModelDOExample modelExample = new ProductModelDOExample();
		modelExample.createCriteria().andProductIdEqualTo(productDO.getId()).andStatusEqualTo(true);
		List<ProductModelDO> productModelDOS = productModelDOMapper.selectByExample(modelExample);

		List<ProductModelBO> ProductModelBOS = new ArrayList<ProductModelBO>();
		for (ProductModelDO productModelDO : productModelDOS) {
			ProductModelBO productModelBO = ProductModelConverter.convertBO(productModelDO);
			ProductModelBOS.add(productModelBO);
		}
		String specJson = JSON.toJSONString(ProductModelBOS);
		productEditInfoBO.setSpec(specJson);

		// 查询滚动图片
		ProductImageDOExample imageExample = new ProductImageDOExample();
		imageExample.createCriteria().andProductIdEqualTo(productDO.getId())
				.andImgTypeEqualTo(ProductImgTypeEnum.PRODUCT_IMG_HEAD.val).andStatusEqualTo(true);
		List<ProductImageDO> imageDOS = productImageDOMapper.selectByExample(imageExample);
		List<String> images = new ArrayList<String>();
		for (ProductImageDO image : imageDOS) {
			images.add(image.getImagePath());
		}
		String iamgesJson = JSON.toJSONString(images);
		productEditInfoBO.setImagesUrl(iamgesJson);

		// 查询详情图片
		imageExample.clear();
		imageExample.createCriteria().andProductIdEqualTo(productDO.getId())
				.andImgTypeEqualTo(ProductImgTypeEnum.PRODUCT_IMG_DETAIL.val).andStatusEqualTo(true);
		List<ProductImageDO> imageDetailDOS = productImageDOMapper.selectByExample(imageExample);
		List<String> imageDetails = new ArrayList<String>();
		for (ProductImageDO image : imageDetailDOS) {
			imageDetails.add(image.getImagePath());
		}
		String imageDetailsJson = JSON.toJSONString(imageDetails);
		productEditInfoBO.setImageDetailUrl(imageDetailsJson);

		String category = productCategoryService.getFullName(productDO.getCategoryId());
		productEditInfoBO.setCategoryName(category);

		return productEditInfoBO;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional
	public void eidtProduct_bak(EditProductDataParam_bak param) {

		Long productId = param.getProductId();
		int inventory = 0;
		int salesVolume = 0;
		double originalPrice = 0;
		double price = 0;
		int traverseCnt = 0;

		boolean isEdit = true;
		if (productId == 0L || productId == null || productId < 0) {
			// 保存商品信息
			ProductDO productDO = ProductConverter.convertDO(param, 0L);
			productDOMapper.insertSelective(productDO);
			productId = productDO.getId();
			isEdit = false;
		} else {
			// 修改商品基本信息
			ProductDO productDO = ProductConverter.convertDO(param, productId);
			ProductDOExample example = new ProductDOExample();
			example.createCriteria().andIdEqualTo(productId);
			productDOMapper.updateByExampleSelective(productDO, example);
		}

		String spec = param.getSpec();
		List<ProductModelBO> speclist = JSON.parseArray(spec, ProductModelBO.class);
		if (!isEdit) {
			ProductModelDO pmDO = null;
			for (ProductModelBO dataBO : speclist) {
				pmDO = new ProductModelDO();
				pmDO.setMerchantId(param.getMerchantId());
				pmDO.setProductId(productId);
				pmDO.setName(dataBO.getName());
				pmDO.setOriginalPrice(dataBO.getOriginalPrice());
				pmDO.setPrice(dataBO.getPrice());
				pmDO.setInventory(dataBO.getInventory());
				pmDO.setStatus(true);
				pmDO.setGmtCreate(new Date());
				pmDO.setGmtModified(new Date());
				productModelDOMapper.insertSelective(pmDO);

				if (traverseCnt == 0) {
					price = dataBO.getPrice().doubleValue();
				}
				if (dataBO.getOriginalPrice().doubleValue() > originalPrice) {
					originalPrice = dataBO.getOriginalPrice().doubleValue();
				}
				if (dataBO.getPrice().doubleValue() < price) {
					price = dataBO.getPrice().doubleValue();
				}
				inventory += dataBO.getInventory();
				traverseCnt++;
			}
		} else {
			for (ProductModelBO dataBO : speclist) {
				ProductModelDOExample modelExample = new ProductModelDOExample();
				modelExample.createCriteria().andIdEqualTo(Long.valueOf(dataBO.getId()));
				ProductModelDO modelDO = new ProductModelDO();

				modelDO.setOriginalPrice(dataBO.getOriginalPrice());
				modelDO.setPrice(dataBO.getPrice());
				modelDO.setInventory(dataBO.getInventory());
				modelDO.setName(dataBO.getName());
				modelDO.setGmtModified(new Date());
				productModelDOMapper.updateByExampleSelective(modelDO, modelExample);

				Integer inventoryTrans = dataBO.getInventoryTrans();
				if (dataBO.getInventory() != inventoryTrans) {
					ProductModelInventoryDO pmiDO = new ProductModelInventoryDO();
					pmiDO.setProductModelId(dataBO.getId());
					if (dataBO.getInventory() > inventoryTrans) {
						pmiDO.setQuantity(dataBO.getInventory() - inventoryTrans);
						pmiDO.setType(ProductModelInventoryTypeEnum.PLUS.getValue());
					} else {
						pmiDO.setQuantity(inventoryTrans - dataBO.getInventory());
						pmiDO.setType(ProductModelInventoryTypeEnum.MINUS.getValue());
					}
					pmiDO.setGmtCreate(new Date());
					pmiDO.setGmtModified(new Date());
					productModelInventoryDOMapper.insertSelective(pmiDO);
				}

				if (traverseCnt == 0) {
					price = dataBO.getPrice().doubleValue();
				}
				if (dataBO.getOriginalPrice().doubleValue() > originalPrice) {
					originalPrice = dataBO.getOriginalPrice().doubleValue();
				}
				if (dataBO.getPrice().doubleValue() < price) {
					price = dataBO.getPrice().doubleValue();
				}
				inventory += dataBO.getInventory();
				salesVolume += dataBO.getSalesVolume();
				traverseCnt++;
			}

		}

		// 删除商品型号信息
		if (param.getDeleteSpecIds() != null && !"".equals(param.getDeleteSpecIds())) {
			String deleteSpecIds = param.getDeleteSpecIds();
			List<String> specIdsList = Arrays.asList(deleteSpecIds.split(","));
			for(String specId : specIdsList){
				ProductModelDOExample modelExample = new ProductModelDOExample();
				modelExample.createCriteria().andIdEqualTo(Long.valueOf(specId));
				ProductModelDO modelDO = new ProductModelDO();
				modelDO.setStatus(false);
				modelDO.setGmtModified(new Date());
				productModelDOMapper.updateByExampleSelective(modelDO, modelExample);

				// 逻辑删除商品型号评价
				delProductCommentTransactionMainServiceImpl.sendNotice(Long.valueOf(specId));
			}
		}

		// 修改商品库存、销量、最高价、最低价
		ProductDO productDO = new ProductDO();
		productDO.setTotalInventory(inventory);
		productDO.setTotalSalesVolume(salesVolume);
		productDO.setMinPrice(new BigDecimal(price));
		productDO.setMaxPrice(new BigDecimal(originalPrice));
		ProductDOExample example = new ProductDOExample();
		example.createCriteria().andIdEqualTo(productId);
		productDOMapper.updateByExampleSelective(productDO, example);

		if (isEdit) {
			// 删除产品图片
			ProductImageDOExample imageExample = new ProductImageDOExample();
			imageExample.createCriteria().andProductIdEqualTo(productId);
			ProductImageDO imageDO = new ProductImageDO();
			imageDO.setStatus(false);
			imageDO.setGmtModified(new Date());
			productImageDOMapper.updateByExampleSelective(imageDO, imageExample);
		}

		// 保存商品滚动图片信息
		ProductImageDO pcDO = null;
		String imageUrl = param.getProductImages();
		String[] imageUrls = imageUrl.split(",");
		for (int i = 0; i < imageUrls.length; i++) {
			pcDO = new ProductImageDO();
			pcDO.setProductId(Long.valueOf(productId));
			pcDO.setImagePath(imageUrls[i]);
			pcDO.setGmtCreate(new Date());
			pcDO.setGmtModified(new Date());
			pcDO.setSortid(0);
			pcDO.setStatus(true);
			pcDO.setImgType(ProductImgTypeEnum.PRODUCT_IMG_HEAD.val);
			productImageDOMapper.insert(pcDO);
		}

		// 保存商品详情图片
		Map<String, List<String>> detailImageMap = param.getDetailImageMap();
		Iterator itr = detailImageMap.keySet().iterator();
		while (itr.hasNext()) {
			String key = itr.next().toString();
			Object obj = detailImageMap.get(key);
			List<String> images = (List<String>) obj;
			for (String s1 : images) {
				pcDO = new ProductImageDO();
				pcDO.setProductId(Long.valueOf(productId));
				pcDO.setImagePath(s1);
				pcDO.setGmtCreate(new Date());
				pcDO.setGmtModified(new Date());
				pcDO.setStatus(true);
				pcDO.setSortid(Integer.valueOf(key.substring(key.lastIndexOf("-") + 1)));
				pcDO.setImgType(ProductImgTypeEnum.PRODUCT_IMG_DETAIL.val);
				productImageDOMapper.insert(pcDO);
			}
		}

		SolrInputDocument document = ProductConverter.convertSolrInputDocument(productId, param);
		document.addField("originalPrice_d", originalPrice);
		document.addField("price_d", price);
		document.addField("inventory_i", inventory);
		document.addField("salesVolume_i", salesVolume);
		SolrUtil.addSolrDocs(document, SolrUtil.SOLR_PRODUCT_CORE);
	}
	
	
	@Override
	@Transactional
	public void eidtProduct(EditProductDataParam param) {

		Long productId = param.getProductId();
		int inventory = 0;
		int salesVolume = 0;
		double originalPrice = 0;
		double price = 0;
		int traverseCnt = 0;

		boolean isEdit = true;
		if (productId == 0L || productId == null || productId < 0) {
			// 保存商品信息
			ProductDO productDO = ProductConverter.convertDO(param, 0L);
			productDOMapper.insertSelective(productDO);
			productId = productDO.getId();
			isEdit = false;
		} else {
			// 修改商品基本信息
			ProductDO productDO = ProductConverter.convertDO(param, productId);
			ProductDOExample example = new ProductDOExample();
			example.createCriteria().andIdEqualTo(productId);
			productDOMapper.updateByExampleSelective(productDO, example);
		}

		String spec = param.getSpec();
		List<ProductModelBO> speclist = JSON.parseArray(spec, ProductModelBO.class);
		if (!isEdit) {
			ProductModelDO pmDO = null;
			for (ProductModelBO dataBO : speclist) {
				pmDO = new ProductModelDO();
				pmDO.setMerchantId(param.getMerchantId());
				pmDO.setProductId(productId);
				pmDO.setName(dataBO.getName());
				pmDO.setOriginalPrice(dataBO.getOriginalPrice());
				pmDO.setPrice(dataBO.getPrice());
				pmDO.setInventory(dataBO.getInventory());
				pmDO.setStatus(true);
				pmDO.setGmtCreate(new Date());
				pmDO.setGmtModified(new Date());
				productModelDOMapper.insertSelective(pmDO);

				if (traverseCnt == 0) {
					price = dataBO.getPrice().doubleValue();
				}
				if (dataBO.getOriginalPrice().doubleValue() > originalPrice) {
					originalPrice = dataBO.getOriginalPrice().doubleValue();
				}
				if (dataBO.getPrice().doubleValue() < price) {
					price = dataBO.getPrice().doubleValue();
				}
				inventory += dataBO.getInventory();
				traverseCnt++;
			}
		} else {
			for (ProductModelBO dataBO : speclist) {
				ProductModelDOExample modelExample = new ProductModelDOExample();
				modelExample.createCriteria().andIdEqualTo(Long.valueOf(dataBO.getId()));
				ProductModelDO modelDO = new ProductModelDO();

				modelDO.setOriginalPrice(dataBO.getOriginalPrice());
				modelDO.setPrice(dataBO.getPrice());
				modelDO.setInventory(dataBO.getInventory());
				modelDO.setName(dataBO.getName());
				modelDO.setGmtModified(new Date());
				productModelDOMapper.updateByExampleSelective(modelDO, modelExample);

				Integer inventoryTrans = dataBO.getInventoryTrans();
				if (dataBO.getInventory() != inventoryTrans) {
					ProductModelInventoryDO pmiDO = new ProductModelInventoryDO();
					pmiDO.setProductModelId(dataBO.getId());
					if (dataBO.getInventory() > inventoryTrans) {
						pmiDO.setQuantity(dataBO.getInventory() - inventoryTrans);
						pmiDO.setType(ProductModelInventoryTypeEnum.PLUS.getValue());
					} else {
						pmiDO.setQuantity(inventoryTrans - dataBO.getInventory());
						pmiDO.setType(ProductModelInventoryTypeEnum.MINUS.getValue());
					}
					pmiDO.setGmtCreate(new Date());
					pmiDO.setGmtModified(new Date());
					productModelInventoryDOMapper.insertSelective(pmiDO);
				}

				if (traverseCnt == 0) {
					price = dataBO.getPrice().doubleValue();
				}
				if (dataBO.getOriginalPrice().doubleValue() > originalPrice) {
					originalPrice = dataBO.getOriginalPrice().doubleValue();
				}
				if (dataBO.getPrice().doubleValue() < price) {
					price = dataBO.getPrice().doubleValue();
				}
				inventory += dataBO.getInventory();
				salesVolume += dataBO.getSalesVolume();
				traverseCnt++;
			}

		}

		// 删除商品型号信息
		if (param.getDeleteSpecIds() != null && !"".equals(param.getDeleteSpecIds())) {
			String deleteSpecIds = param.getDeleteSpecIds();
			List<String> specIdsList = Arrays.asList(deleteSpecIds.split(","));
			for(String specId : specIdsList){
				ProductModelDOExample modelExample = new ProductModelDOExample();
				modelExample.createCriteria().andIdEqualTo(Long.valueOf(specId));
				ProductModelDO modelDO = new ProductModelDO();
				modelDO.setStatus(false);
				modelDO.setGmtModified(new Date());
				productModelDOMapper.updateByExampleSelective(modelDO, modelExample);

				// 逻辑删除商品型号评价
				delProductCommentTransactionMainServiceImpl.sendNotice(Long.valueOf(specId));
			}
		}

		// 修改商品库存、销量、最高价、最低价
		ProductDO productDO = new ProductDO();
		productDO.setTotalInventory(inventory);
		productDO.setTotalSalesVolume(salesVolume);
		productDO.setMinPrice(new BigDecimal(price));
		productDO.setMaxPrice(new BigDecimal(originalPrice));
		ProductDOExample example = new ProductDOExample();
		example.createCriteria().andIdEqualTo(productId);
		productDOMapper.updateByExampleSelective(productDO, example);

		if (isEdit) {
			// 删除产品图片
			ProductImageDOExample imageExample = new ProductImageDOExample();
			imageExample.createCriteria().andProductIdEqualTo(productId);
			ProductImageDO imageDO = new ProductImageDO();
			imageDO.setStatus(false);
			imageDO.setGmtModified(new Date());
			productImageDOMapper.updateByExampleSelective(imageDO, imageExample);
		}

		// 保存商品滚动图片信息
		ProductImageDO pcDO = null;
		String imageUrl = param.getProductImages();
		String[] imageUrls = imageUrl.split(",");
		for (int i = 0; i < imageUrls.length; i++) {
			pcDO = new ProductImageDO();
			pcDO.setProductId(Long.valueOf(productId));
			pcDO.setImagePath(imageUrls[i]);
			pcDO.setGmtCreate(new Date());
			pcDO.setGmtModified(new Date());
			pcDO.setSortid(0);
			pcDO.setStatus(true);
			pcDO.setImgType(ProductImgTypeEnum.PRODUCT_IMG_HEAD.val);
			productImageDOMapper.insert(pcDO);
		}

		// 保存商品详情图片
		String detaiImageUrl = param.getDetailImages();
		String[] detaiImageUrls = detaiImageUrl.split(",");
		for (int i = 0; i < detaiImageUrls.length; i++) {
			pcDO = new ProductImageDO();
			pcDO.setProductId(Long.valueOf(productId));
			pcDO.setImagePath(imageUrls[i]);
			pcDO.setGmtCreate(new Date());
			pcDO.setGmtModified(new Date());
			pcDO.setStatus(true);
			pcDO.setSortid(i + 1);
			pcDO.setImgType(ProductImgTypeEnum.PRODUCT_IMG_DETAIL.val);
			productImageDOMapper.insert(pcDO);
		}

		SolrInputDocument document = ProductConverter.convertSolrInputDocument(productId, param);
		document.addField("originalPrice_d", originalPrice);
		document.addField("price_d", price);
		document.addField("inventory_i", inventory);
		document.addField("salesVolume_i", salesVolume);
		SolrUtil.addSolrDocs(document, SolrUtil.SOLR_PRODUCT_CORE);
	}
	

	@Override
	public void editTotalInventory(Long productId, int num, String flag) {
		ProductNumsView view = new ProductNumsView();
		view.setProductId(productId);
		view.setFlag(flag);
		view.setNum(num);
		view.setGmtModified(new Date());
		productDOMapperExtend.editTotalInventory(view);
	}

	@Override
	public void editTotalSaleVolume(Long productId, int num, String flag) {
		ProductNumsView view = new ProductNumsView();
		view.setProductId(productId);
		view.setFlag(flag);
		view.setNum(num);
		view.setGmtModified(new Date());
		productDOMapperExtend.editTotalSaleVolume(view);

	}

	@Override
	public void editTotalFavorite(Long productId, int num, String flag) {
		ProductNumsView view = new ProductNumsView();
		view.setProductId(productId);
		view.setFlag(flag);
		view.setNum(num);
		view.setGmtModified(new Date());
		productDOMapperExtend.editTotalFavorite(view);

	}

}
